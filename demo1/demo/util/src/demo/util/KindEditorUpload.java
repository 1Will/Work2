package demo.util;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KindEditor上传文件工具
 */
public class KindEditorUpload {
    private String PATH_LINE = "/";
    public void fileUpload(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("imgFile") MultipartFile[] imgFile) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        //文件保存本地目录路径
        String savePath = request.getSession().getServletContext().getRealPath(PATH_LINE) + "resources" + PATH_LINE + "extranet" + PATH_LINE;
        //文件保存目录URL
        String saveUrl=request.getScheme() //当前链接使用的协议
                +"://" + request.getServerName()//服务器地址
                + ":" + request.getServerPort() //端口号
                + request.getContextPath()+"/resources/extranet/";//应用名称
        if (!ServletFileUpload.isMultipartContent(request)) {
            out.print(getError("请选择文件。"));
            out.close();
            return;
        }
        //检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            out.print(getError("上传目录不存在。"));
            out.close();
            return;
        }
        //检查目录写权限
        if (!uploadDir.canWrite()) {
            out.print(getError("上传目录没有写权限。"));
            out.close();
            return;
        }
        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }
        //定义允许上传的文件扩展名
        Map<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,mp4,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,xml,txt,zip,rar,gz,bz2");
        if (!extMap.containsKey(dirName)) {
            out.print(getError("目录名不正确。"));
            out.close();
            return;
        }
        //创建文件夹
        savePath += dirName + PATH_LINE;
        saveUrl += dirName + PATH_LINE;
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + PATH_LINE;
        saveUrl += ymd + PATH_LINE;
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //最大文件大小
        long maxSize = 10000000;
        // 保存文件
        for (MultipartFile iFile : imgFile) {
            String fileName = iFile.getOriginalFilename();
            //检查文件大小
            if (iFile.getSize() > maxSize) {
                out.print(getError("上传文件大小超过限制。"));
                out.close();
                return;
            }
            //检查扩展名
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                out.print(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
                out.close();
                return;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            try {
                File uploadedFile = new File(savePath, newFileName);
                // 写入文件
                FileUtils.copyInputStreamToFile(iFile.getInputStream(), uploadedFile);
            } catch (Exception e) {
                out.print(getError("上传文件失败。"));
                out.close();
                return;
            }
            JSONObject obj = new JSONObject();
            obj.put("error", 0);
            obj.put("url", saveUrl + newFileName);
            out.print(obj.toString());
            out.close();
        }
    }
    private String getError(String errorMsg) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", errorMsg);
        return obj.toString();
    }
}