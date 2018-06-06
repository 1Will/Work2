package demo.controller.tree;

import demo.controller.BaseController;
import demo.domain.entity.Xtcd;
import demo.service.xtcd.XtcdService;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单树Controller
 */
@Controller
@RequestMapping("/tree")
public class CdTreeController extends BaseController {
    @Autowired
    private XtcdService xtcdService;

    //获取菜单树
    @ResponseBody
    @RequestMapping("/getCdTree")
    public List getCdTree(@RequestParam(required = false) Long id, String ssxt,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) String cdIds) {
        List<Map<String, Object>> nodes = new ArrayList();
        List<Xtcd> list = xtcdService.getCdTree(id, ssxt);
        Map<String, Object> map;
        for (Xtcd node : list) {
            map = new HashMap();
            map.put("id", node.getId());
            map.put("pId", node.getSjcdId() == null ? 0 : node.getSjcdId());
            map.put("name", node.getCdmc());
            map.put("open", node.getSjcdId() == null);
            if (CommonUtil.isNotNull(type)&&CommonUtil.isNotNull(cdIds)) {
                cdIds = "," + cdIds + ",";
                if (cdIds.contains("," + node.getId() + ",")) {
                    map.put("checked", true);
                }
            }
            nodes.add(map);
        }
        return nodes;
    }
}
