package com.testfinra.numbercombinations.handler;

import com.testfinra.numbercombinations.config.NumberRouter;
import com.testfinra.numbercombinations.domain.NumberCombination;
import com.testfinra.numbercombinations.service.NumberCombinationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class NumberCombinationsHandlerTest {

    @MockBean
    NumberCombinationService service;

    //@InjectMocks
    //NumberCombinationsHandler handler = new NumberCombinationsHandler(service);

    @Autowired
    WebTestClient webTestClient;

    Map<String, String> map = new HashMap<>();
    NumberCombination nc = new NumberCombination();

    @BeforeEach
    public void setUp() {

        map.put("id", "1111111");
        map.put("index", "0");
        nc.value = "1";
        nc.combinations = new ArrayList<>();

        Mockito.when(service.getNumberCombinations(map)).thenReturn(Mono.just(nc));
    }

    @Test
    public void getNumberCombinationsSuccess() throws Exception {
        nc.combinations.add("1111111");
        this.webTestClient.get().uri("/1111111/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(NumberCombination.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getStatus()).isEqualByComparingTo(HttpStatus.OK);
                    Assertions.assertThat(result.getResponseBody()).isNotNull();
                    Assertions.assertThat(result.getResponseBodyContent().toString().contains("1"));
                    Assertions.assertThat(result.getResponseBody().value.equals("1"));
                    Assertions.assertThat(result.getResponseBody().combinations.size()).isEqualTo(1);
                });
    }

    @Test
    public void getNumberCombinationsSuccessSimple() throws Exception {
        Map<String, String> maps = new HashMap<>();
        NumberCombination ncs = new NumberCombination();
        maps.put("id", "1111112");
        maps.put("index", "0");

        ncs.value = "7";
        ncs.combinations = new ArrayList<>();
        ncs.combinations.add("1111112");
        ncs.combinations.add("1111121");
        ncs.combinations.add("1111211");
        ncs.combinations.add("1112111");
        ncs.combinations.add("1121111");
        ncs.combinations.add("1211111");
        ncs.combinations.add("2111111");


        Mockito.when(service.getNumberCombinations(maps)).thenReturn(Mono.just(ncs));

        this.webTestClient.get().uri("/1111112/0")
                .exchange()
                .expectStatus().isOk()
                .expectBody(NumberCombination.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getStatus()).isEqualByComparingTo(HttpStatus.OK);
                    Assertions.assertThat(result.getResponseBody()).isNotNull();
                    Assertions.assertThat(result.getResponseBody().value.equals("7"));
                    Assertions.assertThat(result.getResponseBody().combinations.size()).isEqualTo(7);
                });
    }

    @Test
    public void getNumberCombinations134567() throws Exception {
        Map<String, String> mapn = new HashMap<>();
        NumberCombination ncn = new NumberCombination();
        mapn.put("id", "1234567");
        mapn.put("index", "0");

        ncn.value = "5040";
        ncn.combinations = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ncn.combinations.add("1111111");
        }

        Mockito.when(service.getNumberCombinations(mapn)).thenReturn(Mono.just(ncn));

        this.webTestClient.get().uri("/1234567/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(NumberCombination.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getStatus()).isEqualByComparingTo(HttpStatus.OK);
                    Assertions.assertThat(result.getResponseBody()).isNotNull();
                    Assertions.assertThat(result.getResponseBodyContent().toString().contains("5040"));
                    Assertions.assertThat(result.getResponseBody().value.equals("5040"));
                    Assertions.assertThat(result.getResponseBody().combinations.size()).isEqualTo(1000);
                });
    }

    @Test
    public void getNumberCombinations134567LastPage() throws Exception {
        Map<String, String> mapl = new HashMap<>();
        NumberCombination ncl = new NumberCombination();
        mapl.put("id", "1234567");
        mapl.put("index", "5");

        ncl.value = "5040";
        ncl.combinations = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            ncl.combinations.add("1111111");
        }

        Mockito.when(service.getNumberCombinations(mapl)).thenReturn(Mono.just(ncl));
        this.webTestClient.get().uri("/1234567/5")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(NumberCombination.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getStatus()).isEqualByComparingTo(HttpStatus.OK);
                    Assertions.assertThat(result.getResponseBody()).isNotNull();
                    Assertions.assertThat(result.getResponseBodyContent().toString().contains("5040"));
                    Assertions.assertThat(result.getResponseBody().value.equals("5040"));
                    Assertions.assertThat(result.getResponseBody().combinations.size()).isEqualTo(40);
                });
    }

    @Test
    public void getNumberCombinationsFail() throws Exception {
        Map<String, String> mapf = new HashMap<>();
        NumberCombination ncf = new NumberCombination();
        mapf.put("id", "0");
        mapf.put("index", "0");

        ncf.value = "5040";
        ncf.combinations = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            ncf.combinations.add("1111111");
        }

        Mockito.when(service.getNumberCombinations(mapf)).thenThrow(RuntimeException.class);

        this.webTestClient.get().uri("/1111/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(NumberCombination.class)
                .consumeWith(result -> {
                    Assertions.assertThat(result.getStatus()).isEqualByComparingTo(HttpStatus.INTERNAL_SERVER_ERROR);
                    Assertions.assertThat(result.getResponseBody()).isNotNull();
                    Assertions.assertThat(result.getResponseBody().value).isNull();
                    Assertions.assertThat(result.getResponseBody().combinations).isNull();
                });
    }
}