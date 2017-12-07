package com.jackson.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class ObjectMapperTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        StdTypeResolverBuilder typeResolverBuilder =
                new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL)
                        .init(JsonTypeInfo.Id.CLASS, null)
                        .inclusion(JsonTypeInfo.As.WRAPPER_OBJECT);

        objectMapper = new ObjectMapper();
        objectMapper.setDefaultTyping(typeResolverBuilder);
        objectMapper.findAndRegisterModules();
    }

    @Test
    public void testLocalDateSerialization() throws JsonProcessingException {
        final LocalDate localDate = LocalDate.of(2017, 12, 5);
        String dateHolderStr = objectMapper.writeValueAsString(new Holder(localDate, localDate));
        Assert.assertEquals("{\"localDate\":[2017,12,5],\"object\":{\"java.time.LocalDate\":[2017,12,5]}}", dateHolderStr);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private static class Holder {
        @JsonProperty
        private LocalDate localDate;
        @JsonProperty
        private Object object;
        public Holder(LocalDate localDate, Object object) {
            this.localDate = localDate;
            this.object = object;
        }
    }
}