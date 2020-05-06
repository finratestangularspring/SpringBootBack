package com.testfinra.numbercombinations.config;

import com.testfinra.numbercombinations.handler.NumberCombinationsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class NumberRouter {

    @Bean
    RouterFunction<ServerResponse> home(NumberCombinationsHandler handler) {

        return route().GET("/{id}/{index}", serverRequest -> handler.getNumberCombinations(serverRequest.pathVariables(), serverRequest))
                .build();
    }
}