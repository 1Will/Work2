package demo.dao.xtjs;

import demo.dao.BaseDao;
import demo.domain.entity.Xtjs;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色Dao
 */
@Repository
public class XtjsDao extends BaseDao {

    /**
     * 查询系统角色总数
     *
     * @param jsmc 角色名称
     * @return
     */
    public Integer getXtjsTotal(String jsmc) {
        List<Object> args = new ArrayList();
        StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM T_XT_JS WHERE CZBS <> ?");
        args.add(Czbs.SC.getIndex());
        if (CommonUtil.isNotNull(jsmc)) {
            sql.append(" AND INSTR(JSMC, ?) > 0");
            args.add(jsmc);
        }
        return getJdbcTemplate().queryForObject(sql.toString(), args.toArray(), Integer.class);
    }

    /**
     * 查询系统角色分页列表
     *
     * @param jsmc   角色名称
     * @param option 分页对象参数
     * @return
     */
    public List getXtjsListPage(String jsmc, PageOption option) {
        Map<String, Object> param = new HashMap();
        StringBuilder sql = new StringBuilder("SELECT ID,JSMC,JSDM FROM T_XT_JS WHERE CZBS<>:CZBS");
        if (CommonUtil.isNotNull(jsmc)) {
            sql.append(" AND INSTR(JSMC, :JSMC) > 0");
            param.put("JSMC", jsmc);
        }
        sql.append(" ORDER BY CZSJ DESC NULLS LAST");
        param.put("CZBS", Czbs.SC.getIndex());
        return queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), param);
    }

    /**
     * 保存系统角色
     *
     * @param xtjs 系统角色
     */
    public void saveXtjs(Xtjs xtjs) {
        saveOrUpdate(xtjs);
        Map<String, Object> param = new HashMap();
        Long id = xtjs.getId();
        String delSql = "DELETE FROM T_XT_JSCD WHERE JS_ID = :ID";
        param.put("ID", id);
        excuteSql(delSql, param);
    }

    /**
     * 保存角色菜单
     *
     * @param jsId 角色id
     * @param cdId 菜单id
     */
    public void saveJscd(Long jsId, Long cdId) {
        excuteSql("INSERT INTO T_XT_JSCD (ID,JS_ID,CD_ID) VALUES (SEQ_XT_JSCD.NEXTVAL," + jsId + "," + cdId + ")");
    }

    /**
     * 系统角色删除
     *
     * @param id   角色ID
     * @param yhid 用户ID
     */

    public void deleteXtjs(Long id, Long yhid) {
        String sql = "UPDATE T_XT_JS SET CZBS = ?,CZR_ID = ?,CZSJ = SYSDATE WHERE ID = ?";
        excuteSql(sql, Czbs.SC.getIndex(), yhid, id);
    }

    /**重复校验
     *
     * @param id 角色Id
     * @param jsmc 角色名称
     * @param jsType 角色类型
     * @return
     */
    public boolean isRepeat(Long id, String jsmc, String jsType) {
        List param = new ArrayList();
        StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM T_XT_JS JS WHERE JS.CZBS <> ?");
        param.add(Czbs.SC.getIndex());
        if ("jsdm".equals(jsType)) {
            sql.append(" AND JS.JSDM = ?\n");
            param.add(jsmc);
        }
        if ("jsmc".equals(jsType)) {
            sql.append(" AND JS.JSMC = ?\n");
            param.add(jsmc);
        }
        if (CommonUtil.isNotNull(id)) {
            sql.append(" AND JS.ID <> ?");
            param.add(id);
        }
        return getJdbcTemplate().queryForObject(sql.toString(), param.toArray(), Integer.class) > 0;
    }

    /**
     * 角色对应的菜单列表
     *
     * @param jsId 角色Id
     * @return
     */
    public List<Map<String, Object>> getJscdByjsId(Long jsId) {
        Map<String, Object> param = new HashMap();
        String sql = "SELECT CD.ID CDID,CD.CDMC FROM T_XT_JSCD JSCD,T_XT_CD CD WHERE JSCD.CD_ID = CD.ID AND JSCD.JS_ID = :JSID AND CD.CZBS <> :CZBS";
        param.put("JSID", jsId);
        param.put("CZBS", Czbs.SC.getIndex());
        return queryMapList(sql, param);
    }

    /**
     * 根据角色Id删除对应的菜单
     *
     * @param jsid 角色Id
     */
    public void deleteCdByJsId(Long jsid) {
        String sql = "DELETE FROM T_XT_JSCD WHERE JS_ID = ?";
        excuteSql(sql, jsid);
    }
}
