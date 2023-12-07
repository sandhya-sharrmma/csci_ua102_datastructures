package project5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a binary search tree to store Word objects. 
 * It implements Index interface and Iterable interface.   
 *
 * @author Sandhya Sharma  
 * @version December 2, 2023
 *
 */
public class BSTIndex implements Index, Iterable<Word> 
{
    //Inner class to represent nodes of this tree 
    private class Node implements Comparable<Node> 
    {
        Word data;
        Node left;
        Node right;

        public Node(String word){
            if(word == null) 
                throw new NullPointerException ("Does not allow null."); 
            this.data = new Word(word);
        }

        //implementing the compareTo method of the Comparable interface
        public int compareTo(Node n) {
            return this.data.compareTo(n.data);
        }
    }
    
    //private members: a reference to the root node and the size of the tree
    private Node root;
    private int size;

    /**
     * Constructs a new empty binary search tree.
     * It sets the root to null and size to 0.
     */
    public BSTIndex() 
    {
        root = null;
        size = 0;
    }

    
     /**
     * Adds an item to the index in sorted order: a Word object bigger than the root 
     * is added to the right subtree and a Word object smaller than the root is added
     * to the left subtree.
     * If the Word object with the same string already exists, its count is 
     * incremented by one. 
     * 
     * References taken from @author Joanna Klukowska's implementation in IntBST class
     * 
     * @param word new item to be added
     * @throws IllegalArgumentException when item is null
     */
    public void add(String word) throws IllegalArgumentException
    {
        //if the word is null, throw an exception
        if(word == null)
            throw new IllegalArgumentException("Word to be added cannot be null or empty");
        
        if(word.trim().isEmpty())
            return;

        //convert the word to lowercase and trim it
        word = word.toLowerCase().trim();

        // if the tree is empty, create a new node and set it as the root
        if (root == null){
            root = new Node(word);
            size++;
            return;
        }   

        Node current = root;
        while (current != null){
            // if the word is less than the current node, go to the left subtree
            if (word.compareTo(current.data.getWord()) < 0){ 
                if (current.left == null){
                    current.left = new Node(word);
                    size++; 
                    return; 
                }
                else 
                    current = current.left;
                
            }
            // if the word is greater than the current node, go to the right subtree
            else if(word.compareTo(current.data.getWord()) > 0){
                if(current.right == null){
                    current.right = new Node(word);
                    size++;
                    return;
                } 
                else 
                    current = current.right;
            }
            // if the word is equal to the current node, increment the count
            else{ 
                current.data.incrementCount();
                return;
            }
        }
    }
    
     /**
     * Removes an item from the tree if it exists, otherwise the tree remains
     * unchanged. This operation removes the Word object matching the
     * item regardless of what the count is.
     *
     * @param word item to be removed
     */ 
    public void remove(String word) 
    {
        if(word == null || word.trim().isEmpty())
            return;

        word = word.toLowerCase().trim();

        //calls to the recursive remove method explained below in detail
        root = remove(root, word);
    }
    
     /**
     * Returns the node after modifying the references to the nodes in the tree 
     * such that the Word object with the @param word is removed from the tree. 
     * If the Word object with the same string does not exist, 
     * the method returns the node without any modifications.
     *
     * @param node the node of the tree to be checked for the word to be removed
     * @param word the word (Word object's attribute) to be removed from the tree
     * @return the node of the tree (after the removal)
     */ 
    private Node remove(Node node, String word)
    {
        if (node == null)
            return null;
    
        int cmp = word.compareTo(node.data.getWord());
        if (cmp < 0) 
            node.left = remove(node.left, word);
        else if (cmp > 0)
            node.right = remove(node.right, word);
        else {
            // case 1: no children
            if (node.left == null && node.right == null) {
                size--;
                return null;
            }
            // case 2: one child
            else if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            }
            // case 3: two children
            else {
                Node temp = findSuccessor(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data.getWord());
            }
        }
    
        return node;
    }
    
    /**
     * Finds the successor of the given node argument.
     * 
     * @param node the node whose successor is to be found
     * @return the successor of the given node
     */
    private Node findSuccessor(Node node) 
    {
        while (node.left != null)
            node = node.left;
        
        return node;
    }
    
    /**
     * Returns the count of the Word object associated with the given string, 
     * or -1 if such a Word object does not exist.
     * 
     * @param word the word whose count should be returned
     * @return the count associated with the word, or -1 if the word does not exist
     */
    public int get(String word) 
    {
        Node current = root;
        while(current != null){
            if(word.compareTo(current.data.getWord()) < 0)
                current = current.left;
            else if(word.compareTo(current.data.getWord()) > 0)
                current = current.right;
            else if(word.compareTo(current.data.getWord()) == 0)
                return current.data.getCount();
        }
        return -1;
    }

    /**
     * Returns the number of unique words stored in the index.
     * NOTE: this counts each word only once even it the count associated 
     * with a word is larger than one
     * @return number of items stored in the index
     */

    public int size()
    {
        return size;
    }

    /**
     * Returns a string representation of this tree in the following format:
     * [count word, count word, ...] 
     * 
     * @return a string representation of this tree
     */
    @Override
    public String toString()
    {
        String to_return = "["; 

        if(root == null)
            return "[]";

        Iterator<Word> it = this.iterator();
        while(it.hasNext())
            to_return = to_return + it.next().toString() + ", ";

        //remove the last comma and space in the string
        if (to_return.toString().endsWith(", "))
            to_return = to_return.substring(0, to_return.length() - 2);
        
        to_return = to_return + "]";

        return to_return.toString();
    }

    /**
     * An equals method to compare this tree with a SortedLinkedList implementation 
     * for the same input data. 
     * Two data structures are equal if they contain the same number of elements and each 
     * element is equal to the corresponding element in the other data structure.
     * Here, the two data structures being compared for equality is this tree and the
     * SortedLinkedList implementation. 
     * 
     * Given that two data structures are being compared, it uses the iterator implemented 
     * for each data structure to traverse through each data structure and compare the
     * elements at each position. 
     * 
     * @param o the object to compare with this tree
     * @return true if the specified object is equal to this tree, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (!(o instanceof SortedLinkedList))
            return false;
        SortedLinkedList other = (SortedLinkedList) o;

        if(this.size() != other.size())
            return false;

        Iterator<Word> tree_iterator = this.iterator();
        Iterator<Word> list_iterator = other.iterator();

        while(tree_iterator.hasNext() && list_iterator.hasNext()){
            Word w1 = tree_iterator.next();
            Word w2 = list_iterator.next();
            if(!w1.equals(w2))
                return false;
        }
        return true;
    }

    /**
     * An iterator class implemented to traverse through the tree in inorder traversal.
     * It uses an ArrayList to store the elements in the tree in inorder traversal, 
     * meaning, the elements in this BST is traversed in order and each Word object is added
     * to the ArrayList when an iterator is instantiated. 
     * 
     * The iterator has three methods: hasNext(), next(), and remove().
     */
    private class TreeIterator implements Iterator<Word> 
    {
        //private members: an ArrayList to store the elements in the tree in inorder traversal
        //and two variables to keep track of the current index and the last returned index
        private ArrayList<Word> arrayList;
        private int currentIndex;
        private int lastReturnedIndex;

        /*
         * Constructs a new iterator for this tree. 
         */
        public TreeIterator(){
            arrayList = new ArrayList<>();
            currentIndex = 0;
            inorderTraversal(root);
        }

        /**
         * Traverses through the tree in inorder traversal and adds each Word object to the 
         * ArrayList. 
         * 
         * @param node the node to start the traversal from (begins with the root)
         */
        private void inorderTraversal(Node node){
            if (node != null){
                inorderTraversal(node.left);
                arrayList.add(node.data);
                inorderTraversal(node.right);
            }
        }

        /**
         * Returns true if the iteration has more elements.
         */
        @Override
        public boolean hasNext(){
            return currentIndex < arrayList.size();
        }
        
        /**
         * Returns the next element in the iteration and advances the iterator
         * in the arraylist. 
         * The variable lastReturnedIndex is used to keep track of the last returned 
         * element in the iteration to be used in the remove() method.
         * 
         * @throws NoSuchElementException if the iteration has no more elements 
         * @return the next element in the iteration
         */
        @Override
        public Word next(){
            if (!hasNext()) {
                throw new NoSuchElementException("End of the list reached.");
            }
            lastReturnedIndex = currentIndex;
            currentIndex++; 
            return arrayList.get(lastReturnedIndex);
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator.
         * This method can be called only once per call to next(). 
         * 
         * It uses the lastReturnedIndex variable to remove the element from the ArrayList.
         * It also removes the Word object from the tree using the remove() method implemented 
         * above.
         * 
         * @throws IllegalStateException if the next method has not yet been called meaning the 
         * iterator is at the beginning of the list. 
         */
        public void remove(){
            if(lastReturnedIndex == -1)
                throw new IllegalStateException("Iterator at the beginning of the list.");
            
            Word word_to_remove = arrayList.get(lastReturnedIndex);
            arrayList.remove(lastReturnedIndex);
            BSTIndex.this.remove(word_to_remove.getWord());
            if (lastReturnedIndex < currentIndex) {
                currentIndex--;
            }
            lastReturnedIndex = -1;
        }
    }

    /**
     * Returns an iterator over the elements in the tree.
     * 
     * @return an iterator over the elements in the tree
     */
    @Override
    public Iterator<Word> iterator() {
        return new TreeIterator();
    }
}
