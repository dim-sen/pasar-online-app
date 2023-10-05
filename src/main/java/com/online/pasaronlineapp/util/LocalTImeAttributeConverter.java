package com.online.pasaronlineapp.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

@Converter(autoApply = true)
public class LocalTImeAttributeConverter implements AttributeConverter<LocalTime, Time> {
    @Override
    public Time convertToDatabaseColumn(LocalTime localTime) {
        if (localTime != null) {
            return Time.valueOf(localTime);
        }
        return null;
    }

    @Override
    public LocalTime convertToEntityAttribute(Time time) {
        if (time != null) {
            return time.toLocalTime();
        }
        return null;
    }
}
