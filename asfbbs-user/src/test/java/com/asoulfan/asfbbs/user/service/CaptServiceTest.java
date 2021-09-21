package com.asoulfan.asfbbs.user.service;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.asoulfan.asfbbs.user.BaseTest;

/**
 * @author asuka
 * @date 2021/09/05
 */
public class CaptServiceTest extends BaseTest {
    @Test
    public void test_rest() throws InterruptedException {
        Thread.sleep(3000);
        String result = WebClient.create().post()
                .uri("http://localhost:8083/user/getCapt")
                .contentType(MediaType.APPLICATION_JSON)
                // .body(BodyInserters.fromObject("{\"user\":{\"id\":1}}"))
                .retrieve()
                .bodyToMono(String.class).block();

        assertThat(result).isEqualTo("{\"data\":true,\"success\":true}");
    }
}
