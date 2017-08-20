import java.util.Random;
import java.io.*;
/**
 *
 * @name DataGenerator
 * @desc  Generates data to analyse efficiency of different Multiset implementations
 * @author Rahul Raghavan, Farid Farzin
 * @created 17/08/2017
 */
public class MultisetAnalyser
{
	protected static final String classname = "MultisetDataGenerator";

	/** Main Method
 	* 
 	* @args Implementation: linkedlist, bst, sortedlinkedlist
 	* @args Size: Size of fixed set to draw from
 	* @args Number of Additions
 	* @args Number of Removals
 	* @args Number of Searches
 	*/ 
	public static void main(String[] args) throws IllegalArgumentException {
		Random multiRandom = new Random(System.currentTimeMillis());
		String implementation = args[0];
		int size = 0;
		int add = 0;
		int remove = 0;
		int search = 0;
		Multiset<Integer> multiset = null;
		if (args.length != 5) {
			usage();
		}
		else {
			switch(implementation) {
				case "linkedlist":
					multiset = new LinkedListMultiset<Integer>();
					break;
				case "sortedlinkedlist":
					multiset = new SortedLinkedListMultiset<Integer>();
					break;
				case "bst":
					multiset = new BstMultiset<Integer>();
					break;
				default:
					throw new IllegalArgumentException("Invalid implementation type specified. Valid types are: linkedlist, sortedlinkedlist, bst");
			}
			try {
				size = Integer.parseInt(args[1]);
				if (size < 0) {
					throw new IllegalArgumentException("Invalid size specified. Size must be a positive integer.");
				}
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid size specified. Please specify a positive integer less than a million");
			}
			try {
				add = Integer.parseInt(args[2]);
				if (add > size || add < 0) {
					throw new IllegalArgumentException("Number of additions must be lower than size of set, and not less than 0");
				}
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid number of additions specified. Please specify a positive integer less than a million");
			}
			try {
				search = Integer.parseInt(args[3]);
				if (search < 0) {
					throw new IllegalArgumentException("Invalid searches specified. Searches must be a positive integer.");
				}
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid number of searches specified. Number must be a valid positive integer");
			}
			try {
				remove = Integer.parseInt(args[4]);
				if(remove > add || remove < 0) {
					throw new IllegalArgumentException("Number of removals must be lower than number of additions, and not less than 0");
				}
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid number of removals specified. Please specify a positive integer.");
			}
			// Perform Testing
			double addTime = 0;
			for (int i =0; i< add; ++i) {
				int nextInt = multiRandom.nextInt(size);
				long addStartTime = System.nanoTime();
				multiset.add(nextInt);
				long addEndTime = System.nanoTime();
				addTime += (double)(addEndTime - addStartTime);
				//System.out.println(nextInt);
			}

			double searchTime = 0;
			for (int i =0; i< search; ++i) {
				int nextInt = multiRandom.nextInt(size);
				long searchStartTime = System.nanoTime();
				multiset.search(multiRandom.nextInt(size));
				long searchEndTime = System.nanoTime();
				searchTime += (double)(searchEndTime - searchStartTime);
			}

			double removeTime = 0;
			for (int i =0; i< remove; ++i) {
				int nextInt = multiRandom.nextInt(size);
				long removeStartTime = System.nanoTime();
				multiset.removeOne(multiRandom.nextInt(size));
				long removeEndTime = System.nanoTime();
				removeTime = (double)(removeEndTime - removeStartTime);
			}

			System.out.println("Add Time: " + ((double)(addTime)) / Math.pow(10, 9) + " sec");
			System.out.println("Search Time: " + ((double)(searchTime)) / Math.pow(10, 9) + " sec");
			System.out.println("Remove Time: " + ((double)(removeTime)) / Math.pow(10, 9) + " sec");
			}
	}

	public static void usage() {
		System.out.println("Usage: java MultisetAnalyser <multiset type: bst, linkedlist or sortedlinkedlist> <size of random set> <number of additions> <number of searches> <number of removals>\nFor example: java MultisetAnalyser linkedlist 100000 1000 400000 30");
	}
}
