package demo.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author wy
 * @date ${date}
 * $END
 */
public class FileUtil {

    /**
     * 文件上传
     *
     * @param session
     * @param file
     * @return 返回 访问路径
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String uploadFile(HttpSession session, MultipartFile file, String path) throws FileNotFoundException, IOException {
        //随机文件名
        String name = getRandomName(file.getOriginalFilename());
        //本地保存路径
        String savePath = session.getServletContext().getRealPath(path) + "\\" + name;
        //访问路径
        String visitPath = path + "/" + name;
        File saveFile = new File(savePath);
        //文件夹不存在则创建
        createFolder(saveFile);
        file.transferTo(saveFile);
        return visitPath;
    }

    /**
     * 富文本中文件上传
     *
     * @param fileName 真实文件名
     * @param path     相对路劲
     * @param buffer   文件字节数组
     * @param session
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String uploadFile(String fileName, String path, byte buffer[], HttpSession session) throws FileNotFoundException, IOException {
        //随机文件名
        String name = getRandomName(fileName);
        //本地保存路径
        String savePath = session.getServletContext().getRealPath(path) + "\\" + name;
        //访问路径
        String visitPath = path + "/" + name;
        File saveFile = new File(savePath);
        //文件夹不存在则创建
        createFolder(saveFile);
        if (buffer.length >= 0) {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile, true));
            out.write(buffer);
            out.close();
        }
        return visitPath;
    }

    /**
     * 文件下载
     *
     * @param name     文件名
     * @param path     文件路径
     * @param session  HttpSession
     * @param response HttpServletResponse
     * @throws IOException
     */

    public static void downloadFile(String name, String path, HttpSession session, HttpServletResponse response) throws IOException {
        // 1.获取要下载的文件的绝对路径
        String realPath = session.getServletContext().getRealPath(path);
        // 2.获取要下载的文件名
        // 3.设置content-disposition响应头控制浏览器弹出保存框，若没有此句则浏览器会直接打开并显示文件。中文名要经过URLEncoder.encode编码，否则虽然客户端能下载但显示的名字是乱码
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
        // 4.获取要下载的文件输入流
        InputStream in = new FileInputStream(realPath);
        int len = 0;
        // 5.创建数据缓冲区
        byte[] buffer = new byte[1024];
        // 6.通过response对象获取OutputStream流
        OutputStream out = response.getOutputStream();
        // 7.将FileInputStream流写入到buffer缓冲区
        while ((len = in.read(buffer)) > 0) {
            // 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
            out.write(buffer, 0, len);
        }
        out.close();
        in.close();
    }


    /**
     * 文件下载
     *
     * @param name     文件名
     * @param buffer   文件字节数组
     * @param response HttpServletResponse
     * @throws IOException
     */

    public static void downloadFile(String name, byte[] buffer, HttpServletResponse response) throws IOException {
        // 1.设置content-disposition响应头控制浏览器弹出保存框，若没有此句则浏览器会直接打开并显示文件。中文名要经过URLEncoder.encode编码，否则虽然客户端能下载但显示的名字是乱码
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
        // 2.通过response对象获取OutputStream流
        OutputStream out = response.getOutputStream();
        //写文件
        out.write(buffer);
        out.close();
    }


    /**
     * 获取随机文件名
     *
     * @param fileName 文件名
     * @return 随机文件名
     */
    public static String getRandomName(String fileName) {
        String[] names = fileName.split("\\.");
        String name;
        if (names.length > 1) {
            name = UUID.randomUUID() + "." + names[names.length - 1];
        } else {
            name = UUID.randomUUID() + "";
        }
        return name;
    }

    /**
     * 文件夹不存在则创建
     *
     * @param saveFile
     */
    public static void createFolder(File saveFile) {
        File fileParent = saveFile.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
    }
}
