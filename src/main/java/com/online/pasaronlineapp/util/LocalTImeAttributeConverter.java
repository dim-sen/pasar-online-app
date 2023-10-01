package com.online.pasaronlineapp.util;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

@Slf4j
@Converter(autoApply = true)
public class LocalTImeAttributeConverter implements AttributeConverter<LocalTime, Time> {
    @Override
    public Time convertToDatabaseColumn(LocalTime localTime) {
        if (localTime != null) {
            log.info("Convert to DB");
            log.info("Time.valueOf(localTime): " + Time.valueOf(localTime));
            return Time.valueOf(localTime);
        }
        return null;
    }

    @Override
    public LocalTime convertToEntityAttribute(Time time) {
        if (time != null) {
            log.info("Convert to Entity");
            log.info("time.toLocalTime()" + time.toLocalTime());
            return time.toLocalTime();
        }
        return null;
    }
}
