import java.util.Comparator;

public class compareAbbrev implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getAbbrev().compareTo(o2.getAbbrev()) < 0) {
			return -1;
		}else if(o1.getAbbrev().compareTo(o2.getAbbrev()) > 0) {
			return 1;
		}else {
			return 0;
		}
	}

}
