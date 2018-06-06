package demo.dao.xtfj;

import demo.dao.BaseDao;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件Dao
 */
@Repository
public class XtfjDao extends BaseDao {

    /**
     * 查询附件列表
     *
     * @param option
     * @param xxId   信息Id
     * @return
     */
    public List getFjList(PageOption option, Long xxId) {
        StringBuilder sql = new StringBuilder("SELECT T.ID, T.FJMC, T.FJDX, T.CCLJ, T.CZSJ\n" +
                "  FROM T_XT_FJ T\n" +
                " WHERE T.CZBS <> :CZBS\n" +
                "   AND T.XXFB_ID = :XXFB_ID\n");
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put("CZBS", Czbs.SC.getIndex());
        params.put("XXFB_ID", xxId);
        sql.append("ORDER BY T.CZSJ DESC");
        return super.queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), params);

    }

    /**
     * 获取附件数量
     *
     * @param xxId 信息Id
     * @return
     */
    public int getFjTotal(Long xxId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(T.ID)\n" +
                "  FROM T_XT_FJ T\n" +
                " WHERE T.CZBS <> :CZBS\n" +
                "   AND T.XXFB_ID = :XXFB_ID\n");
        Map params = new HashMap();
        params.put("CZBS", Czbs.SC.getIndex());
        params.put("XXFB_ID", xxId);
        return Integer.parseInt(queryUniqueResult(sql.toString(), params).toString());
    }

    /**
     * 删除附件
     *
     * @param id
     * @param czyhId
     */
    public void deleteXtfj(Long id, Long czyhId) {
        String sql = "UPDATE T_XT_FJ\n" +
                "   SET CZBS = :CZBS, CZSJ = SYSDATE, CZYH_ID = :CZYH_ID\n" +
                " WHERE ID = :ID";
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("CZYH_ID", czyhId);
        paraMap.put("ID", id);
        excuteSql(sql, paraMap);
    }

}
