package com.haitao.apollo.plugin.database.dialect;

/**
 * 
* @ClassName: OracleDialect 
* @Description: Oracle数据库方言实现
* @author zengbin
* @date 2015年10月28日 上午11:17:56
 */
public class OracleDialect implements Dialect {
    
    @Override
    public String getPageSql(String sql, int offset, int limit, String orderBy, String order) {
        sql = getLineSql(sql.trim());
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        
        StringBuffer pageSql = new StringBuffer(sql.length() + 100);
        pageSql.append("select * from ( select row_.*, rownum rownum_ from ( ");
        pageSql.append(sql);
        pageSql.append(" ) row_ ) where rownum_ > " + offset + " and rownum_ <= "
                       + (offset + limit));
        if (isForUpdate) {
            pageSql.append(" for update");
        }
        return pageSql.toString();
    }
    
    private String getLineSql(String sql) {
        return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
    }
    
    public static final void main(String[] args) {
        String sql = "select id,name , size ,width  from   t_app ";
        Dialect dialect = new OracleDialect();
        System.out.println(dialect.getPageSql(sql, 0, 20, "id,name", "desc,asc"));
    }
}
