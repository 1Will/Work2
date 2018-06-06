package demo.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ash on 2016-04-29.
 */
public class Base64Util {
    // 图片转化成base64字符串
    public static String GetImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            if (imgFile == null || "".equals(imgFile)) {
                return null;
            }
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    // base64字符串转化成图片
    public static boolean generateImage(String imgStr, String imgFilePath, String fileName) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
        {
            return false;
        }

        try {
            File file = new File(imgFilePath);
            Boolean hasDir = true;
            if (!(hasDir = file.exists())) {
                hasDir = file.mkdirs();
            }
            // Base64解码
            if (hasDir) {
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] b = decoder.decodeBuffer(imgStr);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {// 调整异常数据
                        b[i] += 256;
                    }
                }
                // 生成jpeg图片
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(imgFilePath + "\\" + fileName));
                imageOutput.write(b, 0, b.length);
                imageOutput.close();
            }
            return hasDir;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
