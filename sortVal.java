import java.util.Comparator;

public class sortVal implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(Float.parseFloat(o1.getVal()) < Float.parseFloat(o2.getVal())){
			return -1;
		}else if(Float.parseFloat(o1.getVal()) > Float.parseFloat(o2.getVal())) {
			return 1;
		}else {
			return 0;
		}
	}

}
