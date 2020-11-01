package ru.otus.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class MessagesLocalizationService implements LocalizationService {

    private final MessageSource localization;

    @Override
    public String getLocalized(String key, Locale locale, String... args) {
        return localization.getMessage(key, args, locale);
    }
}
