package edu.purduecal.compiler.scanner;

public interface ITransitionTable {
	public State getNextState(State currentState, CharacterCategory inputCharCategory);
}
