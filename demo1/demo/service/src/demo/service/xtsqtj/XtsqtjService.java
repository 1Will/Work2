package demo.service.xtsqtj;

import demo.dao.xtsqtj.XtsqtjDao;
import demo.domain.pojo.PageOption;
import demo.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author a
 * @date ${date}
 * $END
 */
@Service
public class XtsqtjService {
    @Autowired
    private XtsqtjDao xtsqtjDao;

    /**
     * 获取机构统计列表,带分页
     *
     * @param option 页面对象
     * @param jgId   上级机构id
     * @return 带分页社区统计list
     */
    public List getXtsqtjList(PageOption option, Long jgId) {
        return xtsqtjDao.getXtsqtjList(option, jgId);
    }

    /**
     * 获取机构统计列表,不带分页
     *
     * @param jgId 机构ID
     * @return 不带分页社区统计list
     */
    public List getXtsqtjList(Long jgId) {
        List data = new ArrayList();
        List xtsqtjList = xtsqtjDao.getXtsqtjList(jgId);
        data.add(xtsqtjList);
        if (xtsqtjList != null && xtsqtjList.size() > 0) {
            Object[] json = new Object[xtsqtjList.size()];
            //组装页面图标需要的信息
            for (int i = 0; i < xtsqtjList.size(); i++) {
                Map<String,Object> newMap = new HashMap();
                newMap.put("y",((Object[])xtsqtjList.get(i))[2]);
                newMap.put("name",((Object[])xtsqtjList.get(i))[1]);
                //获取next值，为0则不添加key
                if(!"0".equals(((Object[])xtsqtjList.get(i))[3].toString())){
                    newMap.put("key",((Object[])xtsqtjList.get(i))[0]);
                }

                json[i] = newMap;
            }
            String jsonStr = JsonUtil.objectToJson(json).replaceAll("\"", "'");
            data.add(jsonStr);
        }
        return data;
    }

    /**
     * 获取下级机构总数
     *
     * @param jgId 上级机构id
     * @return 机构总数
     */
    public int getXtsqtjTotal(Long jgId) {
        return xtsqtjDao.getXtsqtjTotal(jgId);
    }
}
