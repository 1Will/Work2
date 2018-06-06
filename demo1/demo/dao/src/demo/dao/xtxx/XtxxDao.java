package demo.dao.xtxx;

import demo.dao.BaseDao;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息发布Dao
 */
@Repository
public class XtxxDao extends BaseDao {
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
        StringBuilder sql = new StringBuilder("SELECT T.ID ID,\n" +
                "       T.XXBT XXBT,\n" +
                "       T.GJZ GJZ,\n" +
                "       T.FBSJ FBSJ,\n" +
                "       T.FBZT FBZT,\n" +
                "       T.TOP TOP,\n" +
                "       T.CZSJ CZSJ,\n" +
                "       (SELECT YH.XM FROM T_XT_YH YH WHERE YH.ID = T.FBR_ID) FBR,\n" +
                "       (SELECT JG.JGMC FROM T_XT_JG JG WHERE JG.ID = T.FBDW_ID) FBDW\n" +
                "  FROM T_XT_XX T\n" +
                " WHERE T.CZBS <> :CZBS\n");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("CZBS", Czbs.SC.getIndex());
        appendSql(sql, params, fbr, xxbt, gjz);
        sql.append("ORDER BY T.TOP DESC , T.FBSJ DESC NULLS LAST ,T.CZSJ DESC");
        return super.queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), params);
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
        StringBuilder sql = new StringBuilder("SELECT COUNT(T.ID) FROM T_XT_XX T WHERE T.CZBS <>:CZBS\n");
        Map params = new HashMap();
        appendSql(sql, params, fbr, xxbt, gjz);
        params.put("CZBS", Czbs.SC.getIndex());
        return Integer.parseInt(queryUniqueResult(sql.toString(), params).toString());
    }

    /**
     * 拼接sql
     *
     * @param sql
     * @param params
     * @param fbr
     * @param xxbt
     * @param gjz
     */
    public void appendSql(StringBuilder sql, Map params, Long fbr, String xxbt, String gjz) {
        sql.append("AND T.FBR_ID = :FBR_ID\n");
        params.put("FBR_ID", fbr);
        if (CommonUtil.isNotNull(xxbt)) {
            sql.append("AND INSTR(T.XXBT,:XXBT) > 0\n");
            params.put("XXBT", xxbt);
        }
        if (CommonUtil.isNotNull(gjz)) {
            sql.append("AND INSTR(T.GJZ,:GJZ) > 0\n");
            params.put("GJZ", gjz);
        }
    }

    /**
     * 删除信息发布
     *
     * @param id
     * @param czyhId 操作人id
     */
    public void deleteXtxx(Long id, Long czyhId) {
        String sql = "UPDATE T_XT_XX\n" +
                "   SET CZBS = :CZBS, CZSJ = SYSDATE, CZYH_ID = :CZYH_ID\n" +
                " WHERE ID = :ID";
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("CZYH_ID", czyhId);
        paraMap.put("ID", id);
        excuteSql(sql, paraMap);
    }

    /**
     * 取消所有置顶
     */
    public void cancleTop() {
        String sql = "UPDATE T_XT_XX SET TOP = :TOP";
        Map paraMap = new HashMap();
        paraMap.put("TOP", 0);
        excuteSql(sql, paraMap);
    }

}
