package demo.controller.tree;

import demo.controller.BaseController;
import demo.service.xtyh.XtyhService;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
/**
 * 系统角色树Controller
 */
@Controller
@RequestMapping("/tree")
public class JsTreeController extends BaseController {
    @Autowired
    private XtyhService xtyhService;

    //用户角色树
    @ResponseBody
    @RequestMapping("/getYhjsTree")
    public List getYhjsTree(String jsIds) {
        List<Map> list = xtyhService.getYhjsList();
        List<Map> nodes = new ArrayList();
        for (Map map : list) {
            HashMap<String, Object> treeNode = new HashMap();
            if (CommonUtil.isNotNull(jsIds)) {
                jsIds = "," + jsIds + ",";
                if (jsIds.contains("," + map.get("ID") + ",")) {
                    treeNode.put("checked", true);
                }
            }
            treeNode.put("id", map.get("ID"));
            treeNode.put("name", map.get("JSMC"));
            nodes.add(treeNode);
        }
        return nodes;
    }
}
