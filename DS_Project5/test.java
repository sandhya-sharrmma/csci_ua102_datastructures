package project5;

public class test 
{
    public static void main(String[] args) {
        Word word = new Word("1hello");
        Word word2 = new Word("helle");
        Word word3 = new Word("hell");
        Word word4 = new Word("you");
        Word word5 = new Word("and");
        Word word6 = new Word("ME");
        BSTIndex bst = new BSTIndex();

        bst.add(word2.getWord());
        bst.add(word.getWord());
        bst.add(word3.getWord());
        bst.add(word4.getWord());
        bst.add(word5.getWord());
        bst.add(word6.getWord());



        System.out.println(bst);
    }
}
