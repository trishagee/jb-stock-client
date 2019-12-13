package com.mechanitis.demo.stockclient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    @Profile("sse")
    public StockClient webClientStockClient(WebClient webClient) {
        return new WebClientStockClient(webClient);
    }

    @Bean
    @Profile("rsocket")
    public StockClient rSocketStockClient(RSocketRequester rSocketRequester) {
        return new RSocketStockClient(rSocketRequester);
    }

    @Bean
    public RSocketRequester rSocketRequester(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") RSocketRequester.Builder builder) {
        return builder.connectTcp("localhost", 7000).block();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
