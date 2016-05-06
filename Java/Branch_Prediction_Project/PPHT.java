/*
 * Course: CS 59000-06 Computer Architecture
 * Tyler Paul
 * Branch Prediction Project
 *
 * The PPHT implements Per-Address Pattern History Tables.
 * The PPHT operates on numTable PHTs.
 * A prediction can be fetched from a specified PHT and a specified
 * PHT can be updated according to the A2 automata and the result of
 * whether or not the last branch was taken.
 */

public class PPHT {
   int numTables;
   PHT[] patternHistoryTables;

   //constructor
   public PPHT(int numTables, int numEntriesPerTable) {
      this.numTables = numTables;
      patternHistoryTables = new PHT[numTables];
      for (int i = 0; i < numTables; i++)
         patternHistoryTables[i] = new PHT(numEntriesPerTable);
   }

   /* updatePHTAt updates the patten history table (specified by tableNumber)
    * at the given index given the 0th bit of the history register (which
    * specifies if the last branch was taken or not).
    * It returns the prediction.
    */
   public void updatePHTAt(int tableNumber, int index, boolean hr0) {
      byte oldState = patternHistoryTables[tableNumber].getPHBits(index);
      byte newState = A2.getNextState(oldState, hr0);
      patternHistoryTables[tableNumber].setPHBits(index, newState);
   }

   /* getPrediction returns the prediction of a specified PHT for
    * a specified index
    */
   public boolean getPrediction(int tableNumber, int index) {
      byte state = patternHistoryTables[tableNumber].getPHBits(index);
      return A2.getPrediction(state);
   }

   /* printPPHT prints all the PHTs in the PPHT.
    */
   public void printPPHT() {
      for (int i = 0; i < numTables; i++) {
         System.out.println("");
         System.out.println("PHT Number " + i);
         patternHistoryTables[i].print();
      }
   }
}