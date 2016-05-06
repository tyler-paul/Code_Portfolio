import java.util.LinkedList;
import java.util.Arrays;

public class ActionGetter {
	public static LinkedList<VacWorldState> usedList = new LinkedList<VacWorldState>();

	public static LinkedList<String> getActions(int currentState, boolean[] isClean, LinkedList<Integer> dirtySpots) {
		LinkedList<String> ans = new LinkedList<String>();
		VacNode node = getNode(currentState, isClean, dirtySpots);

		while (node != null && node.parent != null) {
			ans.addFirst(node.action);
			node = node.parent;
		}

		return ans;
	}

	public static VacNode getNode(int currentState, boolean[] isClean, LinkedList<Integer> dirtySpots) {
		usedList = new LinkedList<VacWorldState>();
		VacWorldState initState = new VacWorldState(currentState, isClean, dirtySpots);

		System.out.println("initial score: " + getScore(initState));

		LinkedList<VacNode> queue = new LinkedList<VacNode>();
		queue.addFirst(new VacNode(initState, null, null));
		usedList.add(initState);
		VacNode node;
		int count = 0;
		while (true) {
			if (queue.isEmpty())
				return null;
			node = queue.removeLast();
			System.out.println("current score: " + getScore(node.state));
			count++;
			if (isGoalState(node.state) || getScore(node.state) == 0) {
				System.out.println("Rounds: " + count);
				return node;
			}
			addToQueue(queue, node);
		}
	}

	public static int getScore(VacWorldState state) {
		int currentState = state.currentState;
		int n = (int) Math.sqrt(state.isClean.length);
		int currentX = currentState / n;
		int currentY = currentState % n;
		int newX;
		int newY;
		int score = 0;
		for (int spot : state.dirtySpots) {
			newX = spot / n;
			newY = spot % n;
			score = score + Math.abs(currentX - newX) + Math.abs(currentY - newY) + 2 * n;
		}

		return score;
	}

	public static boolean isGoalState(VacWorldState state) {
		for (int i = 0; i < state.isClean.length; i++)
			if (state.isClean[i] == false)
				return false;
		return true;
	}

	public static void addToQueue(LinkedList<VacNode> queue, VacNode node) {
		String[] actions = { "right", "left", "up", "down", "suck" };
		VacWorldState state;

		// sort nodes
		VacNode[] list = new VacNode[actions.length];
		for (int i = 0; i < actions.length; i++) {
			state = (node.state).clone();
			performAction(state, actions[i]);
			list[i] = new VacNode(state, node, actions[i]);
		}
		Arrays.sort(list);

		// add nodes to queue
		for (int i = actions.length - 1; i >= 0; i--) {
			if (!usedList.contains(list[i].state)) {
				queue.addLast(list[i]);
				usedList.add(list[i].state);
			}
		}
	}

	public static void performAction(VacWorldState state, String action) {
		int n = (int) Math.sqrt(state.isClean.length);
		if (action.equals("right")) {
			if ((state.currentState + 1) % n != 0)
				state.currentState += 1;
		} else if (action.equals("left")) {
			if ((state.currentState) % n != 0)
				state.currentState -= 1;
		} else if (action.equals("up")) {
			if (state.currentState >= n)
				state.currentState -= n;
		} else if (action.equals("down")) {
			if (state.currentState < n * n - n)
				state.currentState += n;
		} else if (action.equals("suck")) {
			state.dirtySpots.remove(new Integer(state.currentState));
			state.isClean[state.currentState] = true;
		}
	}
}
