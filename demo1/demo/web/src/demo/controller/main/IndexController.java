package demo.controller.main;
import demo.controller.BaseController;
import demo.service.xtcd.XtcdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.Map;
/**
 * 主页Controller
 */
@Controller
@RequestMapping("/main")
public class IndexController extends BaseController {
    @Autowired
    private XtcdService xtcdService;
    /**
     * 系统用户菜单模块
     */
    @RequestMapping("/left")
    public ModelMap left(HttpSession session) {
        Long yhId = getLoginXtyh(session).getId();
        ModelMap map = new ModelMap();
        Map cdmap = xtcdService.getCdList(yhId);
        session.setAttribute("yhcdList", cdmap.get("yhcdList"));
        //具有子级菜单的菜单个数
        session.setAttribute("count", cdmap.get("count"));
        return map;
    }
}
