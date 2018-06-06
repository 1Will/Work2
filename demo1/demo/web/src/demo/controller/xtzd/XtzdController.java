package demo.controller.xtzd;

import demo.controller.BaseController;
import demo.domain.entity.Xtzd;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典Controller
 */
@Controller
@RequestMapping("/xtzd")
public class XtzdController extends BaseController {

    //系统字典Service
    @Autowired
    private XtzdService xtzdService;

    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtzd/" + page);
    }

    //字典管理列表
    @RequestMapping("/xtzdList")
    public ModelMap xtzdList(String zdmc, String zddm, String sjzd, PageOption option) {
        ModelMap map = new ModelMap();
        option.setTotal(xtzdService.getXtzdTotal(zdmc, zddm, sjzd));
        map.put("xtzdList", xtzdService.getXtzdList(zdmc, zddm, sjzd, option));
        map.put("option", option);
        return map;
    }

    // 字典维护
    @RequestMapping("/xtzdEdit")
    public ModelMap xtzdEdit(Long id, Long sjzd) {
        ModelMap map = new ModelMap();
        map.put("sjzdList", xtzdService.getAllSjzd());
        map.put("sjzd", sjzd);
        if (CommonUtil.isNotNull(id)) {
            map.put("xtzd", xtzdService.getXtzdById(id));
        }
        return map;
    }

    //保存字典
    @ResponseBody
    @RequestMapping(value = "/saveXtzd")
    public String saveXtzd(Xtzd xtzd, HttpSession session) {
        try {
            //判断字典代码是否重复，如果重复则不允许保存
            if (xtzdService.isZddmCf(xtzd.getId(), xtzd.getZddm())) {
                xtzdService.saveXtzd(xtzd, getLoginXtyh(session).getId());
                return Zxbz.CG.getIndex();
            } else {
                return Zxbz.SB.getIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    //删除字典
    @ResponseBody
    @RequestMapping("/deleteXtzd")
    public String deleteXtzd(Long id, HttpSession session) {
        try {
            xtzdService.deleteXtzd(id, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    //判断选中字典项是否有下级
    @ResponseBody
    @RequestMapping("/checkXtzd")
    public String checkXtzd(Long id) {
        try {
            if (xtzdService.checkXtzd(id)) {
                return Zxbz.CG.getIndex();
            } else {
                return Zxbz.SB.getIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

    //字典项树
    @ResponseBody
    @RequestMapping("/getXtzdTree")
    public List getXtzdTree() {
        List nodes = new ArrayList();
        try {
            List<Xtzd> list = xtzdService.getAllSjzd();
            if (CommonUtil.isNotNull(list) && list.size() > 0) {
                for (Xtzd xtzd : list) {
                    Map map = new HashMap();
                    map.put("id", xtzd.getId());
                    map.put("pId", xtzd.getSjzd_id());
                    map.put("name", xtzd.getZdmc());
                    map.put("open", !CommonUtil.isNotNull(xtzd.getSjzd_id()));
                    nodes.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }
}
