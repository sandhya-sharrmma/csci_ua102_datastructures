package project5;

public class Word implements Comparable<Word>
{
    private String word;
    private int count;
    
    public Word(String word)
    {
        this.word = word;
        this.count = 1;
    }

    public int incrementCount()
    {
        this.count++;
        return this.count;
    }
    
    public String getWord()
    {
        return this.word.toLowerCase();
    }
    
    public int getCount()
    {
        return this.count;
    }
    
    public String toString()
    {
        return String.format("%5d  %s", this.count, this.word.toLowerCase());
    }
    
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
    
    public int compareTo(Word other)
    {
        return this.word.compareTo(other.word);
    }
}
