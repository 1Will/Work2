package demo.dao.xtsqtj;

import demo.dao.BaseDao;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wcy
 * @date ${date}
 * $END
 */
@Repository
public class XtsqtjDao extends BaseDao {
    /**
     * 获取机构统计列表,带分页逻辑
     *
     * @param option 页面对象
     * @param jgId   上级机构id
     * @return 返回List
     */
    public List getXtsqtjList(PageOption option, Long jgId) {
        StringBuilder sql = new StringBuilder();
        Map paraMap = new HashMap();
        appendSql(sql, paraMap, jgId);
        return super.queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), paraMap);
    }

    /**
     * 获取机构统计列表,不带分页逻辑
     *
     * @param jgId 上级机构id
     * @return 返回List
     */
    public List getXtsqtjList(Long jgId) {
        StringBuilder sql = new StringBuilder();
        Map paraMap = new HashMap();
        appendSql(sql, paraMap, jgId);
        return super.queryArrayList(sql.toString(), paraMap);
    }

    /**
     * StringBuilder添加sql语句
     *
     * @param sql
     * @param paraMap
     * @param jgId
     */
    private void appendSql(StringBuilder sql, Map paraMap, Long jgId) {
        sql.append("SELECT J.ID,\n" +
                "       J.JGMC,\n" +
                "       (SELECT COUNT(SQ.ID)\n" +
                "          FROM T_XT_JG JG\n" +
                "          LEFT JOIN T_XT_SQJG SQJG\n" +
                "            ON SQJG.JG_ID = JG.ID\n" +
                "          LEFT JOIN T_XT_SQ SQ\n" +
                "            ON SQ.ID = SQJG.SQ_ID\n" +
                "         WHERE JG.ID IN (SELECT A.ID\n" +
                "                           FROM T_XT_JG A\n" +
                "                          START WITH A.ID = J.ID\n" +
                "                         CONNECT BY PRIOR A.ID = A.SJJG_ID)\n" +
                "           AND SQ.CZBS <> :CZBS\n" +
                "           AND JG.CZBS <> :CZBS) AS SQNUM,\n" +
                "       (SELECT COUNT(*)\n" +
                "          FROM T_XT_JG C\n" +
                "         WHERE C.SJJG_ID = J.ID\n" +
                "           AND C.CZBS <> :CZBS) AS NEXT\n" +
                "  FROM T_XT_JG J\n" +
                " WHERE J.SJJG_ID = :SJJGID\n" +
                "   AND J.CZBS <> :CZBS\n" +
                " ORDER BY J.ID");
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("SJJGID", jgId);
    }

    /**
     * 获取下级机构总数
     *
     * @param jgId 上级机构id
     * @return 机构总数
     */
    public int getXtsqtjTotal(Long jgId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(J.ID)\n" +
                "  FROM T_XT_JG J\n" +
                " WHERE J.SJJG_ID = :SJJGID\n" +
                "   AND J.CZBS <> :CZBS");
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("SJJGID", jgId);
        return Integer.parseInt(queryUniqueResult(sql.toString(), paraMap).toString());
    }
}
