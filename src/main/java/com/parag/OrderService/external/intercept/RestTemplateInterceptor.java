package com.parag.OrderService.external.intercept;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

/**
 * Interceptor for adding OAuth2 access tokens to outgoing HTTP requests made via RestTemplate.
 * This ensures that all requests sent by RestTemplate include the necessary Authorization header.
 */
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    // OAuth2AuthorizedClientManager is responsible for managing OAuth2 client authorization.
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    /**
     * Constructor to initialize the RestTemplateInterceptor with an OAuth2AuthorizedClientManager.
     *
     * @param oAuth2AuthorizedClientManager Manages OAuth2 client authorization and token retrieval.
     */
    public RestTemplateInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    /**
     * Intercepts every outgoing HTTP request made by RestTemplate to add an OAuth2 access token.
     *
     * @param request The outgoing HTTP request.
     * @param body The request body.
     * @param execution Used to execute the request with the modified headers.
     * @return The response after executing the request.
     * @throws IOException If an error occurs while processing the request.
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Add an "Authorization" header with the OAuth2 Bearer token to the request.
        request.getHeaders().add("Authorization",
                "Bearer " + // OAuth2 access tokens use the "Bearer" authentication scheme.
                        oAuth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest // Request authorization
                                        .withClientRegistrationId("internal-client") // Specifies the OAuth2 client ID
                                        .principal("email") // Identifies the requesting principal (system user)
                                        .build()) // Constructs the authorization request
                                .getAccessToken() // Retrieves the access token
                                .getTokenValue()); // Extracts the raw token value

        // Continue with the execution of the request and return the response.
        return execution.execute(request, body);
    }
}
