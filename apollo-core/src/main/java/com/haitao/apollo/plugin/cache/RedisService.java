/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月9日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.plugin.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName: RedisService
 * @Description: 分布式缓存redis相关服务接口
 * @author zengbin
 * @date 2015年11月9日 下午9:10:00
 */
@Service("redisService")
public class RedisService implements InitializingBean {

	// jedis连接池
	private static JedisPool jedisPool = null;

	@Resource(name = "jedisConnectionFactory")
	private JedisConnectionFactory jedisConnectionFactory;

	@Override
	public void afterPropertiesSet() throws Exception {
		jedisPool = new JedisPool(jedisConnectionFactory.getPoolConfig(),
				jedisConnectionFactory.getHostName(),
				jedisConnectionFactory.getPort(),
				jedisConnectionFactory.getTimeout(),
				jedisConnectionFactory.getPassword());
	}

	/**
	 * 获取jedis实例(从连接池中获取)
	 * 
	 * @return
	 */
	public Jedis getJedis() {
		if (jedisPool != null) {
			return jedisPool.getResource();
		} else {
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 通过key删除（字节）
	 * 
	 * @param key
	 */
	public void del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.del(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.del(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 添加key value 并且设置存活时间(byte)
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(byte[] key, byte[] value, int liveTime) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
			jedis.expire(key, liveTime);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
			jedis.expire(key, liveTime);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
		} finally {
			this.returnResource(jedis);
		}
	}

	/***
	 * <pre>
	 * 设置对象
	 * </pre>
	 * 
	 * @param key
	 * @param obj
	 */
	public void setObj(String key, Object obj) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			byte[] byteArray = bos.toByteArray();
			oos.close();
			bos.close();
			this.set(key.getBytes(), byteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * <pre>
	 * 获取对象
	 * </pre>
	 * 
	 * @param key
	 * @param obj
	 */
	public Object getObj(String key) {
		try {
			byte[] byteArray = this.get(key.getBytes());
			if (null == byteArray) {
				return null;
			}
			ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
			ObjectInputStream inputStream = new ObjectInputStream(bis);
			Object readObject = (Object) inputStream.readObject();
			inputStream.close();
			bis.close();
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加key value (字节)(序列化)
	 * 
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, value);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.get(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 获取redis value (byte [] )(反序列化)
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.get(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 通过正则匹配keys
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.keys(pattern);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.exists(key);
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	public String flushDB() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.flushDB();
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 查看redis里有多少数据
	 */
	public long dbSize() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.dbSize();
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 检查是否连接成功
	 * 
	 * @return
	 */
	public String ping() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.ping();
		} finally {
			this.returnResource(jedis);
		}
	}
}
