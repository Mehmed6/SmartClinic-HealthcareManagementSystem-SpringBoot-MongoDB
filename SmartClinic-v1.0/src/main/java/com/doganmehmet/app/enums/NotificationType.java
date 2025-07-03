package com.doganmehmet.app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum NotificationType {
    EMAIL, SMS;

    @JsonCreator
    public static NotificationType fromString(String value) {
        return NotificationType.valueOf(value.toUpperCase(Locale.ENGLISH));
    }
}
