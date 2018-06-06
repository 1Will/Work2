package demo.controller.xtjs;


import demo.controller.BaseController;
import demo.domain.entity.Xtjs;
import demo.domain.enums.Zxbz;
import demo.domain.pojo.PageOption;
import demo.service.xtjs.XtjsService;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 系统角色Controller
 */
@Controller
@RequestMapping("/xtjs")
public class XtjsController extends BaseController {
    //系统角色Service
    @Autowired
    private XtjsService xtjsService;

    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtjs/" + page);
    }


    //系统角色列表
    @RequestMapping("/xtjsList")
    public ModelMap getXtjsList(PageOption option, String jsmc) {
        ModelMap map = new ModelMap();
        Integer xtjsTotal = xtjsService.getXtjsTotal(jsmc); //查询角色管理总数
        List list = xtjsService.getXtjsListPage(jsmc, option);
        option.setTotal(xtjsTotal);
        map.put("xtjsList", list);
        map.put("option", option);
        return map;
    }

    //系统角色编辑
    @RequestMapping("/xtjsEdit")
    public ModelMap xtjsEdit(Long id) {
        ModelMap map = new ModelMap();
        if (CommonUtil.isNotNull(id)) {//修改
            map.put("data", xtjsService.getJscdByjsId(id));
            map.put("xtjs", xtjsService.getXtjsById(id));
        }
        return map;
    }

    //系统角色保存
    @ResponseBody
    @RequestMapping("/saveXtjs")
    public String saveXtjs(Xtjs xtjs, HttpSession session) {
        try {
            xtjsService.saveXtjs(xtjs, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

 //角色名称或角色代码重复校验
    @ResponseBody
    @RequestMapping("/isRepeat")
    public String isRepeat(Long id, String mc, String type) {
        try {
            if (xtjsService.isRepeat(id, mc, type)) {
                return Zxbz.CG.getIndex();
            } else {
                return Zxbz.SB.getIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.YC.getIndex();
        }
    }

   //系统角色删除
    @ResponseBody
    @RequestMapping("/deleteXtjs")
    public String deleteXtjs(Long id, HttpSession session) {
        try {
            xtjsService.deleteXtjs(id, getLoginXtyh(session).getId());
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            return Zxbz.YC.getIndex();
        }
    }
}
