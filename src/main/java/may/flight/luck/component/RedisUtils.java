package may.flight.luck.component;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

   @Resource
   private RedisTemplate redisTemplate;

   public <K> String getStringValue(K key) {
       return String.valueOf(redisTemplate.opsForValue().get(key));
   }

   public <K,V> void setValue(K  key, V value){
       redisTemplate.opsForValue().set(key, value);

   }

}
