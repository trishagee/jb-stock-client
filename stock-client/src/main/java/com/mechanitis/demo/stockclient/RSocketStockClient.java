package com.mechanitis.demo.stockclient;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

import static java.time.Duration.ofSeconds;
import static reactor.util.retry.Retry.backoff;

@Log4j2
public class RSocketStockClient implements StockClient {
    private final RSocketRequester rSocketRequester;

    RSocketStockClient(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @Override
    public Flux<StockPrice> pricesFor(String symbol) {
        log.info("RSocket stock client");
        return rSocketRequester.route("stockPrices")
                               .data(symbol)
                               .retrieveFlux(StockPrice.class)
                               .retryWhen(backoff(5, ofSeconds(1)).maxBackoff(ofSeconds(20)))
                               .doOnError(IOException.class, e -> log.error(e.getMessage()));
    }
}
