import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



// Class DelivA does the work for deliverable DelivA of the Prog340

public class DelivA{

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivA( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		work();
		output.flush();
		
	
	}
	
	
	public void work() {
		Collections.sort(g.getNodeList(), new inCompare());
		System.out.println("Indegree: ");
		output.println("Indegree: ");
		
		for(Node node: g.getNodeList()) {
			System.out.println(node.printIn());
			output.println(node.printIn());
		}
		
		Collections.sort(g.getNodeList(), new outCompare());
		System.out.println("\nOutdegree: ");
		output.println("\nOutdegree: ");
		for(Node node: g.getNodeList()) {
			System.out.println(node.printOut());
			output.println(node.printOut());
		}
	}
}

