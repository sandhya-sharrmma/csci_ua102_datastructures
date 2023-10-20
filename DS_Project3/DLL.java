// package project3; 

import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import java.util.Iterator;

public class DLL<E extends Comparable<E>> implements Iterable<E>
{
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

    public DLL()
    {
        head = null; 
        tail = null;
        size = 0;
    }

    public int size()
    {
        if (head == null)
            return 0;
        
        int size = 0;

        Node current = head;

        while(current != null){
            size++;
            current = current.next;
        }

        return size;
    }

    public boolean add(E element) throws NullPointerException
    {
        if (element == null)
            throw new NullPointerException("Cannot add null.");

        Node newNode = new Node(element);

        if (size == 0){
            head = tail = newNode;
            size++;
            return true;
        }
        
        Node current = head;

        while(current.next != null){
            if(current.data.compareTo(newNode.data) > 0){
                if(current == head){
                    newNode.next = head;
                    head.prev = newNode;
                    head = newNode;
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
        size++;
        return true; 
    }

    public void clear()
    {
        head = null;
        tail = null; 
        size = 0; 
    }

    public boolean contains(Object o)
    {
        if (o == null)
            return false;
        
        if(this.size() == 0)
            return false;

        Node current = head;

        while(current != null){
            if(current.data.equals(o))
                return true;
            current = current.next;
        }

        return false;
    }

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

    public String toString()
    {
        String toReturn = "[";

        Node current = head;

        while(current != null){
            if(current.next != null)
                toReturn += current.data + ", ";
            else 
                toReturn += current.data + "]"; 
            current = current.next;
        }

        return toReturn;
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) 
    {
        if(o == null)
            return false;
        
        if(this.size() == 0)
            return false;

        if(!o.getClass().equals(this.getClass()))
            return false;
        
        if(this.size() != ((DLL)o).size())
            return false;

        if(o == this)
            return true;
        
        Node cur1 = this.head;
        Node cur2 = ((DLL)o).head;

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
                throw new NoSuchElementException("the end of the list reached");
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next; 
            return tmp;
        }
    }

    public Iterator<E> iterator() 
    {
        return new ListIterator();
    }

}
