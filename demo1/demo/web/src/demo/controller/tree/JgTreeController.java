package demo.controller.tree;

import demo.controller.BaseController;
import demo.service.xtjg.XtjgService;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 系统机构树Controller
 */
@Controller
@RequestMapping("/tree")
public class JgTreeController extends BaseController {

    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("tree/" + page);
    }
    //机构Service
    @Autowired
    private XtjgService xtjgService;

    //系统机构树
    @ResponseBody
    @RequestMapping("/getXtjgTree")
    public List getXtjgTree(Long id, HttpServletRequest request) {
        String path = request.getContextPath();
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> list = xtjgService.getXtjgTree(id);
        if (CommonUtil.isNotNull(list) && list.size() > 0) {
            for (Map map : list) {
                Map<String, Object> node = new HashMap();
                node.put("id", map.get("ID"));
                node.put("pId", map.get("SJJG_ID"));
                node.put("name", map.get("JGMC"));
                node.put("icon", path + "/resources/image/1_close.png");
                node.put("isParent",Integer.parseInt(map.get("XJZS").toString())>0);
                node.put("open", true);
                nodes.add(node);
            }
        }
        return nodes;
    }
}