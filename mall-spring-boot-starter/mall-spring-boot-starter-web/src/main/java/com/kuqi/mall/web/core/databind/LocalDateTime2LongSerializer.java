package com.kuqi.mall.web.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.kuqi.mall.web.core.utils.DateUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author iloveoverfly
 * @Date 2020/10/12 12:28
 **/

public class LocalDateTime2LongSerializer extends StdScalarSerializer<LocalDateTime> {

    public static final LocalDateTime2LongSerializer INSTANCE = new LocalDateTime2LongSerializer();

    protected LocalDateTime2LongSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(DateUtils.getTimestampOfDateTime(value));
    }


    @Override
    public void serializeWithType(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        gen.writeNumber(DateUtils.getTimestampOfDateTime(value));
    }


}
