# Multiset-Madness

Implementation of a Multiset ADT (Abstract Data Type) using three different implementations:
	- Linkedlist (double)
	- Sorted LinkedList (double)
	- Binary Search Tree

# Analysis

## Scenario 1: Additions and Removals
For each implementation (bst, linkedlist, sortedlinkedlist):
1. Test insertion time (average over 10 results for each test)
	- Test 3-4 values (increasing number of insertions from low to high)
	- For example:
		> `java MultisetAnalyser bst 50000 5000 0 0`
		> `java MultisetAnalyser bst 500000 50000 0 0`
		> `java MultisetAnalyser bst 5000000 500000 0 0`
2. Test removal time (average over 10 results for each test)
	- Test 3-4 values (increasing number of removals from low to high)

NB: Make sure size of fixed set to number of operations is constant for each test


## Analyse:
1. Compare time taken for each operation between implementations (eg.. Time taken for insertions between bst and linkedlist)
2. Compare time taken to scale
	- i.e what happens when N is increased. What is the relationship to the time taken, and is this expected?

