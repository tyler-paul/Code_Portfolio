/*
 * Course: CS 59000-06 Computer Architecture
 * Tyler Paul
 * Branch Prediction Project
 *
 * The PHT class implements a pattern history table.
 * The table has numEntries entries (indexed by a BHT)
 * and each entry has 2 pattern bits.
 * Pattern 0 indicates strongly not taken
 * Pattern 1 indicates weakly not taken
 * Pattern 2 indicates weakly taken
 * Pattern 3 indicates strongly taken
 * (Note: a byte of memory  is actually used for each entry rather than 2 bits,
 *  since a byte is the smallest unit of memory available as a Java primitive type)
 *
 */

public class PHT {
   int numEntries;
   byte[] patternBits;

   //constructor
   /* Each patternBits BitSet will be initialized to the weak taken
    * (state 2) state.
    */
   public PHT(int numEntries) {
      this.numEntries = numEntries;
      patternBits = new byte[numEntries];
      for (int i = 0; i < numEntries; i++) {
         patternBits[i] = 2;
      }
   }

   //get methods
   public byte getPHBits(int index) {
      return patternBits[index];
   }
   //set methods
   public void setPHBits(int index, byte value) {
      patternBits[index] = value;
   }
   //print entries (used for debugging)
   public void print() {
      for (int i = 0; i < numEntries; i++) {
         System.out.println("PHTEntry["+i+"]= " + patternBits[i]);
      }
   }
}