package demo.controller.xtsq;

import demo.controller.BaseController;
import demo.domain.entity.Xtjg;
import demo.domain.entity.Xtsq;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
import demo.service.xtjg.XtjgService;
import demo.service.xtsq.XtsqService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

/**
 * 社区管理模块Controller
 *
 * @author k
 */
@Controller
@RequestMapping("/xtsq")
public class XtsqController extends BaseController {
    @Autowired
    private XtsqService xtsqService;

    @Autowired
    private XtjgService xtjgService;

    private final static String PIC_PATH = "/upload";

    /**
     * 公用跳转页面
     */
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtsq/" + page);
    }

    /**
     * 社区列表
     */
    @RequestMapping("/xtsqList")
    public ModelMap getXtsqList(PageOption option, String sqmc, Long jgid) {
        ModelMap map = new ModelMap();
        try {
            map = xtsqService.getXtsqMap(option, sqmc, jgid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 社区保存
     *
     * @param sqmc 社区名称
     * @param id   社区id
     * @param jgid 机构id
     * @return String
     */
    @ResponseBody
    @RequestMapping("/xtsqSave")
    public String xtyhSave(HttpSession session, String sqmc, Long id, Long jgid, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Xtsq xtsq;
            if (CommonUtil.isNotNull(id)) {
                xtsq = xtsqService.getXtsqById(id);
            } else {
                xtsq = new Xtsq();
            }
            xtsq.setSqmc(sqmc);
            if (file != null) {
                String path = FileUtil.uploadFile(session, file, PIC_PATH);
                xtsq.setCclj(path);
                xtsq.setZpmc(file.getOriginalFilename());
            }
            xtsqService.saveXtsq(xtsq, getLoginXtyh(session).getId(), jgid);
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    /**
     * 维护社区
     *
     * @param id 社区id
     * @return 社区
     */
    @RequestMapping("xtsqEdit")
    public ModelMap xtsqEdit(Long id, Long jgid) {
        ModelMap map = new ModelMap();
        Xtsq xtsq = new Xtsq();
        if (CommonUtil.isNotNull(jgid)) {
            Xtjg xtjg = xtjgService.getXtjg(jgid);
            xtsq.setJgmc(xtjg.getJgmc());
            xtsq.setJgId(xtjg.getId());
        }
        if (CommonUtil.isNotNull(id)) {
            xtsq = xtsqService.getXtsqById(id);
        }
        map.put("xtsq", xtsq);
        return map;
    }

    /**
     * 删除社区
     *
     * @param id      社区id
     * @param session session
     * @return String
     */
    @ResponseBody
    @RequestMapping("/deleteXtsq")
    public String deleteXtsq(Long id, HttpSession session) {
        try {
            xtsqService.deleteXtsq(id, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    /**
     * 模板下载
     *
     * @param file     名称
     * @param session  session
     * @param response response
     */
    @ResponseBody
    @RequestMapping("/downloadSqExcel")
    public void downloadSqExcel(String file, HttpSession session, HttpServletResponse response) {
        try {
            file = URLDecoder.decode(file, "UTF-8");
            String[] name = file.split("/");
            FileUtil.downloadFile(name[name.length - 1], file, session, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Excel导入
     *
     * @param file    文件
     * @param session session
     * @return 返回信息
     */
    @ResponseBody
    @RequestMapping(value = "/importSqExcel", produces = "text/html;charset=UTF-8")
    public String importSqExcel(@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) {
        String msg;
        try {
            msg = xtsqService.importSq(file, session, getLoginXtyh(session).getId());
        } catch (Exception e) {
            e.printStackTrace();
            msg = "cw";
        }
        return msg;
    }

}