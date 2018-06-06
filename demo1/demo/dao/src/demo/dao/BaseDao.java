package demo.dao;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.io.Serializable;
import java.sql.*;

import java.sql.Date;
import java.util.*;


/**
 * Created by wyl on 2017/12/19.
 * 系统通用数据层
 */
@Repository
public class BaseDao {
    //数据层模板
    @Autowired(required = true)
    @Qualifier("jdbcTemplate")
    public JdbcTemplate jdbcTemplate;
    @Autowired(required = true)
    @Qualifier("hibernateTemplate")
    public HibernateTemplate hibernateTemplate;

    /**
     * HQL语句操作部分
     */

    /**
     * @param hql    hql语句
     * @param params 各绑定变量参数
     * @return 实体集合
     */
    public List findEntityList(String hql, Object... params) {
        return createQuery(hql, params).list();
    }

    /**
     * @param hql    hql语句
     * @param params 参数集合
     * @return 实体集合
     */
    public List findEntityList(String hql, List params) {
        return createQuery(hql, params).list();
    }

    /**
     * @param hql    hql语句
     * @param params 参数键值对
     * @return 实体集合
     */
    public List findEntityList(String hql, Map<String, Object> params) {
        return createQuery(hql, params).list();
    }

    /**
     * @param hql    hql语句
     * @param params 各绑定变量参数
     * @return Map集合（key为实体属性）
     */
    public List findMapList(String hql, Object... params) {
        return createQuery(hql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * @param hql    hql语句
     * @param params 参数集合
     * @return Map集合（key为实体属性）
     */
    public List findMapList(String hql, List params) {
        return createQuery(hql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * @param hql    hql语句
     * @param params 参数键值对
     * @return Map集合（key为实体属性）
     */
    public List findMapList(String hql, Map<String, Object> params) {
        return createQuery(hql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 分页HQL查询
     *
     * @param hql      HQL语句
     * @param pageNo   页码
     * @param pageSize 单页条数
     * @param params   各绑定变量参数
     * @return 单页实体集合
     */
    public List findEntityListForPage(String hql, int pageNo, int pageSize, Object... params) {
        return createQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
    }

    /**
     * 分页HQL查询
     *
     * @param hql      HQL语句
     * @param pageNo   页码
     * @param pageSize 单页条数
     * @param params   参数集合
     * @return 单页实体集合
     */
    public List findEntityListForPage(String hql, int pageNo, int pageSize, List params) {
        return createQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
    }

    /**
     * 分页HQL查询
     *
     * @param hql      HQL语句
     * @param pageNo   页码
     * @param pageSize 单页条数
     * @param params   参数键值对
     * @return 单页实体集合
     */
    public List findEntityListForPage(String hql, int pageNo, int pageSize, Map<String, Object> params) {
        return createQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
    }

    /**
     * 分页HQL查询
     *
     * @param hql      HQL语句
     * @param pageNo   页码
     * @param pageSize 单页条数
     * @param params   各绑定变量参数
     * @return 单页Map集合
     */
    public List findMapListForPage(String hql, int pageNo, int pageSize, Object... params) {
        return createQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 分页HQL查询
     *
     * @param hql      HQL语句
     * @param pageNo   页码
     * @param pageSize 单页条数
     * @param params   参数集合
     * @return 单页Map集合
     */
    public List findMapListForPage(String hql, int pageNo, int pageSize, List params) {
        return createQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 分页HQL查询
     *
     * @param hql      HQL语句
     * @param pageNo   页码
     * @param pageSize 单页条数
     * @param params   参数键值对
     * @return 单页Map集合
     */
    public List findMapListForPage(String hql, int pageNo, int pageSize, Map<String, Object> params) {
        return createQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 各绑定变量参数
     * @return 实体对象
     */
    public Object findUniqueResult(String hql, Object... params) {
        return createQuery(hql, params).uniqueResult();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 参数集合
     * @return 实体对象
     */
    public Object findUniqueResult(String hql, List params) {
        return createQuery(hql, params).uniqueResult();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 参数键值对
     * @return 实体对象
     */
    public Object findUniqueResult(String hql, Map<String, Object> params) {
        return createQuery(hql, params).uniqueResult();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 各绑定变量参数
     * @return Map对象
     */
    public Map<String, Object> findUniqueMapResult(String hql, Object... params) {
        return (Map<String, Object>) createQuery(hql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .uniqueResult();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 参数集合
     * @return Map对象
     */
    public Map<String, Object> findUniqueMapResult(String hql, List params) {
        return (Map<String, Object>) createQuery(hql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .uniqueResult();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 参数集合
     * @return Map对象
     */
    public Map<String, Object> findUniqueMapResult(String hql, Map<String, Object> params) {
        return (Map<String, Object>) createQuery(hql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .uniqueResult();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 各绑定变量参数
     * @return Map对象
     */
    public void excuteHql(String hql, Object... params) {
        createQuery(hql, params).executeUpdate();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 参数集合
     * @return Map对象
     */
    public void excuteHql(String hql, List params) {
        createQuery(hql, params).executeUpdate();
    }

    /**
     * HQL查询单条数据
     *
     * @param hql    HQL语句
     * @param params 参数键值对
     * @return Map对象
     */
    public void excuteHql(String hql, Map<String, Object> params) {
        createQuery(hql, params).executeUpdate();
    }

    /**
     * 实体保存
     *
     * @param object
     */
    @Transactional

    public void saveOrUpdate(Object object) {
        hibernateTemplate.saveOrUpdate(object);
    }

    /**
     * 实体保存
     *
     * @param object
     */
    public void save(Object object) {
        hibernateTemplate.save(object);
    }

    /**
     * 实体保存（更新或者插入）
     *
     * @param object
     */
    public void update(Object object) {
        hibernateTemplate.update(object);
    }

    public <T> T merge(Object object) {
        return (T) hibernateTemplate.merge(object);
    }

    /**
     * 根据主键获取实体对象
     *
     * @param classz
     * @param id
     * @param <T>
     * @return
     */
    public <T> T getObject(Class<T> classz, Serializable id) {
        return hibernateTemplate.get(classz, id);
    }

    /**
     * SQL语句操作部分
     */

    /**
     * @param sql    SQL语句
     * @param params 各绑定变量参数
     * @return Object结果集
     */
    public List queryArrayList(String sql, Object... params) {
        return createSQLQuery(sql, params).list();
    }

    /**
     * @param sql    SQL语句
     * @param params 参数集合
     * @return Object结果集
     */
    public List queryArrayList(String sql, List params) {
        return createSQLQuery(sql, params).list();
    }

    /**
     * @param sql    SQL语句
     * @param params 参数键值对
     * @return Object结果集
     */
    public List queryArrayList(String sql, Map<String, Object> params) {
        return createSQLQuery(sql, params).list();
    }

    /**
     * @param sql    SQL语句
     * @param params 各绑定变量参数
     * @return Map集合（KEY为大写数据库字段名）
     */
    public List queryMapList(String sql, Object... params) {
        return createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * @param sql    SQL语句
     * @param params 各绑定变量参数
     * @return Map集合（KEY为大写数据库字段名）
     */
    public List queryMapList(String sql, List params) {
        return createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * @param sql    SQL语句
     * @param params 参数键值对
     * @return Map集合（KEY为大写数据库字段名）
     */
    public List queryMapList(String sql, Map<String, Object> params) {
        return createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * @param sql    SQL语句
     * @param clobs  CLOB类型数组
     * @param params 参数对象
     * @return Map集合
     */
    public List queryClobMapList(String sql, final String[] clobs, Object... params) {
        return createSQLQuery(sql, params).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).list();
    }

    /**
     * @param sql    SQL语句
     * @param clobs  CLOB类型数组
     * @param params 参数列表
     * @return Map集合
     */
    public List queryClobMapList(String sql, final String[] clobs, List params) {
        return createSQLQuery(sql, params).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).list();
    }

    /**
     * @param sql    SQL语句
     * @param clobs  CLOB类型数组
     * @param params 参数键值对
     * @return Map集合
     */
    public List queryClobMapList(String sql, final String[] clobs, Map<String, Object> params) {
        return createSQLQuery(sql, params).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数对象
     * @return 分页列表
     */
    public List queryArrayListForPage(String sql, int pageNo, int pageSize, Object... params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数对象
     * @return 分页列表
     */
    public List queryArrayListForPage(String sql, int pageNo, int pageSize, List params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数键值对
     * @return 分页列表
     */
    public List queryArrayListForPage(String sql, int pageNo, int pageSize, Map<String, Object> params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数键值对
     * @return 分页列表
     */
    public List queryMapListForPage(String sql, int pageNo, int pageSize, Object... params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 带session参数的queryMapListForPage，防止session上限
     *
     * @param sql
     * @param pageNo
     * @param pageSize
     * @param session
     * @param params
     * @return
     */
    public List queryMapListForPageForIndex(String sql, int pageNo, int pageSize, Session session, List params) {
        return createSQLQueryForIndex(sql, session, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数列表
     * @return 分页列表
     */
    public List queryMapListForPage(String sql, int pageNo, int pageSize, List params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数键值对
     * @return 分页列表
     */
    public List queryMapListForPage(String sql, int pageNo, int pageSize, Map<String, Object> params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数键值对
     * @return 分页列表
     */
    public List queryClobMapListForPage(String sql, int pageNo, int pageSize, final String[] clobs, Object... params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数列表
     * @return 分页(含Clob类型)Map集合
     */
    public List queryClobMapListForPage(String sql, int pageNo, int pageSize, final String[] clobs, List params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).list();
    }

    /**
     * 分页SQL查询
     *
     * @param sql      SQL语句
     * @param pageNo   页码
     * @param pageSize 每页数据个数
     * @param params   参数键值对
     * @return 分页(含Clob类型)Map集合
     */
    public List queryClobMapListForPage(String sql, int pageNo, int pageSize, final String[] clobs, Map<String, Object> params) {
        return createSQLQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).list();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量参数
     * @return 一个实体
     */
    public Object queryUniqueResult(String sql, Object... params) {
        return createSQLQuery(sql, params).uniqueResult();
    }

    /**
     * 带session的queryUniqueResult方法，防止session上限
     *
     * @param sql    sql语句
     * @param params 各绑定变量参数
     * @return 一个实体
     */
    public Object queryUniqueResultForIndex(String sql, Session session, List params) {
        return createSQLQueryForIndex(sql, session, params).uniqueResult();
    }

    public Object queryUniqueResultForIndex(String sql, Session session, Map params) {
        return createSQLQueryForIndex(sql, session, params).uniqueResult();
    }


    /**
     * @param sql    sql语句
     * @param params 各绑定变量列表
     * @return 一个实体
     */
    public Object queryUniqueResult(String sql, List params) {
        return createSQLQuery(sql, params).uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量键值对
     * @return 一个实体
     */
    public Object queryUniqueResult(String sql, Map<String, Object> params) {
        return createSQLQuery(sql, params).uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量参数
     * @return 一个实体的键值对
     */
    public Map<String, Object> queryUniqueMapResult(String sql, Object... params) {
        return (Map<String, Object>) createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量列表
     * @return 一个实体的键值对
     */
    public Map<String, Object> queryUniqueMapResult(String sql, List params) {
        return (Map<String, Object>) createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量键值对
     * @return 一个实体的键值对
     */
    public Map<String, Object> queryUniqueMapResult(String sql, Map<String, Object> params) {
        return (Map<String, Object>) createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量参数
     * @return 一个Clob类型实体的键值对
     */
    public Map<String, Object> queryUniqueClobMapResult(String sql, final String[] clobs, Object... params) {
        return (Map<String, Object>) createSQLQuery(sql, params).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量列表
     * @return 一个Clob类型实体的键值对
     */
    public Map<String, Object> queryUniqueClobMapResult(String sql, final String[] clobs, List params) {
        return (Map<String, Object>) createSQLQuery(sql, params).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量键值对
     * @return 一个Clob类型实体的键值对
     */
    public Map<String, Object> queryUniqueClobMapResult(String sql, final String[] clobs, Map<String, Object> params) {
        return (Map<String, Object>) createSQLQuery(sql, params).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                return transformClob(values, columns, clobs);
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).uniqueResult();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量参数
     */
    public void excuteSql(String sql, Object... params) {
        createSQLQuery(sql, params).executeUpdate();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量列表
     */
    public void excuteSql(String sql, List params) {
        createSQLQuery(sql, params).executeUpdate();
    }

    /**
     * @param sql    sql语句
     * @param params 各绑定变量键值对
     */
    public void excuteSql(String sql, Map<String, Object> params) {
        createSQLQuery(sql, params).executeUpdate();
    }

    /**
     * 获取当前数据库时间
     *
     * @return TIMESTAMP类型时间
     */
    public Date getDbCurrentTime() {
        return (Date) createSQLQuery("select sysdate from dual").addScalar("sysdate", StandardBasicTypes.TIMESTAMP)
                .uniqueResult();
    }

    /**
     * 获取当前数据库时间
     *
     * @return DATE类型时间
     */
    public Date getDbCurrentDate() {
        return (Date) createSQLQuery("select sysdate from dual").addScalar("sysdate", StandardBasicTypes.DATE)
                .uniqueResult();
    }

    /**
     * 执行存储过程
     *
     * @param sql       存储过程语句，如{call proc(?,?,?)}
     * @param inParams  传入参数，key是传入参数的位置，value是参数值
     * @param outParams 传出参数，key是传出参数的位置，value是传出参数的类型，如OracleTypes.CURSOR
     * @return 返回参数封装为MAP形式，key是参数的位置，跟outParams的key一致，value是参数值，如果是集合类型，则该参数封装为List<Map<String,Object>>类型
     */
    public Map<Integer, Object> callProcedure(final String sql, final Map<Integer, Object> inParams, final Map<Integer, Integer> outParams) {
        return (Map<Integer, Object>) jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement st = connection.prepareCall(sql);
                //设置传入参数
                if (inParams != null && !inParams.isEmpty()) {
                    Object value = null;
                    for (Map.Entry<Integer, Object> en : inParams.entrySet()) {
                        value = en.getValue();
                        if (value != null && value instanceof Date) {
                            st.setObject(en.getKey(), new java.sql.Date(((Date) value).getTime()));
                        } else {
                            st.setObject(en.getKey(), en.getValue());
                        }
                    }
                }
                //设置传出参数
                if (outParams != null && !outParams.isEmpty()) {
                    for (Map.Entry<Integer, Integer> en : outParams.entrySet()) {
                        st.registerOutParameter(en.getKey(), en.getValue());
                    }
                }
                return st;
            }
        }, new CallableStatementCallback<Object>() {
            @Override
            public Object doInCallableStatement(CallableStatement st) throws SQLException, DataAccessException {
                //传出参数封装成MAP形式返回，位置跟outParams的key一致
                Map<Integer, Object> resultMap = null;
                //执行存储过程
                st.execute();
                //封装结果集
                if (outParams != null && !outParams.isEmpty()) {
                    resultMap = new HashMap<Integer, Object>();
                    //非结果集类型的传出参数
                    Object result = null;
                    //结果集类型的传出参数,封装成MAP的集合形式输出
                    ResultSet rs = null;
                    ResultSetMetaData meteData = null;
                    Integer columnLen = 0;
                    String filedName = null;
                    Map<String, Object> map = null;
                    List<Map<String, Object>> mapList = null;
                    //循环迭代传出参数
                    for (Map.Entry<Integer, Integer> en : outParams.entrySet()) {
                        result = st.getObject(en.getKey());
                        if (result instanceof ResultSet) {
                            //结果集类型
                            rs = (ResultSet) result;
                            //获取列总数
                            meteData = rs.getMetaData();
                            columnLen = meteData.getColumnCount();
                            mapList = new ArrayList<Map<String, Object>>();
                            while (rs.next()) {
                                map = new HashMap<String, Object>();
                                for (int i = 0; i < columnLen; i++) {
                                    filedName = meteData.getColumnName(i + 1);
                                    map.put(filedName.toUpperCase(), rs.getObject(filedName));
                                }
                                mapList.add(map);
                            }
                            resultMap.put(en.getKey(), mapList);
                        } else {
                            //非结果集类型
                            resultMap.put(en.getKey(), result);
                        }
                    }
                }
                return resultMap;
            }
        });
    }

//**************************************************************************************

    /**
     * 执行存储过程（多个返回值）
     *
     * @param procedureSql 存储过程语句，例如： "{call prLS_OrderByMemberOrNotMember(?,?,?,?,?,?,?,?,?,?)}"
     * @param params       参数 List形式（不包括最后的返回值）
     * @param outTypeList  返回值为多个，其类型组成的列表
     * @return 存储过程执行结果Object类型
     * <p/>
     * @description 使用说明：
     * 调用执行存储过程
     * 这里的存储过程是针对返回结果集为不定数目返回值的存储过程
     * 常用于返回值是多个，如同时返回计算和错误信息等
     */
    public List<Object> executeProcedure(final String procedureSql, final List<Object> params, final List<Integer> outTypeList) {
        List<Object> result = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement stCall = null;
        ResultSet rs = null;
        try {
            Session session = getSession();
            session.flush();
            conn = jdbcTemplate.getDataSource().getConnection();
            stCall = conn.prepareCall(procedureSql);
            int i = 1;
            for (; i <= params.size(); i++) {
                if (params.get(i - 1) != null) {
                    stCall.setObject(i, params.get(i - 1));
                } else {
                    stCall.setObject(i, "");
                }
            }
            if (outTypeList != null && outTypeList.size() > 0) {
                for (int j = 0; j < outTypeList.size(); j++) {
                    stCall.registerOutParameter(i + j, outTypeList.get(j));
                }
                stCall.execute();
                for (int j = 0; j < outTypeList.size(); j++) {
                    if (outTypeList.get(j).equals(oracle.jdbc.OracleTypes.CURSOR)) {
                        List resultSetList;
                        rs = (ResultSet) stCall.getObject(i + j);
                        resultSetList = getListByResultSet(rs, null, 0);
                        result.add(resultSetList);
                    } else {
                        result.add(stCall.getObject(i + j));
                    }
                }
            } else {
                stCall.execute();
            }
        } catch (SQLException e) {
            result.add("存储过程启动异常：" + e);
        } finally {
            try {
                if (stCall != null) {
                    stCall.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                Map<String, Object> errMap = new HashMap<String, Object>();
                errMap.put("procedureReturnErrorMessage", "资源关闭异常");
                result.add(errMap);
            }
        }
        return result;
    }

    protected List<Map<String, Object>> getListByResultSet(ResultSet rs, Map<String, String> columnToProperty, int total) throws SQLException {
        if (columnToProperty == null) {
            columnToProperty = new HashMap<String, String>();
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        ResultSetMetaData meta = rs.getMetaData();
        int nColNum = meta.getColumnCount();
        int counter = 1;
        while (rs.next()) {
            Map<String, Object> vRow = new HashMap<String, Object>();
            for (int j = 1; j <= nColNum; j++) {
                if (!"count".equalsIgnoreCase(meta.getColumnName(j))) {
                    Object sValue = rs.getObject(j);
                    sValue = getValueFromOracleType(sValue);
                    String column = meta.getColumnName(j).toUpperCase();
                    String property = columnToProperty.get(column);
                    if (property != null && !"".equals(property)) {
                        vRow.put(property, sValue);
                    } else {
                        vRow.put(column, sValue);
                    }
                }
            }
            result.add(vRow);
            if (total > 0 && counter >= total) {
                break;
            } else {
                counter++;
            }
        }
        return result;
    }

    private Object getValueFromOracleType(Object value) {
        if (value == null) {
            return "";
        }
        if ("oracle.sql.BLOB".equals(value.getClass().getName())) {
            return value;
        } else if ("oracle.sql.STRUCT".equals(value.getClass().getName())) {
            return value;
        } else {
            return value.toString();
        }
    }
//**************************************************************************************

    /**
     * 公用操作部分
     */
    /**
     * 获取数据库session
     *
     * @return session
     */
    public Session getSession() {
        try {
            return hibernateTemplate.getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            return hibernateTemplate.getSessionFactory().openSession();
        }
    }

    public Session getSessionForLucene() {
        return hibernateTemplate.getSessionFactory().openSession();
    }

    /**
     * @param hql    HQL语句
     * @param params 各绑定变量参数
     * @return 查询结果
     */
    public Query createQuery(String hql, Object... params) {
        Query query = getSession().createQuery(hql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] == null) {
                    query.setParameter(i, "");
                } else {
                    query.setParameter(i, params[i]);
                }
            }
        }
        return query;
    }

    public void prepareCallNoReturn(final CharSequence callSql, final Object... inParameters) {
        Session session = getSession();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall(callSql.toString());
                int inParametersLength = inParameters.length;
                for (int i = 0; i < inParametersLength; i++) {
                    cs.setObject(i + 1, inParameters[i]);
                }
                cs.registerOutParameter(inParametersLength + 1, Types.VARCHAR);
                cs.executeUpdate();
                if (!"SUCCESS".equals(cs.getString(inParametersLength + 1))) {
                    throw new RuntimeException(cs.getString(inParametersLength + 2));
                }
            }
        });
    }

    /**
     * @param hql    HQL语句
     * @param params 各绑定变量列表
     * @return 查询结果
     */
    public Query createQuery(String hql, List params) {
        Query query = getSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) == null) {
                    query.setParameter(i, "");
                } else {
                    query.setParameter(i, params.get(i));
                }
            }
        }
        return query;
    }

    /**
     * @param hql    HQL语句
     * @param params 各绑定变量键值对
     * @return 查询结果
     */
    public Query createQuery(String hql, Map<String, Object> params) {
        Query query = getSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> en : params.entrySet()) {
                if (en.getValue() instanceof Collection) {
                    query.setParameterList(en.getKey(), (Collection) en.getValue());
                } else if (en.getValue() instanceof Object[]) {
                    query.setParameterList(en.getKey(), (Object[]) en.getValue());
                } else {
                    if (en.getValue() == null) {
                        query.setParameter(en.getKey(), "");
                    } else {
                        query.setParameter(en.getKey(), en.getValue());
                    }
                }
            }
        }
        return query;
    }

    /**
     * @param sql    SQL语句
     * @param params 各绑定变量参数
     * @return 查询结果
     */
    public SQLQuery createSQLQuery(String sql, Object... params) {
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] == null) {
                    sqlQuery.setParameter(i, "");
                } else {
                    sqlQuery.setParameter(i, params[i]);
                }
            }
        }
        return sqlQuery;
    }

    /**
     * 带session参数的createSQLQuery，防止session上限
     *
     * @param sql
     * @param session
     * @param params
     * @return
     */
    public SQLQuery createSQLQueryForIndex(String sql, Session session, List params) {
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) == null) {
                    sqlQuery.setParameter(i, "");
                } else {
                    sqlQuery.setParameter(i, params.get(i));
                }
            }
        }
        return sqlQuery;
    }

    public SQLQuery createSQLQueryForIndex(String sql, Session session, Map<String, Object> params) {
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> en : params.entrySet()) {
                if (en.getValue() instanceof Collection) {
                    sqlQuery.setParameterList(en.getKey(), (Collection) en.getValue());
                } else if (en.getValue() instanceof Object[]) {
                    sqlQuery.setParameterList(en.getKey(), (Object[]) en.getValue());
                } else {
                    if (en.getValue() == null) {
                        sqlQuery.setParameter(en.getKey(), "");
                    } else {
                        sqlQuery.setParameter(en.getKey(), en.getValue());
                    }
                }
            }
        }
        return sqlQuery;
    }

    /**
     * @param sql    SQL语句
     * @param params 各绑定变量列表
     * @return 查询结果
     */
    public SQLQuery createSQLQuery(String sql, List params) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) == null) {
                    sqlQuery.setParameter(i, "");
                } else {
                    sqlQuery.setParameter(i, params.get(i));
                }
            }
        }
        return sqlQuery;
    }

    /**
     * @param sql    SQL语句
     * @param params 各绑定变量键值对
     * @return 查询结果
     */
    public SQLQuery createSQLQuery(String sql, Map<String, Object> params) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> en : params.entrySet()) {
                if (en.getValue() instanceof Collection) {
                    sqlQuery.setParameterList(en.getKey(), (Collection) en.getValue());
                } else if (en.getValue() instanceof Object[]) {
                    sqlQuery.setParameterList(en.getKey(), (Object[]) en.getValue());
                } else {
                    if (en.getValue() == null) {
                        sqlQuery.setParameter(en.getKey(), "");
                    } else {
                        sqlQuery.setParameter(en.getKey(), en.getValue());
                    }
                }
            }
        }
        return sqlQuery;
    }

    /**
     * 将CLOB转为键值对
     *
     * @param values  值数组
     * @param columns 列数组
     * @param clobs   CLOB数组
     * @return 查询结果键值对
     */
    public Map<String, Object> transformClob(Object[] values, String[] columns, String[] clobs) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (columns != null && columns.length > 0 && clobs != null && clobs.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                if (ArrayUtils.contains(clobs, columns[i])) {
                    map.put(columns[i], clobToStr(values[i]));
                } else {
                    map.put(columns[i], values[i]);
                }
            }
        }
        return map;
    }

    /**
     * 将CLOB转为字符串
     *
     * @param obj CLOB对象
     * @return 字符串
     */
    public String clobToStr(Object obj) {
        Reader reader = null;
        try {
            if (obj != null && obj instanceof Clob) {
                Clob clob = (Clob) obj;
                reader = clob.getCharacterStream();
                char[] chars = new char[(int) clob.length()];
                reader.read(chars);
                return new String(chars);
            } else if (obj != null && obj instanceof String) {
                return obj.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 以下为JdbcTemplate和HibernateTemplate的set和get方法
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }


}
