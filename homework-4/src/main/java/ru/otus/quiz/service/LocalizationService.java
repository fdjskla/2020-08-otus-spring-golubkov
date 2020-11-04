package ru.otus.quiz.service;

import java.util.Locale;

public interface LocalizationService {

    default String getLocalized(String key) {
        return getLocalized(key, Locale.ENGLISH);
    }

    default String getLocalized(String key, Locale locale) {
        return getLocalized(key, locale, new String[]{});
    }

    String getLocalized(String key, Locale locale, String... args);
}
