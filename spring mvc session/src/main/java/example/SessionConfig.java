package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class SessionConfig {

    @Bean
    public LettuceConnectionFactory connectionFactory(){
        RedisClusterConfiguration cluster = new RedisClusterConfiguration();
        cluster.addClusterNode(new RedisClusterNode("192.168.30.40",6379));
        cluster.addClusterNode(new RedisClusterNode("192.168.30.41",6379));
        cluster.addClusterNode(new RedisClusterNode("192.168.30.42",6379));
        return new LettuceConnectionFactory(cluster);
    }

}
