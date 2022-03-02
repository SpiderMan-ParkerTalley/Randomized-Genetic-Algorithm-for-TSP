import java.util.*;

// A node of a graph for the Spring 2018 ICS 340 program

public class Node{

	String name;
	String val;  // The value of the Node
	String abbrev;  // The abbreviation for the Node
	ArrayList<Edge> outgoingEdges;  
	ArrayList<Edge> incomingEdges;
	String color;
	int discovery;
	int finish;
	Node previous;
	
	
	//create variables for colors and start/finish times
	public Node( String theAbbrev ) {
		setAbbrev( theAbbrev );
		val = null;
		name = null;
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
		color = "WHITE";
		discovery = 0;
		finish = 0;
		previous = null;
	}
	
	
	public boolean equals(Node other) {
		return this.getName().equals(other.getName());
	}
	
	public Node getPrevious() {
		return previous;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getDiscovery() {
		return discovery;
	}
	
	public int getFinish() {
		return finish;
	}
	
	public String getAbbrev() {
		return abbrev;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVal() {
		return val;
	}
	
	public ArrayList<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	
	public ArrayList<Edge> getIncomingEdges() {
		return incomingEdges;
	}
	
	public void setPrevious(Node thePrevious) {
		previous = thePrevious;
	}
	
	public void setColor(String theColor) {
		color = theColor;
	}
	
	public void setDiscovery(int theDiscovery) {
		discovery = theDiscovery;
	}
	
	public void setFinish(int theFinish) {
		finish = theFinish;
	}
	
	public void setAbbrev( String theAbbrev ) {
		abbrev = theAbbrev;
	}
	
	public void setName( String theName ) {
		name = theName;
	}
	
	public void setVal( String theVal ) {
		val = theVal;
	}
	
	public void addOutgoingEdge( Edge e ) {
		outgoingEdges.add( e );
	}
	
	public void addIncomingEdge( Edge e ) {
		incomingEdges.add( e );
	}

	public String printIn() {
		String output = "";
		output += "Node " + this.getAbbrev() + " has indegree " + this.getIncomingEdges().size() + ".";
		return output;
	}
	
	public String printOut() {
		String output = "";
		output += "Node " + this.getAbbrev() + " has outdegree " + this.getOutgoingEdges().size() + ".";
		return output;
	}
	
	//public String toString() {
	//	return abbrev;
	//}
	
	public String toString() {
		String output = "";
		output += this.getAbbrev() + "\t" + this.getDiscovery() + "\t" + this.getFinish();
		return output;
	}
	
	
	public String printNode() {
		String output = "";
		output += this.getAbbrev();
		return output;
	}

	
}

