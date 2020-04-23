package com.playground.observability.translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class TranslatorController {

    private final Random rnd = new Random();

    @Autowired
    private Dictionary dictionary;

    @GetMapping("/translate")
    public ResponseEntity<String> index(@RequestParam(value = "target", defaultValue = "ES") String target,
                                @RequestParam(value = "word", defaultValue = "hello") String word) {
        if (Math.abs(rnd.nextInt()) % 100 != 0) {
            return ResponseEntity.ok(dictionary.translateTo(TargetLanguage.valueOf(target.toUpperCase()), word));
        } else {
            return ResponseEntity.badRequest().body("Error in translation request for " + word);
        }
    }

}
