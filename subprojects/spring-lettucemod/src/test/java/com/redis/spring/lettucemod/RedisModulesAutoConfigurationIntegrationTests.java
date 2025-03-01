package com.redis.spring.lettucemod;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.redis.lettucemod.RedisModulesClient;
import com.redis.lettucemod.api.StatefulRedisModulesConnection;
import com.redis.lettucemod.search.Suggestion;
import com.redis.testcontainers.RedisModulesContainer;

/**
 * Integration tests for {@link RedisModulesAutoConfiguration}.
 */
@Testcontainers(disabledWithoutDocker = true)
class RedisModulesAutoConfigurationIntegrationTests {

	@Container
	static final RedisModulesContainer redisModules = new RedisModulesContainer(
			RedisModulesContainer.DEFAULT_IMAGE_NAME.withTag(RedisModulesContainer.DEFAULT_TAG));

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(RedisModulesAutoConfiguration.class))
			.withPropertyValues("spring.redis.host: " + redisModules.getHost(),
					"spring.redis.port:" + redisModules.getFirstMappedPort());

	@Test
	void defaultConfiguration() {
		this.contextRunner.run((context) -> {
			assertThat(context.getBean("client")).isInstanceOf(RedisModulesClient.class);
			assertThat(context).hasSingleBean(RedisModulesClient.class);
			assertThat(context).hasSingleBean(StatefulRedisModulesConnection.class);
			RedisModulesClient client = context.getBean(RedisModulesClient.class);
			StatefulRedisModulesConnection<String, String> connection = client.connect();
			String key = "suggestIdx";
			connection.sync().sugadd(key, "rome", 1);
			connection.sync().sugadd(key, "romarin", 1);
			List<Suggestion<String>> suggestions = connection.sync().sugget(key, "rom");
			Assertions.assertEquals(2, suggestions.size());
		});
	}

}
