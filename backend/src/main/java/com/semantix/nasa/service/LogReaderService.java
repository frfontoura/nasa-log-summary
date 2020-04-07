package com.semantix.nasa.service;

import com.semantix.nasa.domain.LogSummary;

/**
 * This interface must be implemented by classes responsible for reading logs in a given source (local, S3, HDFS, etc.).
 *
 * @author frfontoura
 * @version 1.0 07/04/2020
 */
public interface LogReaderService {

    /**
     * Performs the processing of the files and returns the summary information.
     *
     * @return the summary of logs
     */
    LogSummary process();
}
