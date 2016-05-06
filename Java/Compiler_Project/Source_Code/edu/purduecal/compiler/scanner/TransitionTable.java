/*
 * TransitionTable is a table used by the scanner to correctly determine the tokens of
 * the source file. 
 */

package edu.purduecal.compiler.scanner;

import java.util.HashMap;
import java.util.Map;

public class TransitionTable implements ITransitionTable {
	Map<TransitionTableKey, State> map;
	
	/*
	 * TransitionTableKey is a static nested class to act as a key
	 * for the hashmap used to implement the transition table.
	 */
	private static class TransitionTableKey {
		State state;
		CharacterCategory characterCategory;
		
		public TransitionTableKey(State state, CharacterCategory characterCategory) {
			super();
			this.state = state;
			this.characterCategory = characterCategory;
		}
		
		@Override
		public boolean equals(Object key) {
			if (!(key instanceof TransitionTableKey))
				return false;
			if (this.state.equals(((TransitionTableKey)key).state) 
					&&
					this.characterCategory.equals(((TransitionTableKey)key).characterCategory) 
					)
					return true;
			else
				return false;
		}
		
		@Override
		public int hashCode() {
			return state.hashCode() + characterCategory.hashCode();
		}
	}
	
	
	/*
	 * Construct and initialize the TransitionTable according to the specified microsyntax of the project
	 */
	public TransitionTable() {
		map = new HashMap<TransitionTableKey, State>();
		
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.LETTER), State.S_1);
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.DIGIT), State.S_2);
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.ADDITION), State.S_3);
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.SUBTRACTION), State.S_4);
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.MULTIPLICATION), State.S_5);
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.DIVISION), State.S_6);
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.LEFT_PAREN), State.S_7);
		map.put(new TransitionTableKey(State.S_0, CharacterCategory.RIGHT_PAREN), State.S_8);
		
		map.put(new TransitionTableKey(State.S_1, CharacterCategory.LETTER), State.S_1);
		map.put(new TransitionTableKey(State.S_1, CharacterCategory.DIGIT), State.S_1);
		
		map.put(new TransitionTableKey(State.S_2, CharacterCategory.DIGIT), State.S_2);
	}
	
	/*
	 * Get the next state of the DFA.
	 * Input:
	 *    currentState : The current state of the DFA
	 *    inputCharCategory: The character category of the character which causes a transition in the DFA
	 * Returns:
	 *     The next state of the DFA or
	 *     State_error if the next state does not exist
	 */
	public State getNextState(State currentState, CharacterCategory inputCharCategory) {
		State state = map.get(new TransitionTableKey(currentState, inputCharCategory));
		if (state == null)
			state = State.S_error;
		return state;
	}
}
