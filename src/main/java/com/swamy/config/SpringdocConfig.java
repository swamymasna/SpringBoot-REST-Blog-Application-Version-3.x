package com.swamy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class SpringdocConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(
				new Info()
				.title("BLOG-APPLICATION")
				.version("2.0.0")
				.description("Spring-Doc")
				);
	}
}
