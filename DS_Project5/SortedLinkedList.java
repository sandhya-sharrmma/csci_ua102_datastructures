package project5;

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * This is an implementation of a sorted doubly-linked list to store Word objects.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow null elements.
 *
 * @author Joanna Klukowska
 * @author Sandhya Sharma
 * 
 * @version December 3, 2023
 *
 * @param <Word> the type of elements held in this list
 */

public class SortedLinkedList implements Index, Iterable<Word>
{
    /* Inner class to represent nodes of this list.*/
    private class Node implements Comparable<Node> 
    {
        Word data;
        Node next;
        Node prev;
        
        Node (Word data) {
            if(data == null) 
                throw new NullPointerException ("does not allow null");
            this.data = data;
        }
        Node (Word data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }
        public int compareTo(Node n) {
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
    public void add(String element)
    {
        if(element == null)
            return;  
        
        Node newNode = new Node(new Word(element));

        if(size == 0){
            head = tail = newNode;
            size++;
            return;
        }
        
        Node current = head;

        while(current != null){
            if(current.data.compareTo(newNode.data) == 0){
                current.data.incrementCount();
                return;
            }
            else if(current.data.compareTo(newNode.data) > 0){
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
                return;
            }
            
            current = current.next;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        newNode.next = null;
        size++;
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throw IndexOutOfBoundsException  if the index is out of
     * range (index < 0 || index >= size())
     */
    public int get(String word)
    {
        if(word == null)
            return -1;
        
        if(this.size() == 0)
            return -1;
        
        if(!word.getClass().equals(head.data.getClass()))
            return -1;

        Node current = head; 

        while(current != null){
            if(current.data.getWord().equals(word))
                return current.data.getCount();
            current = current.next;
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

    public void remove(String word) 
    {
        if (word == null)
            return;
        
        if(this.size() == 0)
            return;

        Node current = head; 

        while(current != null){
            if(current.data.getWord().equals(word)){
                if(current == head){
                    head = head.next;
                    head.prev = null;
                    size--;
                    break;
                }
                else if(current == tail){ 
                    tail = tail.prev;
                    tail.next = null;
                    size--;
                    break;
                }
                else {
                    current.prev.next = current.next; 
                    current.next.prev = current.prev;
                    size--;
                    break;
                }
            }
            else 
                current = current.next;
        }
    }

     /**
     *  Returns a string representation of the list.
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
        if (o == null)
            return false;
        if (!(o instanceof BSTIndex))
            return false;
        BSTIndex other = (BSTIndex) o;

        if(this.size() != other.size())
            return false;

        Iterator<Word> list_iterator = this.iterator();
        Iterator<Word> tree_iterator = other.iterator();

        while(list_iterator.hasNext() && tree_iterator.hasNext()){
            Word w1 = list_iterator.next();
            Word w2 = tree_iterator.next();
            if(!w1.equals(w2))
                return false;
        }
        return true;
    }

    //ITERATORS
   /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<Word> 
    {
        Node nextToReturn = head;
        Node lastReturned = null;

        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public Word next() throws NoSuchElementException {
            if (nextToReturn == null)
                throw new NoSuchElementException("End of the list reached.");
            lastReturned = nextToReturn;
            Word tmp = lastReturned.data;
            nextToReturn = nextToReturn.next; 
            return tmp;
        }

        public void remove() {
            if (lastReturned == null)
                throw new NullPointerException("next() has not been called yet.");
            
            String word_to_remove = lastReturned.data.getWord();

            SortedLinkedList.this.remove(word_to_remove); 
        }
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    public Iterator<Word> iterator() 
    {
        return new ListIterator();
    }

}
