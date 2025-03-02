package com.parag.OrderService;

import com.parag.OrderService.external.intercept.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository; // Manages registered OAuth2 clients
	@Autowired
	private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository; // Stores authorized clients and their tokens


	/**
	 * This bean returns an instance of RestTemplate to facilitate inter-service API calls.
	 * For example, the Order Service will call the Product Service to fetch product details.
	 * Additionally, the bean is annotated with @LoadBalanced, meaning it will work
	 * with service discovery and automatically distribute requests across multiple instances.
	 *
	 * @return A configured RestTemplate instance with OAuth2 authentication and load balancing.
	 */
	@Bean
	@LoadBalanced // Enables client-side load balancing using Spring Cloud LoadBalancer
	public RestTemplate restTemplate() {
		// Create a new RestTemplate instance for making HTTP requests
		RestTemplate restTemplate = new RestTemplate();

		// Set interceptors to add OAuth2 authentication tokens to each outgoing request
		restTemplate.setInterceptors(
				Arrays.asList(
						new RestTemplateInterceptor(
								clientManager(clientRegistrationRepository,
										oAuth2AuthorizedClientRepository)
						) // Attach OAuth2 token interceptor
				)
		);

		// Return the fully configured RestTemplate instance
		return restTemplate;
	}

	/**
	 * Creates and configures an OAuth2AuthorizedClientManager bean.
	 * This manager handles authorization for OAuth2 clients, particularly those using the client credentials flow.
	 *
	 * @param clientRegistrationRepository Repository containing registered OAuth2 clients.
	 * @param oAuth2AuthorizedClientRepository Repository for storing authorized OAuth2 clients.
	 * @return Configured OAuth2AuthorizedClientManager instance.
	 */
	@Bean
	public OAuth2AuthorizedClientManager clientManager(
			ClientRegistrationRepository clientRegistrationRepository, // Manages registered OAuth2 clients
			OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository // Stores authorized clients and their tokens
	) {
		// Define an OAuth2AuthorizedClientProvider, which handles authorization logic.
		OAuth2AuthorizedClientProvider oAuth2AuthorizedClientProvider =
				OAuth2AuthorizedClientProviderBuilder // Creates a builder for the client provider
						.builder()
						.clientCredentials() // Configures support for the client credentials grant type
						.build(); // Builds the provider

		// Create an instance of DefaultOAuth2AuthorizedClientManager.
		// This is responsible for managing OAuth2 authorized clients.
		DefaultOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager =
				new DefaultOAuth2AuthorizedClientManager(
						clientRegistrationRepository, // Injected client registration repository
						oAuth2AuthorizedClientRepository // Injected authorized client repository
				);

		// Set the authorized client provider in the client manager.
		// This ensures the manager can handle authorization using client credentials.
		oAuth2AuthorizedClientManager.
				setAuthorizedClientProvider(oAuth2AuthorizedClientProvider);

		// Return the fully configured OAuth2AuthorizedClientManager bean.
		return oAuth2AuthorizedClientManager;
	}
}
