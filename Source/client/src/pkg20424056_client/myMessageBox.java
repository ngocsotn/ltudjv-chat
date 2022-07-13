/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_client;

import javax.swing.JOptionPane;

/**
 *
 * @author NGOC
 */
public class myMessageBox {
    public static void showMessage(String titleBar, String infoMessage)
        {
            if(titleBar == null || infoMessage == null)
            {
                return;
            }
            JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
        }
        
    public static Boolean showYesNo(String titleBar, String infoMessage)
        {
            if(titleBar == null || infoMessage == null)
            {
                return false;
            }
            int dialogResult = JOptionPane.showConfirmDialog (null, infoMessage,titleBar,JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
              return true;
            }
            return false;
        }
}
