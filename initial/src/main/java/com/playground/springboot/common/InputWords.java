package com.playground.springboot.common;

import java.util.ArrayList;
import java.util.List;

public class InputWords {

    private static final List<String> ENGLISH = new ArrayList<>();

    static {
        ENGLISH.add("Hello".toLowerCase());
        ENGLISH.add("Goodbye".toLowerCase());
        ENGLISH.add("Good morning".toLowerCase());
        ENGLISH.add("Good night".toLowerCase());
        ENGLISH.add("How are you?".toLowerCase());
        ENGLISH.add("What is your name?".toLowerCase());
        ENGLISH.add("My tailor is rich".toLowerCase());
        ENGLISH.add("My house is your house".toLowerCase());
    }

    public static List<String> getEnglishWords() {
        return ENGLISH;
    }
}
