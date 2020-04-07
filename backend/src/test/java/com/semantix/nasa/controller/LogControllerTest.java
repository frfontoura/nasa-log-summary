package com.semantix.nasa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semantix.nasa.domain.LogSummary;
import com.semantix.nasa.service.LogReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import scala.Tuple2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Test for the {@link LogController}
 *
 * @author frfontoura
 * @version 1.0 07/04/2020
 */
@WebMvcTest(LogController.class)
public class LogControllerTest {

    @MockBean
    private LogReaderService logReaderService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private JacksonTester<LogSummary> jsonSummary;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, mapper);
    }

    @Test
    public void processTest() throws Exception{
        final LogSummary summary = createLogSummary();
        given(logReaderService.process()).willReturn(summary);

        final MockHttpServletResponse response = mvc.perform(get("/logs/process")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonSummary.write(summary).getJson());
    }

    /**
     * @return a LogSummary example
     */
    private LogSummary createLogSummary() {
        final List<Tuple2<String, Integer>> top5NotFound = Arrays.asList(new Tuple2<>("/url", 42));
        final List<Tuple2<LocalDate, Integer>> notFoundByDay = Arrays.asList(new Tuple2<>(LocalDate.now(), 9));

        return LogSummary.builder()
                .totalSize(1000l)
                .notFoundCount(123l)
                .uniqueHosts(300l)
                .top5NotFound(top5NotFound)
                .notFoundByDay(notFoundByDay)
                .build();
    }

}
