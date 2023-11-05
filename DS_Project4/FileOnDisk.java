import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class FileOnDisk extends File
{
    //data fields 
    private String pathname; //path of the file or directory
    private List<File> all_files; //list of files in the directory
    private long totalSize; //total size of the file or directory

    //constructor
    public FileOnDisk(String pathname) throws NullPointerException
    {
        super(pathname);
        this.pathname = pathname;
        this.all_files = new ArrayList<File>(); 
        getAllFiles(this); 
        this.totalSize = this.getTotalSize(); 
    }

    public void getAllFiles(File temp)
    {
        if(temp.exists() && temp.isFile()){
            all_files.add(temp);
            totalSize += temp.length();
            return; 
        }
 
        if (temp.exists() && temp.isDirectory()){
            File[] files = temp.listFiles(); 

            if (files.length == 0)
                return;

            for (int i = 0; i < files.length; i++)
                getAllFiles(files[i]);
        }               
    } 

    public long getTotalSize()
    {
        return totalSize;
    }
    
    public List <FileOnDisk> getLargestFiles(int numOfFiles)
    {
        List<FileOnDisk> largest_files = new ArrayList<>();

        if (this.exists() && this.isFile())
            return null; 
        else if (this.exists() && this.isDirectory()){
            for (int i = 0; i < all_files.size(); i++)
                largest_files.add(new FileOnDisk(all_files.get(i).getAbsolutePath()));
            largest_files.sort(new FileOnDiskComparatorBySize());
            if (numOfFiles < largest_files.size())
                largest_files = largest_files.subList(0, numOfFiles);
            Collections.reverse(largest_files);
        }
        else
            throw new IllegalStateException("Object is neither a file nor a directory.");
        
        return largest_files;
    }

    @Override
    public String toString()
    {
        String final_output = " "; 

        if (all_files.size() == 0)
            final_output = "The given file/directory is empty.";
         
        String size = memoryConverter(); 

        final_output = size + "     " + pathname;
        
        return final_output;
    }

    public String memoryConverter() {
        String size = " ";

        if (totalSize < 1024)
            size = String.format("%8.2f %s", Double.valueOf(totalSize), "bytes");
        else if (totalSize >= 1024 && totalSize < 1024*2024)
            size = String.format("%8.2f %s", Double.valueOf(totalSize/1024.0), "KB");
        else if (totalSize >= 1024*2024 && totalSize < 1024*1024*1024)
            size = String.format("%8.2f %s", Double.valueOf(totalSize/(1024.0*1024)), "MB");
        else if (totalSize >= 1024*1024*1024)
            size = String.format("%8.2f %s", Double.valueOf(totalSize/(1024.0*1024*1024)), "GB"); 

        return size;
    }
}
