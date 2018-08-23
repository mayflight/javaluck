package may.flight.luck.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

   @Resource
   private RedisTemplate redisTemplate;

   public String getStringValue(String key) {
       Object object = redisTemplate.opsForValue().get(key);
       return null == object ? null : String.valueOf(object);
   }

   public <K> Object getValue(K key) {
       return redisTemplate.opsForValue().get(key);
   }

   @SuppressWarnings("unchecked")
   public <K,V> void setValue(K  key, V value, int timeOut, TimeUnit timeUnit){
       redisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
   }

   @SuppressWarnings("unchecked")
   public <K> void invalid(K key) {
       redisTemplate.delete(key);
   }
}
