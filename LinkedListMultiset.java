import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T> extends Multiset<T>
{
	protected Node mHead;
	protected Node mTail;
	protected int mLength;

	public LinkedListMultiset() {
		// Implement me!
		mTail = null;
		mHead = null;
		mLength = 0;

	} // end of LinkedListMultiset()
	
	
	public void add(T item) {
		// Implement me!
		if (mLength == 0) {
			Node newNode = new Node(item, 1);
			mHead = newNode;
			mTail = newNode;
		}
		else {
			int count = search(item);
			Node newNode = new Node(item, count+1);
			newNode.setNext(mHead)
			mHead.setPrev(newNode);
			mHead = newNode;
		}
		mLength++;
	} // end of add()
	
	
	public int search(T item) {
		// Implement me!
		Node currNode = mHead;
		int count = 0;
		for (int i=0; i < mLength; ++i) {
			if(currNode.getValue() == item) {
				count++;	
			}
			currNode = currNode.getNext();
		}	
		// default return, please override when you implement this method
		return count;
	} // end of add()
	
	
	public void removeOne(T item) {
		// Implement me!
		Node currNode = mHead;
		boolean removed = false;
		for (int i=0; i < mLength; ++i) {
			if(currNode.getValue() == item) {
				if(!removed) {
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
				}
			}
		}
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		// Implement me!
	} // end of removeAll()
	
	
	public void print(PrintStream out) {
		// Implement me!
	} // end of print()

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
		public getValue() {
			return mInstances;
		}
		public getNext() {
			return mNext;
		}
		public getPrev() {
			return mPrev;
		}
		public getInstances {
			return mInstances;
		}
		public setValue(T val) {
			mValue = val;
		}
		public setNext(Node next) {
			mNext = next;
		}
		public setPrev(Node prev) {
			mPrev = prev;
		}
		public setInstances(int inst) {
			mInstances = inst;
		}
	}
	
} // end of class LinkedListMultiset
