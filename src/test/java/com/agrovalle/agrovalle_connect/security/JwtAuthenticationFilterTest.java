package com.agrovalle.agrovalle_connect.security;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtAuthenticationFilterTest {

    private final JwtAuthenticationFilter filter = new JwtAuthenticationFilter();

    @Test
    void missingAuthorizationHeaderContinuesFilterChain() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicInteger chainCalls = new AtomicInteger();

        filter.doFilterInternal(request, response, incrementingChain(chainCalls));

        assertEquals(1, chainCalls.get());
    }

    @Test
    void nonBearerAuthorizationHeaderContinuesFilterChain() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic credentials");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicInteger chainCalls = new AtomicInteger();

        filter.doFilterInternal(request, response, incrementingChain(chainCalls));

        assertEquals(1, chainCalls.get());
    }

    @Test
    void bearerAuthorizationHeaderContinuesFilterChain() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicInteger chainCalls = new AtomicInteger();

        filter.doFilterInternal(request, response, incrementingChain(chainCalls));

        assertEquals(1, chainCalls.get());
    }

    private FilterChain incrementingChain(AtomicInteger calls) {
        return (request, response) -> calls.incrementAndGet();
    }
}
