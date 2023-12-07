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
     * If the element is already present in the list, its count is incremented by 1.
     *
     * @param element the element to add
     * @throws IllegalArgumentException if the element is null
     */
    public void add(String element)
    {
        if(element == null)
            throw new IllegalArgumentException("Element cannot be null.");  
        
        element = element.toLowerCase().trim();

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
                else{
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
     * Returns the count of the specified word in this list 
     * or -1 if this list does not contain the word.
     *
     * @param word the word whose count is to be returned
     * @return the count of the specified word in this list, 
     * or -1 if this list does not contain the word
     */
    public int get(String word)
    {
        if(word == null || this.size() == 0 || word.trim().isEmpty())
            return -1;

        word = word.toLowerCase().trim();

        Node current = head; 

        while(current != null){
            if(current.data.getWord().equals(word))
                return current.data.getCount();
            current = current.next;
        }

        return -1;
    }

     /**
     * Removes an item from the list if it exists, otherwise the list remains
     * unchanged. This operation removes the Word object matching the
     * word regardless of what the count is.
     * 
     * @param word the word to remove, if present
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
    @Override
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
   /**
    * A class for forward iterator for this list. 
    * 
    * The class has three methods: hasNext(), next(), and remove().
    */
    private class ListIterator implements Iterator<Word> 
    {
        Node nextToReturn = head;
        Node lastReturned = null;

        /**
         * Returns true if the iteration has more elements, false otherwise.
         */
        @Override
        public boolean hasNext() 
        {
            return nextToReturn != null;
        }

        /**
         * Returns the next element in the iteration.
         * It also keeps track of the last returned element for the remove() method.
         * 
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Word next() throws NoSuchElementException 
        {
            if (nextToReturn == null)
                throw new NoSuchElementException("End of the list reached.");
            lastReturned = nextToReturn;
            Word tmp = lastReturned.data;
            nextToReturn = nextToReturn.next;
            return tmp;
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator.
         * This method can be called only once per call to next().
         * 
         * @throws IllegalStateException if the next method has not yet been called. 
         */
        public void remove() throws IllegalStateException
        {
            if (lastReturned == null)
                throw new IllegalStateException("next() has not been called yet.");
            
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
