/*
 * Course: CS 59000-06 Computer Architecture
 * Tyler Paul
 * Branch Prediction Project
 *
 * A2 models the A2 (2-bit Saturating Up-down Counter) automaton.
 * A static method gives the new state based on an old state and
 * whether or not the last branch was taken.
 */

public class A2 {
   /* getNextState determines the new state based on a given
    * old state and the result of whether or not the last branch
    * was taken.
    */
   public static byte getNextState(byte oldState, boolean taken) {
      byte newState = 0;
      if ((oldState == 0 && !taken) || (oldState == 1 && !taken)) {
         newState = 0;
      }
      else if ((oldState == 0 && taken) || (oldState == 2 && !taken)) {
         newState = 1;
      }
      else if ((oldState == 1 && taken) || (oldState == 3 && !taken)) {
         newState = 2;
      }
      else {
         newState = 3;
      }
      return newState;
   }

   /* getPrediction returns the brach prediction result given a current
    * state of the automata.
    */
   public static boolean getPrediction (byte state) {
      boolean rv = false;
      //return true if predicted weakly or strongly taken
      if (state == 2 || state == 3)
         rv = true;
      return rv;
   }
}