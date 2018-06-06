package demo.dao.xtsq;

import demo.dao.BaseDao;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.service.xtsq.XtsqService;
import demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区管理Dao
 */
@Repository
public class XtsqDao extends BaseDao {

    /**
     * 获取社区列表
     *
     * @param option 分页参数
     * @param sqmc   社区名称
     * @param jgid   机构Id
     * @return
     */
    public List getXtsqList(PageOption option, String sqmc, Long jgid) {
        StringBuilder sql = new StringBuilder("SELECT SQ.ID,\n" +
                "       SQ.SQMC AS SQMC,\n" +
                "       JG.JGMC,\n" +
                "       SQ.CZSJ,\n" +
                "       (SELECT YH.XM FROM T_XT_YH YH WHERE YH.ID = SQ.CZYH_ID) CZYH\n" +
                "  FROM T_XT_SQ SQ\n" +
                "  LEFT JOIN T_XT_SQJG SQJG\n" +
                "    ON SQJG.SQ_ID = SQ.ID\n" +
                "  LEFT JOIN T_XT_JG JG\n" +
                "    ON JG.ID = SQJG.JG_ID\n" +
                "   AND JG.CZBS <> :CZBS\n" +
                " WHERE SQ.CZBS <> :CZBS");
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        appendParam(sql, paraMap, sqmc, jgid);
        sql.append(" ORDER BY SQ.CZSJ DESC");
        return super.queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), paraMap);
    }

    /**
     * 拼接查询语句
     *
     * @param sql     SQL
     * @param paraMap 参数
     * @param sqmc    用户名称
     * @param jgid    机构Id
     */
    public void appendParam(StringBuilder sql, Map paraMap, String sqmc, Long jgid) {
        if (CommonUtil.isNotNull(jgid)) {
            sql.append("  AND JG.ID IN (SELECT A.ID FROM T_XT_JG A START WITH A.ID = :JGID CONNECT BY PRIOR A.ID = A.SJJG_ID)");
            paraMap.put("JGID", jgid);
        }
        if (CommonUtil.isNotNull(sqmc)) {
            sql.append(" AND INSTR(SQ.SQMC,:SQMC) > 0");
            paraMap.put("SQMC", sqmc);
        }
    }

    /**
     * 获取社区总数
     *
     * @param sqmc 用户名称
     * @param jgid 机构Id
     * @return
     */
    public int getXtsqTotal(String sqmc, Long jgid) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(SQ.ID)\n" +
                "  FROM T_XT_SQ SQ\n" +
                "  LEFT JOIN T_XT_SQJG SQJG\n" +
                "    ON SQJG.SQ_ID = SQ.ID\n" +
                "  LEFT JOIN T_XT_JG JG\n" +
                "    ON JG.ID = SQJG.JG_ID\n" +
                " WHERE SQ.CZBS <> :CZBS");
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        appendParam(sql, paraMap, sqmc, jgid);
        return Integer.parseInt(queryUniqueResult(sql.toString(), paraMap).toString());
    }

    /**
     * 保存社区机构Id
     *
     * @param sqId 社区Id
     * @param jgId 机构Id
     */
    public void saveSqJg(Long sqId, Long jgId) {
        String sql = "INSERT INTO T_XT_SQJG VALUES (SEQ_XT_SQJG.NEXTVAL, :SQ_ID, :JG_ID)";
        Map paraMap = new HashMap();
        paraMap.put("SQ_ID", sqId);
        paraMap.put("JG_ID", jgId);
        super.excuteSql(sql, paraMap);
    }

    /**
     * 删除社区
     *
     * @param id     社区Id
     * @param czyhId 操作人Id
     */
    public void deleteXtsq(Long id, Long czyhId) {
        String sql = "UPDATE T_XT_SQ\n" +
                "   SET CZBS = :CZBS, CZSJ = SYSDATE, CZYH_ID = :CZYH_ID\n" +
                " WHERE ID = :ID";
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("CZYH_ID", czyhId);
        paraMap.put("ID", id);
        excuteSql(sql, paraMap);
    }

    /**
     * 删除社区机构
     *
     * @param sqId 社区Id
     */
    public void deleteSqJg(Long sqId) {
        String sql = "DELETE FROM T_XT_SQJG WHERE SQ_ID = :SQ_ID";
        Map paraMap = new HashMap();
        paraMap.put("SQ_ID", sqId);
        super.excuteSql(sql, paraMap);
    }

}
