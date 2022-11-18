package solo_project.solo_project.common.config;

import java.time.Duration;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

  public static final int DEFAULT_EXPIRE_TIME = 10;

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .disableCachingNullValues()
        .computePrefixWith(CacheKeyPrefix.simple())
        .entryTtl(Duration.ofSeconds(DEFAULT_EXPIRE_TIME))
        .serializeKeysWith(RedisSerializationContext
            .SerializationPair
            .fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext
            .SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer()));

    Map<String, RedisCacheConfiguration> cacheConfigurations
        = setCustomCacheConfigurations(redisCacheConfiguration);

    return RedisCacheManager.RedisCacheManagerBuilder
        .fromConnectionFactory(redisConnectionFactory)
        .cacheDefaults(redisCacheConfiguration)
        .withInitialCacheConfigurations(cacheConfigurations)
        .build();
  }

  private Map<String, RedisCacheConfiguration> setCustomCacheConfigurations(
      RedisCacheConfiguration configuration) {

    return Map.of(
        "brief", configuration.entryTtl(Duration.ofSeconds(10)),
        "medium", configuration.entryTtl(Duration.ofMinutes(1)),
        "long", configuration.entryTtl(Duration.ofMinutes(10))
//        ,"BoardService::boards", configuration.entryTtl(Duration.ofMinutes(5)),
//        "BoardService::boardsForAdmin", configuration.entryTtl(Duration.ofMinutes(5))
    );
  }

}
