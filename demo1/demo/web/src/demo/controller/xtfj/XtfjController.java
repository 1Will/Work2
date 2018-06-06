package demo.controller.xtfj;

import demo.controller.BaseController;
import demo.domain.entity.Xtfj;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
import demo.service.xtfj.XtfjService;
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

/**
 * 附件Controller
 */
@Controller
@RequestMapping("/xtfj")
public class XtfjController extends BaseController {

    @Autowired
    private XtfjService xtfjService;

    private final static String FILE_PATH = "/upload";

    /**
     * 公用跳转页面
     */
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtfj/" + page);
    }
    /**
     * @param xxId     信息发布ID
     * @param option
     * @return
     */
    @RequestMapping("/xtfjList")
    public ModelMap xtfjList(Long xxId, PageOption option) {
        ModelMap map = new ModelMap();
        if (CommonUtil.isNotNull(xxId)) {
            try {
                option.setTotal(xtfjService.getFjTotal(xxId));
                map.put("option", option);
                map.put("xtfjList", xtfjService.getFjList(option, xxId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 添加上传附件——demo3
     *
     * @param files
     * @param xxid
     * @param session
     * @return 返回Json {files:[{"id":"id","name":"name"},...]}
     */
    @ResponseBody
    @RequestMapping(value = "/addXtfj", produces = "text/json;charset=UTF-8")
    public String addXtfj(@RequestParam(value = "file", required = false) MultipartFile[] files, Long xxid, HttpSession session) {
        StringBuffer result = new StringBuffer("{\"files\": [");
        try {
            for (int i = 0; i < files.length; i++) {
                Xtfj xtfj = new Xtfj();
                xtfj.setFjmc(files[i].getOriginalFilename());
                xtfj.setFjdx(files[i].getSize());
                //编辑信息发布时，直接添加附件
                xtfj.setXxfbId(xxid);
                xtfj.setAnnex(files[i].getBytes());
                Long id = xtfjService.saveXtfj(xtfj, getLoginXtyh(session).getId());
                if (i > 0) {
                    result.append(",");
                }
                String name = files[i].getOriginalFilename();
                result.append("{\""+ name +"\":\"true\",\"id\":\"" + id + "\",\"name\":\"" + name + "\"} ");
            }
            result.append("]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * 附件下载——demo3
     *
     * @param id 附件id
     * @param session
     * @param response
     */
    @ResponseBody
    @RequestMapping("/downloadXtfj")
    public void downloadXtfj(Long id, HttpSession session, HttpServletResponse response) {
        try {
            Xtfj xtfj = xtfjService.getXtfjById(id);
            FileUtil.downloadFile(xtfj.getFjmc(),xtfj.getAnnex(),response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除附件
     *
     * @param id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteXtfj")
    public String deleteXtfj(Long id, HttpSession session) {
        try {
            xtfjService.deleteXtfj(id, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }


    /**
     * 添加上传附件——demo2
     *
     * @param files
     * @param xxid
     * @param session
     * @return 返回Json {files:[{"id":"id","name":"name"},...]}
     */
    @ResponseBody
    @RequestMapping(value = "/addXtfj_old", produces = "text/json;charset=UTF-8")
    public String addXtfjOld(@RequestParam(value = "file", required = false) MultipartFile[] files, Long xxid, HttpSession session) {
        StringBuffer result = new StringBuffer("{\"files\": [");
        try {
            for (int i = 0; i < files.length; i++) {
                Xtfj xtfj = new Xtfj();
                xtfj.setFjmc(files[i].getOriginalFilename());
                xtfj.setFjdx(files[i].getSize());
                //编辑信息发布时，直接添加附件
                xtfj.setXxfbId(xxid);
                String path = FileUtil.uploadFile(session, files[i], FILE_PATH);
                xtfj.setCclj(path);
                Long id = xtfjService.saveXtfj(xtfj, getLoginXtyh(session).getId());
                if (i > 0) {
                    result.append(",");
                }
                String name = files[i].getOriginalFilename();
                result.append("{\""+ name +"\":\"true\",\"id\":\"" + id + "\",\"name\":\"" + name + "\"} ");
            }
            result.append("]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * 附件下载——demo2
     *
     * @param id 附件id
     * @param session
     * @param response
     */
    @ResponseBody
    @RequestMapping("/downloadXtfj_old")
    public void downloadXtfjOld(Long id, HttpSession session, HttpServletResponse response) {
        try {
            Xtfj xtfj = xtfjService.getXtfjById(id);
            FileUtil.downloadFile(xtfj.getFjmc(),xtfj.getCclj(),session,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
