package com.mechanitis.demo.stockclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class RSocketStockClientIntegrationTest {

    @Autowired
    private RSocketRequester.Builder builder;

    private RSocketRequester createRSocketRequester() {
        return builder.connectTcp("localhost", 7000).block();
    }

    @Test
    void shouldRetrieveStockPricesFromTheService() {
        // given
        RSocketStockClient rSocketStockClient = new RSocketStockClient(createRSocketRequester());

        // when
        Flux<StockPrice> prices = rSocketStockClient.pricesFor("SYMBOL");

        // then
        StepVerifier.create(prices.take(5))
                    .expectNextMatches(stockPrice -> stockPrice.getSymbol().equals("SYMBOL"))
                    .expectNextMatches(stockPrice -> stockPrice.getSymbol().equals("SYMBOL"))
                    .expectNextMatches(stockPrice -> stockPrice.getSymbol().equals("SYMBOL"))
                    .expectNextMatches(stockPrice -> stockPrice.getSymbol().equals("SYMBOL"))
                    .expectNextMatches(stockPrice -> stockPrice.getSymbol().equals("SYMBOL"))
                    .verifyComplete();
    }
}