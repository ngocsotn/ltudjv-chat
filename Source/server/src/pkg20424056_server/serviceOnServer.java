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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NGOC
 */
public class serviceOnServer extends Thread{
    private ServerSocket svSocket = null;
    private server_main _main = null;
    public void setIn(ServerSocket _svSocket, server_main _mainin)
    {
        svSocket = _svSocket;
        _main = _mainin;
    }
    @Override
    public void run()
    {
        while(true)
        {
            Socket newClient = null;
            try {
                newClient = svSocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(serviceOnServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            _main.appendLog("A client connected, IP: " + newClient.getRemoteSocketAddress().toString() +"\n");
            
            
            
            serviceForClient t1 = new serviceForClient();
            try {
                t1.setIn(newClient, "Guest", _main);
            } catch (IOException ex) {
                Logger.getLogger(serviceOnServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            t1.start();
//            socketDetails tmp = new socketDetails(newClient, inFromClient, outToClient);
//            socketList.put(String.valueOf(num), tmp);       

        }
    }
}
