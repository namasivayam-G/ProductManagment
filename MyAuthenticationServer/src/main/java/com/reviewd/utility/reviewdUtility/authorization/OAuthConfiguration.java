package com.reviewd.utility.reviewdUtility.authorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configuration.ClientDetailsServiceConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
//To setup the authorization server extend the authorization server configure adapter
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	private AuthenticationManager am;

	private PasswordEncoder pe;
	
	private UserDetailsService uds;

	@Value("${jwt.clientId:dummy}")
	private String clientId;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.signing.key:dummy}")
	private String signingKey;

	@Value("${jwt.accessTokenValiditySeconds:dummy}")
	private int accessTokenValiditySeconds;

	@Value("${jwt.authorisedGrantType}")
	private String authorisedGrantType;

	/*
	 * public OAuthConfiguration(AuthenticationManager am, PasswordEncoder pe,
	 * String clientId, String secret, String signingKey, int
	 * accessTokenValiditySeconds, String authorisedGrantType) { super(); this.am =
	 * am; this.pe = pe; this.clientId = clientId; this.secret = secret;
	 * this.signingKey = signingKey; this.accessTokenValiditySeconds =
	 * accessTokenValiditySeconds; this.authorisedGrantType = authorisedGrantType; }
	 */

	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(secret).accessTokenValiditySeconds(accessTokenValiditySeconds)
				.authorizedGrantTypes(authorisedGrantType).scopes("read", "write").resourceIds("api").autoApprove(true);

	}

	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.accessTokenConverter(accessTokenConverter())
		.userDetailsService(uds)
		.authenticationManager(am);
	}

	@Bean
	JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		return converter;
	}
	
}
