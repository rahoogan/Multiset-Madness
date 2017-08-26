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
		MultisetAnalyser ma = new MultisetAnalyser();
		ma.multisetAnalysis();
		//analyser(args);
	}
	
	private static void analyser(String[] args){
		Random multiRandom = new Random(System.currentTimeMillis());
		String implementation = args[0];
		int fixedsetSize = 0;
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
				case "hash":
					multiset = new HashMultiset<Integer>();
					break;
				case "baltree":
					multiset = new BalTreeMultiset<Integer>();
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

			// Set ratio of size of fixed set to size of multiset
			fixedsetSize = size;
			int complete = 0;
			int oldComplete =0;
			double growTime =0;
			// Grow multiset to Size N
			System.out.println("Growing Multiset to size...");
			for (int i =0; i<size; ++i) {
				int nextInt = multiRandom.nextInt(fixedsetSize);
				long growStartTime = System.nanoTime();
				multiset.add(nextInt);
				long growEndTime = System.nanoTime();
				growTime += (double)(growEndTime - growStartTime);

				complete = ((int)(((double)i/(double)size)*100));
				if(complete > 0 && complete%10 == 0 && complete != oldComplete) {
					System.out.println(complete +"% Complete (" + ((double)(growTime))/Math.pow(10,9) + " secs)");
				}
				oldComplete = complete;
			}
			System.out.println("Starting tests...");

			double limit = 3600.00*Math.pow(10,9);
			boolean early = false;
			complete = 0;
			oldComplete =0;

			// Perform Testing
			double addTime = 0;
			for (int i =0; i< add; ++i) {
				int nextInt = multiRandom.nextInt(fixedsetSize);
				long addStartTime = System.nanoTime();
				multiset.add(nextInt);
				long addEndTime = System.nanoTime();
				addTime += (double)(addEndTime - addStartTime);

				complete = ((int)(((double)i/(double)add)*100));
				if(complete > 0 && complete%10 == 0 && complete != oldComplete) {
					System.out.println(complete +"% Complete (" + ((double)(addTime))/Math.pow(10,9) + " secs)");
				}

				if (addTime >= limit) {
					System.out.println("Operation took longer than "+ ((double)(addTime))/Math.pow(10,9) + " secs to complete");
					System.out.println("Operation progressed " + complete + " %");
					early = true;
					break;
				}
				oldComplete = complete;
			}
			if (!early)
				System.out.println("Add Time: " + ((double)(addTime)) / Math.pow(10, 9) + " sec");
				

			double searchTime = 0;
			early = false;
			complete =0;
			oldComplete =0;
			for (int i =0; i< search; ++i) {
				int nextInt = multiRandom.nextInt(fixedsetSize);
				long searchStartTime = System.nanoTime();
				multiset.search(nextInt);
				long searchEndTime = System.nanoTime();
				searchTime += (double)(searchEndTime - searchStartTime);

				complete = ((int)(((double)i/(double)search)*100));
				if(complete > 0 && complete%10 == 0 && complete != oldComplete) {
					System.out.println(complete +"% Complete (" + ((double)(searchTime))/Math.pow(10,9) + " secs)");
				}

				if (searchTime >= limit) {
					System.out.println("Operation took longer than "+ ((double)(searchTime))/Math.pow(10,9) + " secs to complete");
					System.out.println("Operation progressed " + complete + " %");
					early = true;
					break;
				}
				oldComplete = complete;
			}
			if (!early)
				System.out.println("Search Time: " + ((double)(searchTime)) / Math.pow(10, 9) + " sec");
				

			double removeTime = 0;
			early = false;
			complete = 0;
			oldComplete =0;
			for (int i =0; i< remove; ++i) {
				int nextInt = multiRandom.nextInt(fixedsetSize);
				long removeStartTime = System.nanoTime();
				multiset.removeOne(nextInt);
				long removeEndTime = System.nanoTime();
				removeTime += (double)(removeEndTime - removeStartTime);

				complete = ((int)(((double)i/(double)remove)*100));
				if(complete > 0 && complete%10 == 0 && complete != oldComplete) {
					System.out.println(complete +"% Complete (" + ((double)(removeTime))/Math.pow(10,9) + " secs)");
				}

				if (removeTime >= limit) {
					System.out.println("Operation took longer than "+ ((double)(removeTime))/Math.pow(10,9) + " secs to complete");
					System.out.println("Operation progressed " + complete + " %");
					early = true;
					break;
				}
				oldComplete = complete;
			}
			if(!early)
				System.out.println("Remove Time: " + ((double)(removeTime)) / Math.pow(10, 9) + " sec");
		}
	}

	public static void usage() {
		System.out.println("Usage: java MultisetAnalyser <multiset type: bst, linkedlist or sortedlinkedlist> <size of random set> <number of additions> <number of searches> <number of removals>\nFor example: java MultisetAnalyser linkedlist 100000 1000 400000 30");
	}
	
	public void multisetAnalysis(){
		String[] msName={"bst", "hash", "baltree", "linkedlist", "sortedlinkedlist"};
		String[] msSize={"10000","100000","1000000"};
		String[] args;

		for(int i=0; i<msName.length;i++){	
			for(int j=0;j<msSize.length;j++){
				//only addition
			        System.out.println("multiset name: "+msName[i]);
				System.out.println("size length: " + msSize[j]);
				System.out.println("Scenario 1: (only addition) ");
				analyser(scen1(msName[i],msSize[j]));
				System.out.println("**************************************************************************");
			        System.out.println("multiset name: "+msName[i]);
				System.out.println("size length: " + msSize[j]);
				System.out.println("Scenario 2: (addition equals deletion) ");
				analyser(scen2(msName[i],msSize[j]));
				System.out.println("**************************************************************************");
			        System.out.println("multiset name: "+msName[i]);
				System.out.println("size length: " + msSize[j]);
				System.out.println("Scenario 3: (only deletion) ");
				analyser(scen3(msName[i],msSize[j]));
				System.out.println("**************************************************************************");
			        System.out.println("multiset name: "+msName[i]);
				System.out.println("size length: " + msSize[j]);
				System.out.println("Scenario 4: (Search > addition + deletion) 2N");
				analyser(scen4(msName[i],msSize[j], "2"));
				System.out.println("**************************************************************************");
			        System.out.println("multiset name: "+msName[i]);
				System.out.println("size length: " + msSize[j]);
				System.out.println("Scenario 4: (Search > addition + deletion) 3N");
				analyser(scen4(msName[i],msSize[j],"3"));
				System.out.println("**************************************************************************");
			        System.out.println("multiset name: "+msName[i]);
				System.out.println("size length: " + msSize[j]);
				System.out.println("Scenario 4: (Search > addition + deletion) 4N");
				analyser(scen4(msName[i],msSize[j],"4"));
				System.out.println("**************************************************************************");
			}
		}
	}
	//only addition
	private String[] scen1(String name, String size){
		String[] args=new String[5];

		args[0]=name; //name
		args[1]=size;//size
		args[2]=size;//add
		args[3]="0";//search
		args[4]="0";//remove
		return args;
	}
	//addition equals removal
	private String[] scen2(String name, String size){
		String[] args=new String[5];

		args[0]=name; //name
		args[1]=size;//size
		args[2]=size;//add
		args[3]="0";//search
		args[4]=size;//remove
		return args;
	}
	//only removal
	private String[] scen3(String name, String size){
		String[] args=new String[5];

		args[0]=name; //name
		args[1]=size;//size
		args[2]="0";//add
		args[3]="0";//search
		args[4]=size;//remove
		return args;
	}
	//search greater than addd+removal
	private String[] scen4(String name, String size, String searchNo){
		String[] args=new String[5];
		int s = (Integer.parseInt(size));
		int sn = (Integer.parseInt(searchNo));
		args[0]=name; //name
		args[1]=size;//size
		args[2]=Integer.toString(s/2);//add
		System.out.println("Number of searches: "+ searchNo);
		args[3]= Integer.toString(s*sn);
		args[4]=Integer.toString(s/2);//remove
		return args;
	}
}
