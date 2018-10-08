package bankdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFiles
{
    public static void readMaster()
    {
        BufferedReader br = null;
        Path inputMaster = Paths.get("master.txt");
        Charset cs = Charset.forName("US-ASCII");
        Pattern pattern = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
        Matcher matcher;
        String custId,name,pan;
        try
        {
            br = Files.newBufferedReader(inputMaster, cs);
            String line = null;
            while((line = br.readLine())!=null)
            {
                custId = line.substring(6,15);
                //System.out.println("custId "+custId);
                matcher = pattern.matcher(custId);
                if(matcher.find())
                {
                    name = line.substring(16,54);
                    pan = line.substring(56, 66);
                    MyDatabase.insertMaster(custId, name, pan);
                }
                    
            }
        }
        catch(IOException ex)
        {
            System.err.println("Error:"+ex.getMessage());
        }
    }
 
    public static void readDetail()
    {
        BufferedReader br = null;
        Path inputDetail = Paths.get("detail.txt");
        Charset cs = Charset.forName("US-ASCII");
        Pattern pattern = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
        Matcher matcher;
        String custId;
        try
        {
            br = Files.newBufferedReader(inputDetail, cs);
            String line = null;
            while((line = br.readLine())!=null)
            {
                if(line.length()<9) continue;
                custId = line.substring(0,9);
                //System.out.println("custId "+custId);
                matcher = pattern.matcher(custId);
                if(matcher.find())
                {
                    //System.out.println(" -> "+custId);
                    MyDatabase.insertDetail(custId);
                }
                    
            }
        }
        catch(IOException ex)
        {
            System.err.println("Error:"+ex.getMessage());
        }
    }   
    
}
