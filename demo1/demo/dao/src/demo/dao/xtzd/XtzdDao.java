package demo.dao.xtzd;
import demo.dao.BaseDao;
import demo.domain.entity.Xtzd;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统字典Dao
 */
@Repository
public class XtzdDao extends BaseDao {

    /**查询条件拼接
     *
     * @param sql sql
     * @param params  Map参数
     * @param zdmc 字典名称
     * @param zddm 字典代码
     * @param sjzdId 数据字典Id
     */
    public void appendSql(StringBuilder sql,Map params,String zdmc,String zddm,String sjzdId) {
        sql.append(" WHERE T.CZBS <> :CZBS");
        params.put("CZBS", Czbs.SC.getIndex());
        //查询条件
        if (CommonUtil.isNotNull(zdmc)) {
            sql.append(" AND INSTR(T.ZDMC,:ZDMC) > 0 \n");
            params.put("ZDMC",zdmc);
        }
        if (CommonUtil.isNotNull(zddm)) {
            sql.append(" AND INSTR(T.ZDDM, :ZDDM) > 0 \n");
            params.put("ZDDM",zddm);
        }
        if (CommonUtil.isNotNull(sjzdId)) {
            sql.append(" AND (T.SJZD_ID = :SJZD_ID OR T.ID = :SJZD_ID) \n");
            params.put("SJZD_ID",sjzdId);
        }

    }

    /**
     * 字典总数
     *
     * @param zdmc 字典名称
     * @param zddm 字典代码
     * @param sjzd 上级字典
     * @return
     */
    public Integer getXtzdTotal(String zdmc, String zddm, String sjzd)  {
        StringBuilder sql = new StringBuilder("SELECT COUNT(T.ID) FROM T_XT_ZD T");
        Map params = new HashMap();
        appendSql(sql,params,zdmc,zddm,sjzd);
        return Integer.parseInt(queryUniqueResult(sql.toString(),params).toString());
    }

    /**
     * 字典列表
     *
     * @param zdmc   字典名称
     * @param zddm   字典代码
     * @param sjzd   上级字典
     * @param option 分页参数
     * @return
     */
    public List getXtzdList(String zdmc, String zddm, String sjzd, PageOption option) {
        StringBuilder sql = new StringBuilder("SELECT T.ID,\n" +
                "       T.ZDMC,\n" +
                "       T.ZDDM,\n" +
                "       T.SJZD_ID SJZD,\n" +
                "       DECODE(T.SJZD_ID,0, '',(SELECT ZDMC FROM T_XT_ZD WHERE ID = T.SJZD_ID)) SJZDMC,\n" +
                "       T.XH\n" +
                "  FROM T_XT_ZD T");
        Map params = new HashMap();
        appendSql(sql,params,zdmc,zddm,sjzd);
        sql.append(" ORDER BY T.CZSJ DESC");
        return queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), params);
    }

    /**
     * 获取所有是上级字典的集合
     *
     * @return 字典集合
     */
    public List<Xtzd> getAllSjzd()  {
        String hql = "from Xtzd where czbs<>:CZBS and (sjzd_id = '0' or sjzd_id is null) order by czsj desc";
        Map params = new HashMap();
        params.put("CZBS", Czbs.SC.getIndex());
        return findEntityList(hql, params);
    }


    /**
     * 字典项删除
     *
     * @param id    字典id
     * @param czrId 操作人id
     */
    public void deleteXtzd(Long id, Long czrId) {
        Map params = new HashMap();
        String sql = "UPDATE T_XT_ZD SET CZBS=:CZBS,CZR_ID=:CZR_ID,CZSJ=SYSDATE WHERE ID =:ID";
        params.put("CZBS", Czbs.SC.getIndex());
        params.put("CZR_ID", czrId);
        params.put("ID", id);
        excuteSql(sql, params);
    }

    /**
     * 判断选中字典项是否有下级
     *
     * @param id 字典id
     * @return
     */
    public boolean checkXtzd(Long id) {
        List params = new ArrayList();
        String sql = " SELECT COUNT(1) FROM T_XT_ZD WHERE CZBS <> ? AND SJZD_ID = ? ";
        params.add(Czbs.SC.getIndex());
        params.add(id);
        return super.getJdbcTemplate().queryForObject(sql, params.toArray(), Integer.class) > 0;
    }

    /**
     * 判断字典代码是否重复
     * @param id 字典Id
     * @param zddm 字典代码
     * @return
     */
    public boolean isZddmCf(Long id, String zddm)  {
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        sql.append("SELECT COUNT(1)\n" +
                "  FROM T_XT_ZD T\n" +
                " WHERE T.ZDDM = ?\n" +
                "   AND T.CZBS <> ?\n");
        params.add(zddm);
        params.add(Czbs.SC.getIndex());
        if (CommonUtil.isNotNull(id)) {
            sql.append("   AND T.ID <> ?\n");
            params.add(id);
        }
        return super.getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class) <= 0;
    }

    /**
     * 根据字典代码获取字典项内容
     *
     * @param dictCode 字典代码
     */
    public List<Map<String, Object>> getXtzdByZddm(String dictCode) {
        String sql = "SELECT *\n" +
                "  FROM T_XT_ZD ZD\n" +
                " WHERE EXISTS (SELECT 1\n" +
                "          FROM T_XT_ZD T\n" +
                "         WHERE T.ZDDM = :ZDDM\n" +
                "           AND T.CZBS <> :CZBS\n" +
                "           AND ZD.SJZD_ID = T.ID)\n" +
                "AND ZD.CZBS <> :CZBS\n " +
                " ORDER BY ZD.XH";
        Map param = new HashMap();
        param.put("CZBS",Czbs.SC.getIndex());
        param.put("ZDDM",dictCode);
        return super.queryMapList(sql, param);
    }
}
