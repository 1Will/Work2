package demo.dao.xtyh;

import demo.dao.BaseDao;
import demo.domain.entity.Xtyh;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户Dao
 */
@Repository
public class XtyhDao extends BaseDao {
    /**
     * 根据登录名与密码获取用户信息
     *
     * @param loginName 登录名
     * @param loginPwd  登录密码
     * @return
     */
    public Xtyh getYhByNameAndPwd(String loginName, String loginPwd) {
        String hql = "from Xtyh where dlm=? and  mm=?  and czbs<>?";
        return (Xtyh) findUniqueResult(hql, loginName, loginPwd, Czbs.SC.getIndex());
    }

    /**
     * 获取用户总数
     *
     * @param yhmc 用户名称
     * @param jgid 机构Id
     * @return
     */
    public int getXtyhTotal(String yhmc, Long jgid) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(ID) FROM T_XT_YH WHERE CZBS <>:CZBS");
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        appendParam(sql, paraMap, yhmc, jgid);
        return Integer.parseInt(queryUniqueResult(sql.toString(), paraMap).toString());
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
        StringBuilder sql = new StringBuilder("SELECT YH.ID, YH.XM, ZD.ZDMC AS XB_NO, ( SELECT JGMC FROM T_XT_JG" +
                " JG WHERE JG.ID=YH.JG_ID AND JG.CZBS<>:CZBS)JGMC, SFZHM, LXDH, YH.CZSJ\n" +
                "  FROM T_XT_YH YH\n" +
                "  LEFT JOIN T_XT_ZD ZD\n" +
                "    ON ZD.ID = YH.XB_NO\n" +
                "    AND ZD.CZBS <> :CZBS\n" +
                " WHERE YH.CZBS <> :CZBS");
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        appendParam(sql, paraMap, yhmc, jgid);
        sql.append(" ORDER BY YH.CZSJ DESC");
        return super.queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), paraMap);
    }


    /**
     * 拼接查询语句
     *
     * @param sql     SQL
     * @param paraMap 参数
     * @param yhmc    用户名称
     * @param jgid    机构Id
     */
    public void appendParam(StringBuilder sql, Map paraMap, String yhmc, Long jgid) {
        if (CommonUtil.isNotNull(jgid)) {
            sql.append("  AND JG_ID IN (SELECT A.ID FROM T_XT_JG A START WITH A.ID = :JGID CONNECT BY PRIOR A.ID = A.SJJG_ID)");
            paraMap.put("JGID", jgid);
        }
        if (CommonUtil.isNotNull(yhmc)) {
            sql.append(" AND INSTR(XM,:YHMC) > 0");
            paraMap.put("YHMC", yhmc);
        }
    }

    /**
     * 删除公安用户
     *
     * @param id     用户Id
     * @param czrId 操作人Id
     */
    public void deleteXtyh(Long id, Long czrId) {
        String sql = "UPDATE T_XT_YH SET CZBS=:CZBS,CZSJ=SYSDATE,CZR_ID=:CZR_ID WHERE ID=:ID";
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("CZR_ID", czrId);
        paraMap.put("ID", id);
        excuteSql(sql, paraMap);

    }

    /**
     * 验证登录名是否重复
     *
     * @param id  公安用户主键id
     * @param dlm 登录名
     * @return
     */
    public boolean isDlmRepeated(Long id, String dlm) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(ID)\n" +
                "  FROM T_XT_YH YH\n" +
                "   WHERE YH.CZBS <> :CZBS\n" +
                "   AND DLM = :DLM");
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("DLM", dlm);
        if (CommonUtil.isNotNull(id)) {
            sql.append(" AND YH.ID <> :ID");
            paraMap.put("ID", id);
        }
        return Integer.parseInt(queryUniqueResult(sql.toString(), paraMap).toString()) > 0;
    }

    /**
     * 验证身份证号是否重复
     *
     * @param id    用户id
     * @param sfzhm 身份证号码
     * @return
     */
    public boolean isIdNumberRepeated(Long id, String sfzhm) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(1)\n" +
                "  FROM T_XT_YH YH\n" +
                " WHERE YH.CZBS <> :CZBS\n" +
                "   AND SFZHM = :SFZHM\n");
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("SFZHM", sfzhm);
        if (CommonUtil.isNotNull(id)) {
            sql.append(" AND YH.ID <> :ID");
            paraMap.put("ID", id);
        }
        return Integer.parseInt(queryUniqueResult(sql.toString(), paraMap).toString()) > 0;
    }

    /**
     * 保存用户角色Id
     *
     * @param yhId 用户Id
     * @param jsId 角色Id
     */
    public void saveYhJs(Long yhId, String jsId) {
        String sql = "INSERT INTO T_XT_YHJS VALUES(SEQ_XT_YHJS.NEXTVAL,:YH_ID,:JS_ID)";
        Map paraMap = new HashMap();
        paraMap.put("JS_ID", jsId);
        paraMap.put("YH_ID", yhId);
        super.excuteSql(sql, paraMap);
    }

    /**
     * 删除用户角色
     *
     * @param yhId 用户Id
     */
    public void deleteYhjs(Long yhId) {
        String sql = "DELETE FROM T_XT_YHJS WHERE YH_ID =:YH_ID";
        Map paraMap = new HashMap();
        paraMap.put("YH_ID", yhId);
        super.excuteSql(sql, paraMap);
    }

    /**
     * 获取全部角色信息
     *
     * @return
     */
    public List<Map> getYhjsList() {
        String sql = "SELECT ID,JSMC FROM T_XT_JS WHERE CZBS<>:CZBS ORDER BY CZSJ";
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        return super.queryMapList(sql, paraMap);
    }

    /**
     * 获取用户角色信息
     *
     * @param yhId 用户id
     * @return
     */
    public List<Map> getYhjsInfo(Long yhId) {
        String sql = "SELECT JS.ID, JS.JSMC\n" +
                "  FROM T_XT_JS JS\n" +
                "  JOIN T_XT_YHJS YH\n" +
                "    ON YH.JS_ID = JS.ID\n" +
                "   AND YH.YH_ID = :YHID\n" +
                "   AND JS.CZBS <> :CZBS";
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("YHID", yhId);
        return super.queryMapList(sql, paraMap);
    }
}
