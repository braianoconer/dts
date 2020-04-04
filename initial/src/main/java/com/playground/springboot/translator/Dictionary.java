package com.playground.springboot.translator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Dictionary {

    private final List<String> ENGLISH = new ArrayList<>();
    private final Map<String, String> ENG_SPA = new HashMap<>();
    private final Map<String, String> ENG_ITA = new HashMap<>();
    private final Map<String, String> ENG_DEU = new HashMap<>();

    private static final String NO_TRANSLATION = "N/A";

    public Dictionary() {
        ENGLISH.add("Hello".toLowerCase());
        ENGLISH.add("Goodbye".toLowerCase());
        ENGLISH.add("Good morning".toLowerCase());
        ENGLISH.add("Good night".toLowerCase());
        ENGLISH.add("How are you?".toLowerCase());
        ENGLISH.add("What is your name?".toLowerCase());
        ENGLISH.add("My tailor is rich".toLowerCase());
        ENGLISH.add("My house is your house".toLowerCase());

        ENG_SPA.put(ENGLISH.get(0), "Hola");
        ENG_SPA.put(ENGLISH.get(1), "Adios");
        ENG_SPA.put(ENGLISH.get(2), "Buenos dias");
        ENG_SPA.put(ENGLISH.get(3), "Buenas noches");
        ENG_SPA.put(ENGLISH.get(4), "Como estas?");
        ENG_SPA.put(ENGLISH.get(5), "Mi sastre es rico");
        ENG_SPA.put(ENGLISH.get(6), "Mi casa es tu casa");

        ENG_ITA.put(ENGLISH.get(0), "Ciao");
        ENG_ITA.put(ENGLISH.get(1), "Arrivederci");
        ENG_ITA.put(ENGLISH.get(2), "Buon giorno");
        ENG_ITA.put(ENGLISH.get(3), "Buonanotte");
        ENG_ITA.put(ENGLISH.get(4), "Come stai?");
        ENG_ITA.put(ENGLISH.get(5), "Il mio sartro e ricco");
        ENG_ITA.put(ENGLISH.get(6), "La mia casa e tua");

        ENG_DEU.put(ENGLISH.get(0), "Hallo");
        ENG_DEU.put(ENGLISH.get(1), "Tschuss");
        ENG_DEU.put(ENGLISH.get(2), "Guten Morgen");
        ENG_DEU.put(ENGLISH.get(3), "Guten Nacht");
        ENG_DEU.put(ENGLISH.get(4), "Wie geht's dir?");
        ENG_DEU.put(ENGLISH.get(5), "Mein Schneider ist reich");
        ENG_DEU.put(ENGLISH.get(6), "Mein Haus ist dein Haus");
    }

    public String translateTo(TargetLanguage targetLang, String word) {
        String result = null;
        switch (targetLang) {
            case ES:
                result = ENG_SPA.getOrDefault(word.toLowerCase(), NO_TRANSLATION);
                break;
            case IT:
                result = ENG_ITA.getOrDefault(word.toLowerCase(), NO_TRANSLATION);
                break;
            case DE:
                result = ENG_DEU.getOrDefault(word.toLowerCase(), NO_TRANSLATION);
                break;
        }
        return result;
    }
}
