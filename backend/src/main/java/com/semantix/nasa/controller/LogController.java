package com.semantix.nasa.controller;

import com.semantix.nasa.domain.LogSummary;
import com.semantix.nasa.service.LogReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for exposing services related to log processing.
 *
 * @author frfontoura
 * @version 1.0 06/04/2020
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/logs")
public class LogController {

    public final LogReaderService logReaderService;

    /**
     * Performs the processing of the files found in the data folder and returns the summary information.
     *
     * @return the summary of logs
     */
    @GetMapping("process")
    public ResponseEntity<LogSummary> process() {
        return ResponseEntity.ok(logReaderService.process());
    }
}
