package solo_project.solo_project.domain.cashe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheTokenPort {

  private final ObjectMapper objectMapper;
  private final StringRedisTemplate stringRedisTemplate;
  private final ValueOperations<String, String> stringValueOperations;
  private final HashOperations<String, String, String> hashValueOperations;

  public CacheTokenPort(ObjectMapper objectMapper, StringRedisTemplate stringRedisTemplate) {
    this.objectMapper = objectMapper;
    this.stringRedisTemplate = stringRedisTemplate;
    this.stringValueOperations = stringRedisTemplate.opsForValue();
    this.hashValueOperations = stringRedisTemplate.opsForHash();
  }

  public String getData(String key) {
    return stringValueOperations.get(key);
  }

  public int getDataAsInt(String key) {
    return Integer.parseInt(Objects.requireNonNull(stringValueOperations.get(key)));
  }

  public void setData(String key, Object value) {
    stringValueOperations.set(key, String.valueOf(value));
  }

  public void setDataAndExpiration(String key, Object value, Long duration) {
    Duration expireDuration = Duration.ofSeconds(duration);
    stringValueOperations.set(key, String.valueOf(value), expireDuration);
  }

  public void deleteData(String key) {
    stringRedisTemplate.delete(key);
  }

  public String getHashData(String key, String hashKey) {
    return hashValueOperations.get(key, hashKey);
  }

  public void setHashData(String key, String hashKey, Object value) {
    hashValueOperations.put(key, hashKey, String.valueOf(value));
  }

  public void increment(String key) {
    stringValueOperations.increment(key, 1L);
  }

  public <T> T getObjectData(String key, Class<T> t) {
    try {
      return objectMapper.readValue(stringValueOperations.get(key), new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      log.info("[ERROR] : Invalid Cash Key => {}", e.getMessage());
      return null;
    }
  }

}
