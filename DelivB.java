import java.io.*;
import java.util.Arrays;
import java.util.Collections;

// Class DelivB does the work for deliverable DelivB of the Prog340

public class DelivB {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivB( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {   // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		
		Collections.sort(g.getNodeList(), new compareAbbrev());
		System.out.println("DFS of Graph: \n");
		output.println("DFS of Graph: \n");
		System.out.println("Node\tDisc\tFinish");
		output.println("Node\tDisc\tFinish");
		Collections.sort(g.getEdgeList(), new compareEdge());
		DFS(g);
		
		for(Node node : g.getNodeList()) {
			System.out.println(node);
			output.println(node);
		}
		
		System.out.println("\nEdge Classification: ");
		System.out.println("Edge\tType");
		output.println("\nEdge Classification: ");
		output.println("Edge\tType");
		classify();
		
		Collections.sort(g.getEdgeList(), new comparePrint());
		
		for(Edge edge : g.getEdgeList()) {
			System.out.println(edge);
			output.println(edge);
		}
		output.flush();
	}
	
	
	
	static int time = 0;
	
	public void DFS(Graph graph) {
		for(Node node: g.getNodeList()) {
			if(node.getVal().equalsIgnoreCase("S")) {
				visit(g, node);
			}
		}
		for(Node node : g.getNodeList()) {
			if (node.getColor().equalsIgnoreCase("WHITE")) {
				visit(g, node);
			}
		}
	}
	
	 
	//keep track of edge categories
	public void visit(Graph graph, Node node) {
		time = time + 1;
		node.setDiscovery(time);
		node.setColor("GRAY");
		//tail is where we started, head is where we are going
		for(Edge edges : g.getEdgeList()) {
			if(edges.getTail().equals(node) && edges.getHead().getColor().equalsIgnoreCase("WHITE")) {
				edges.setClassification("Tree");
				edges.getHead().setPrevious(edges.getTail());
				visit(g, edges.getHead());
			}
		}
		node.setColor("BLACK");
		time = time + 1;
		node.setFinish(time);
	
	}
	
	public void classify() {
		for(Edge edges : g.getEdgeList()) {
			if(edges.getClassification().equalsIgnoreCase("")) {
				if(edges.getTail().getDiscovery() < edges.getHead().getDiscovery() && 
						edges.getTail().getFinish() > edges.getHead().getFinish()) {
					edges.setClassification("Forward");
				}else if(edges.getTail().getDiscovery() > edges.getHead().getDiscovery() &&
						edges.getTail().getFinish() < edges.getHead().getFinish()) {
					edges.setClassification("Back");
				}else {
					edges.setClassification("Cross");
				}
			}
		}
	}
}




