/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_server;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author NGOC
 */
public class controller {
    public boolean login(String username, String password) throws SQLException
    {
        Connection conn = (Connection) myConnection.reConnect();
        
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        
        ResultSet rst = null;
        if(conn != null)
        {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setString(1, username);
            pre.setString(2, password);

            rst = pre.executeQuery();
            int count = 0;
            while(rst.next())
            {
                count +=1;
                if(count > 0){
                    return true;
                }
            }
        }
        myConnection.forceClose();
        return false;
    }
    
    public boolean reigster(String username, String password) throws SQLException
    {
        if(login(username, password) == true)
        {
            return false;
        }
        
        Connection conn = (Connection) myConnection.reConnect();
        int rowsAffected = 0;
        String query = "INSERT INTO user(username,password) values(?,?)";
        
        if(conn != null)
        {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setString(1, username);
            pre.setString(2, password);
            
            rowsAffected = pre.executeUpdate();
        }
        myConnection.forceClose();
        
        if(rowsAffected > 0)
        {
            return true;
        }
        return false;
    }
}
