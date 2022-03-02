//import java.util.*;

// Edge between two nodes
public class Edge {
	
	int dist;
	Node tail;
	Node head;
	String classification;
	
	public Edge( Node tailNode, Node headNode, int dist) {
		setDist( dist );
		setTail( tailNode );
		setHead( headNode );
		classification = "";
	}
	
	public String getClassification() {
		return classification;
	}
	
	public Node getTail() {
		return tail;
	}
	
	public Node getHead() {
		return head;
	}
	
	public int getDist() {
		return dist;
	}
	
	public void setClassification(String theClass) {
		classification = theClass;
	}
	
	public void setTail( Node n ) {
		tail = n;
	}
	
	public void setHead( Node n ) {
		head = n;
	}
	
	public void setDist( int i ) {
		dist = i;
	}
	
	public String toString() {
		String output = "";
		output += this.getTail().getAbbrev() + "->" + this.getHead().getAbbrev() + "\t" + this.getClassification();
		return output;
	}
}
