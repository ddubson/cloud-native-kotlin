package com.cloud.native.auth

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer

@Configuration
@EnableAuthorizationServer
class OAuthConfiguration(val authenticationManager: AuthenticationManager)
    : AuthorizationServerConfigurerAdapter() {
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory().withClient("html5")
                .authorizedGrantTypes("password")
                .scopes("openid").secret("password")
                .and()
                .withClient("batch-job-client").authorizedGrantTypes("client_credentials")
                .secret("password").scopes("openid")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(this.authenticationManager)
    }
}