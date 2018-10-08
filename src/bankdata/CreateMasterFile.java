package bankdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateMasterFile
{
    private Path inputFile = Paths.get("/home/vijay/nbprojects/bankdata/oldmaster.txt");
    private Path outputFile = Paths.get("master.txt");
    private BufferedReader reader;
    private BufferedWriter writer;
    private Charset cs = Charset.defaultCharset();
    
    public static void main(String[] args) throws IOException
    {
        CreateMasterFile cmf = new CreateMasterFile();
        cmf.writeMaster();
    }
    
    public void writeMaster() throws IOException
    {
        try
        {
            this.reader = Files.newBufferedReader(inputFile, cs);
            this.writer = Files.newBufferedWriter(outputFile);
            String line;
            int srno=0;
            while((line=reader.readLine())!=null)
            {
                if(srno%25==0)this.getHeader();
                String inputLine = String.format("%5d!%s%n",++srno,line);
                this.writer.write(inputLine);
                //System.out.format("%5d!%s%n",++srno,line);
                
            }
            this.writer.write("-----!---------!--------------------------------------!-----------\n");
        }
        catch(IOException ex)
        {
            System.err.format("Unable to read file [%s]%n", ex.getMessage());
        }
        finally
        {
            this.writer.close();
            this.reader.close();
        }
    }
    
    public void getHeader() throws IOException
    {
        try
        {
            this.writer.write("-----!---------!--------------------------------------!-----------\n");
            this.writer.write("SrNO.! Cust-ID !                 NAME                 !   PAN     \n");
            this.writer.write("-----!---------!--------------------------------------!-----------\n");
        }
        catch(IOException ex)
        {
            System.err.format("Unable to read file [%s]%n", ex.getMessage());
        }
        finally
        {
            this.reader.close();
            this.writer.close();
        }
    }
}
