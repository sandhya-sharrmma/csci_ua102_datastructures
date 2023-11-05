import java.util.Comparator;

public class FileOnDiskComparatorBySize implements Comparator<FileOnDisk>
{
    public int compare(FileOnDisk o1, FileOnDisk o2)
    {
        if(o1 == null || o2 == null)
            throw new NullPointerException("ERROR: One or both of the files are null."); 

        if(o1.exists() == false || o2.exists() == false)
            throw new IllegalStateException("ERROR: One or both of the files do not exist.");

        if(o1 == o2)
            return 0;
        
        if(o1.getTotalSize() > o2.getTotalSize())
            return 1;
        else if(o1.getTotalSize() < o2.getTotalSize())
            return -1;
        else{
            int temp = -5; 
            try{
                temp = o1.getCanonicalFile().compareTo(o2.getAbsoluteFile());
            }
            catch(Exception e){
                System.out.println("ERROR: " + e.getMessage());
            }

            return temp; 
        }
            
    }
}
