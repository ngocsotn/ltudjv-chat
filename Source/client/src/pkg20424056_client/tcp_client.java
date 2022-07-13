/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author NGOC
 */
public class tcp_client {
    private static String serverIP = "127.0.0.1";
    private static String  serverPort = "10000";
    private static Socket clientSocket = null;
    public static DataOutputStream outToServer = null;
    public static BufferedReader inFromServer = null;
    public static String currentUser = "Guest";
    public static String onlineUsers[] = null;
    
    public static boolean connectToServer(String IPin, String portin) throws IOException
    {
        serverIP = IPin;
        serverPort = portin;
        try{
            clientSocket = new Socket(serverIP, Integer.parseInt(serverPort));
        }
        catch(Exception e)
        {
            return false;
        }
        if(clientSocket.isConnected() == false)
        {
            return false;
        }
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        return true;
    }

    //message-toUser-content
    //fromsuer-touser-content
    public static void sendMessage(String toUser, String message) throws IOException
    {
        if(clientSocket == null)
        {
            return;
        }
        
        String toSend = "message-" + toUser + "-" + message + "\n";
        outToServer.writeBytes(toSend);
    }
    
    public static boolean refresh() throws IOException
    {
        if(clientSocket == null)
        {
            return false;
        }
        
        String toSend = "refresh-" + currentUser + "-" + currentUser + '\n';
        outToServer.writeBytes(toSend);
        
        return true;
    }
    
    // 0 = fail from server, 1 = success, 2 = pass not equal
    public static int register(String username, String password, String rePassword) throws IOException
    {
        if(clientSocket == null)
        {
            return 0;
        }
        if(!password.equals(rePassword))
        {
            return 2;
        }
        //send to server
        String toSend = "register-" + username + "-" + password + '\n';
        outToServer.writeBytes(toSend);
        String msgFromSv = inFromServer.readLine();
        if(msgFromSv.equals("ok"))
        {
            return 1;
        }
        
        if(clientSocket != null)
        {
            clientSocket.close();
        }
        
        return 0;
    }
    
    public static boolean login(String username, String password) throws IOException
    {
        if(clientSocket == null)
        {
            return false;
        }
        
        //send msg login
        String toSend = "login-" + username + "-" + password + '\n';
        outToServer.writeBytes(toSend);
        
        String msgFromSv = inFromServer.readLine();
        if(msgFromSv.equals("ok"))
        {
            currentUser = username;
            return true;
        }
        return false;
    }
    
    public static void closeConnect() throws IOException
    {
        if(clientSocket!= null)
        {
            String toSend = "exit-"+ currentUser + "-" + currentUser + "\n";
            outToServer.writeBytes(toSend);
            clientSocket.close();
        }
    }
}
