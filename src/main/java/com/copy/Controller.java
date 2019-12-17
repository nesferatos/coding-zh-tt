package com.copy;


import com.copy.model.Counter;
import com.copy.model.CounterData;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);


    @Autowired
    private CounterRepository counterRepository;

    private static final String COUNTER_ID = "counter_id";

    private static final String AMOUNT = "amount";

    @PostMapping(value = "/counter_callback", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> counterСallback(@RequestBody JsonNode body) {
        if (!body.hasNonNull(COUNTER_ID))
            return ResponseEntity.badRequest().body("the " + COUNTER_ID + " must be present");
        if (!body.hasNonNull(AMOUNT))
            return ResponseEntity.badRequest().body("the " + AMOUNT + " must be present");

        String counterCode = body.get(COUNTER_ID).asText();
        Double amount = body.get(AMOUNT).asDouble();

        Counter counter = counterRepository.findByCode(counterCode);



        return ResponseEntity.ok("ok");
    }


    @GetMapping(value = "/counter_create", consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> counterСallback(@RequestParam String code) {
        Counter counter = new Counter();
        counter.setCode(code);
        counterRepository.save(counter);
        logger.debug("counter [{}] created", code);
        return ResponseEntity.ok("counter [" + code + "] created");
    }
}
