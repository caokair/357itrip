package cn.itrip.common;



import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class redisUtil {
    private static JedisPool j;
    static{
        JedisPoolConfig c=new JedisPoolConfig();
        c.setMaxTotal(10);
        c.setMaxIdle(5);
        c.setMinIdle(0);
        c.setMaxWaitMillis(500);
        j=new JedisPool(c,"127.0.0.1",6379,1000,"123456");
    }
    public  Jedis getJedis(){
        return j.getResource();
    }
}
