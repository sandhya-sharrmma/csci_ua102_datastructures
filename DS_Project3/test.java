import java.util.*;

public class test 
{
    public static void main (String[] args) 
    {
        DLL<Integer> iList1 = new DLL<>();
        DLL<Integer> iList2 = new DLL<>();

        for(int k = 1; k< 5; k++) {
            iList1.add(k); 
            iList2.add(k); 
        }

        iList1.add(2);
        iList1.add(3);
        iList1.add(4);
        iList1.add(-1);
        iList1.add(0);
        iList1.add(1);

        System.out.println(iList1);

        // System.out.println(iList.remove("p"));
        System.out.println(iList1.equals(null));
    }
}

