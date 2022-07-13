/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_server;

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
//import java.util.ArrayList;
//import java.util.Scanner;

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
        
    public String[] readUser(String path) throws FileNotFoundException, IOException
    {
        if(!isExisted(path))
        {
            createEmptyFile(path);
            String [] tmp = new String[1];
            return tmp;
        }
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        
        String [] rs = new String[6];
        int i = 0;
        while (br.ready()) {
            if(i == 6)
            {
                break;
            }
            String item = br.readLine();
            rs[i] = item;
            i++;
        }
        if(i < 6)
        {
            rs = new String [1];
        }
        
        br.close();
        fr.close();

        return rs;
    }
    
    public Boolean rewriteUser(String path, String [] newData) throws IOException
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
    
//    public ArrayList<hocsinh> importFileCSV(String path)
//    {
//        ArrayList<hocsinh> rs = new ArrayList<hocsinh>();
//        ArrayList<Integer> pk = new ArrayList<Integer>();
//        Scanner scan = null;
//        try
//        {
//            scan = new Scanner(new File(path));
//        }
//        catch(FileNotFoundException e)
//        {
//            System.out.println("Khong tim thay <" + path + "> de import CSV file!");
//            return null;
//        }
//        
//        try
//        {
//            rs.clear();
//            Boolean isFirstLine = false;
//            Boolean isFormatOK = true;
//
//            while(scan.hasNextLine()){
//                hocsinh tmp = null;
//
//                String line = scan.nextLine();
//
//                String[] dtArr = line.split(",", -1);
//                if(isFirstLine == false)
//                {
//                    isFirstLine = true;
//                    if(!dtArr[0].equals("mhs"))
//                    {
//                        System.out.println("Dinh dang file sai mhs");
//                        isFormatOK = false;
//                        break;
//                    }
//                    if(!dtArr[1].equals("tenhs"))
//                    {
//                        System.out.println("Dinh dang file sai tenhs");
//                        isFormatOK = false;
//                        break;
//                    }
//                    if(!dtArr[2].equals("diem"))
//                    {
//                        System.out.println("Dinh dang file sai diem");
//                        isFormatOK = false;
//                        break;
//                    }
//                    if(!dtArr[3].equals("hinhanh"))
//                    {
//                        isFormatOK = false;
//                        break;
//                    }
//                    if(!dtArr[4].equals("diachi"))
//                    {
//                        System.out.println("Dinh dang file sai diachi");
//                        isFormatOK = false;
//                        break;
//                    }
//                    if(!dtArr[5].equals("ghichu"))
//                    {
//                        System.out.println("Dinh dang file sai ghichu");
//                        isFormatOK = false;
//                        break;
//                    }
//                    continue;
//                }
//                
//                if(dtArr.length != 6)
//                {
//                    System.out.println("Dinh dang file sai hoac qua so dong");
//                    isFormatOK = false;
//                    break;
//                }
//                if(pk.contains(Integer.parseInt(dtArr[0])))
//                {
//                    System.out.println("Trung khoa chinh");
//                    return null;
//                }
//                
//                
//                tmp = new hocsinh();
//                tmp.setMHS(Integer.parseInt(dtArr[0]));
//                pk.add(tmp.getMHS());
//                tmp.setTenHS(dtArr[1]);
//                tmp.setDiem(Double.valueOf(dtArr[2]));
//                tmp.setHinhAnh(dtArr[3]);
//                tmp.setDiaChi(dtArr[4]);
//                tmp.setGhiChu(dtArr[5]);
//                
//                rs.add(tmp);
//            }
//            if(isFormatOK == false)
//            {
//                return null;
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("Import <" + path +"> that bai, file sai dinh dang hoac thieu du lieu!");
//            return null;
//        }
//        return rs;
//    }
    
//    public boolean exportFileCSV(ArrayList<hocsinh> ls, String path) throws IOException
//    {
//        makeFileEmpty(path);
//        
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//        File file = new File(path);
//        fw = new FileWriter(file.getAbsoluteFile(), true);
//        bw = new BufferedWriter(fw);
//        String content = "";
//        
//        bw.write("mhs,tenhs,diem,hinhanh,diachi,ghichu\n");
//        for (hocsinh item: ls)
//        {
//            content = String.valueOf(item.getMHS()) + ","
//                    + item.getTenHS() + ","
//                    + String.valueOf(item.getDiem()) + ","
//                    + item.getHinhAnh() + ","
//                    + item.getDiaChi() + ","
//                    + item.getGhiChu()+"\n";
//            bw.write(content);
//        }
//        
//        bw.close();
//        fw.close();
//        file.exists();
//        
//        return true;
//    }
}
