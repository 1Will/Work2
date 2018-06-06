package demo.controller.xtcd;

import demo.controller.BaseController;
import demo.domain.entity.Xtcd;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
import demo.service.xtcd.XtcdService;
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
 * 系统菜单Controller
 */
@Controller
@RequestMapping("/xtcd")
public class XtcdController extends BaseController {
    //系统菜单Service
    @Autowired
    private XtcdService xtcdService;

    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtcd/" + page);
    }

    //菜单管理列表
    @RequestMapping("/xtcdList")
    public ModelMap xtcdList(String cdmc, String ssxt, PageOption option) {
        ModelMap map = new ModelMap();
        option.setTotal(xtcdService.getCdTotal(cdmc, ssxt));
        map.put("option", option);
        map.put("xtcdList", xtcdService.getCdList(option, cdmc, ssxt));
        return map;
    }

    //判断是否有子菜单
    @ResponseBody
    @RequestMapping("/hasChildMenu")
    public String hasChildMenu(Long cdId, String ssxt) {
        try {
            if (xtcdService.hasChildMenu(cdId, ssxt)) {
                return Zxbz.CG.getIndex();
            } else {
                return Zxbz.SB.getIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    //删除菜单
    @ResponseBody
    @RequestMapping("/deleteCd")
    public String deleteCd(Long cdId, HttpSession session, String ssxt) {
        try {
            xtcdService.deleteCd(cdId, getLoginXtyh(session).getId(), ssxt);
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }

    }

    //编辑菜单
    @RequestMapping("/xtcdEdit")
    public ModelMap xtcdEdit(Long id) {
        ModelMap map = new ModelMap();
        try {
            if (CommonUtil.isNotNull(id)) {
                map.put("cd", xtcdService.getCdById(id));
            } else {
                map.put("initXh", xtcdService.getInitXh());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    //检查菜单名称是否重复
    @ResponseBody
    @RequestMapping("/checkCdmc")
    public String checkCdmc(Long id, String cdmc, String ssxt) {
        try {
            if (xtcdService.checkCdmc(id, cdmc, ssxt)) {
                return Zxbz.CG.getIndex();
            } else {
                return Zxbz.SB.getIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }

    }

    //保存菜单实体
    @ResponseBody
    @RequestMapping("/saveXtcd")
    public String saveXtcd(Xtcd xtcd, HttpSession session) {
        try {
            xtcdService.saveXtcd(xtcd, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }
}
