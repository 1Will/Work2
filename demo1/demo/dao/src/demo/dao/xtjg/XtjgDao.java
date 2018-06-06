package demo.dao.xtjg;

import demo.dao.BaseDao;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统机构dao层
 */
@Repository(value = "xtjgDao")
public class XtjgDao extends BaseDao {

    /**
     * 获取系统机构所有集合
     *
     * @param id 机构Id
     * @return
     */
    public List<Map<String, Object>> getXtjgTree(Long id) {
        Map<String, Object> params = new HashMap();
        StringBuilder sql = new StringBuilder("SELECT JG.ID,\n" +
                "       JG.JGMC,\n" +
                "       JG.SJJG_ID,\n" +
                "       (SELECT COUNT(1)\n" +
                "          FROM T_XT_JG\n" +
                "         WHERE SJJG_ID = JG.ID\n" +
                "           AND CZBS <> '3') XJZS\n" +
                "  FROM T_XT_JG JG\n" +
                " WHERE JG.CZBS <> :CZBS\n");
        params.put("CZBS", Czbs.SC.getIndex());
        if (CommonUtil.isNotNull(id)) {
            sql.append(" AND JG.SJJG_ID = :ID");
            params.put("ID", id);
        } else {
            sql.append(" AND JG.SJJG_ID IN (0,10)");
        }
        sql.append("  ORDER BY JG.CZSJ DESC, JG.JGDM");
        return queryMapList(sql.toString(), params);
    }

    /**
     * 查询条件拼接语句
     *
     * @param sql    sql语句
     * @param params 查询参数
     * @param sjjgId 上级机构id
     * @param jgmc   机构名称
     * @param jgdm   机构代码
     */
    private void appendSql(StringBuilder sql, Map<String, Object> params, Long sjjgId, String jgmc, String jgdm) {
        sql.append(" WHERE JG.CZBS <> :CZBS ");
        params.put("CZBS", Czbs.SC.getIndex());
        //上级机构id查询
        if (CommonUtil.isNotNull(sjjgId)) {
            sql.append(" START WITH JG.ID = :SJJG_ID CONNECT BY PRIOR JG.ID = JG.SJJG_ID");
            params.put("SJJG_ID", sjjgId);
        }
        //机构名称查询
        if (CommonUtil.isNotNull(jgmc)) {
            sql.append(" AND INSTR(JG.JGMC,:JGMC) > 0 ");
            params.put("JGMC", jgmc);
        }
        //机构代码查询
        if (CommonUtil.isNotNull(jgdm)) {
            sql.append(" AND INSTR(JG.JGDM,:JGDM) > 0 ");
            params.put("JGDM", jgdm);
        }
    }


    /**
     * 获取系统机构列表总数
     *
     * @param jgmc    机构名称
     * @param jgdm    机构代码
     * @param sjjgId 上级机构id
     * @return
     */
    public Integer getXtjgTotal(String jgmc, String jgdm, Long sjjgId) {
        Map<String, Object> params = new HashMap();
        StringBuilder sql = new StringBuilder(" SELECT COUNT(1) FROM T_XT_JG JG");
        appendSql(sql, params, sjjgId, jgmc, jgdm);
        return Integer.parseInt(queryUniqueResult(sql.toString(), params).toString());
    }

    /**
     * 添加sql
     *
     * @param sql
     */
    private void appendSqlTop(StringBuilder sql) {
        sql.append("SELECT \n" +
                "  JG.ID,\n" +
                "  JG.JGDM,\n" +
                "  JG.JGMC,\n" +
                "  JG.DHHM,\n" +
                "  JG.JGLX,\n" +
                "  JG.SJJG_ID,\n" +
                "  JG.CZSJ,\n" +
                "  JG.CZR_ID, \n" +
                "  (SELECT T.JGMC FROM T_XT_JG T WHERE T.ID = JG.SJJG_ID AND T.CZBS <> :CZBS) SJJGMC \n" +
                "FROM T_XT_JG JG");
    }

    /**
     * 获取系统机构列表集合，带分页
     *
     * @param jgmc    机构名称
     * @param jgdm    机构代码
     * @param sjjgId 上级机构id
     * @param option  分页参数
     * @return 机构列表
     */
    public List getXtjgList(String jgmc, String jgdm, Long sjjgId, PageOption option) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap();
        appendSqlTop(sql);
        appendSql(sql, params, sjjgId, jgmc, jgdm);
        sql.append(" ORDER BY JG.CZSJ DESC ");
        return queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), params);
    }

    /**
     * 获取系统机构列表集合,不带分页
     *
     * @param jgmc    机构名称
     * @param jgdm    机构代码
     * @param sjjgId 上级机构id
     * @return 机构列表
     */
    public List getXtjgList(String jgmc, String jgdm, Long sjjgId) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap();
        appendSqlTop(sql);
        appendSql(sql, params, sjjgId, jgmc, jgdm);
        sql.append(" ORDER BY JG.CZSJ DESC ");
        return queryMapList(sql.toString(), params);
    }


    /**
     * 系统机构删除
     *
     * @param id   机构id
     * @param yhId 用户id
     */
    public void deleteXtjg(Long id, Long yhId) {
        Map<String, Object> params = new HashMap();
        String sql = " UPDATE T_XT_JG SET CZR_ID = :CZR_ID,CZSJ = SYSDATE,CZBS = :CZBS WHERE ID = :ID";
        params.put("ID", id);
        params.put("CZR_ID", yhId);
        params.put("CZBS", Czbs.SC.getIndex());
        excuteSql(sql, params);
    }

    /**
     * 删除时检查有无下级机构
     *
     * @param id 机构id
     * @return 下级机构数量
     */
    public Boolean hasXjjg(Long id) {
        List params = new ArrayList();
        String sql = "SELECT COUNT(1) FROM T_XT_JG WHERE CZBS <> :CZBS AND SJJG_ID = :ID";
        params.add(Czbs.SC.getIndex());
        params.add(id);
        return getJdbcTemplate().queryForObject(sql, params.toArray(), Integer.class) > 0;
    }

    /**
     * 删除时检查机构下有无人员
     *
     * @param id 机构Id
     * @return 人员数量
     */
    public Boolean hasJgYh(Long id) {
        List params = new ArrayList();
        String sql = "SELECT COUNT(1) FROM T_XT_YH WHERE CZBS <> ? AND JG_ID = ?";
        params.add(Czbs.SC.getIndex());
        params.add(id);
        return getJdbcTemplate().queryForObject(sql, params.toArray(), Integer.class) > 0;
    }

    /**
     * 机构名称或机构代码重复校验
     *
     * @param id   机构Id
     * @param mc   字段名称
     * @param type 名称或者代码类型
     * @return
     */
    public boolean isRepeat(Long id, String mc, String type) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM T_XT_JG WHERE CZBS <> :CZBS");
        Map<String, Object> params = new HashMap();
        params.put("CZBS", Czbs.SC.getIndex());
        if (CommonUtil.isNotNull(id)) {
            sql.append(" AND ID <> :ID");
            params.put("ID", id);
        }
        if ("jgdm".equals(type)) {
            sql.append(" AND JGDM = :JGDM ");
            params.put("JGDM", mc);
        } else if ("jgmc".equals(type)) {
            sql.append(" AND JGMC = :JGMC ");
            params.put("JGMC", mc);
        }
        return Integer.parseInt(queryUniqueResult(sql.toString(), params).toString()) > 0;
    }
}
