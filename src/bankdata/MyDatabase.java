package bankdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDatabase
{
     public static Connection getConnection()
    {
        Path p = Paths.get("db");
        try
        {
            Files.createDirectory(p);
        }
        catch(IOException ex)
        {
            System.err.println("Directory already exists"+ex.getMessage());
        }
        
        String url = "jdbc:sqlite:db/bank.db";
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException ex)
        {
            System.err.println("Connection not established with database:"+ex.getMessage());
        }
        return conn;
    }
    
    public static void createTables()
    {
        String sqlMaster = "CREATE TABLE IF NOT EXISTS master"
                    +"(cust_id text, name text, pan text)";
        String sqlDetail = "CREATE TABLE IF NOT EXISTS detail"
                    +"(cust_id text)";
        
        Connection conn = MyDatabase.getConnection();
        try(Statement stmt = conn.createStatement())
        {
            stmt.execute(sqlMaster);
            stmt.execute(sqlDetail);
        }
        catch(SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    public static void insertMaster(String custId, String name, String pan)
    {
        Connection conn = MyDatabase.getConnection();
        String sql = "INSERT INTO master(cust_id,name,pan) values(?,?,?)";
        try
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,custId);
            pstmt.setString(2, name);
            pstmt.setString(3, pan);
            pstmt.executeUpdate();
            System.out.println("custId "+custId);
        }
        catch(SQLException ex)
        {
            System.err.format("Unable to insrt data [%s]%n",ex.getMessage());
        }
    }
     public static void insertDetail(String custId)
    {
        Connection conn = MyDatabase.getConnection();
        String sql = "INSERT INTO detail(cust_id) values(?)";
        try
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,custId);
            pstmt.executeUpdate();
            System.out.println("custId "+custId);
        }
        catch(SQLException ex)
        {
            System.err.format("Unable to insrt data [%s]%n",ex.getMessage());
        }
    }
    
    public static void dropTables()
    {
        String sqlMaster = "DROP TABLE IF EXISTS master";
        String sqlDetail = "DROP TABLE IF EXISTS detail";
        
        Connection conn = MyDatabase.getConnection();
        try(Statement stmt = conn.createStatement())
        {
            stmt.execute(sqlMaster);
            stmt.execute(sqlDetail);
        }
        catch(SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
}
