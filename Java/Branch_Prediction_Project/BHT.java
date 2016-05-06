/*
 * Course: CS 59000-06 Computer Architecture
 * Tyler Paul
 * Branch Prediction Project
 *
 * The BHT class implements a branch history table. Each entry in the BHT
 * has the following format:
 *
 *           tag bits                  HR bits
 *  |............................|....................|
 *   ^                            ^                  ^
 *   numTagBits+numHRBits-1       numHRBits-1        0
 *
 * In particular the history register contents for the i'th entry is
 * entries[i][numHRBits-1 ... 0].
 * The tag bits are in entries[i][numHRBits ... numTagBits+numHRBits-1]
 *
 * A BHT can be constructed for either a LRU replacement policy or a random
 * replacement policy depending on if the lruReplaceEnable flag is set in
 * the constructor.
 *
 * To add a branch instruction to the BHT call cacheAddress(address). If address
 * is already in the BHT, then the nothings happens. Otherwise the entry is added
 * according to a specified replacement policy. In either case the index of the
 * branch instruction entry in the BHT is returned.
 *
 * To update the history of a BHT entry call updateHistory(index, wasTaken) to
 * shift the results of the latest branch result in the history register for that entry.
 */

import java.lang.Math;
import java.util.BitSet;

public class BHT {
   //instance variables
   int numEntries;           //number of entries in the BHT
   int numAddressBits;       //number of bits in an address (commonly 32)
   int numIndexBits;         //number of index bits needed to index into the BHT
   int numTagBits;           //number of tag bits for each BHT entry
   int numHRBits;            //number of history register bits for each BHT entry
   int associativity;        //BHT is to be associativity-way associative
   int numSets;              //number of sets for the associative cache
   boolean[][] entries;      //data structure to hold the bits in the BHT
   LRUQueue[] lruQueues;     //least recently used elements of the cache
   int cacheHits;            //counter to keep track of total cache hits
   int cacheMisses;          //counter to keep track of total cache misses
   boolean lruReplaceEnable; //if enabled LRU replacement is used, otherwise random replacement is used

   //constructor
   public BHT(int numEntries, int numAddressBits, int numHRBits, int associativity, boolean lruReplaceEnable) {
      this.numEntries = numEntries;
      this.numAddressBits = numAddressBits;
      this.numHRBits = numHRBits;
      this.associativity = associativity;
      this.lruReplaceEnable = lruReplaceEnable;
      numIndexBits = (int)(Math.log(numEntries)/Math.log(2)); //numIndexBits = lg(numEntries)
      numTagBits = numAddressBits - numIndexBits + (int)(Math.log(associativity)/Math.log(2));
      //Initialize the entries of the BHT. These choices are more or less arbitrary.
      entries = new boolean[numEntries][numTagBits + numHRBits];
      for (int i = 0; i < numEntries; i++) {
         for (int j = 0; j < numTagBits + numHRBits; j++) {
            entries[i][j] = true;
         }
      }
      numSets = numEntries/associativity;
      //initialize lru array if lru enabled
      if (lruReplaceEnable) {
         lruQueues = new LRUQueue[numSets];
         for (int i = 0; i < numSets; i++) {
            lruQueues[i] = new LRUQueue(associativity);
         }
      }
      cacheHits = 0;
      cacheMisses = 0;
   }

   /* getTagBitsValue returns the integer value of the tag bits.
    */
   public int getTagBitsValue(int index) {
      int val = 0;
      for (int i = numHRBits; i <= numHRBits + numTagBits - 1; i++) {
         if (entries[index][i] == true)
            val += (int)Math.pow(2, i - numHRBits);
      }
      return val;
   }

   /* setTagBits sets the tag bits at an index in the BHT.
    */
   public void setTagBits(int index, int tag) {
      for (int i = numHRBits; i <= numHRBits + numTagBits - 1; i++) {
         if (tag % 2 == 1)
            entries[index][i] = true;
         else
            entries[index][i] = false;
         tag = tag/2;
      }
   }

   /* setHRBits sets the HR bits at an index in the BHT.
    */
   public void setHRBits(int index, int tag) {
      for (int i = 0; i < numHRBits; i++) {
         if (tag % 2 == 1)
            entries[index][i] = true;
         else
            entries[index][i] = false;
         tag = tag/2;
      }
   }

   /* getHistoryBitsValue returns the integer value of the history bits.
    */
   public int getHistoryBitsValue(int index) {
      int val = 0;
      for (int i = 0; i <= numHRBits - 1; i++) {
         if (entries[index][i] == true)
            val += (int)Math.pow(2, i);
      }
      return val;
   }

   /* setOfAddress returns the set number of the location of the set-associative BHT entry which
    * should correspond to the address of a branch instruction.
    * This method returns the numIndexBits - lg(associativity) rightmost bits of the address
    * which corresponds to the set in the BHT
    */
   public int setOfAddress(long address) {
      int j = (int)(Math.log(associativity)/Math.log(2));
      int set = ((int)(address & ((1 << (numIndexBits - j)) - 1)));
      return set;
   }

   /* tagOfAddress returns the tag of the location of the BHT entry which should correspond
    * to the address of a branch instruction.
    * This method returns the leftmost bits of the address after shifting right
    * numIndexBits - lg(associativity) bits. This corresponds to the tag of the address.
    */
   public int tagOfAddress(long address) {
      int j = (int)(Math.log(associativity)/Math.log(2));
      int tag = (int)(address >>> (numIndexBits - j));
      return tag;
   }

   /* cacheAddress caches the branch instruction at a specified address into the BHT.
    * If there is a cache hit, then the number of the entry in the cache is returned.
    * If there is a cache miss, then the branch instruction is added to the cache
    * and the number of the entry in the cache is returned.
    */
   public int cacheAddress(long address) {
      int tag = tagOfAddress(address);
      int setNum = setOfAddress(address);
      int offset;
      int index = -1;
      boolean hit = false;
      for (offset = 0; offset < associativity; offset++) {
         if (tag == getTagBitsValue(associativity * setNum + offset)){ //cache hit
            hit = true;
            cacheHits++;
            index = associativity * setNum + offset;
            if (lruReplaceEnable)
               lruQueues[setNum].update(offset); //update LRU
            break;
         }
      }
      if (hit == false) { //replace an entry with current branch instruction on cache misses
         cacheMisses++;
         //choose new entry based on replacement policy
         if (lruReplaceEnable) {
            offset = lruQueues[setNum].dequeue();
            lruQueues[setNum].update(offset);
         }
         else
            offset = (int)(associativity * Math.random());
         index = associativity * setNum + offset; //index of the BHT entry
         setTagBits(index, tag); //replace entry in the BHT
      }
      return index;
   }

   /* updateHistory updates the history register at a given index of the BHT. wasTaken
    * is shifted into rightmost bit of the history register.
    */
   public void updateHistory(int index, boolean wasTaken) {
      //shift HR left
      for (int i = numHRBits - 1; i > 0; i--)
         entries[index][i] = entries[index][i - 1];
      entries[index][0] = wasTaken;

   }

   /* print the contents of the BHT to standard output (useful for debugging/testing)
    */
   public void printBHT() {
      for (int i = 0; i < numEntries; i++) {
         System.out.println("index = " + i);
         //System.out.println(entries[i].length());
         for (int j = entries[i].length - 1; j >= 0; j--) {
            if (entries[i][j] == true) //check if jth bit is set
               System.out.print("1");
            else
               System.out.print("0");
         }
         System.out.println(""); //new line
      }
   }

   /* printCacheStats prints the cache hits count, cache miss count, and hit percentage
    */
   public void printCacheStats() {
      System.out.println("CACHE HITS         : " + cacheHits);
      System.out.println("CACHE MISSES       : " + cacheMisses);
      System.out.println("HIT PERCENTAGE     : " + ((double)cacheHits)/(cacheHits + cacheMisses));
   }

   //testing
   public static void main(String[] args) {
      BHT bht = new BHT(4, 6, 3, 2, false);
      bht.cacheAddress(8);
      System.out.println(bht.tagOfAddress(8));
      bht.printBHT();
      bht.cacheAddress(25);
      System.out.println(bht.tagOfAddress(25));
      bht.printBHT();
      bht.cacheAddress(6);
      System.out.println(bht.tagOfAddress(6));
      bht.printBHT();
      bht.cacheAddress(35);
      System.out.println(bht.tagOfAddress(35));
      bht.printBHT();

      //bht.updateHistory(3, false);
      //bht.printBHT();

      //bht.cacheAddress(12);
      //bht.cacheAddress(29);
      //bht.cacheAddress(14);
      //bht.cacheAddress(25);
      //bht.cacheAddress(39);
      //bht.printBHT();
   }

}