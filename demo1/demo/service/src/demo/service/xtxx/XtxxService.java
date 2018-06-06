package demo.service.xtxx;

import demo.dao.xtxx.XtxxDao;
import demo.domain.entity.Xtxx;
import demo.domain.entity.Xtyh;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 信息发布Service
 */
@Service
public class XtxxService {
    @Autowired
    private XtxxDao xtxxDao;

    /**
     * 获取信息列表
     *
     * @param option
     * @param fbr    发布人ID
     * @param xxbt   信息标题
     * @param gjz    关键字
     * @return
     */
    public List getXxList(PageOption option, Long fbr, String xxbt, String gjz) throws Exception {
        return xtxxDao.getXxList(option, fbr, xxbt, gjz);
    }

    /**
     * 获取当前登陆者发布信息总数
     *
     * @param fbr
     * @param xxbt
     * @param gjz
     * @return
     */
    public int getXxTotal(Long fbr, String xxbt, String gjz) {
        return xtxxDao.getXxTotal(fbr, xxbt, gjz);
    }

    /**
     * 通过id获取信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Xtxx getXxById(Long id) throws Exception {
        return xtxxDao.getObject(Xtxx.class, id);
    }

    /**
     * 保存或修改信息
     *
     * @param xtxx
     * @param xtyh
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateXtxx(Xtxx xtxx, Xtyh xtyh) throws Exception {
        if (CommonUtil.isNotNull(xtxx.getCzsj())) {
            //修改
            xtxx.setCzbs(Czbs.XG.getIndex());
        } else {
            //新增,设置发布人、不置顶
            xtxx.setCzbs(Czbs.XZ.getIndex());
            xtxx.setFbrId(xtyh.getId());
        }
        //发布信息,信息只能发布一次,此处只会执行一次
        if ("1".equals(xtxx.getFbzt())) {
            xtxx.setFbdwId(xtyh.getJg_id());
            xtxx.setFbsj(new Date());
        }
        saveXtxx(xtxx, xtyh);
    }

    /**
     * 保存信息发布，添加操作记录
     *
     * @param xtxx
     * @param xtyh
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveXtxx(Xtxx xtxx, Xtyh xtyh) throws Exception {
        xtxx.setCzyhId(xtyh.getId());
        xtxx.setCzsj(new Date());
        //保存
        xtxxDao.saveOrUpdate(xtxx);
    }

    /**
     * 直接保存对象
     * @param xtxx
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveXtxx(Xtxx xtxx) throws Exception {
        xtxxDao.saveOrUpdate(xtxx);
    }

    /**
     * 删除信息发布
     *
     * @param id
     * @param czyhId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteXtxx(Long id, Long czyhId) throws Exception {
        xtxxDao.deleteXtxx(id, czyhId);
    }

    /**
     * 取消所有置顶
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancleTop() {
        xtxxDao.cancleTop();
    }
}