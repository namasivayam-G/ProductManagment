package com.namas.microservices.productcustomermanager;

import java.time.Duration;

import org.aspectj.weaver.loadtime.Options;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductCustomerManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCustomerManagerApplication.class, args);
	}

	/*
	 * @Bean public RestTemplate getTemplate() { return new RestTemplate(); }
	 */

	@Bean(name = "nibba")
	public WebClient getWebClient() {
		//fixed("namas", 2, 1000, Duration.ofMillis(1000))
		
		TcpClient timeoutClient = TcpClient.create(ConnectionProvider.fixed("test", 1, 1, Duration.ofMillis(10000)))
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
				.doOnConnected(c -> c.addHandlerLast(new ReadTimeoutHandler(10000)).addHandlerLast(new WriteTimeoutHandler(1000)));
		ReactorClientHttpConnector connector = new ReactorClientHttpConnector(HttpClient.from(timeoutClient));

			
		
		return WebClient.builder().clientConnector(connector).build();
		
		/**
		 * Create a new {@link ConnectionProvider} to cache and reuse a fixed maximum
		 * number of {@link Connection}.
		 * <p>A Fixed {@link ConnectionProvider} will open up to the given max connection value.
		 * Further connections will be pending acquisition indefinitely.
		 *
		 * @param name the connection pool name
		 * @param maxConnections the maximum number of connections before starting pending
		 * @param acquireTimeout the maximum time in millis after which a pending acquire
		 *                          must complete or the {@link TimeoutException} will be thrown.
		 * @param maxIdleTime the {@link Duration} after which the channel will be closed (resolution: ms),
		 *                    if {@code NULL} there is no max idle time
		 *
		 * @return a new {@link ConnectionProvider} to cache and reuse a fixed maximum
		 * number of {@link Connection}
		 */
	}
}