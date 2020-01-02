package com.copy;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.is;
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

    private static final String VILLAGE_1_NAME = "Villarriba";
    private static final String VILLAGE_2_NAME = "Villabajo";

    @Test
    void counterСallback() throws Exception {

        GsonJsonParser jsonParser = new GsonJsonParser();


        createVillage(VILLAGE_1_NAME);
        createVillage(VILLAGE_2_NAME);


        String counter1 = createCounter(VILLAGE_2_NAME);
        String counter2 = createCounter(VILLAGE_2_NAME);

        String counter3 = createCounter(VILLAGE_1_NAME);


        ResultActions counterDataRequestResult = mvc.perform(get("/counter")
                .param("id", counter3)
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));


        Map<String, Object> parsedCounterData = jsonParser.parseMap(counterDataRequestResult.andReturn().getResponse().getContentAsString());

        Assert.assertEquals(parsedCounterData.get("village_name"), VILLAGE_1_NAME);


        for (int i = 0; i < 10; i++) {
            counterCallback(counter1, 1000.);
            counterCallback(counter2, 1000.);
            counterCallback(counter3, 1000.);
        }

        Thread.sleep(5000);

        counterCallback(counter1, 1.123);
        counterCallback(counter2, 2.123);
        counterCallback(counter2, 3.123);
        counterCallback(counter3, 4.123);
        counterCallback(counter3, 5.123);



        ResultActions report = mvc.perform(get("/consumption_report")
                .param("duration", "5s")
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));

        Map<String, Object> parsedReport = jsonParser.parseMap(report.andReturn().getResponse().getContentAsString());


        Double consumption = (Double) ((Map) ((List) parsedReport.get("villages")).get(0)).get("consumption");
        Assert.assertTrue(consumption > 0 && consumption < 100);

        consumption = (Double) ((Map) ((List) parsedReport.get("villages")).get(1)).get("consumption");
        Assert.assertTrue(consumption > 0 && consumption < 100);

    }

    private void createVillage(String villageName) throws Exception {
        mvc.perform(post("/village_create")
                .param("villageName", villageName)
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));
    }

    private String createCounter(String villageName) throws Exception {
        ResultActions resultActions = mvc.perform(post("/counter_create")
                .param("villageName", villageName)
                .contentType(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

        return resultActions.andReturn().getResponse().getContentAsString();
    }

    private void counterCallback(String counterId, Double amount) throws Exception {
        mvc.perform(post("/counter_callback")
                .content("{\"counter_id\": " + counterId + ",\"amount\": " + amount +"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

    }
}