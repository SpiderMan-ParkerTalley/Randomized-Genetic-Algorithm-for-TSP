import java.awt.List;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/*
Due to the length of the report, it has been attached in a separate word file.

Class DelivD does the work for deliverable DelivD of the Prog340.
*/
public class DelivD {

	File inputFile;
	File outputFile;
	PrintWriter output;
	static Graph g;
	
	/**
	 * Static array to keep track of the randomized mutations.
	 * In other words, store the changes done to the tour.
	 */
	static ArrayList<String> mutations = new ArrayList<String>();
	
	/**
	 * Static array to keep track of every tour that has been tried; used to 
	 * avoid duplicates and waste compute time or space.
	 */
	static ArrayList<ArrayList<Node>> allTours = new ArrayList<ArrayList<Node>>();
	
	/**
	 * Static counter for keeping track of when to mutate.
	 */
	static int counter = 0;
	
	
	public DelivD( File in, Graph gr ) {
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
		
		/*
		Starting tour is based on the input graph and how it is by default
		Best tour will be the distance of that imported graph by default
		*/
		int bestTour = findDist(g.getNodeList());
		allTours.add(g.getNodeList());
		
		System.out.print("Start: \n" + printTour(g.getNodeList()) + " " + findDist(g.getNodeList()) + "\n");
		output.print(printTour(g.getNodeList())  + " " + findDist(g.getNodeList()) + "(Start based on input graph)\n");
		counter += 1;
		/*
		Original population and associated distances of those populations to 
		sort by lowest to highest distance.
		 
		Based on randomization.
		*/
		ArrayList<ArrayList<Node>> population = findPopulation(g.getNodeList().size()/2);
		ArrayList<Integer> distances = new ArrayList<Integer>();
		

		// Adding distances of population to distances array.
		for(int j=0; j<population.size(); j++) {
			distances.add(findDist(population.get(j)));
		}
		
		/*
		Sorting both the population tours and distances based on distance using 
		a selection sort also sorting mutations so we can keep track which tour 
		had a randomized mutation or not.
		*/
		for(int i=0; i<distances.size()-1; i++) {
			int min = i;
			for(int j=i+1; j<distances.size(); j++) {
				if(distances.get(j).compareTo(distances.get(min)) < 0) {
					min = j;
				}
			}
			int temp = distances.get(min);
			ArrayList<Node> temp1 = population.get(min);
			String temp2 = mutations.get(min);
			
			distances.set(min, distances.get(i));
			population.set(min, population.get(i));
			mutations.set(min, mutations.get(i));
			
			distances.set(i, temp);
			population.set(i, temp1);
			mutations.set(i, temp2);
		}
		
		/*  
		Printing generation one, which is the initial randomized population 
		from our imported graph tours.
		
		Verbose mode prints to a .txt output file each and every new tour we 
		make, even if it is not better than our best tour so far.
		
		Summary mode prints to the console, if and only if the distance is 
		better than the best tour dist so far.
		
		This accounts for swap mutations chosen at random by checking if the 
		mutation array at a certain index correlating with our tour distances 
		is equal to 1, or a mutation has occurred, or 0, a mutation has not 
		occurred.
		*/
		
		System.out.println("Gen 1: ");
		String sOut = "";
		for(int t=0; t<population.size(); t++) {
			if(distances.get(t) < bestTour) {
				sOut += printTour(population.get(t)) + " " + distances.get(t)+ "\n";
				bestTour = distances.get(t);
			}
			if(mutations.get(t).equals("1")) {
				output.print(printTour(population.get(t)) + " " + distances.get(t) + "(Randomized Population "
						+ "with Swap Mutation - Gen 1)\n");
			}else {
				output.print(printTour(population.get(t)) + " " + distances.get(t) + "(Randomized Population - Gen 1)\n");
			}
		}
		if(sOut.isBlank()) {
			System.out.println("No improved iterations for this generation");
		}else {
			System.out.print(sOut);
		}
		output.print("\n");
		
		// Creating the next generation based on our first gen population.
		ArrayList<ArrayList<Node>> nextGen = newGen(population);
		
		
		/**
		A random number to decide how many iterations, or generations will 
		occur (5-10).
	    
		A minimum of 6 generations and max of 11 total generations possiblel.
		*/
		Random rand = new Random();
		int iterations = rand.nextInt(10);
		
		while(iterations < 5) {
			iterations = rand.nextInt(10);
		}
		
		// Summary and verbose printing for chosen iterations
		for(int i=0; i<iterations; i++) {
			System.out.println("Gen " + (i+2) + ": ");
			ArrayList<Integer> nextGenDist = new ArrayList<Integer>();
		
			String sOut2 = "";
			/* 
			Special casing generation 2 because we are comparing to initial 
			gen 1 population.
			*/
			if(i == 0) {
				for(int j=0; j<nextGen.size(); j++) {
					nextGenDist.add(findDist(nextGen.get(j)));
				}
				
				// Sorting all three arrays using selection sort
				for(int k=0; k<nextGenDist.size()-1; k++) {
					int min = k;
					for(int j=k+1; j<nextGenDist.size(); j++) {
						if(nextGenDist.get(j) < nextGenDist.get(min)) {
							min = j;
						}
					}
					int temp = nextGenDist.get(min);
					ArrayList<Node> temp1 = nextGen.get(min);
					String temp2 = mutations.get(min);
					
					nextGenDist.set(min, nextGenDist.get(k));
					nextGen.set(min, nextGen.get(k));
					mutations.set(min, mutations.get(k));
					
					nextGenDist.set(k, temp);
					nextGen.set(k, temp1);
					mutations.set(k, temp2);
				}
				/*
				Making corresponding verbose and summary outputs based on if the 
				distances are better than the last and dependent on if the next 
				gen is an elite parent from the previous gen, an offspring with 
				a randomly selected swap mutation, or a regular offspring.
				*/
				for(int t=0; t<nextGen.size(); t++) {
					output.print(printTour(nextGen.get(t)) + " "  + nextGenDist.get(t));
					if(mutations.get(t).equals("1")) {
						if(population.contains(nextGen.get(t))){
							output.print("(Elitism Parent Gen " + (i+2) + ")\n");
						}else {
							output.print("(Offspring with Swap Mutation Gen " + (i+2) + ")\n");
						}
					}else {
						if(population.contains(nextGen.get(t))){
							output.print("(Elitism Parent Gen " + (i+2) + ")\n");
						}else {
							output.print("(Offspring Gen " + (i+2) + ")\n");
						}
					if(nextGenDist.get(t) < bestTour) {
						sOut2 += printTour(nextGen.get(t)) + " " + nextGenDist.get(t) + "\n";
						bestTour = nextGenDist.get(t);
						}
					}
				
				}
			}
			
			else {
				// Accounting for the rest of the generations 3-n
				ArrayList<ArrayList<Node>> previous = nextGen;
				nextGen = newGen(nextGen);
				for(int j=0; j<nextGen.size(); j++) {
					nextGenDist.add(findDist(nextGen.get(j)));
				}
				
				for(int k=0; k<nextGenDist.size()-1; k++) {
					int min = k;
					for(int j=k+1; j<nextGenDist.size(); j++) {
						if(nextGenDist.get(j) < nextGenDist.get(min)) {
							min = j;
						}
					}
					int temp = nextGenDist.get(min);
					ArrayList<Node> temp1 = nextGen.get(min);
					String temp2 = mutations.get(min);
					
					nextGenDist.set(min, nextGenDist.get(k));
					nextGen.set(min, nextGen.get(k));
					mutations.set(min, mutations.get(k));
					
					nextGenDist.set(k, temp);
					nextGen.set(k, temp1);
					mutations.set(k, temp2);
				}
				for(int t=0; t<nextGen.size(); t++) {
					output.print(printTour(nextGen.get(t)) + " "  + nextGenDist.get(t));
					if(mutations.get(t).equals("1")) {
						if(previous.contains(nextGen.get(t))){
							output.print("(Elitism Parent Gen " + (i+2) + ")\n");
						}else {
							output.print("(Offspring with Swap Mutation Gen " + (i+2) + ")\n");
						}
					}else {
						if(previous.contains(nextGen.get(t))){
							output.print("(Elitism Parent Gen " + (i+2) + ")\n");
						}else {
							output.print("(Offspring Gen " + (i+2) + ")\n");
						}
					if(nextGenDist.get(t) < bestTour) {
						sOut2 += printTour(nextGen.get(t)) + " " + nextGenDist.get(t) + "\n";
						bestTour = nextGenDist.get(t);
						}
					}
				}
			}
			// If there are no improved iterations, we will print that
			if(sOut2.equals("")) {
				System.out.println("No improved iterations for this generation");
			}else {
				System.out.print(sOut2);
			}
			output.print("\n");
		}
		// Flushing .txt output
		output.flush();
	}
	
	/**
	 * Used to create the initial population with a parameter or a max 
	 * population size. Accounts for randomized mutations based on module
	 * operation on static counter.
	 * @param popSize number of times to pop array.
	 * @return a popluation of traveling salesman.
	 */
	public static ArrayList<ArrayList<Node>> findPopulation(int popSize){
		ArrayList<ArrayList<Node>> population = new ArrayList<ArrayList<Node>>();
		mutations.clear();
		
		while(population.size() != popSize) {
			ArrayList<Node> newTour = findTour(g.getNodeList());
			if(counter % 4 == 0) {
				newTour = mutation(newTour);
				if(allTours.contains(newTour) == false) {
					population.add(newTour);
					allTours.add(newTour);
					mutations.add("1");
				}
			}else {
				if(allTours.contains(newTour) == false) {
					population.add(newTour);
					allTours.add(newTour);
					mutations.add("0");
				}
			}
			counter += 1;
		}
		return population;
	}

	/**
	 * Create new generations, breeding with each tour. Start by splitting the 
	 * parents tours in four and grabbing the top 25% tours to move onto the 
	 * next gen in addition to the offspring.
	 * @param parents past generations of TSP.
	 * @return the new randomized generation.
	 */
	public static ArrayList<ArrayList<Node>> newGen(ArrayList<ArrayList<Node>> parents){
		ArrayList<ArrayList<Node>> newGen = new ArrayList<ArrayList<Node>>();
		mutations.clear();
		
		for(int i = 0; i < parents.size() / 4; i++) {
			newGen.add(parents.get(i));
			mutations.add("0");
		}
		
		for(int i = 0; i < parents.size() - 1; i++) {
			ArrayList<Node> child = breed(parents.get(i), parents.get(i + 1));
			if(allTours.contains(child) == false) {
				newGen.add(child);
				allTours.add(child);
			}
		}
		
		return newGen;
		
	}
	
	//
	/**
	 * Finds a randomized tour used by findPopulation() that randomly adds nodes 
	 * using a RNG until the newTour's size is equal to the old tour's size and 
	 * all nodes have been added.
	 * @param nodes the tour that is current being found.
	 * @return the new tour.
	 */
	public static ArrayList<Node> findTour(ArrayList<Node> nodes){
		ArrayList<Node> newTour = new ArrayList<Node>();
		Random rand = new Random();

		while(newTour.size() != nodes.size()) {
			int r1 = rand.nextInt(nodes.size());
			if(newTour.contains(nodes.get(r1)) == false) {
				newTour.add(nodes.get(r1));
			}
		}
		return newTour;
	}
	
	/**
	 * Randomized mutation function that randomly swaps two nodes n amount of 
	 * times decided by an RNG.
	 * @param offspring current array being mutated.
	 * @return array that has been mutated.
	 */
	public static ArrayList<Node> mutation(ArrayList<Node> offspring){
		Random rand = new Random();
		int max = offspring.size();
		
		Node temp = null;
		int index1 = 0;
		int index2 = 0;
		
		index1 = rand.nextInt(max-1) + 1;
		index2 = rand.nextInt(max-1) + 1;
		int numSwap = rand.nextInt(10);
			
		while(index1 == index2) {
			index1 = rand.nextInt(max-1) + 1;
			index2 = rand.nextInt(max-1) + 1;
		}
			
		for(int i=0; i<numSwap; i++) {	
			temp = offspring.get(index1);
			offspring.set(index1, offspring.get(index2));
			offspring.set(index2, temp);
		}
			
		return offspring;
	}
	
	/**
	 * Breed two tours using a RNG to pick two numbers to represent indexes. 
	 * 
	 * Starts at smallest index and adds the associated indexes to the child 
	 * from p1 and ends at the bigger index. 
	 * 
	 * Goes through parent 2 and adds the remaining nodes if they are not 
	 * already contained in child. 
	 * 
	 * If counter modulo 3 is 0, then we will mutate. This method also keeps 
	 * track of the mutation array.
	 * @param p1 first array being compared.
	 * @param p2 second array being compared.
	 * @return best array out of the two being compared.
	 */
	public static ArrayList<Node> breed(ArrayList<Node> p1, ArrayList<Node> p2) {
		ArrayList<Node> child = new ArrayList<Node>();
		
		Random rand = new Random();
		if(counter %3 == 0) {
			int r1 = rand.nextInt(p1.size());
			int r2 = rand.nextInt(p1.size());
			
			int start = Math.min(r1, r2);
			int end = Math.max(r1, r2);
			
			for(int i=start; i<end; i++) {
				child.add(p1.get(i));
					
			}
						
			for(int i=0; i<p2.size(); i++) {
				if(child.contains(p2.get(i)) == false) {
					child.add(p2.get(i));	
					}		
				}
			child = mutation(child);
			mutations.add("1");
		}else {
			int r1 = rand.nextInt(p1.size());
			int r2 = rand.nextInt(p1.size());
			
			int start = Math.min(r1, r2);
			int end = Math.max(r1, r2);
			
			for(int i=start; i<end; i++) {
				child.add(p1.get(i));
					
			}
						
			for(int i=0; i<p2.size(); i++) {
				if(child.contains(p2.get(i)) == false) {
					child.add(p2.get(i));	
					}		
				}
			mutations.add("0");
		}
		
		counter += 1;
		return child;
	}
	/**
	 * This determines the total distance of the tour.
	 * @param tour the tour that's distance is being computed.
	 * @return the distance fo the complete tour.
	 */
	public static int findDist(ArrayList<Node> tour) {
		int dist = 0;
		for(int i=0; i<tour.size()-1; i++) {
			for(Edge edge: g.getEdgeList()) {
				if(edge.getTail().equals(tour.get(i)) && edge.getHead().equals(tour.get(i+1))) {
					dist += edge.getDist();
				}
			}
		}
		for(Edge edge: g.getEdgeList()) {
			if(edge.getTail().equals(tour.get(tour.size()-1)) && edge.getHead().equals(tour.get(0))) {
				dist += edge.getDist();
			}
		}
		return dist;
	}
	
	/**
	 * Display (prints) the informaiton abotu the tour.
	 * @param tour the tour that is being displayed.
	 * @return a string of representation of the tour.
	 */
	public static String printTour(ArrayList<Node> tour) {
		String output = "";
		for(Node node : tour) {
			output += node.getAbbrev().charAt(0);
		}
		output += tour.get(0).getAbbrev().charAt(0);
		return output; 
	}

}


