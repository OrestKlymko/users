package com.task.user.mapper;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocaleDateMapper extends PropertyEditorSupport {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDate.parse(text, FORMATTER));
    }

    @Override
    public String getAsText() {
        LocalDate value = (LocalDate) getValue();
        return value != null ? FORMATTER.format(value) : "";
    }
}
