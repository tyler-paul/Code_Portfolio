/*
 * Course: CS 59000-06 Computer Architecture
 * Tyler Paul
 * Branch Prediction Project
 *
 * The TwoLevelBranchPredictorPAp class implements Two-Level Adaptive Branch Branch
 * Predictor using a Per-Address Pattern History Tables.
 * Calling the constructor will cause the input file to be read line by line and feed
 * each branch instruction into the branch predictor. The number of correct predictions
 * will be counted and statistics are printed upon completion of reading the entire file.
 */

import java.lang.Math;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TwoLevelBranchPredictorPAp {
   BHT bht;                     //branch history table
   PPHT ppht;                   //per address pattern history table
   long correctPredictionCount; //count of the branches to branch predictor predicted correctly
   long totalPredictions;       //total number of branch predictions the branch predictor attempted

   /* The parameters for the constructor are as follows:
    * numEntries : number of entries in the BHT
    * numAddressBits : number of bits in an address (commonly 32)
    * numHRBits : number of history register bits for each BHT entry
    * associativity : The BHT will be associativity-way set associative (1 will mean direct-mapped)
    * lruReplaceEnable : true enables LRU replacement, false enables random replacement in BHT
    * fileName : file name string
    * The constructor will read the the file line by line and record whether the branch predictor
    * predicted the branch correctly. Upon completion the cache hit and miss count along with the
    * hit percentage will be printed.
    */
   public TwoLevelBranchPredictorPAp(int numEntries, int numAddressBits, int numHRBits, int associativity, boolean lruReplaceEnable, String fileName) {
      //construct BHT and PPHT
      bht = new BHT(numEntries, numAddressBits, numHRBits, associativity, lruReplaceEnable);
      int numTables = numEntries; //for PAp there is a table for each entry in BHT
      int numEntriesPerTable = (int)Math.pow(2, numHRBits); //2^numHRBits entries per PHT
      ppht = new PPHT(numTables, numEntriesPerTable);
      correctPredictionCount = 0;
      totalPredictions = 0;

      try {
         BufferedReader in = new BufferedReader(new FileReader(fileName));
         String line;
         long address; //address of the current instruction
         boolean actuallyTaken; //indicates if branch was taken
         while ((line = in.readLine()) != null) { //read file line by line
            String[] tokens = line.split(" "); //split line into 3 sections
            address = Long.parseLong(tokens[0], 16); //first section is address
            if (tokens[1].equals("+")) {//second section is + or - indicating if branch taken
               actuallyTaken = true;
            }
            else {
               actuallyTaken = false;
            }
            //Process the branch instruction. If the branch predictor predicts correct,
            //then true is returned.
            if (processInstruction(address, actuallyTaken))
               correctPredictionCount++;
            totalPredictions++;
         }
         in.close();
      }
      catch(IOException e) {
         System.out.println("IOEXCEPTION");
      }
      bht.printCacheStats(); //print caches misses, cache hits, and hit percentage
      printResults(); //print prediction counts and accuracy
   }

   /* processInstruction processes the instruction at the specified address.
    * If the branch predictor predicted correctly then true is returned. Also
    * update the BHT and PPHT to improve predictions for future instructions.
    */
   private boolean processInstruction(long address, boolean actuallyTaken) {
      boolean wasPredictionCorrect = false;

      //STEP 1: Cache the branch instruction and get the index of the
      //instruction in the BHT.
      int index = bht.cacheAddress(address);

      //STEP 2: Lookup the history bits of the instruction to obtain
      //the index into the corresponding PHT
      int phtIndex = bht.getHistoryBitsValue(index);

      //STEP 3: Get the prediction from the corresponding PHT of the
      //entry in the BHT from the PPHT. Determine if the prediction
      //was correct
      boolean newPrediction = ppht.getPrediction(index, phtIndex);
      if (actuallyTaken == newPrediction)
         wasPredictionCorrect = true;

      //STEP 4: Update the branch history register in the BHT
      //and update the pattern history bits in the PHT
      bht.updateHistory(index, actuallyTaken);
      ppht.updatePHTAt(index, phtIndex, actuallyTaken);

      return wasPredictionCorrect;
   }

   public void printResults() {
      System.out.println("TOTAL PREDICTIONS  : " + totalPredictions);
      System.out.println("CORRECT PREDICTIONS: " + correctPredictionCount);
      System.out.println("PREDICTION ACCURACY: " + ((double)correctPredictionCount) / totalPredictions);
   }
}