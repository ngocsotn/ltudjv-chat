/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 *
 * @author NGOC
 */
public class fileController {
        public Boolean isExisted(String path)
    {
        File f = new File(path);
        if(f.exists() && !f.isDirectory()) { 
            f.exists();
            return true;
        }
        f.exists();
        return false;
    }
    
    public void createEmptyFile(String path) throws IOException
    {
        File f = new File(path);
        f.delete();
        f.createNewFile();
        f.exists();
    }
    
    private void makeFileEmpty(String path) throws IOException
    {
        File f = new File(path);
        f.delete();
        f.createNewFile();
        f.exists();
        
    }
    
    public Boolean copyFile(String cmd, String srcPath, String desPath) throws FileNotFoundException, IOException
    {
    // https://www.journaldev.com/861/java-copy-file
        if(cmd.equals("copy")) // else "backup"
        {
            if(!isExisted(srcPath) || isExisted(desPath))
            {
                return false;
            }
        }
        else if(cmd.equals("overWrite"))
        {
            if(!isExisted(srcPath))
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        
        InputStream is = null;
        OutputStream os = null;
        try {
           is = new FileInputStream(srcPath);
           os = new FileOutputStream(desPath);
           byte[] buffer = new byte[1024];
           int length;
           while ((length = is.read(buffer)) > 0) {
               os.write(buffer, 0, length);
           }
       } finally {
           is.close();
           os.close();
       }
       return true;
    }
    
    public String[] readSetting(String path) throws FileNotFoundException, IOException
    {
        if(!isExisted(path))
        {
            createEmptyFile(path);
            String [] tmp = new String[1];
            return tmp;
        }
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        
        String [] rs = new String[2];
        int i = 0;
        while (br.ready()) {
            if(i == 2)
            {
                break;
            }
            String item = br.readLine();
            rs[i] = item;
            i++;
        }
        if(i < 2)
        {
            rs = new String [1];
        }
        
        br.close();
        fr.close();

        return rs;
    }
    
    public Boolean rewriteSetting(String path, String [] newData) throws IOException
    {
        makeFileEmpty(path);

        BufferedWriter bw = null;
        FileWriter fw = null;
        File file = new File(path);
        fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        String content = "";
        
        for ( String item : newData)
        {      
            content = "";
            content += item + "\n";
            bw.write(content);
        }
        
        bw.close();
        fw.close();
        file.exists();
        return true;
    }
}
