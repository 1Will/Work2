package demo.controller.xtyh;

import demo.controller.BaseController;
import demo.domain.entity.Xtyh;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
import demo.service.xtjg.XtjgService;
import demo.service.xtyh.XtyhService;
import demo.service.xtzd.XtzdService;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 用户管理模块Controller
 */
@Controller
@RequestMapping("/xtyh")
public class XtyhController extends BaseController {
    //系统用户Service
    @Autowired
    private XtyhService xtyhService;
    //系统机构Service
    @Autowired
    private XtjgService xtjgService;
    //数据字典Service
    @Autowired
    private XtzdService xtzdService;

    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtyh/" + page);
    }

    //用户列表
    @RequestMapping("/xtyhList")
    public ModelMap getXtyhList(PageOption option, String yhmc, Long jgid) {
        ModelMap map = new ModelMap();
        option.setTotal(xtyhService.getXtyhTotal(yhmc, jgid));
        map.put("xtyhList", xtyhService.getXtyhList(option, yhmc, jgid));
        map.put("option", option);
        return map;
    }

    //删除用户
    @ResponseBody
    @RequestMapping("/deleteXtyh")
    public String deleteXtyh(Long id, HttpSession session) {
        try {
            xtyhService.deleteXtyh(id, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    // 维护用户
    @RequestMapping("xtyhEdit")
    public ModelMap xtyhEdit(Long id, Long jgid) {
        ModelMap map = new ModelMap();
        Xtyh xtyh = new Xtyh();
        if (CommonUtil.isNotNull(jgid)) {
            xtyh.setJg_id(jgid);
            xtyh.setJgmc(xtjgService.getXtjg(jgid).getJgmc());
        }
        if (CommonUtil.isNotNull(id)) {
            xtyh = xtyhService.getXtyhById(id);
            map.put("data", xtyhService.getYhjsInfo(xtyh.getId()));
            map.put("xtyh", xtyh);
        }
        map.put("xbList", xtzdService.getXtzdByZddm("XB"));
        return map;
    }

    //保存用户
    @ResponseBody
    @RequestMapping("xtyhSave")
    public String xtyhSave(HttpSession session, Xtyh xtyh, String jsIds) {
        try {
            xtyhService.saveXtyh(xtyh, getLoginXtyh(session).getId(), jsIds);
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    //用户角色集合
    @RequestMapping("/yhjsList")
    public ModelMap yhjsList(String jsIds) {
        ModelMap map = new ModelMap();
        map.put("jsIds", jsIds);
        return map;
    }

    //验证登录名是否重复
    @ResponseBody
    @RequestMapping(value = "isDlmRepeated")
    public String isDlmRepeated(Long id, String dlm) {
        try {
            return xtyhService.isDlmRepeated(id, dlm) ? Zxbz.CG.getIndex() : Zxbz.SB.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    //验证身份证号是否重复
    @ResponseBody
    @RequestMapping(value = "isIdNumberRepeated")
    public String isIdNumberRepeated(Long id, String idCard) {
        try {
            return xtyhService.isIdNumberRepeated(id, idCard) ? Zxbz.CG.getIndex() : Zxbz.SB.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }
}
