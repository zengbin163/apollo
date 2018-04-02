package com.haitao.apollo.plugin.database.dialect;

/**
* @ClassName: Dialect 
* @Description: 数据库方言接口
* @author zengbin
* @date 2015年10月28日 上午11:05:53
 */
public interface Dialect {
    
    public static enum Type {
        MYSQL {
            @SuppressWarnings("unused")
            public String getValue() {
                return "mysql";
            }
        },
        SQLSERVER {
            @SuppressWarnings("unused")
            public String getValue() {
                return "sqlserver";
            }
        },
        ORACLE {
            @SuppressWarnings("unused")
            public String getValue() {
                return "oracle";
            }
        }
    }
    
    /**
     * @Descrption  获取分页SQL
     * @param  sql 原始查询SQL
     * @param  offset 开始记录索引（从零开始）
     * @param  limit 每页记录大小
     * @param  orderBy 排序字段
     * @param  order 排序方向
     * @return  返回数据库相关的分页SQL语句
     */
    public abstract String getPageSql(String sql, int offset, int limit, String orderBy,
                                      String order);
}
