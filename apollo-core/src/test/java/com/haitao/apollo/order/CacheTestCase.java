package com.haitao.apollo.order;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class CacheTestCase implements Serializable {
	private static final long serialVersionUID = 2709691403847250167L;
	private static JedisPool jedisPool;

	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setTestOnBorrow(true);
		config.setMaxIdle(200);
		config.setMinIdle(10);
		config.setMaxTotal(1024);
		config.setMaxWaitMillis(10000);
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
				config);
		jedisConnectionFactory.setHostName("114.55.30.32");
		jedisConnectionFactory.setPort(7000);
		jedisConnectionFactory.setPassword("111111");
		jedisConnectionFactory.setTimeout(10000);
		jedisPool = new JedisPool(jedisConnectionFactory.getPoolConfig(),
				jedisConnectionFactory.getHostName(),
				jedisConnectionFactory.getPort(),
				jedisConnectionFactory.getTimeout(),
				jedisConnectionFactory.getPassword());
	}

	public Jedis getJedis() {
		if (jedisPool != null) {
			Jedis resource = jedisPool.getResource();
			return resource;
		} else {
			return null;
		}
	}

	public void set(byte[] key, byte[] value) {
		this.getJedis().set(key, value);
	}

	public byte[] get(byte[] key) {
		return this.getJedis().get(key);
	}

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

	public static final void main(String[] args) {
		CacheTestCase test = new CacheTestCase();
		Jedis jedis = test.getJedis();
		for (int i = 0; i < 100000000; i++) {
			jedis.set("testCase" + i, "value" + i);
			System.out.println(jedis.get("testCase" + i));
		}
	}

}
