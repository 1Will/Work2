package demo.service.xtjs;
import demo.domain.enums.Czbs;
import demo.util.CommonUtil;
import demo.dao.xtjs.XtjsDao;
import demo.domain.entity.Xtjs;
import demo.domain.pojo.PageOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
/**
 * 系统角色Service
 */
@Service
public class XtjsService {
    //系统角色Dao
    @Autowired
    private XtjsDao xtjsDao;

    /**
     * 查询系统角色总数
     *
     * @param jsmc 角色名称
     * @return
     */
    public Integer getXtjsTotal(String jsmc) {
        return xtjsDao.getXtjsTotal(jsmc);
    }

    /**
     * 查询系统角色分页列表
     *
     * @param jsmc   角色名称
     * @param option 分页对象参数
     * @return
     */
    public List getXtjsListPage(String jsmc,PageOption option) {
        return xtjsDao.getXtjsListPage(jsmc,option);
    }

    /**
     * 获取系统角色实体
     * @param id 系统角色id
     * @return
     */
    public Xtjs getXtjsById(Long id) {
        return xtjsDao.getObject(Xtjs.class,id);
    }
    /**
     * 保存系统角色
     * @param xtjs 系统角色实体
     * @param yhId 用户Id
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveXtjs(Xtjs xtjs, Long yhId) {
        if(CommonUtil.isNotNull(xtjs.getId())){
            xtjs.setCzbs(Czbs.XG.getIndex());
        }else{
            xtjs.setCzbs(Czbs.XZ.getIndex());
        }
        xtjs.setCzrId(yhId);
        xtjs.setCzsj(new Date());
        xtjsDao.saveXtjs(xtjs);
        Long jsId = xtjs.getId();
        xtjsDao.deleteCdByJsId(jsId); //删除角色菜单表里的数据
        String jscdIds = xtjs.getJscdIds();
        if(CommonUtil.isNotNull(jscdIds.trim())) { //保存角色菜单关联表
            String[] cdIdArr = jscdIds.split(",");
            for (int i = 0; i < cdIdArr.length; i++) {
                xtjsDao.saveJscd(jsId,Long.parseLong(cdIdArr[i]));
            }
        }
    }

    /**
     * 系统角色删除
     * @param id 角色Id
     * @param yhId 用户Id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteXtjs(Long id,Long yhId) {
        xtjsDao.deleteXtjs(id,yhId);

    }


    /**
     * 查询角色对应的菜单ID,角色名称
     * @param id 角色ID
     * @return
     */
    public Map getJscdByjsId(Long id){
        List<Map<String,Object>> list = xtjsDao.getJscdByjsId(id);
        Map<String, Object> jscdmap = new HashMap();
        if(CommonUtil.isNotNull(list) && list.size()>0){
            String cdmcs = ""; //菜单名称
            String cdIds = ""; //菜单id
            for(Map<String,Object> map : list){
                cdmcs += map.get("CDMC").toString() + ",";
                cdIds += map.get("CDID").toString()+",";
            }
            jscdmap.put("cdmcs",cdmcs.substring(0,cdmcs.length()-1));
            jscdmap.put("cdIds",cdIds.substring(0,cdIds.length()-1));
        }
        return jscdmap;
    }


    /**重复校验
     *
     * @param id 角色Id
     * @param jsmc 角色名称
     * @param jsType 角色类型
     * @return
     */
    public boolean isRepeat(Long id,String jsmc, String jsType) {
        return xtjsDao.isRepeat(id,jsmc,jsType);
    }
}
