package com.haitao.apollo.plugin.database.dialect;

/**
* @ClassName: SqlServerDialect 
* @Description: SqlServer数据库方言实现
* @author zengbin
* @date 2015年10月28日 下午2:45:24
 */
public class SqlServerDialect implements Dialect {
    
    public String getPageSql(String sql, int offset, int limit, String orderBy, String order) {
        sql = sql.trim();
        StringBuffer pageSql = new StringBuffer(sql.length() + 100);
        pageSql.append("select * from(select a.*,row_number() over (order by ").append(orderBy)
            .append(" ").append(order).append(") rownum from( ");
        pageSql.append(getLineSql(sql));
        pageSql.append(") a ) b where rownum >= " + offset + " and rownum < " + (offset + limit));
        return pageSql.toString();
    }
    
    private String getLineSql(String sql) {
        return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
    }
    
    public static final void main(String[] args) {
        String sql = "select id,name , size ,width  from   t_app ";
        Dialect dialect = new SqlServerDialect();
        System.out.println(dialect.getPageSql(sql, 0, 20, "id,name", "desc,asc"));
    }
}