package com.krisleonard.codingproject.appsheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

/**
 * Application to get user information from the AppSheet REST API endpoints
 */
@SpringBootApplication
public class Application {

    /**
     * The logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /** The REST Request default read timeout value in seconds */
    private static final long REST_REQUEST_READ_TIMEOUT_DEFAULT = 5;

    public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
    }

	/**
	 * Returns the RestTemplate object
	 *
	 * @param restTemplateBuilder A RestTemplateBuilder
	 * @return The RestTemplate object to use for REST requests
	 */
	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
			.setReadTimeout(Duration.ofSeconds(REST_REQUEST_READ_TIMEOUT_DEFAULT))
			.build();
    }
}
