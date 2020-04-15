package com.playground.observability.translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslatorController {

    @Autowired
    private Dictionary dictionary;

    @GetMapping("/translate")
    public String index(@RequestParam(value = "target", defaultValue = "ES") String target,
                        @RequestParam(value = "word", defaultValue = "hello") String word) {
        return dictionary.translateTo(TargetLanguage.valueOf(target.toUpperCase()), word);
    }

}
