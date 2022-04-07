/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 26 2021
 * PA7
 * Explanation of the class: This class is the assignment. It creates a double-linked list using an
 * ArrayList, with nodes having reference to the previous and next nodes.
 * Known bugs: N/A
 */

package Homework7;

import java.util.ArrayList;

public class IDLList<E> {
	
	/**
	 * This nested inner class is the class for the Node. It is a relatively simple class, only
	 * needing to contain the data, the reference to the next, and the reference to the previous.
	 * It also has two operations, both of which are constructors.
	 */
	class Node<E> {
		E data;
		Node<E> next;
		Node<E> prev;
		
		Node (E elem) {
			data=elem;
		}
		
		Node (E elem, Node<E> prev, Node<E> next) {
			data=elem;
			this.prev=prev;
			this.next=next;
		}
	}
	
	Node<E> head;
	Node<E> tail;
	int size;
	ArrayList<Node<E>> indicies;
	
	/**
	 * This method instantiates the list, making a new arraylist of type Node<E>.
	 */
	public IDLList() {
		indicies=new ArrayList<Node<E>>();
	}
	
	/**
	 * This method adds things to the class. It first determines if it can add both the next and the
	 * previous nodes as references, and if so, calls the Node method in full.
	 * If it cannot, it will determine which parts it cannot have and replace the reference with null.
	 * It also changes the references before and after the new node to be the node.
	 */
	public boolean add (int index, E elem) {
		if ((index < size) && (index-1 >= 0)) {	//Ensuring the program can refer to a next and a previous.
			indicies.add(index, new Node(elem, indicies.get(index-1), indicies.get(index)));
			indicies.get(index-1).next=indicies.get(index);	//If it is in bounds, it will add the next and previous.
			indicies.get(index+1).prev=indicies.get(index);
		} else if ((index == size) && (size > 0)) {	//This will be true if the index is being appended. So, the end must be null.
			indicies.add(index, new Node(elem, indicies.get(index-1), null));
			indicies.get(index-1).next=indicies.get(index);	//There is no index+1, so it doesn't need to refer to the new node because it doesn't exist.
		} else if ((index-1 < 0) && (size > 0)) {	//This will be true if the index is at the beginning, meaning that it is the first. However, we must ensure that it has a next.
			indicies.add(index, new Node(elem, null, indicies.get(index)));
			indicies.get(index+1).prev=indicies.get(index);
		} else if (size == 0) {	//If it is the first element, both sides will be null.
			indicies.add(index, new Node(elem, null, null));
		}
		size++;	//Increments the size to reflect the add.
		return true;	//Because .add (index, value) returns void, we can return true here.
	}
	
	/**
	 * This will add it at the beginning, or index 0.
	 */
	public boolean add (E elem) {
		return(add(0, elem));	//We shouldn't put the code for the first here, for someone could call add (0, elem) instead of add (elem).
	}							//So, we just need to call add.
	
	/**
	 * This adds it at the end, or index "size".
	 */
	public boolean append (E elem) {
		return(add(size, elem));	//This is the same idea as add(elem)
	}
	
	/**
	 * The following method returns the proper data. It also checks to see if it's in range.
	 */
	public E get (int index) {
		if ((index >= 0) && (index < size) && (size > 0)) {
			return indicies.get(index).data;
		} else {	//If the index is not in range for the list, it will return null.
			return null;
		}
	}
	
	/**
	 * This will call get(0) to return the first element.
	 */
	public E getHead () {
		return (get(0));
	}
	
	/**
	 * This will get the last element, calling get(size-1)
	 */
	public E getLast() {
		return (get(size-1));	//Because it checks to see if the size is greater than 0, we do not
	}							//risk an IndexOutOfBoundsException.
	
	/**
	 * This method returns the size of the list.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * This method removes the first element, calling removeAt(0) because the code is already there.
	 */
	public E remove() {
		return (removeAt(0));
	}
	
	/**
	 * This removes the last element, calling removeAt(size-1).
	 */
	public E removeLast() {
		return (removeAt(size-1));
	}
	
	/**
	 * This method removes an element, changing the references and returning the data.
	 */
	public E removeAt(int index) {
		if (index==0) {	//The if/else if/else statements check to make sure I can change both the previous
			if (size > 1) {	//and next nodes, if they are there.
				indicies.get(index+1).prev=null;
			}			//If they are not, I change the one that is there to null.
		}else if (index == size-1) {
			if (size > 1) {
				indicies.get(index-1).next=null;
			}
		} else {	//If both are there, I change the references to refer to each other, passing over this element.
			indicies.get(index-1).next=indicies.get(index+1);
			indicies.get(index+1).prev=indicies.get(index-1);
		}
		E y=indicies.get(index).data;	//To return it, I need to get the data before removing the element.
		indicies.remove(index);	//Then, I can remove it.
		size--;	//I also must decrement the size to reflect this.
		return y;	//Returning ends the method, meaning that I have to get the data, then remove it, then return the data.
	}
	
	/**
	 * This removes the first instance of elem, which will call removeAt once it finds the element at the index.
	 */
	public boolean remove (E elem) {
		for (int i=0; i<size; i++) {
			if (indicies.get(i).data == elem) {
				removeAt(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This simply returns the string version of the list.
	 */
	public String toString() {
		String ret = "[";	//I am building a string here, which will change with the size of the list.
		if (size > 0) {
			ret += indicies.get(0).data;	//I need to add the first element outside of the for loop so that the ", " is correctly implemented.
			for (int i=1; i<size; i++) {
				ret+= ", ";
				ret += indicies.get(i).data;
			}
		}
		ret += "]";
		return ret;
	}
}