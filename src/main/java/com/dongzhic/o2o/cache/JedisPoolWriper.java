package com.dongzhic.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 强指定redis的JedisPool接口构造函数，这样才能在centos成功创建jedispool
 * @author dongzc
 * @date 2018/8/8 16:17
 */
public class JedisPoolWriper {

    /**
     * Redis连接池对象
     */
    private JedisPool jedisPool;

    /**
     * @param poolConfig 连接池配置信息
     * @param host 服务器ip
     * @param port 服务器redis端口6379
     */
    public JedisPoolWriper (final JedisPoolConfig poolConfig, final String host,
                            final int port) {
        try {
            jedisPool = new JedisPool(poolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Redis连接池对象
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 注入Redis连接池对象
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
