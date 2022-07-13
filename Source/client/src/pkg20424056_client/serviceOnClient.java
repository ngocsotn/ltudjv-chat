/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NGOC
 */
public class serviceOnClient extends Thread{
    private chat_window cw = null;
    tcp_client tcpc = null;

    @Override
    public void run()
    {
        while(true)
        {
            if(tcpc.inFromServer == null)
            {
                return;
            }
            
            String newMsg = "";
            try {
                newMsg = tcpc.inFromServer.readLine();
            } catch (IOException ex) {
                Logger.getLogger(serviceOnClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //[message/refresh]-[...]
            String []items = newMsg.split("-", 2); // tách làm 2  mảnh
            if(items.length > 1)
            {
                if(items[0].equals("refresh"))
                {
                    //System.out.println("recieve refresh from server: " + newMsg);
                    if(items[0] !=null)
                    {
                        if(items[1].length() > 0)
                        {
                            tcpc.onlineUsers = items[1].split("-");   
                        }
                    }
                }
   
                //[message]-[fromUser-toUser-content]
                else if(items[0].equals("message"))
                {
                    //System.out.println("recieve message from server: " + newMsg);
                    //fromUser-toUser-Content
                    String []msgItems = items[1].split("-", 3); // tách làm 3  mảnh

                    if(client_main.windowList.isEmpty() == false)
                    {
                        cw = client_main.windowList.get(msgItems[0]);
                        cw.appendMessage(msgItems[0] + ": " + msgItems[2]+"\n");
                    }
                }
                else
                {
                    System.out.println("recieve random from server: " + newMsg);
                }
            }
        }
    }
}

