package com.copy;


import com.copy.model.Counter;
import com.copy.model.Village;
import com.copy.model.VillageReportRec;
import com.copy.repository.CounterRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private EnergyService energyService;

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

        Long counterId = body.get(COUNTER_ID).asLong();
        Double amount = body.get(AMOUNT).asDouble();

        energyService.saveCounterData(counterId, amount);
        return ResponseEntity.ok("ok");
    }


    @PostMapping(value = "/counter_create", consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> counterCreate(@RequestParam String villageName) {
        Counter counter;
        try {
            counter = energyService.createCounter(villageName);
        } catch (NoSuchElementException e) {
            logger.debug(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        logger.debug("counter [{}] created", counter.getId());
        return ResponseEntity.ok(counter.getId().toString());
    }

    @PostMapping(value = "/village_create", consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> villageCreate(@RequestParam String villageName) {
        Village village;
        try {
            village = energyService.createVillage(villageName);
        } catch (NoSuchElementException e) {
            logger.debug(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        logger.debug("village [{}] created", village.getId());
        return ResponseEntity.ok(village.getId().toString());
    }

    @GetMapping(value = "counter", consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> counter(@RequestParam Long id) {
        Optional<Counter> counter = counterRepository.findById(id);
        if (counter.isPresent()) {
            HashMap<Object, Object> counterInfo = new HashMap<>();
            String name = counter.get().getVillage().getName();
            counterInfo.put("id", counter.get().getId());
            counterInfo.put("village_name", counter.get().getVillage().getName());
            return new ResponseEntity(counterInfo, HttpStatus.OK);//TODO: return json
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "consumption_report", consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Object, Object>> consumptionReport(@RequestParam String duration) {
        Duration d = Duration.parse("PT" + duration);

        Map<String, Object> report = new HashMap<>();
        report.put("villages", energyService.getVillageConsumptionReport(d));
        return new ResponseEntity(report, HttpStatus.OK);
    }
}
