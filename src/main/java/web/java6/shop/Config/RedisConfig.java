package web.java6.shop.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import web.java6.shop.cart.Cart;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Cấu hình kết nối Redis (host, port, password nếu có)
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("localhost");         // đổi nếu bạn chạy redis trên server khác
        config.setPort(6379);                    // port mặc định

        return new LettuceConnectionFactory(config);
    }

    @Bean
public RedisTemplate<String, Cart> redisTemplate() {
    RedisTemplate<String, Cart> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());

    // Key là String
    template.setKeySerializer(new StringRedisSerializer());

    // Value là Cart, dùng Jackson serializer chuẩn Long
    Jackson2JsonRedisSerializer<Cart> serializer = new Jackson2JsonRedisSerializer<>(Cart.class);
    template.setValueSerializer(serializer);
    template.setHashValueSerializer(serializer);

    template.setHashKeySerializer(new StringRedisSerializer());
    template.afterPropertiesSet();
    return template;
}
}
