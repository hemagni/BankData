package bankdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Bankdata
{
    
    public static void main(String[] args)
    {
        //MyDatabase.createDatabase("bank.db");
        //MyDatabase.dropTables();
        //MyDatabase.createTables();
        //ReadFiles.readMaster();
        ReadFiles.readDetail();
        

    }
    
    public void readMaster()
    {
        List<String>master = new ArrayList<>(); 
        String custId=null,name=null,pan=null;
        Path file = Paths.get("/home/vijay/Downloads/master.txt");
        Charset charset = Charset.forName("US-ASCII");
        try(BufferedReader br = Files.newBufferedReader(file, charset))
        {
            String line = null;
            while((line = br.readLine())!=null)
            {
                master.add(line);
                if(line.length()<60) continue;
                custId = line.substring(0,9);
                name = line.substring(11, 48);
                pan = line.substring(50, 60);
                //System.out.format("Cust Id:%10s Name:%40s PAN:%12s%n",custId,name,pan);
            }
        }
        catch(IOException ex)
        {
            System.err.println("IOException: "+ex.getMessage());
        }
        String exp = "[^1-9]*";
        master.stream().filter(e->e.matches(exp)).forEach(e->System.out.println(e));
//        master.stream().forEach(e->{
//                                        System.out.println("line:"+e);
//                                    });
        
    }
    
    private Connection connect()
    {
        Connection conn = null;
        String url = "jdbc:sqlite:/home/vijay/sqlite/db/chinook.db";
        try
        {
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException ex)
        {
            System.err.println("Error:"+ex.getMessage());
        }
        return conn;
        
    }
    
    public void printData()
    {
        Connection conn = this.connect();
        String query = "select cust_id,name,pan from bank_data";
        String out = null;
        int counter =0;
        BufferedWriter bw = null;
        Path p = Paths.get("bankdata.txt");
        try
        {
            bw = Files.newBufferedWriter(p, Charset.forName("US-ASCII"));
        }
        catch(IOException ex)
        {
            System.err.println("Error:"+ex.getMessage());
        }
       
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            out=String.format("-------!---------!----------------------------------------!-----------%n");
            bw.write(out);
            out=String.format(" Sr.No.! Cust Id !             Name                       !     PAN   %n");
            bw.write(out);
            out=String.format("-------!---------!----------------------------------------!-----------%n");
            bw.write(out);
    
            while(rs.next())
            {
                System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));
                out = String.format("%7d %-9s %-40s %-11s%n",++counter,rs.getString(1),rs.getString(2),rs.getString(3));
                bw.write(out);
            }
        }
        catch(SQLException ex)
        {
            System.err.println("Error:"+ex.getMessage());
        }
        catch(IOException ex)
        {
            System.err.println("Error:"+ex.getMessage());
        }
        finally
        {
            try
            {
                if(conn!=null) conn.close();
                if(bw!=null) bw.close();
            }
            catch(SQLException ex)
            {
                System.err.println("Error While Clossing Connection: "+ex.getMessage());
            }
            catch(IOException ex)
            {
                System.err.println("Error:"+ex.getMessage());
            }
        }
    }
    
}
