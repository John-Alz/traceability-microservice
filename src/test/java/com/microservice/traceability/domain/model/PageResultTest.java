package com.microservice.traceability.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageResultTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        PageResult<String> result = new PageResult<>();

        result.setContent(List.of("item1", "item2"));
        result.setPage(1);
        result.setSize(2);
        result.setTotalPages(3);
        result.setTotalElements(6L);

        assertEquals(List.of("item1", "item2"), result.getContent());
        assertEquals(1, result.getPage());
        assertEquals(2, result.getSize());
        assertEquals(3, result.getTotalPages());
        assertEquals(6L, result.getTotalElements());
    }

    @Test
    void testAllArgsConstructor() {
        List<String> content = List.of("a", "b", "c");
        PageResult<String> result = new PageResult<>(content, 0, 3, 1, 3L);

        assertEquals(content, result.getContent());
        assertEquals(0, result.getPage());
        assertEquals(3, result.getSize());
        assertEquals(1, result.getTotalPages());
        assertEquals(3L, result.getTotalElements());
    }
}
