package com.eyes.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;

@Component
public class JsonObjectMapperProvider extends ObjectMapper {

    public static class UnixTimestampDeserializer extends JsonDeserializer<Timestamp> {
        @Override
        public Timestamp deserialize(JsonParser parser, DeserializationContext context)
                throws IOException {
            String unixTimestamp = parser.getText().trim();
            return new Timestamp(Long.valueOf(unixTimestamp));
        }
    }

    public JsonObjectMapperProvider() {
        this.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        Hibernate5Module module = new Hibernate5Module();
        module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        this.registerModule(module);
    }
}
