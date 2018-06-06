package demo.service.xtzd;

import demo.dao.xtzd.XtzdDao;
import demo.domain.entity.Xtyh;
import demo.domain.entity.Xtzd;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统字典Service
 */
@Service
public class XtzdService {
    //系统字典Dao
    @Autowired
    private XtzdDao xtzdDao;

    /**
     * 字典管理总数
     *
     * @param zdmc 字典名称
     * @param zddm 字典代码
     * @param sjzd 上级字典
     * @return
     */
    public Integer getXtzdTotal(String zdmc, String zddm, String sjzd) {
        return xtzdDao.getXtzdTotal(zdmc, zddm, sjzd);
    }

    /**
     * 字典管理列表
     *
     * @param zdmc   字典名称
     * @param zddm   字典代码
     * @param sjzd   上级字典
     * @param option 分页参数
     * @return
     */
    public List getXtzdList(String zdmc, String zddm, String sjzd, PageOption option) {
        return xtzdDao.getXtzdList(zdmc, zddm, sjzd, option);
    }

    /**
     * 获取所有是上级字典的集合
     *
     * @return 字典集合
     */
    public List<Xtzd> getAllSjzd() {
        return xtzdDao.getAllSjzd();
    }

    /**
     * 获取系统字典实体
     *
     * @param id 主键id
     * @return Xtzd
     */
    public Xtzd getXtzdById(Long id) {
        return xtzdDao.getObject(Xtzd.class, id);
    }

    /**
     * 判断字典代码是否重复
     *
     * @param zddm 字典代码
     * @return Integer
     */
    public boolean isZddmCf(Long id, String zddm) throws Exception {
        return xtzdDao.isZddmCf(id, zddm);
    }

    /**
     * 字典项保存
     *
     * @param xtzd  系统字典实体
     * @param czrId 操作人ID
     * @return
     */
    @Transactional
    public void saveXtzd(Xtzd xtzd, Long czrId) throws Exception {
        if (CommonUtil.isNotNull(xtzd.getId())) {
            xtzd.setCzbs(Czbs.XG.getIndex());//修改
        } else {
            xtzd.setCzbs(Czbs.XZ.getIndex());//新增
        }
        if (CommonUtil.isNull(xtzd.getSjzd_id())) {
            xtzd.setSjzd_id(0L);
        }
        xtzd.setCzsj(new Date());
        xtzd.setCzr_id(czrId);
        xtzdDao.saveOrUpdate(xtzd);
    }

    /**
     * 字典项删除
     *
     * @param id   主键id
     * @param czrId 操作人Id
     */
    @Transactional
    public void deleteXtzd(Long id, Long czrId) throws Exception {
        xtzdDao.deleteXtzd(id, czrId);
    }

    /**
     * 判断选中字典项是否有下级
     *
     * @param id 系统字典id
     * @return boolean
     */
    public boolean checkXtzd(Long id) throws Exception {
        return xtzdDao.checkXtzd(id);
    }

    /**
     * 根据字典代码获取字典项内容
     *
     * @param dictCode 字典代码
     */
    public List<Map<String, Object>> getXtzdByZddm(String dictCode) {
        return xtzdDao.getXtzdByZddm(dictCode);
    }

}
