import java.util.Comparator;

public class sortDist implements Comparator<Edge>{

	@Override
	public int compare(Edge o1, Edge o2) {
		if(o1.getDist() < o2.getDist()) {
			return -1;
		}else if(o1.getDist() > o2.getDist()) {
			return 1;
		}else {
			return 0;
		}
	}

}
