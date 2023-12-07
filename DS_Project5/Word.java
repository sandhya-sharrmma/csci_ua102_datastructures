package project5;

/**
 * This class represents a Word object that stores the word and its frequency in the input file.
 * It implements Comparable interface to compare two Word objects lexicographically.
 *
 * @author Sandhya Sharma
 * @version December 3, 2023
 *
 */
public class Word implements Comparable<Word>
{
    //private members
    private String word; //the word stored in this object
    private int count; //the count of the word stored in this object
    
    /**
     * Constructs a new Word object with the given word and sets initial count as 1.
     * @param word the word to be stored
     * @throws NullPointerException if the word is null or empty 
     */
    public Word(String word) throws NullPointerException 
    {
        if(word == null || word.trim().isEmpty())
            throw new NullPointerException("Word cannot be null or empty.");
        
        this.word = word;
        this.count = 1;
    }

    /**
     * Increments the count of the word by 1.
     * @return the new count of the word
     */
    public int incrementCount()
    {
        this.count++;
        return this.count;
    }
    
    /**
     * Returns the word stored in this object.
     * @return the word stored in this object
     */
    public String getWord()
    {
        return this.word.toLowerCase();
    }
    
    /**
     * Returns the count of the word stored in this object.
     * @return the count of the word stored in this object
     */
    public int getCount()
    {
        return this.count;
    }
    
    /**
     * Returns a string representation of this object in the following format: count word
     * @return a string representation of this object
     */
    @Override
    public String toString()
    {
        return String.format("%5d  %s", this.count, this.word.toLowerCase());
    }

    /**
     * Compares two Word objects lexicographically.
     * @param other the Word object to be compared
     * @return a negative integer, zero, or a positive integer as this object is less 
     * than, equal to, or greater than the specified object.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (!(o instanceof Word))
            return false;
        Word other = (Word) o;

        if(this.word.equalsIgnoreCase(other.word) && this.count == other.count)
            return true;
        else
            return false;
    }
    
    /**
     * Compares two Word objects lexicographically.
     * @param other the Word object to be compared
     * @return a negative integer, zero, or a positive integer as this object is less 
     * than, equal to, or greater than the specified object.
     */
    public int compareTo(Word other)
    {
        return this.word.compareTo(other.word);
    }
}
