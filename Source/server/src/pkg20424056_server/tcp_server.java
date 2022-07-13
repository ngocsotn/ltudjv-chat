/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author NGOC
 */
public class tcp_server {

    /**
     * @return the tcpPort
     */
    public tcp_server(server_main mainin)
    {
        _main = mainin;
    }
    public String getTcpPort() {
        return tcpPort;
    }

    /**
     * @param tcpPort the tcpPort to set
     */
    public void setTcpPort(String _tcpPort) {
        this.tcpPort = _tcpPort;
    }

    /**
     * @return the serverIP
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * @param serverIP the serverIP to set
     */
    public void setServerIP(String _serverIP) {
        this.serverIP = _serverIP;
    }
    
    private String tcpPort = "10000";
    private String serverIP = "127.0.0.1";
    ServerSocket svSocket = null;
    private server_main _main = null;
    static HashMap<String, socketDetails> socketList = new HashMap<String, socketDetails>();
    serviceOnServer t0 = null;
    public String  getCurrentIP() throws UnknownHostException
    {
        String rs = "";
        
        InetAddress ipHostName = InetAddress.getLocalHost();     
        rs = ipHostName.toString();
        
        return rs;
    }
    
    public void startListenTCP() throws IOException
    {
        int theTCPPort = 10000;
        try{
            theTCPPort = Integer.parseInt(tcpPort);
        }
        catch(NumberFormatException e)
        {
            System.out.println(e.toString());
            theTCPPort = 10000;
        }
        //open port 
        svSocket = new ServerSocket(theTCPPort);
        _main.appendLog("Server is listening on port " + theTCPPort +"\n");
        t0 = new serviceOnServer();
        t0.setIn(svSocket, _main);
        t0.start();
        //wait for client
    }
    
    public void closeConnect() throws IOException
    {
        if(svSocket != null)
        {
            if(t0!=null)
            {
                t0.stop();
//                t0.interrupt();
            }
            svSocket.close();
        }
    }
    
}
