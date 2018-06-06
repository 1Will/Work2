package demo.controller.xtxx;

import demo.controller.BaseController;
import demo.domain.entity.Xtxx;
import demo.domain.enums.Czbs;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
import demo.service.xtxx.XtxxService;
import demo.util.CommonUtil;
import demo.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * 信息发布Controller
 */
@Controller
@RequestMapping("/xtxx")
public class XtxxController extends BaseController {

    @Autowired
    private XtxxService xtxxService;

    private final static String FILE_PATH = "/upload";

    /**
     * 公用跳转页面
     */
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtxx/" + page);
    }


    /**
     * 获取信息列表
     *
     * @param session
     * @param xxbt
     * @param gjz
     * @param option
     * @return
     */
    @RequestMapping("/xtxxList")
    public ModelMap xtxxList(HttpSession session, String xxbt, String gjz, PageOption option) {
        ModelMap map = new ModelMap();
        Long fbr = getLoginXtyh(session).getId();
        try {
            option.setTotal(xtxxService.getXxTotal(fbr, xxbt, gjz));
            map.put("option", option);
            map.put("xtxxList", xtxxService.getXxList(option, fbr, xxbt, gjz));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 编辑信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/xtxxEdit")
    public ModelMap xtxxEdit(Long id) {
        ModelMap map = new ModelMap();
        Xtxx xtxx;
        try {
            if (CommonUtil.isNotNull(id)) {
                xtxx = xtxxService.getXxById(id);
            } else {
                xtxx = new Xtxx();
                xtxx.setCzbs(Czbs.SC.getIndex());
                xtxxService.saveXtxx(xtxx);
            }
            map.put("xtxx", xtxx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 保存信息发布
     *
     * @param session
     * @param xtxx
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveXtxx")
    public String saveXtxx(HttpSession session, Xtxx xtxx) {
        try {
            xtxxService.saveOrUpdateXtxx(xtxx, getLoginXtyh(session));
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    /**
     * 置顶/取消置顶信息
     *
     * @param id
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping("/toTop")
    public String toTop(HttpSession session, Long id, String flag) {
        try {
            Xtxx xtxx = xtxxService.getXxById(id);
            if ("1".equals(flag)) {
                xtxx.setTop(true);
            } else {
                xtxx.setTop(false);
            }
            xtxxService.cancleTop();
            xtxxService.saveXtxx(xtxx, getLoginXtyh(session));
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    /**
     * 删除信息
     *
     * @param id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteXtxx")
    public String deleteXtxx(Long id, HttpSession session) {
        try {
            xtxxService.deleteXtxx(id, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    /**
     * 富文本输入框文件上传
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFile", produces = "text/json;charset=UTF-8")
    public String uploadFile(HttpServletRequest request) {
        String message = "";
        try {
            String[] dispoString = request.getHeader("Content-Disposition").split("\"");
            //文件名
            String fileName = dispoString[dispoString.length - 1];
            int j = 0;
            int i = request.getContentLength();
            byte[] buffer = new byte[i];
            //获取表单的上传文件
            while (j < i) {
                int k = request.getInputStream().read(buffer, j, i - j);
                j += k;
            }
            // 上传文件
            String path = FileUtil.uploadFile(fileName, FILE_PATH, buffer, request.getSession());
            message = "{'err':'','msg':'" + path + "'}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


}