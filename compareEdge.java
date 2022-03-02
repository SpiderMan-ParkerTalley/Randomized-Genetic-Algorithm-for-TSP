import java.util.Comparator;

public class compareEdge implements Comparator<Edge> {

	@Override
	public int compare(Edge o1, Edge o2) {
		if(o1.getDist() < o2.getDist()) {
			return -1;
		}else if (o1.getDist() > o2.getDist()){
			return 1;
		}else {
			return o1.getHead().getAbbrev().compareToIgnoreCase(o2.getHead().getAbbrev());
		}
	}
	
}
