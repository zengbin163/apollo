/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年8月11日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.filter;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

/** 
* @ClassName: SimplePropertyExcludeFilter 
* @Description: fastjson exclude 过滤器
* @author zengbin
* @date 2015年8月11日 下午5:20:51 
*/
public class SimplePropertyExcludeFilter implements PropertyPreFilter {
    
    
    private Class<?> clazz;
    private Set<String> includes;
    private Set<String> excludes;

    public SimplePropertyExcludeFilter(String[] properties) {
        this(null, properties);
    }

    public SimplePropertyExcludeFilter(Class<?> clazz, String[] properties) {
        this.includes = new HashSet<String>();
        this.excludes = new HashSet<String>();
        this.clazz = clazz;
        for (String item : properties){
            if (item != null){
                this.excludes.add(item);
            }
        }   
    }

    public Class<?> getClazz() {
      return this.clazz;
    }

    public Set<String> getIncludes() {
        return this.includes;
    }

   public Set<String> getExcludes() {
       return this.excludes;
   }

    
   public boolean apply(JSONSerializer serializer, Object source, String name) {
       if (source == null) {
         return true;
       }

       if ((this.clazz != null) && (!(this.clazz.isInstance(source)))) {
         return true;
       }

       if (this.excludes.contains(name)) {
         return false;
       }

       return ((this.includes.size() == 0) || (this.includes.contains(name)));
     }
    
}

