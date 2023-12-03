package project5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTIndex implements Index, Iterable<Word> 
{
    private class Node implements Comparable<Node> 
    {
        Word data;
        Node left;
        Node right;

        public Node(String word){
            if(word == null) 
                throw new NullPointerException ("does not allow null"); 
            this.data = new Word(word);
        }

        public int compareTo(Node n) {
            return this.data.compareTo(n.data);
        }
    }

    private Node root;
    private int size;

    public BSTIndex() 
    {
        root = null;
        size = 0;
    }

    public void add(String word) 
    {
        if(word == null)
            return;

        if (root == null){
            root = new Node(word);
            size++;
            return;
        }   

        Node current = root;
        while (current != null){
            if (word.compareTo(current.data.getWord()) < 0){ //add in the left subtree
                if (current.left == null){
                    current.left = new Node(word);
                    size++;
                    return; 
                }
                else 
                    current = current.left;
                
            }
            else if(word.compareTo(current.data.getWord()) > 0){//add in the right subtree
                if(current.right == null){
                    current.right = new Node(word);
                    size++;
                    return;
                } 
                else 
                    current = current.right;
            }
            else{ //duplicate
                current.data.incrementCount();
                return;
            }
        }
    }

    public void remove(String word) {
        if(word == null)
            return;
        root = remove(root, word);
    }
    
    private Node remove(Node node, String word) {
        if (node == null) {
            return null;
        }
    
        int cmp = word.compareTo(node.data.getWord());
        if (cmp < 0) {
            node.left = remove(node.left, word);
        } else if (cmp > 0) {
            node.right = remove(node.right, word);
        } else {
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
                Node temp = minNode(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data.getWord());
            }
        }
    
        return node;
    }
    
    private Node minNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

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

    public int size()
    {
        return size;
    }

    @Override
    public String toString()
    {
        String to_return = "["; 

        if(root == null)
            return "[]";

        Iterator<Word> it = this.iterator();
        while(it.hasNext()){
            to_return = to_return + it.next().toString() + ", ";
        }

        if (to_return.toString().endsWith(", "))
            to_return = to_return.substring(0, to_return.length() - 2);
        
        to_return = to_return + "]";

        return to_return.toString();
    }

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

    //ITERATORS
   /* A basic forward iterator for this list. */
    private class TreeIterator implements Iterator<Word> {
        private ArrayList<Word> arrayList;
        private int currentIndex;
        private int lastReturnedIndex;

        public TreeIterator(){
            arrayList = new ArrayList<>();
            currentIndex = 0;
            inorderTraversal(root);
        }

        private void inorderTraversal(Node node){
            if (node != null){
                inorderTraversal(node.left);
                arrayList.add(node.data);
                inorderTraversal(node.right);
            }
        }

        @Override
        public boolean hasNext(){
            return currentIndex < arrayList.size();
        }

        @Override
        public Word next(){
            if (!hasNext()) {
                throw new NoSuchElementException("End of the list reached.");
            }
            lastReturnedIndex = currentIndex;
            currentIndex++; 
            return arrayList.get(lastReturnedIndex);
        }

        public void remove(){
            if(lastReturnedIndex == -1)
                throw new IllegalStateException("Iterator at the beginning of the list – cannot remove.");
            
            Word word_to_remove = arrayList.get(lastReturnedIndex);
            arrayList.remove(lastReturnedIndex);
            BSTIndex.this.remove(word_to_remove.getWord());
            if (lastReturnedIndex < currentIndex) {
                currentIndex--;
            }
            lastReturnedIndex = -1;
        }
    }

    @Override
    public Iterator<Word> iterator() {
        return new TreeIterator();
    }
}


