package com.testfinra.numbercombinations.handler;

import com.testfinra.numbercombinations.domain.NumberCombination;
import com.testfinra.numbercombinations.service.NumberCombinationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@AllArgsConstructor
public class NumberCombinationsHandler {

    private NumberCombinationService service;

    @CrossOrigin
    public Mono<ServerResponse> getNumberCombinations(Map<String, String> pathVars, ServerRequest serverRequest) {

        return ServerResponse.ok()
                //.contentType(MediaType.APPLICATION_JSON)
                .body(service.getNumberCombinations(pathVars), NumberCombination.class);
    }
}
