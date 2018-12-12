package com.didispace.util;

import java.io.*;

/**
 * 文件流工具类
 *
 */
public class FileUtil {

    /**
     * 如果文件夹不存在则创建
     *
     * @param file
     */
    public static void creatParentFile(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    /**
     * 判断文件是否存在，不存在创建文件
     *
     * @param file
     */
    public static void creatFile(File file) {
        if (!file.exists()) {
            try {
                creatParentFile(file);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入File
     *
     * @param b
     * @param file
     */
    public static void writeFileByByte(byte[] b, File file) {
        try {
            creatFile(file);
            OutputStream os = new FileOutputStream(file);
            for (int x = 0; x < b.length; x++) {
                os.write(b[x]);
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取File成byte数组
     *
     * @param file
     * @return
     */
    public static byte[] readFileToByte(File file) {
        try {
            //创建字节输入流
            InputStream myflie = new FileInputStream(file);
            //创建一个长度为myflie长的竹筒
            byte[] tem = new byte[myflie.available()];
            //“取水”
            myflie.read(tem);
            //关闭
            myflie.close();
            return tem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按编码读取
     * @param file
     * @param code
     * @return
     */
    public static String readFileWithCode(File file, String code){
        //创建字节输入流
        StringBuilder builder=new StringBuilder();
        BufferedReader reader=null;
        try {
            InputStream is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is,code));
            String line;
            while((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    public static String readFileWithCode(File file){
        return readFileWithCode(file,"UTF-8");
    }

    /**
     * 写入File
     *
     * @param b
     * @param file
     * @param code
     */
    public static void writeFileWithCode(String b, File file,String code) {
        try {
            creatFile(file);
            OutputStream os = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(os,code);
            osw.write(b);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileWithCode(String b, File file) {
        writeFileWithCode(b,file,"UTF-8");
    }
}