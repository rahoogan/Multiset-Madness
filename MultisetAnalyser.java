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
		int fixedsetSize = 0;
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
                                case "baltree":
					multiset = new BalTreeMultiset();
                                        break;
				case "hash":
					multiset = new HashMultiset();
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
				if(remove > size) {
					throw new IllegalArgumentException("Number of removals must be lower than the size of the fixed set");
				}
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid number of removals specified. Please specify a positive integer.");
			}

			// Select Fixed Set Size to Multiset Size Ration
			fixedsetSize = size;

			int complete = 0;
			int oldComplete =0;
			int nextInt = 0;
			for (int i =0; i< add; ++i) {
				nextInt = multiRandom.nextInt(fixedsetSize);
				multiset.add(nextInt);
				complete = ((int)(((double)i/(double)add)*100));
				if(complete > 0 && complete%10 == 0 && complete != oldComplete) {
					System.out.println(complete +"% Complete");
				}
				oldComplete = complete;
			}

			// Perform Testing
			double addTime = 0;
			complete =0;
			oldComplete = 0;
			long addStartTime = 0;
			long addEndTime = 0;
			nextInt = 0;
			for (int i =0; i< add; ++i) {
				nextInt = multiRandom.nextInt(fixedsetSize);
				addStartTime = System.nanoTime();
				multiset.add(nextInt);
				addEndTime = System.nanoTime();
				addTime += (double)(addEndTime - addStartTime);
				complete = ((int)(((double)i/(double)add)*100));
				if(complete > 0 && complete%10 == 0 && complete != oldComplete) {
					System.out.println(complete +"% Complete");
				}
				oldComplete = complete;
			}
			System.out.println("Add Time: " + ((double)(addTime)) / Math.pow(10, 9) + " sec");

			double searchTime = 0;
			complete = 0;
			oldComplete =0;
			long searchStartTime = 0;
			long searchEndTime = 0;
			nextInt = 0;
			for (int i =0; i< search; ++i) {
				nextInt = multiRandom.nextInt(fixedsetSize);
				searchStartTime = System.nanoTime();
				multiset.search(nextInt);
				searchEndTime = System.nanoTime();
				searchTime += (double)(searchEndTime - searchStartTime);
				complete = ((int)(((double)i/(double)search)*100));
                                if(complete > 0 && complete%10 == 0 && complete != oldComplete) {
                                        System.out.println(complete +"% Complete");
                                }
				oldComplete = complete;
			}
			System.out.println("Search Time: " + ((double)(searchTime)) / Math.pow(10, 9) + " sec");

			double removeTime = 0;
			complete = 0;
			oldComplete =0;
			long removeStartTime = 0;
			long removeEndTime = 0;
			nextInt = 0;
			for (int i =0; i< remove; ++i) {
				nextInt = multiRandom.nextInt(fixedsetSize);
				removeStartTime = System.nanoTime();
				multiset.removeOne(nextInt);
				removeEndTime = System.nanoTime();
				removeTime += (double)(removeEndTime - removeStartTime);
				complete = ((int)(((double)i/(double)remove)*100));
                                if(complete >0 && complete%10 == 0 && oldComplete != complete) {
                                        System.out.println(complete +"% Complete");
                                }
				oldComplete = complete;
			}
			System.out.println("Remove Time: " + ((double)(removeTime)) / Math.pow(10, 9) + " sec");

			}
	}

	public static void usage() {
		System.out.println("Usage: java MultisetAnalyser <multiset type: bst, linkedlist or sortedlinkedlist> <size of random set> <number of additions> <number of searches> <number of removals>\nFor example: java MultisetAnalyser linkedlist 100000 1000 400000 30");
	}
}
