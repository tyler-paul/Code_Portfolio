/*
 * Course: CS 59000-06 Computer Architecture
 * Tyler Paul
 * Branch Prediction Project
 *
 * The TwoLevelBranchPredictorPApTester class constructs the branch predictor with
 * appropriate parameters.
 */

public class TwoLevelBranchPredictorPApTester {
   public static void main(String[] args) {
      int numEntries           = 512;   //numEntries in BHT
      int numAddressBits       = 32;    //number of bits in an address
      int numHRBits            = 10;    //number of history register bits
      int associativity        = 1;     //BHT will be associativity-way associative
      boolean lruReplaceEnable = true; //True => LRU replacement, FALSE => Random replacement in BHT
      String fileName    = "gccSmall.trace";

      //construct and launch branch predictor
      TwoLevelBranchPredictorPAp bp = new TwoLevelBranchPredictorPAp(numEntries, numAddressBits, numHRBits, associativity, lruReplaceEnable, fileName);
   }
}