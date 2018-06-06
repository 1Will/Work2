package demo.service.xtcd;
import demo.domain.enums.Czbs;
import demo.dao.xtcd.XtcdDao;
import demo.domain.entity.Xtcd;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
/**
 * 系统菜单Service
 */
@Service
public class XtcdService {
    //系统菜单Dao
    @Autowired
    private XtcdDao xtcdDao;

    /**
     * 根据用户查找该用户下的菜单
     *
     * @param yhId 用户Id
     * @return
     */
    public Map getCdList(Long yhId) {
        Map map = new HashMap();
        List<Map> yhcdList = xtcdDao.getDjcdList(yhId);
        int count = 0;
        for (Map djcd : yhcdList) {
            int i = Integer.parseInt(djcd.get("XJGS").toString());
            //下级菜单个数大于0则查询其拥有的下级菜单
            if (i > 0) {
                count++; //有下级菜单的菜单总数
                Long sjcd_id = Long.parseLong(djcd.get("ID").toString());
                djcd.put("XJCD", xtcdDao.getXjcdList(sjcd_id, yhId));
            }
        }
        map.put("yhcdList", yhcdList);
        map.put("count", count);
        return map;
    }


    /**
     * 菜单列表总数
     *
     * @param cdmc 菜单名称
     * @param ssxt 所属系统
     * @return
     */
    public Integer getCdTotal(String cdmc, String ssxt) {
        return xtcdDao.getCdTotal(cdmc, ssxt);
    }

    /**
     * 菜单管理列表集合
     *
     * @param option 分页参数
     * @param cdmc   菜单名称
     * @param ssxt   所属系统
     * @return
     */
    public List getCdList(PageOption option, String cdmc, String ssxt) {
        return xtcdDao.getCdList(option, cdmc, ssxt);
    }


    /**
     * 判断是否有子菜单
     *
     * @param cdId 菜单ID
     * @param ssxt 所属系统
     * @return
     */
    public Boolean hasChildMenu(Long cdId, String ssxt) throws Exception {
        return xtcdDao.getChildMenuNum(cdId, ssxt);
    }
    /**
     * 删除菜单
     *
     * @param cdId   菜单id
     * @param userId 用户id
     * @param ssxt   所属系统
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCd(Long cdId, Long userId, String ssxt) throws Exception {
        xtcdDao.deleteCd(cdId, userId, ssxt);
    }

    /**
     * 获取菜单树
     *
     * @return 菜单集合
     */

    public List<Xtcd> getCdTree(Long id, String ssxt) {
        return xtcdDao.getAllCd(id, ssxt);
    }

    /**
     * 获取菜单实体by ID
     *
     * @param id 菜单id
     * @return 系统菜单实体
     */
    public Xtcd getCdById(Long id) throws Exception {
        return xtcdDao.getObject(Xtcd.class, id);
    }

    /**
     * 获取最大序号
     *
     * @return
     */
    public Long getInitXh() throws Exception {
        return xtcdDao.getInitXh();
    }

    /**
     * 判断菜单是否重复
     *
     * @param id   菜单id
     * @param cdmc 菜单名称
     * @param ssxt 所属系统
     * @return
     */
    public Boolean checkCdmc(Long id, String cdmc, String ssxt) throws Exception {
        return xtcdDao.checkCdmc(id, cdmc, ssxt);
    }


    /**
     * 保存系统菜单
     *
     * @param xtcd  系统菜单实体
     * @param czrId 操作人ID
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveXtcd(Xtcd xtcd, Long czrId) throws Exception {
        if (CommonUtil.isNotNull(xtcd.getId())) {
            xtcd.setCzbs(Czbs.XG.getIndex());
        } else {
            xtcd.setCzbs(Czbs.XZ.getIndex());
        }
        xtcd.setCzsj(new Date());
        xtcd.setCzrId(czrId);
        xtcdDao.saveOrUpdate(xtcd);
    }
}
