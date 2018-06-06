package demo.controller.xtjg;

import demo.controller.BaseController;
import demo.domain.entity.Xtjg;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
import demo.service.xtjg.XtjgService;
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
 * 系统机构Controller
 */
@Controller
@RequestMapping("/xtjg")
public class XtjgController extends BaseController {
    //系统机构Service
    @Autowired
    private XtjgService xtjgService;

    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtjg/" + page);
    }

    //系统机构列表
    @RequestMapping("/xtjgList")
    public ModelMap xtjgList(String jgmc, String jgdm, Long sjjgId, PageOption option) {
        ModelMap map = new ModelMap();
        option.setTotal(xtjgService.getXtjgTotal(jgmc, jgdm, sjjgId));
        map.put("xtjgList", xtjgService.getXtjgList(jgmc, jgdm, sjjgId, option));
        map.put("option", option);
        return map;
    }

    //系统机构编辑
    @RequestMapping("/xtjgEdit")
    public ModelMap xtjgEdit(Long id, Long sjjgId) {
        ModelMap map = new ModelMap();
        if (CommonUtil.isNotNull(id)) {
            map.put("xtjg", xtjgService.getXtjg(id));
        }
        if (CommonUtil.isNotNull(sjjgId)) {
            map.put("sjjg", xtjgService.getXtjg(sjjgId));
        }
        return map;
    }

    //系统机构保存
    @ResponseBody
    @RequestMapping("/saveXtjg")
    public String saveXtjg(Xtjg xtjg, HttpSession session) {
        try {
            xtjgService.saveXtjg(xtjg, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    //机构名称或机构代码重复校验
    @ResponseBody
    @RequestMapping("/isRepeat")
    public String isRepeat(Long id, String mc, String type) {
        try {
            if (xtjgService.isRepeat(id, mc, type)) {
                return Zxbz.CG.getIndex();
            } else {
                return Zxbz.SB.getIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.SB.getIndex();
        }
    }

    //系统机构删除
    @ResponseBody
    @RequestMapping("/deleteXtjg")
    public String deleteXtjg(Long id, HttpSession session) {
        try {
            xtjgService.deleteXtjg(id, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }


    // 删除时检查有无下级机构
    @ResponseBody
    @RequestMapping("/hasXjjg")
    public String hasXjjg(Long id) {
        return xtjgService.hasXjjg(id) ? Zxbz.CG.getIndex() : Zxbz.SB.getIndex();
    }

    //删除时检查机构下有无人员
    @ResponseBody
    @RequestMapping("/hasJgYh")
    public String hasJgYh(Long id) {
        return xtjgService.hasJgYh(id) ? Zxbz.CG.getIndex() : Zxbz.SB.getIndex();
    }
}