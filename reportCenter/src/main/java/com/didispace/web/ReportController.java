package com.didispace.web;

import com.didispace.entity.Report;
import com.didispace.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Controller
public class ReportController {

    public String url = this.getClass().getClassLoader().getResource("").getPath() + "static/grf/";

    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page,ModelMap map,String report) {
        map.addAttribute("report", report);
        return new ModelAndView("/index/" + page);
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/report")
    public String report(ModelMap map, Report report) {
        map.addAttribute("report", report);
        return "index/index";
    }

    @RequestMapping("/editReport")
    public String editReport(ModelMap map, String report) {
        map.addAttribute("report", report);
        return "index/edit";
    }

    @RequestMapping("/saveReport")
    public String saveReport(HttpServletRequest request, String report) {
        int DataLen = request.getContentLength();
        if (DataLen > 0) {
            final int BufSize = 1024; //一次读写数据的缓冲区大小
            //打开写入文件
            String fileName = url + report + ".grf";
            try {
                FileOutputStream fos = new FileOutputStream(fileName.substring(1, fileName.length()));
                //注意：要分批读写，不然在某些条件下对大数据(>8K)模板保存不成功
                //读出客户端发送的数据，并写入文件流
                byte[] DataBuf = new byte[BufSize];
                ServletInputStream sif = request.getInputStream();

                int TotalReadedLen = 0;
                while (DataLen > TotalReadedLen) {
                    int TheReadedlen = sif.read(DataBuf, 0, BufSize);
                    if (TheReadedlen <= 0)
                        break;
                    fos.write(DataBuf, 0, TheReadedlen);
                    TotalReadedLen += TheReadedlen;
                }
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "forward:/editReport?grf=" + report;
    }

    @RequestMapping("/deleteReport")
    @ResponseBody
    public String deleteReport(String report) {
        String fileName = url + report + ".grf";
        File file = new File(fileName);
        file.delete();
        return "1";
    }

    @RequestMapping("/reportList")
    public String reportList(ModelMap map) {
        String fileName = url;
        File fileDir = new File(fileName);
        File[] fs = fileDir.listFiles();
        List names = new ArrayList<String>();
        for (File f : fs) {
            names.add(f.getName().split("\\.")[0]);
        }
        map.put("names", names);
        return "index/reportList";
    }


    @RequestMapping("/downloadReport")
    public void downloadReport(HttpServletResponse response, String report) {
        String fileName = url + report + ".grf";
        File file = new File(fileName);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            //设定输出文件头
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(report + ".grf", "UTF-8"));
            //设置响应头
            response.setContentType("application/x-msdownload");
            os.write(FileUtil.readFileToByte(file));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/uploadReport")
    public String uploadReport(MultipartFile file){
        Map<String ,Object> map = new HashMap();
        try {
            File newFile = new File(url+file.getOriginalFilename());
            FileUtil.writeFileByByte(file.getBytes(),newFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{\n" +
                "\t\"code\": 1,\n" +
                "\t\"msg\": \"success\"\n" +
                "}";
    }
}