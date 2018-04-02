package com.haitao.apollo.plugin.database.page;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haitao.apollo.plugin.database.dialect.Dialect;
import com.haitao.apollo.plugin.database.dialect.MySqlDialect;
import com.haitao.apollo.plugin.database.dialect.OracleDialect;
import com.haitao.apollo.plugin.database.dialect.SqlServerDialect;
import com.haitao.apollo.util.LoggerUtil;
import com.haitao.apollo.util.ReflectUtil;

/**
* @ClassName: PaginationInterceptor 
* @Description:  
  * 实现mybatis提供的拦截器接口，编写我们自己的分页实现，原理就是拦截底层JDBC操作相关的Statement对象，
  * 把前端的分页参数如当前记录索引和每页大小通过拦截器注入到sql语句中，即在sql执行之前通过分页参数重新生成分页sql，
  * 而具体的分页sql实现是分离到Dialect接口中去。
* @author zengbin
* @date 2015年10月28日 下午2:52:09
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class}) })
public class PaginationInterceptor implements Interceptor {
    private Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler)invocation.getTarget();
        // 通过反射获取到当前RoutingStatementHandler对象的delegate属性
        StatementHandler delegate = (StatementHandler)ReflectUtil.getFieldValue(handler, "delegate");
        // 获取到当前StatementHandler的boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的
        // RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
        BoundSql boundSql = delegate.getBoundSql();
        // 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
        Object obj = boundSql.getParameterObject();
        // 这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。
        Page<?> page = this.needPages(obj);
        if (page != null) {
            String originalSql = getLineSql(boundSql.getSql());
            Dialect.Type databaseType = null;
            try {
                databaseType = Dialect.Type
                    .valueOf(properties.getProperty("dialect").toUpperCase());
            } catch (Exception e) {
                databaseType = Dialect.Type.valueOf("MYSQL");
            }
            if (databaseType == null) {
                throw new RuntimeException("请配置数据库的dialect");
            }
            Dialect dialect = null;
            switch (databaseType) {
                case MYSQL:
                    dialect = new MySqlDialect();
                    break;
                case SQLSERVER:
                    dialect = new SqlServerDialect();
                    break;
                case ORACLE:
                    dialect = new OracleDialect();
                    break;
                default:
                    dialect = new MySqlDialect();
            }
            String pageSql = dialect.getPageSql(originalSql, page.getPageNo(), page.getPageSize(),
                page.getOrderBy(), page.getOrder());
            LoggerUtil.ERROR(logger, String.format("执行SQL如下 ： %s", pageSql));
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
            return invocation.proceed();
        }
        return invocation.proceed();
    }
    
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    private String getLineSql(String sql) {
        return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
    }
    
    /**
     * <pre>
     *     是否需要分页
     * </pre>
     * @param obj
     * @return
     */
    private Page<?> needPages(Object obj) {
        if (null != obj && obj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) obj;
            for(Map.Entry<String, Object> en : map.entrySet()){
                Object enObj = en.getValue();
                if(enObj instanceof Page<?>){
                    return (Page<?>)enObj;
                }
            }
        }
        return null;
    }
}
