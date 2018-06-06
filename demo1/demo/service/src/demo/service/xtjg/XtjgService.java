package demo.service.xtjg;

import demo.dao.xtjg.XtjgDao;
import demo.domain.entity.Xtjg;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统机构Service
 */
@Service
public class XtjgService {
    //系统机构Dao
    @Autowired
    private XtjgDao xtjgDao;

    /**
     * 获取系统树
     */
    public List<Map<String, Object>> getXtjgTree(Long id) {
        return xtjgDao.getXtjgTree(id);
    }


    /**
     * 获取系统机构列表总数
     *
     * @param jgmc    机构名称
     * @param jgdm    机构代码
     * @param sjjg_id 上级机构id
     * @return
     */
    public Integer getXtjgTotal(String jgmc, String jgdm, Long sjjg_id) {
        return xtjgDao.getXtjgTotal(jgmc, jgdm, sjjg_id);
    }

    /**
     * 获取系统机构列表集合
     *
     * @param jgmc    机构名称
     * @param jgdm    机构代码
     * @param sjjg_id 上级机构id
     * @param option  分页参数
     */
    public List getXtjgList(String jgmc, String jgdm, Long sjjg_id, PageOption option) {
        return xtjgDao.getXtjgList(jgmc, jgdm, sjjg_id, option);
    }

    /**
     * 获取系统机构实体信息
     *
     * @param id 机构ID
     */
    public Xtjg getXtjg(Long id) {
        return xtjgDao.getObject(Xtjg.class, id);
    }

    /**
     * 系统机构保存
     *
     * @param xtjg 系统机构
     * @param yhId 用户Id
     */
    @Transactional
    public void saveXtjg(Xtjg xtjg, Long yhId) throws RuntimeException {
        if (CommonUtil.isNotNull(xtjg.getId())) {
            xtjg.setCzbs(Czbs.XG.getIndex());//修改
        } else {
            xtjg.setCzbs(Czbs.XZ.getIndex());//新增
        }
        xtjg.setCzr_id(yhId);
        xtjg.setCzsj(new Date());
        xtjgDao.saveOrUpdate(xtjg);
    }

    /**
     * 系统机构删除
     *
     * @param id   机构id
     * @param yhId 用户id
     */
    @Transactional
    public void deleteXtjg(Long id, Long yhId) throws Exception {
        xtjgDao.deleteXtjg(id, yhId);
    }

    /**
     * 检查有无下级机构
     *
     * @param id 机构id
     * @return ture:有/false:无
     */
    public Boolean hasXjjg(Long id) {
        return xtjgDao.hasXjjg(id);
    }

    //机构下有无人员
    public Boolean hasJgYh(Long id) {
        return xtjgDao.hasJgYh(id);
    }

    /**
     * 检查机构名称或代码是否重复
     *
     * @param id 机构id
     * @param mc 名称或代码
     * @return true:是/false:否
     */
    public boolean isRepeat(Long id, String mc, String type) {
        return xtjgDao.isRepeat(id, mc, type);
    }

}
