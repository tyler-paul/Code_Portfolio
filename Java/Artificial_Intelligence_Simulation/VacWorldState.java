import java.util.LinkedList;

public class VacWorldState {
	public int currentState;
	public boolean[] isClean;
	public LinkedList<Integer> dirtySpots;

	public VacWorldState(int currentState, boolean[] isClean, LinkedList<Integer> dirtySpots) {
		this.currentState = currentState;
		this.isClean = isClean;
		this.dirtySpots = dirtySpots;
	}

	public VacWorldState clone() {
		return new VacWorldState(currentState, isClean.clone(), (LinkedList<Integer>) dirtySpots.clone());
	}

	public boolean equals(Object state) {
		if ((currentState == ((VacWorldState) state).currentState)
				&& (dirtySpots.equals(((VacWorldState) state).dirtySpots))) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		String ans = "";
		ans = ans + "current state: " + currentState + " , spots = ";
		for (int num : dirtySpots)
			ans = ans + num + " ";
		ans = ans + " (";
		for (boolean val : isClean)
			ans = ans + val + " ";
		return ans + ")";
	}
}
