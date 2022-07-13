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
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NGOC
 */
public class serviceForClient extends Thread
    {
        String fromUser = "";
        String msgFromClient = "";
        BufferedReader clientReader = null;
        DataOutputStream outToClient = null;
        Socket client = null;
        private server_main _main = null;
        private controller clr = new controller();
        
        public void setIn(Socket newClient,String _from, server_main _mainin) throws IOException
        {
            fromUser = _from;
            client = newClient;
            _main = _mainin;
            
            //Tạo outputStream, nối tới socket
            outToClient = new DataOutputStream(newClient.getOutputStream());
            //Tạo input stream, nối tới Socket
            clientReader = new BufferedReader(new InputStreamReader(newClient.getInputStream()));
        }

        @Override
        public void run()
        {
            if(clientReader == null)
            {
                System.out.println("Thread error!");
                return;
            }
            
            while(true)
            {
                try {
                    msgFromClient = clientReader.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(serviceForClient.class.getName()).log(Level.SEVERE, null, ex);
                }

                String []items = msgFromClient.split("-", 3); // tách làm 3  mảnh

                //System.out.println(items[0]);

                //reigster-username-pwd
                if(items[0].equals("register"))
                {
                    _main.appendLog("This user want to register with username: [" + items[1] + "]\n");
                    try {
                        if(clr.reigster(items[1], items[2]) == false)
                        {
                            _main.appendLog("Register with username ["+ items[1] +"] failed\n");
                            //send msg back
                            outToClient.writeBytes("fail\n"); 
                            return;
                        }
                        else
                        {
                            _main.appendLog("Register with username ["+ items[1] +"] successfully\n");
                            //send msg back
                            outToClient.writeBytes("ok\n"); 
                            return;
                        }

                        //end thread after reigster
                    } catch (SQLException ex) {
                        Logger.getLogger(serviceForClient.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(serviceForClient.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }            
                //login-username-pwd
                else if(items[0].equals("login"))
                {   
                    _main.appendLog("User ["+items[1]+ "] logging in\n");        
                    try {
                        if(clr.login(items[1], items[2]) == false)
                        {
                            _main.appendLog("User ["+ items[1] +"] login failed\n");
                            //send msg back
                            outToClient.writeBytes("fail\n"); 
                        }
                        else
                        {
                            fromUser = items[1];
                            _main.appendLog("User[ "+ items[1] +"] login successfully\n");
                            //send msg back
                            outToClient.writeBytes("ok\n");

                            socketDetails tmp = new socketDetails(client);
                            tmp.setCurrentUser(fromUser);
                            tcp_server.socketList.put(fromUser, tmp);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(serviceForClient.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(serviceForClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //origin: message-toUser-content
                //convert to: msessage-fromUser-toUser-content
                else if(items[0].equals("message"))
                {
                    //get distination
                    socketDetails sd = tcp_server.socketList.get(items[1]);

                    //make new type of string
                    //msessage-fromUser-toUser-content
                    String rs = "message-";
                    items[0] = fromUser;
                    for (String item : items)
                    {
                        rs += item +'-';
                    }     
                    rs = rs.substring(0, rs.length() - 1);
                    rs +="\n";
                    System.out.println(rs);
                    try {
                        //send msg
                        sd.sendMessage(rs);
                    } catch (IOException ex) {
                        Logger.getLogger(serviceForClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                //refresh-username-username
                else if(items[0].equals("refresh"))
                {
                    //refresh-user1-user2-...
                    String rs = "refresh-";
                    for(String key: tcp_server.socketList.keySet() )
                    {
                        if(key.equals(items[1]))
                        {
                            //skip self-online
                            continue;
                        }
                        rs += key + "-";
                    }
                    if(rs.length() > 1)
                    {
                        rs = rs.substring(0, rs.length() - 1);
                    }
                    
                    _main.appendLog("User ["+ items[1] +"] want to refresh online list\n");
                    try {
                        //send msg back
                        outToClient.writeBytes(rs + "\n");
                    } catch (IOException ex) {
                        Logger.getLogger(serviceForClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //exit-username-username
                else if(items[0].equals("exit"))
                {
                    _main.appendLog("User ["+ fromUser + "] exited from server!\n");
                    tcp_server.socketList.remove(fromUser);
                    return;
                }
            }
        }
    }
