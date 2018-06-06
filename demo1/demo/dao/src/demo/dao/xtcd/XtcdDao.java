package demo.dao.xtcd;

import demo.dao.BaseDao;
import demo.domain.entity.Xtcd;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单Dao
 */
@Repository
public class XtcdDao extends BaseDao {

    /**
     * 获取用户的顶级菜单列表
     *
     * @param yhId 登录用户id
     */
    public List<Map> getDjcdList(Long yhId) {
        String sql = "SELECT CD.ID,CD.CDMC,CD.URL,CD.XSTP,CD.SJCD_ID,\n" +
                "       (SELECT COUNT(ID) FROM T_XT_CD T WHERE T.SJCD_ID = CD.ID) XJGS FROM T_XT_CD CD\n" +
                "WHERE CD.CZBS <> :CZBS\n" +
                "  AND CD.SSXT = 0\n" +
                "  AND CD.ID IN (SELECT JSCD.CD_ID FROM T_XT_JSCD JSCD WHERE JSCD.JS_ID IN \n" +
                "    (SELECT YHJS.JS_ID FROM T_XT_YHJS YHJS WHERE YHJS.YH_ID = :YHID))\n" +
                "  AND CD.SJCD_ID IS NULL ORDER BY PXH ASC";
        Map paraMap = new HashMap();
        paraMap.put("YHID", yhId);
        paraMap.put("CZBS", Czbs.SC.getIndex());
        return super.queryMapList(sql, paraMap);
    }


    /**
     * 根据上级菜单id获取下级菜单
     *
     * @param sjcdId 上级菜单Id
     * @param yhId    用户Id
     * @return
     */
    public List<Map> getXjcdList(Long sjcdId, Long yhId) {
        String sql = "SELECT CD.ID, CD.CDMC, CD.URL, CD.XSTP, CD.SJCD_ID\n" +
                "  FROM T_XT_CD CD\n" +
                " WHERE CD.ID IN (SELECT CD.ID\n" +
                "                   FROM T_XT_CD CD, T_XT_JSCD JSCD, T_XT_YHJS YHJS\n" +
                "                  WHERE CD.CZBS <> :CZBS\n" +
                "                    AND SJCD_ID = :SJCD_ID\n" +
                "                    AND CD.ID = JSCD.CD_ID\n" +
                "                    AND JSCD.JS_ID = YHJS.JS_ID\n" +
                "                    AND YHJS.YH_ID = :YHID)\n" +
                "   AND CD.CZBS <> :CZBS\n" +
                " ORDER BY CD.PXH ASC";
        Map paraMap = new HashMap();
        paraMap.put("CZBS", Czbs.SC.getIndex());
        paraMap.put("SJCD_ID", sjcdId);
        paraMap.put("YHID", yhId);
        return super.queryMapList(sql, paraMap);
    }

    /**
     * 菜单列表总数
     *
     * @param cdmc 菜单名称
     * @param ssxt 所属系统
     * @return
     */
    public Integer getCdTotal(String cdmc, String ssxt) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(1)\n" +
                "FROM T_XT_CD T\n" +
                "WHERE T.CZBS <> ?\n");
        List<Object> params = new ArrayList<Object>();
        params.add(Czbs.SC.getIndex());

        if (CommonUtil.isNotNull(cdmc)) {
            sql.append("AND INSTR(T.CDMC, ?) > 0\n");
            params.add(cdmc);
        }
        if (CommonUtil.isNotNull(ssxt)) {
            sql.append("AND T.SSXT = ?\n");
            params.add(ssxt);
        }
        return super.getJdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }


    /**
     * 菜单管理列表集合
     *
     * @param option 分页参数
     * @param cdmc   菜单名称
     * @param ssxt   所属系统
     * @return
     */
    public List getCdList(PageOption option, String cdmc, String ssxt) {
        StringBuilder sql = new StringBuilder("SELECT T.ID,\n" +
                "               T.CDMC,\n" +
                "               T.SSXT,\n" +
                "               (SELECT CD.CDMC\n" +
                "                  FROM T_XT_CD CD\n" +
                "                  WHERE CD.CZBS<> :CZBS\n" +
                "                  AND CD.ID = T.SJCD_ID)SJCDMC,\n" +
                "               T.URL,\n" +
                "               T.CZSJ\n" +
                "               FROM T_XT_CD T\n" +
                "               WHERE T.CZBS <> :CZBS\n");
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put("CZBS", Czbs.SC.getIndex());
        if (CommonUtil.isNotNull(cdmc)) {
            sql.append("AND INSTR(T.CDMC,:CDMC) > 0\n");
            params.put("CDMC", cdmc);
        }
        if (CommonUtil.isNotNull(ssxt)) {
            sql.append("AND T.SSXT = :SSXT\n");
            params.put("SSXT", ssxt);
        }
        sql.append("ORDER BY T.CZSJ DESC");
        return super.queryMapListForPage(sql.toString(), option.getPageNo(), option.getPageSize(), params);
    }

    /**
     * 判断是否有子菜单
     *
     * @param cdId 菜单ID
     * @param ssxt 所属系统
     * @return
     */
    public boolean getChildMenuNum(Long cdId, String ssxt) {
        List<Object> params = new ArrayList<Object>();
        String sql = "SELECT COUNT(1)\n" +
                "FROM T_XT_CD\n" +
                "WHERE CZBS<> ?\n" +
                "AND SJCD_ID = ?\n" +
                "AND SSXT = ?";
        params.add(Czbs.SC.getIndex());
        params.add(cdId);
        params.add(ssxt);
        return super.getJdbcTemplate().queryForObject(sql, params.toArray(), Integer.class) > 0;
    }

    /**
     * 删除菜单
     *
     * @param cdId   菜单id
     * @param userId 用户id
     * @param ssxt   所属系统
     */
    public void deleteCd(Long cdId, Long userId, String ssxt) {
        Map<String, Object> params = new HashMap<String, Object>(16);
        String sql = "UPDATE T_XT_CD SET CZSJ=SYSDATE,CZBS = :CZBS,CZR_ID = :CZR_ID\n" +
                "WHERE ID = :ID\n" +
                "AND SSXT = :SSXT\n";
        params.put("CZBS", Czbs.SC.getIndex());
        params.put("CZR_ID", userId);
        params.put("ID", cdId);
        params.put("SSXT", ssxt);
        super.excuteSql(sql, params);
    }

    /**
     * 获取所有的菜单
     *
     * @param id   菜单Id
     * @param ssxt 所属系统
     * @return
     */
    public List<Xtcd> getAllCd(Long id, String ssxt) {
        StringBuilder hql = new StringBuilder("FROM Xtcd WHERE CZBS<> :CZBS AND SSXT =:SSXT\n");
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("CZBS", Czbs.SC.getIndex());
        param.put("SSXT", ssxt);
        if (CommonUtil.isNotNull(id)) {
            hql.append("AND id <> :ID\n");
            hql.append("AND (sjcdId IS NULL OR sjcdId <> :ID)\n");
            param.put("ID", id);
        }
        hql.append("ORDER BY PXH");
        return findEntityList(hql.toString(), param);
    }

    /**
     * 获取最大序号
     *
     * @return
     */
    public Long getInitXh() {
        String sql = "SELECT MAX(PXH) PXH\n" +
                "FROM T_XT_CD WHERE CZBS<> :CZBS\n";
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("CZBS", Czbs.SC.getIndex());
        Map map = super.queryUniqueMapResult(sql, param);
        Long pxh;
        String maxPxh = "PXH";
        if (CommonUtil.isNotNull(map.get(maxPxh))) {
            pxh = Long.valueOf(map.get("PXH").toString()) + 1;
        } else {
            pxh = 1L;
        }
        return pxh;
    }

    /**
     * 判断菜单是否重复
     *
     * @param id   菜单id
     * @param cdmc 菜单名称
     * @param ssxt 所属系统
     * @return
     */
    public boolean checkCdmc(Long id, String cdmc, String ssxt) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(1)\n" +
                "FROM T_XT_CD\n" +
                "WHERE CZBS<> ?\n" +
                "AND CDMC = ?\n" +
                "AND SSXT = ?\n");
        List<Object> param = new ArrayList<Object>();
        param.add(Czbs.SC.getIndex());
        param.add(cdmc);
        param.add(ssxt);
        if (CommonUtil.isNotNull(id)) {
            sql.append("AND ID<>?\n");
            param.add(id);
        }
        return super.getJdbcTemplate().queryForObject(sql.toString(), Integer.class, param.toArray()) > 0;
    }

}
