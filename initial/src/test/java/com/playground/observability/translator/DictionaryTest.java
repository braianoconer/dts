package com.playground.observability.translator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DictionaryTest {

    Dictionary uut = new Dictionary();

    @Test
    public void testTranslateTo() {
        uut.translateTo(TargetLanguage.ES, "hola").equals("hello");
    }


}
