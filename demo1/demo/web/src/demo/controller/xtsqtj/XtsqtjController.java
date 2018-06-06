package demo.controller.xtsqtj;

import demo.controller.BaseController;
import demo.domain.entity.Xtjg;
import demo.domain.pojo.PageOption;
import demo.service.xtjg.XtjgService;
import demo.service.xtsq.XtsqService;
import demo.service.xtsqtj.XtsqtjService;
import demo.util.ExcelBean;
import demo.util.ExcelExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author a
 * @date ${date}
 * $END
 */
@Controller
@RequestMapping("/xtsqtj")
public class XtsqtjController extends BaseController {
    @Autowired
    private XtsqService xtsqService;

    @Autowired
    private XtjgService xtjgService;

    @Autowired
    private XtsqtjService xtsqtjService;

    /**
     * 公用跳转页面
     */
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("xtsqtj/" + page);
    }


    /**
     * 社区列表
     */
    @RequestMapping("/xtsqtjList")
    public ModelMap getXtsqtjList(PageOption option, Long jgId) {
        ModelMap map = new ModelMap();
        try {
            option.setTotal(xtsqtjService.getXtsqtjTotal(jgId));
            List data = xtsqtjService.getXtsqtjList(jgId);
            map.put("json", data.get(1));
            map.put("xtsqtjList", xtsqtjService.getXtsqtjList(option, jgId));
            map.put("option", option);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 社区列表
     *
     * @param option 页面对象
     * @param jgId   机构ID
     * @return 返回map
     */
    @RequestMapping("/xtsqList")
    public ModelMap getXtsqList(PageOption option, Long jgId) {
        ModelMap map = new ModelMap();
        try {
            map = xtsqService.getXtsqMap(option, "", jgId);
            Xtjg xtjg = xtjgService.getXtjg(jgId);
            map.put("sjjg", xtjg.getSjjg_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 导出Excel，jgId下级机构的所有社区
     *
     * @param jgId
     * @param response
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(Long jgId, HttpServletResponse response) {
        try {
            List sqjgs = xtsqtjService.getXtsqtjList(jgId);
            String json = ExcelBean.getSqtjJson((List)sqjgs.get(0));
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmSS");
            ExcelExportUtil.excelExport("社区统计表" + format.format(new Date()), json, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 维护社区
     * @param id
     * @return
     */
    @RequestMapping("xtsqDetail")
    public ModelMap xtsqDetail(Long id,Long jgid) {
        ModelMap map = new ModelMap();
        map.put("xtsq", xtsqService.getXtsqById(id));
        return map;
    }

}
