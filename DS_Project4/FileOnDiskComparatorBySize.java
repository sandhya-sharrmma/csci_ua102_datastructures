// package project4;

import java.util.Comparator;

public class FileOnDiskComparatorBySize implements Comparator<FileOnDisk>
{
    public int compare(FileOnDisk o1, FileOnDisk o2)
    {
        if (o1 == null || o2 == null)
            throw new NullPointerException("ERROR: One or both of the files are null.");


        if(o1.getTotalSize() < 0 || o2.getTotalSize() < 0)
            throw new IllegalArgumentException("ERROR: One or both of the files have negative size."); 

        long sizeDiff = o2.getTotalSize() - o1.getTotalSize();
        if (sizeDiff != 0)
            return sizeDiff > 0 ? 1 : -1;

        String path1 = null;
        String path2 = null;

        try {
            path1 = o1.getCanonicalPath();
            path2 = o2.getCanonicalPath();
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        return path1.compareToIgnoreCase(path2);
    }
}
