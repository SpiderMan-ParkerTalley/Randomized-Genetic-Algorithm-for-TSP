import java.util.Comparator;

public class outCompare implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getOutgoingEdges().size() > o2.getOutgoingEdges().size()) {
			return -1;
		}else if (o1.getOutgoingEdges().size() < o2.getOutgoingEdges().size()) {
			return 1;
		}else {
			return o1.getAbbrev().compareToIgnoreCase(o2.getAbbrev());
		}
	}

}
