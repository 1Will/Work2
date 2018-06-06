package demo.service.xtfj;

import demo.dao.xtfj.XtfjDao;
import demo.domain.entity.Xtfj;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 附件Service
 */
@Service
public class XtfjService {
    @Autowired
    private XtfjDao xtfjDao;

    /**
     * 查询附件列表
     *
     * @param option
     * @param xxId   信息Id
     * @return
     */
    public List getFjList(PageOption option, Long xxId) {
        return xtfjDao.getFjList(option, xxId);
    }

    public Xtfj getXtfjById(Long id) {
        return xtfjDao.getObject(Xtfj.class, id);
    }

    /**
     * 获取附件数量
     *
     * @param xxId 信息Id
     * @return
     */
    public int getFjTotal(Long xxId) {
        return xtfjDao.getFjTotal(xxId);
    }

    /**
     * 保存附件
     *
     * @param xtfj 附件
     * @param yhId 操作用户id
     */
    @Transactional(rollbackFor = Exception.class)
    public Long saveXtfj(Xtfj xtfj, Long yhId) throws Exception {
        if (CommonUtil.isNotNull(xtfj.getId())) {
            //修改
            xtfj.setCzbs(Czbs.XG.getIndex());
        } else {
            //新增
            xtfj.setCzbs(Czbs.XZ.getIndex());
        }
        xtfj.setCzyhId(yhId);
        xtfj.setCzsj(new Date());
        xtfjDao.saveOrUpdate(xtfj);
        return xtfj.getId();
    }

    /**
     * 删除附件
     *
     * @param id
     * @param czyhId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteXtfj(Long id, Long czyhId) throws Exception {
        xtfjDao.deleteXtfj(id, czyhId);
    }

}
