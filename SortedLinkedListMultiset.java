import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T extends Comparable<T>> extends Multiset<T>
{
	/** A reference to the node at the head of the linked list */
	protected Node mHead;
	/** A reference to the node at the tail of the linked list */
	protected Node mTail;
	/** The length of the linked list */
	protected int mLength;

	public SortedLinkedListMultiset() {
		// Implement me!
		mHead = null;
		mTail = null;
		mLength = 0;
	} // end of SortedLinkedListMultiset()


	public void add(T item) {
		// Implement me!
		Node currNode = mHead;
		Node newNode = new Node(item, 1);
		if (mLength == 0) {
			mHead = newNode;
			mTail = newNode;
			mLength++;
		}
		else {
			if(mLength == 1) {
				T val = currNode.getValue();
				if(val.equals(item)) {
					currNode.setInstances(currNode.getInstances()+1);
				}
				else if(val.compareTo(item) < 0) {
					currNode.setNext(newNode);
					newNode.setPrev(currNode);
					mTail = newNode;
					mLength++;
				}
				else {
					currNode.setPrev(newNode);
					newNode.setNext(currNode);
					mHead = newNode;
					mLength++;
				}
			}
			else {
				while (currNode != null){
					T val = currNode.getValue();
					Node nextNode = currNode.getNext();
					if (nextNode == null && !item.equals(val)) {
						currNode.setNext(newNode);
						newNode.setPrev(currNode);
						mTail = newNode;
						mLength++;
						break;
					}
					else if (item.equals(val)) {
						currNode.setInstances(currNode.getInstances()+1);
						break;
					}
					else if (item.compareTo(val) > 0 && item.compareTo(nextNode.getValue()) < 0) {
						currNode.setNext(newNode);
						newNode.setPrev(currNode);
						newNode.setNext(nextNode);
						nextNode.setPrev(newNode);
						mLength++;
						break;
					}
					currNode = nextNode;
				}
			}
		}
	} // end of add()


	public int search(T item) {
		Node currNode = mHead;
		while(!(currNode.getValue().compareTo(item)==0) && 
				currNode.getNext()!=null){
			currNode=currNode.getNext();
		}
		if(currNode.getValue().compareTo(item)==0)
			return currNode.getInstances();		
		return 0;
	} // end of add()


	public void removeOne(T item) {
		Node currNode = mHead;
		Node next=null;
		while(!(currNode.getValue().compareTo(item)==0) && 
				currNode.getNext()!=null){
			currNode=currNode.getNext();
		}
		if(currNode.getValue().compareTo(item)==0){
			if(currNode.getInstances()!=1)
				currNode.setInstances(currNode.getInstances()-1); 
			else{
				if(mHead.getValue().compareTo(item)==0){
					if(mHead.getNext()!=null){
						mHead=mHead.getNext();
						if(mHead.getPrev()!=null)
							mHead.setPrev(null);
					} else 
						mHead = null;
				}else{
					next = currNode.getNext();
					currNode = currNode.getPrev();
					currNode.setNext(next);
					if(next!=null && currNode.getPrev()!=null)
						currNode.getNext().setPrev(currNode);
				}
			}
		}

	} // end of removeOne()


	public void removeAll(T item) {
		Node currNode = mHead;
		Node next=null;
		if(mHead.getValue().compareTo(item)==0){
			if(mHead.getNext()!=null){
				mHead=mHead.getNext();
				if(mHead.getPrev()!=null)
					mHead.setPrev(null);
			} else 
				mHead = null;
		}
		else{
			while(!(currNode.getValue().compareTo(item)==0) && 
					currNode!=null){
				currNode=currNode.getNext();
			}
			if(currNode.getValue().compareTo(item)==0){
				next = currNode.getNext();
				currNode = currNode.getPrev();
				currNode.setNext(next);
				if(next!=null && currNode.getPrev()!=null)
					currNode.getNext().setPrev(currNode);
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


} // end of class SortedLinkedListMultiset
