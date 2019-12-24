package com.copy;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:app-integration-test.properties")
class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void counterСallback() throws Exception {
        mvc.perform(post("/village_create")
                .param("code", "2")
                .param("villageName", "Villaribo")
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));


        mvc.perform(post("/counter_create")
                .param("code", "2")
                .param("villageName", "Villaribo")
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

        mvc.perform(post("/counter_callback")
                .content("{\"counter_id\": 2,\"amount\": 10000.123}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

        System.out.println();
                //.andExpect(jsonPath("$[0].name", is("bob")));
    }
}