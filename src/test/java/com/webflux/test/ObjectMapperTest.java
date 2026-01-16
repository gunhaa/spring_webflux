package com.webflux.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectMapperTest {


    @Test
    public void 오브젝트_매퍼는_없는_필드를_파싱하면_오류가_발생해야한다() {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInput = "{\"name\":\"tester\", \"age\":30}";

        // when & then
        assertThrows(UnrecognizedPropertyException.class, () -> {
            objectMapper.readValue(jsonInput, TestDto.class);
        });
    }

    private static class TestDto {
        private String name;
    }
}
