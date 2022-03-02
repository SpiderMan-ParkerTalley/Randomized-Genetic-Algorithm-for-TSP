import java.util.Comparator;

public class comparePrint implements Comparator<Edge>{

	@Override
	public int compare(Edge o1, Edge o2) {
		if(o1.getTail().getAbbrev().compareTo(o2.getTail().getAbbrev()) < 0) {
			return -1;
		}else if (o1.getTail().getAbbrev().compareTo(o2.getTail().getAbbrev()) > 0) {
			return 1;
		}else {
			return o1.getHead().getAbbrev().compareTo(o2.getHead().getAbbrev());
		}
	}

}
