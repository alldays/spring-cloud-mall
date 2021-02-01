package com.kuqi.mall.web.core.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.kuqi.mall.web.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author iloveoverfly
 * @Date 2020/10/12 12:29
 **/
@Slf4j
@JacksonStdImpl
public class LocalDateTimeLongDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final JsonDeserializer<LocalDateTime> INSTANCE = new LocalDateTimeLongDeserializer();

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        String dateString = p.getText();
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        try {
            return DateUtils.getDateTimeOfTimestamp(Long.parseLong(dateString));
        } catch (Exception e) {
            log.error("timestamp to java.util.date error", e);
            return null;
        }
    }
}
