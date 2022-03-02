import java.util.Comparator;

public class inCompare implements Comparator<Node> {

	@Override
	public int compare(Node node1, Node node2) {
		if(node1.getIncomingEdges().size() > node2.getIncomingEdges().size()) {
			return -1;
		}else if (node1.getIncomingEdges().size() < node2.getIncomingEdges().size()) {
			return 1;
		}else {
			return node1.getAbbrev().compareToIgnoreCase(node2.getAbbrev());
		}
	}
	
}



