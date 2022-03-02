import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// Class DelivC does the work for deliverable DelivC of the Prog340

public class DelivC {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivC( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {  
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		
		kickoff();
		output.flush();
		

	}
	
	public void kickoff() {
		Collections.sort(g.getNodeList(), new sortVal());
		
		int n = g.getNodeList().size() - 1;
		
		output.print("Shortest bitonic tour has distance ");
		output.print(bitonicTSP(n, n) + "\n");
		
		System.out.print("Shortest bitonic tour has distance ");
		System.out.print(bitonicTSP(n, n) + "\n");

		printTour(g.getNodeList().get(n));
	}
		
	
	
	public int bitonicTSP(int i, int j) {
		if(0 <= i && i <= j && j < g.getNodeList().size()) {
			if(i == 0 && j == 1) {
				g.getNodeList().get(j).setPrevious(g.getNodeList().get(i));
				return findDistance(g.getNodeList().get(i), g.getNodeList().get(j));
			}else if(i < j - 1) {
				g.getNodeList().get(j).setPrevious(g.getNodeList().get(j-1));
				return bitonicTSP(i, j-1) + findDistance(g.getNodeList().get(j-1), g.getNodeList().get(j));
			}else {
				int compare = Integer.MAX_VALUE;
				int index = 0;
				for(int k=0; k < i; k++) {
					int distance = bitonicTSP(k, i) + findDistance(g.getNodeList().get(k), g.getNodeList().get(j));
					if(distance < compare) {
						compare = distance;
						index = k;
					}
				}
				bitonicTSP(index, j-1);
				g.getNodeList().get(j).setPrevious(g.getNodeList().get(index));
				return compare;
			}
		}
		return -1;
	}
	
	
	public void printTour(Node node) {
		int counter = g.getNodeList().size();
		String[] tour = new String[g.getNodeList().size() + 1];
		tour[counter] = node.getAbbrev();
		node = node.previous;
		counter -= 1;
		while(counter > 0) {
			if(node.getPrevious() != null) {
				tour[counter] = node.getAbbrev();
				counter -= 1;
				node.setColor("BLACK");
				node = node.previous;
			}else {
				for(Node n : g.getNodeList()) {
					if(n.getColor().equalsIgnoreCase("WHITE")) {
						tour[counter] = n.getAbbrev();
						counter -= 1;
					}
				}
			} 
		}
		
		output.print("Tour is ");
		System.out.print("Tour is ");
		for(String a : tour) {
			output.print(a);
			System.out.print(a);
		}
	}
		
	
	public int findDistance(Node node1, Node node2) {
		int returnDist = 0;
		for(Edge edge : g.getEdgeList()) {
			if(edge.getTail().equals(node1) && edge.getHead().equals(node2)) {
				returnDist = edge.getDist();
			}
		}
		return returnDist;
	}
	

	

}
	


//


