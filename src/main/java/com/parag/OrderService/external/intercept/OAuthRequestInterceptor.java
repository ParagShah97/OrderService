package com.parag.OrderService.external.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

/**
 * This class is responsible for overriding the default configuration for Feign clients.
 * It ensures that an OAuth2 access token is added to the request headers for all outgoing
 * requests made by the Order Service via Feign client.
 */
@Configuration // Marks this class as a Spring configuration component
public class OAuthRequestInterceptor implements RequestInterceptor {

    // We need an instance of OAuth2AuthorizedClientManager to fetch the OAuth2 access token.
    // This manager is responsible for handling authorization and obtaining tokens for OAuth2 clients.
    @Autowired
    OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    /**
     * This method is executed for every outgoing Feign request.
     * It adds the OAuth2 access token as an "Authorization" header.
     *
     * @param requestTemplate Feign's request template that allows modifying the request before sending it.
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", // Adds an Authorization header
                "Bearer " + // Specifies the token type as Bearer
                        oAuth2AuthorizedClientManager // Uses OAuth2AuthorizedClientManager to get the access token
                                .authorize(OAuth2AuthorizeRequest // Builds an authorization request
                                        .withClientRegistrationId("internal-client") // Uses the OAuth2 client named "internal-client"
                                        .principal("internal") // Identifies the request principal (can be a system user or a service)
                                        .build()) // Constructs the OAuth2 authorization request
                                .getAccessToken() // Retrieves the access token
                                .getTokenValue()); // Extracts the raw token value
    }
}
