import java.io.PrintStream;
import java.util.*;

/**
 * @name LinkedListMultiset
 * @descr Double Linked list implementation of a Multiset ADT (Abstract Data Type)
 * @type Class
 * @author s3586372 Rahul Raghavan, Farid Farzin
 * @created 10-08-2017
 */
public class LinkedListMultiset<T> extends Multiset<T>
{
	/** A reference to the node at the head of the linked list */
	protected Node mHead;
	/** A reference to the node at the tail of the linked list */
	protected Node mTail;
	/** The length of the linked list */
	protected int mLength;

	public LinkedListMultiset() {
		// Implement me!
		mTail = null;
		mHead = null;
		mLength = 0;

	} // end of LinkedListMultiset()
	
	/**
 	* @name add 
 	* @descr Add a new element to the Multiset
 	* @type Method
 	* @param item The new element to add to the multiset
 	*
 	*/
	public void add(T item) {
		// Implement me!
		
		if (mLength == 0) {
			Node newNode = new Node(item, 1);
			mHead = newNode;
			mTail = newNode;
			mLength++;
		}
		else {
			Node currNode = mHead;
			boolean found = false;
			for (int i=0; i < mLength; ++i) {
				if(currNode.getValue().equals(item)) {
					currNode.setInstances(currNode.getInstances() + 1);
					found = true;
					break;
				}
				currNode = currNode.getNext();
			}
			
			if (!found) {
				Node newNode = new Node(item, 1);
                		newNode.setNext(mHead);
                		mHead.setPrev(newNode);
                		mHead = newNode;
				mLength++;
			}
		}
	} // end of add()
	
	/**
 	* @name search 
 	* @descr Search the multiset and return the number of instances of the searched value
 	* @type Method
 	* @param item Element to find in the multiset
 	* @return int The number of snstances of the element found in the multiset
 	*/
	public int search(T item) {
		// Implement me!
		Node currNode = mHead;
		for (int i=0; i < mLength; ++i) {
			if(currNode.getValue().equals(item)) {
				return currNode.getInstances();	
			}
			currNode = currNode.getNext();
		}	
		// default return, please override when you implement this method
		return 0;
	} // end of add()
	
	/**
 	* @name removeOne
 	* @descr Remove the first instance of the element found in the multiset
 	* @type Method
 	* @param item The element to remove from the multiset
 	* @return void
 	*/	 
	public void removeOne(T item) {
		// Implement me!
		Node currNode = mHead;
		int count = 0;
		// Iterate through the linked list to find all instances of the element
		if (mLength > 0) {
			for (int i=0; i < mLength; ++i) {
				if(currNode.getValue().equals(item)) {
					// Get number of instances present
					count = currNode.getInstances();
					// If only one element present, empty the multiset
					if(mLength == 1) {
						if (count ==1) {
							mHead = null;
							mTail = null;
							mLength = 0;
						}
						else {
							currNode.setInstances(count -1);
						}
					}
					else {
						// If only one instance present, remove element from multiset
						if(count == 1) {
							if (currNode == mHead) {
								mHead = currNode.getNext();
								mHead.setPrev(null);
							}
							else if (currNode == mTail) {
								mTail = currNode.getPrev();
								mTail.setNext(null);
							}
							else {
								currNode.getNext().setPrev(currNode.getPrev());
								currNode.getPrev().setNext(currNode.getNext());
							}
							mLength--;
							break;
						}
						else if (count > 1) {
							// Decrement the number of instances stored within the element
							currNode.setInstances(count-1);
							break;
						}
					}
				}
				currNode = currNode.getNext();
			}
		}
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		// Implement me!
		Node currNode = mHead;
		// Iterate through the linked list to find the element, and remove from multiset
		if (mLength > 0) {
			for (int i=0; i < mLength; ++i) {
				if(currNode.getValue().equals(item)) {
					if (mLength == 1) {
						mHead = null;
						mTail = null;
						mLength = 0;
					}
					else {
						if (currNode == mHead) {
							mHead = currNode.getNext();
							mHead.setPrev(null);
						}
						else if (currNode == mTail) {
							mTail = currNode.getPrev();
							mTail.setNext(null);
						}
						else {
							currNode.getNext().setPrev(currNode.getPrev());
							currNode.getPrev().setNext(currNode.getNext());
						}
						mLength--;
						break;
					}
				}
				currNode = currNode.getNext();
			}
		}
	} // end of removeAll()
	
	/**
 	* @name print
 	* @type Method 
 	* @descr Print a string representation of the multiset
 	* @return String representation of the multiset
 	*/ 	
	public void print(PrintStream out) {
		// Implement me!
		Node currNode = mHead;
		while(currNode != null) {
			out.println(currNode.getValue() + printDelim + currNode.getInstances());
			currNode = currNode.getNext();
		}
	} // end of print()

	/**
 	* @name Node
 	* @type Class
 	* @descr Class which represents the individual elements which constitute a multiset
 	* @author s3586372 Rahul Raghavan, Farid Farzin
	* @created 10-08-2017
 	*
 	*/ 
	private class Node
	{
		/** The value of the Node */
		private T mValue;
		/** The reference to the next node */
		private Node mNext;
		/** The reference to the previous node */
		private Node mPrev;
		/** The number of instances of the value in the multiset */
		private int mInstances;

		/** constructor */
		public Node(T item, int instances) {
			mValue = item;
			mNext = null;
			mPrev = null;
			mInstances = instances;
		}
		public T getValue() {
			return mValue;
		}
		public Node getNext() {
			return mNext;
		}
		public Node getPrev() {
			return mPrev;
		}
		public int getInstances() {
			return mInstances;
		}
		public void setValue(T val) {
			mValue = val;
		}
		public void setNext(Node next) {
			mNext = next;
		}
		public void setPrev(Node prev) {
			mPrev = prev;
		}
		public void setInstances(int inst) {
			mInstances = inst;
		}
	}
	
} // end of class LinkedListMultiset
