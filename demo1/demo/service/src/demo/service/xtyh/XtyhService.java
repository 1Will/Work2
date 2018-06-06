package demo.service.xtyh;

import demo.dao.xtyh.XtyhDao;
import demo.domain.entity.Xtyh;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统用户Service
 */
@Service
public class XtyhService {
    //系统用户Dao
    @Autowired
    private XtyhDao xtyhDao;

    /**
     * 根据登录名与密码获取用户信息
     *
     * @param loginName 登录名
     * @param loginPwd  登录密码
     * @return
     */
    public Xtyh getYhByNameAndPwd(String loginName, String loginPwd) {
        return xtyhDao.getYhByNameAndPwd(loginName, loginPwd);
    }

    /**
     * 重置当前登陆人密码
     *
     * @param xtyh  系统用户
     * @param newMm 新密码
     * @return
     * @throws Exception
     */
    @Transactional
    public void changePwd(Xtyh xtyh, String newMm) throws Exception {
        xtyh.setMm(newMm);
        xtyh.setCzsj(new Date());
        xtyhDao.update(xtyh);
    }

    /**
     * 获取用户总数
     *
     * @param yhmc 用户名称
     * @param jgid 机构Id
     * @return
     */
    public int getXtyhTotal(String yhmc, Long jgid) {
        return xtyhDao.getXtyhTotal(yhmc, jgid);
    }

    /**
     * 获取用户列表
     *
     * @param option 分页参数
     * @param yhmc   用户名称
     * @param jgid   机构Id
     * @return
     */
    public List getXtyhList(PageOption option, String yhmc, Long jgid) {
        return xtyhDao.getXtyhList(option, yhmc, jgid);
    }


    /**
     * 系统用户删除
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteXtyh(Long id, Long czr_id) throws Exception {
        //删除公安用户
        xtyhDao.deleteXtyh(id, czr_id);
        //删除该用户的所拥有的角色
        xtyhDao.deleteYhjs(id);

    }

    /**
     * 通过id查找到公安用户
     */
    public Xtyh getXtyhById(Long id) {
        return xtyhDao.getObject(Xtyh.class, id);
    }

    /**
     * 保存或修改用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveXtyh(Xtyh xtyh, Long czrid, String jsIds) throws Exception {
        if (CommonUtil.isNotNull(xtyh.getId())) { //修改
            xtyh.setCzbs(Czbs.XG.getIndex());
        } else { //修改
            xtyh.setCzbs(Czbs.XZ.getIndex());
        }
        xtyh.setCzr_id(czrid);
        xtyh.setCzsj(new Date());
        xtyhDao.saveOrUpdate(xtyh);
        if (CommonUtil.isNotNull(jsIds)) {
            //删除用户所有角色再重新保存
            xtyhDao.deleteYhjs(xtyh.getId());
            String[] arr = jsIds.split(",");
            for (int i = 0; i < arr.length; i++) {
                xtyhDao.saveYhJs(xtyh.getId(), arr[i]);
            }
        }

    }

    /**
     * 获取角色列表数据
     */
    public List<Map> getYhjsList() {
        return xtyhDao.getYhjsList();
    }

    /**
     * 获取用户的角色
     *
     * @param yhId 用户id
     * @return
     */
    public Map getYhjsInfo(Long yhId) {
        List<Map> list = xtyhDao.getYhjsInfo(yhId);
        StringBuilder ids = new StringBuilder();
        StringBuilder jsmcs = new StringBuilder();
        for (Map mapInfo : list) {
            ids.append(mapInfo.get("ID") + ",");
            jsmcs.append(mapInfo.get("JSMC") + ",");
        }
        if (ids.length() > 0 && jsmcs.length() > 0) {
            ids.deleteCharAt(ids.length() - 1);
            jsmcs.deleteCharAt(jsmcs.length() - 1);
        }
        Map map = new HashMap();
        map.put("ids", ids.toString());
        map.put("jsmcs", jsmcs.toString());
        return map;
    }


    /**
     * 验证登录名是否重复
     *
     * @param id  公安用户主键id
     * @param dlm 登录名
     * @return
     */
    public boolean isDlmRepeated(Long id, String dlm) throws Exception {
        return xtyhDao.isDlmRepeated(id, dlm);
    }

    /**
     * 验证身份证号是否重复,大于0返回true则是重复了
     *
     * @param id    公安用户主键id
     * @param sfzhm 身份证号码
     * @return
     */
    public boolean isIdNumberRepeated(Long id, String sfzhm) throws Exception {
        return xtyhDao.isIdNumberRepeated(id, sfzhm);
    }
}
