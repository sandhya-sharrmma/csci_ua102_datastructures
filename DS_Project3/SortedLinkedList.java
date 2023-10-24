package project3;

import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import java.util.Iterator;

/**
 * This is an implementation of a generic sorted doubly-linked list.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow <code>null</code> elements.
 *
 * @author Joanna Klukowska
 * @author Sandhya Sharma
 *
 * @param <E> the type of elements held in this list
 */

public class SortedLinkedList<E extends Comparable<E>> implements Iterable<E>
{
    /* Inner class to represent nodes of this list.*/
    private class Node implements Comparable<Node> 
    {
        E data;
        Node next;
        Node prev;
        Node(E data) {
            if (data == null ) throw new NullPointerException ("does not allow null");
            this.data = data;
        }
        Node (E data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }
        public int compareTo( Node n ) {
            return this.data.compareTo(n.data);
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList()
    {
        head = null; 
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    public int size()
    {
        return size;
    }

     /**
     * Adds the specified element to the list in ascending order.
     *
     * @param element the element to add
     * @return true if the element was added successfully, false otherwise (or if the element is null)
     */
    public boolean add(E element)
    {
        if(element == null)
            return false; 
        
        if(this == null)
            return false;

        Node newNode = new Node(element);

        if (size == 0){
            head = tail = newNode;
            size++;
            return true;
        }
        
        Node current = head;

        while(current != null){
            if(current.data.compareTo(newNode.data) > 0){
                if(current == head){
                    newNode.next = head;
                    head.prev = newNode;
                    head = newNode;
                    newNode.prev = null; 
                }
                else {
                    newNode.next = current;
                    newNode.prev = current.prev;
                    current.prev.next = newNode;
                    current.prev = newNode;
                }
                size++;
                return true;
            }
            else
                current = current.next;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        newNode.next = null;
        size++;

        return true;
    }

    /**
    * Removes all elements from the list.
    */
    public void clear()
    {
        head = null;
        tail = null; 
        size = 0; 
    }

    /**
     * Returns true if the list contains the specified element,
     * false otherwise.
     *
     * @param o the element to search for
     * @return true if the element is in the list,
     * false otherwise
     */
    public boolean contains(Object o)
    {
        if (o == null)
            return false;
        
        if(this.size() == 0)
            return false;
        
        if(!o.getClass().equals(head.data.getClass()))
            return false;

        Node current = head;

        while(current != null){
            if(current.data.equals(o))
                return true;
            current = current.next;
        }

        return false;
    }

     /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throw IndexOutOfBoundsException  if the index is out of
     * range (index < 0 || index >= size())
     */
    public E get(int index) throws IndexOutOfBoundsException
    {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException("Index out of bounds.");

        if(index == 0)
            return head.data;
        else if (index == size-1)
            return tail.data;
        
        int counter = 1; 
        Node current = head.next; 

        while(current != null){
            if(counter == index)
                return current.data;
            current = current.next;
            counter++;
        }

        return null;
    }

     /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the element is not in the list.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element,
     * or -1 if the element is not in the list
     */
    public int indexOf(Object o)
    {
        if (o == null)
            return -1;
        
        if(this.size() == 0)
            return -1;

        if(!o.getClass().equals(head.data.getClass()))
            return -1;

        Node current = head; 
        int counter = 0;

        while(current != null){
            if(current.data.equals(o))
                return counter;
            current = current.next;
            counter++;
        }

        return -1; 
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * starting at the specified index, i.e., in the range of indexes
     * index <= i < size(), or -1 if the element is not in the list
     * in the range of indexes <code>index <= i < size()</code>.
     *
     * @param o the element to search for
     * @param index the index to start searching from
     * @return the index of the first occurrence of the element, starting at the specified index,
     * or -1 if the element is not found
     */
    public int nextIndexOf(Object o, int index)  
    {
        if (o == null)
            return -1;
        
        if(this.size() == 0)
            return -1;

        if(!o.getClass().equals(head.data.getClass()))
            return -1;

        Node current = head; 
        int counter = 0;

        for(int i = 0; i < index; i++){
            current = current.next;
            counter++;
        }
    
        while(current != null){
            if(current.data.equals(o))
                return counter;
            current = current.next;
            counter++;
        }

        return -1;
    }

     /**
     * Removes the first occurence of the specified element from the list.
     *
     * @param o the element to remove
     * @return true if the element was removed successfully,
     * false otherwise
     */

    public boolean remove(Object o)
    {
        if (o == null)
            return false;
        
        if(this.size() == 0)
            return false;
        
        if(!o.getClass().equals(head.data.getClass()))
            return false;

        Node current = head; 

        while(current != null){
            if(current.data.equals(o)){
                if(current == head){
                    head = head.next;
                    head.prev = null;
                    size--;
                }
                else if(current == tail){ 
                    tail = tail.prev;
                    tail.next = null;
                    size--;
                }
                else {
                    current.prev.next = current.next; 
                    current.next.prev = current.prev;
                    size--;
                }
                return true; 
            }
            current = current.next;
        }
        return false;
    }

     /**
     * Returns a string representation of the list.
     *  The string representation consists of a list of the lists's elements in
     *  ascending order, enclosed in square brackets ("[]").
     *  Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */

    @Override
    public String toString()
    {
        String toReturn = "[";

        Node current = head;

        while(current != null){
            if(current.next != null)
                toReturn += current.data + ", ";
            else 
                toReturn += current.data; 
            current = current.next;
        }
        return toReturn + "]";
    }

     /**
     * Compares the specified object with this list for equality.
     *
     * @param o the object to compare with
     * @return true if the specified object is equal to this list,
     * false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) 
    {
        if(o == null)
            return false;

        if(!o.getClass().equals(this.getClass()))
            return false;
        
        if(this.size() != ((SortedLinkedList)o).size())
            return false;

        if(o == this)
            return true;
        
        Node cur1 = this.head;
        Node cur2 = ((SortedLinkedList)o).head;

        while(cur1 != null && cur2 != null){
            if(!cur1.data.equals(cur2.data))
                return false;
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        if(cur1 == null && cur2 == null)
            return true;
        else
            return false;
    }

    //ITERATORS
   /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<E> 
    {
        Node nextToReturn = head;

        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (nextToReturn == null)
                throw new NoSuchElementException("End of the list reached.");
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next; 
            return tmp;
        }
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    public Iterator<E> iterator() 
    {
        return new ListIterator();
    }

}
