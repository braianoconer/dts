package com.playground.observability.translator;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class TranslatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslatorController.class);

    @Autowired
    private Tracer tracer;


    private final Random rnd = new Random();

    @Autowired
    private Dictionary dictionary;

    @GetMapping("/translate")
    public ResponseEntity<String> index(@RequestParam(value = "target", defaultValue = "ES") String target,
                                @RequestParam(value = "word", defaultValue = "hello") String word) {

        LOGGER.warn("---------");
        printTrace();
        LOGGER.warn("---------");

        if (Math.abs(rnd.nextInt()) % 100 != 0) {
            return ResponseEntity.ok(dictionary.translateTo(TargetLanguage.valueOf(target.toUpperCase()), word));
        } else {
            return ResponseEntity.badRequest().body("Error in translation request for " + word);
        }
    }

    private void printTrace () {
        String traceId = tracer.currentSpan().context().traceIdString();
        String spanId = tracer.currentSpan().context().spanIdString();
        LOGGER.info("--- Tracer ---  traceId: {} & spanId: {}", traceId, spanId);
    }

}
