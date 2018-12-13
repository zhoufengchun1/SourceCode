package Practice;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.Format;
import java.util.Date;

public class FileDemo
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("C:/");
        RemoveList(file);
    }

    public static void RemoveList(File file)
    {
        File file1[] = file.listFiles();
        /*if(file1==null)
            return;*/
        //这两句很重要
        for(int i=0;i<file1.length;i++)
        {
            if (file1[i].isFile())
            {

                System.out.println(file1[i].toString()+file1[i].delete());
            }else
                RemoveList(file1[i]);
        }
    }
}
