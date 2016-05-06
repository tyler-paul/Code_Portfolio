public class VacNode implements Comparable {
	public VacWorldState state;
	public VacNode parent;
	public String action;

	public VacNode(VacWorldState state, VacNode parent, String action) {
		this.state = state;
		this.parent = parent;
		this.action = action;
	}

	public int compareTo(Object node) {
		if (!(node instanceof VacNode))
			return -1;
		return new Integer(ActionGetter.getScore(state))
				.compareTo(new Integer(ActionGetter.getScore(((VacNode) node).state)));
	}
}
