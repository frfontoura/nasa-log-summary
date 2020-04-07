package com.semantix.nasa.service;

import com.semantix.nasa.domain.LogSummary;
import com.semantix.nasa.domain.RequestDetails;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service responsible for processing the logs from a local storage defined in the application.properties and summary.
 *
 * @author frfontoura
 * @version 1.0 06/04/2020
 */
@RequiredArgsConstructor
@Service
public class LogReaderLocalStorageService implements LogReaderService {

    private final JavaSparkContext sparkContext;

    @Value("${data.localStorage.path}")
    private String PATH;

    /**
     * Performs the processing of the files found in the data folder and returns the summary information.
     *
     * @return the summary of logs
     */
    @Override
    public LogSummary process() {
        final JavaRDD<String> lines = sparkContext.textFile(PATH, 1);

        final JavaRDD<RequestDetails> detailsRDD = convertToJavaRDDRequestDetails(lines);
        final Long totalSize = getTotalSize(detailsRDD);
        final Long uniqueHosts = getUniqueHosts(detailsRDD);

        final JavaRDD<RequestDetails> notFoundRDD = getNotFoundRequests(detailsRDD);
        final Long notFoundCount = notFoundRDD.count();
        final List<Tuple2<String, Integer>> top5NotFound = getTopFiveNotFoundUrl(notFoundRDD);
        final List<Tuple2<LocalDate, Integer>> notFoundByDay = getNotFoundByDay(notFoundRDD);

        return LogSummary.builder()
                .totalSize(totalSize)
                .uniqueHosts(uniqueHosts)
                .notFoundCount(notFoundCount)
                .notFoundByDay(notFoundByDay)
                .top5NotFound(top5NotFound)
                .build();
    }

    /**
     * Converts a line from the log to a RequestDetails.
     *
     * @param lines
     * @return a RequestDetails
     */
    private JavaRDD<RequestDetails> convertToJavaRDDRequestDetails(final JavaRDD<String> lines) {
        return lines.map(s -> {
                final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("dd/MMM/yyyy")
                        .toFormatter(Locale.ENGLISH);

                final Pattern pattern = Pattern.compile("(.*) - - \\[(.*):(.*):(.*):(.*) -(.*)\\] \"(.*) (.*) (.*)\" ([0-9]*) ([0-9]*|-)");
                final Matcher matcher = pattern.matcher(s);

                if (matcher.matches()) {
                    final int sizeGroup = 11;
                    final String sizeGroupValue = matcher.group(sizeGroup).replace("-", "").trim();
                    final long size = !sizeGroupValue.isBlank() ? Long.valueOf(matcher.group(sizeGroup)) : 0;

                    return RequestDetails.builder()
                            .host(matcher.group(1))
                            .date(LocalDate.parse(matcher.group(2), dateTimeFormatter))
                            .url(matcher.group(8))
                            .status(matcher.group(10))
                            .size(size)
                            .build();
                }
                return null;
            }).filter(requestDetails -> requestDetails != null);
    }

    /**
     * Filters requests with NOT_FOUND(404) status by day.
     *
     * @param notFoundRDD
     * @return list of date and number of NOT_FOUND requests
     */
    private List<Tuple2<LocalDate, Integer>> getNotFoundByDay(final JavaRDD<RequestDetails> notFoundRDD) {
        return notFoundRDD.mapToPair(requestDetails -> new Tuple2<>(requestDetails.getDate(), 1))
                    .reduceByKey((integer, integer2) -> integer + integer2)
                    .sortByKey(true)
                    .collect();
    }

    /**
     * Filters the 5 URLs with more NOT_FOUND(404) occurrences
     *
     * @param notFoundRDD
     * @return a list of 5 URL with more NOT_FOUND occurrences
     */
    private List<Tuple2<String, Integer>> getTopFiveNotFoundUrl(final JavaRDD<RequestDetails> notFoundRDD) {
        return notFoundRDD.mapToPair(requestDetails -> new Tuple2<>(requestDetails.getUrl(), 1))
                    .reduceByKey((integer, integer2) -> integer + integer2)
                    .map(tuple -> tuple)
                    .sortBy(tuple2 -> tuple2._2, false, 1)
                    .take(5);
    }

    /**
     * Search all requests with status NOT_FOUND(404).
     *
     * @param detailsRDD
     * @return requests with status NOT_FOUND
     */
    private JavaRDD<RequestDetails> getNotFoundRequests(final JavaRDD<RequestDetails> detailsRDD) {
        return detailsRDD.filter(requestDetails -> "404".equals(requestDetails.getStatus()));
    }

    /**
     * Search for all unique hosts.
     *
     * @param detailsRDD
     * @return number of unique hosts
     */
    private Long getUniqueHosts(final JavaRDD<RequestDetails> detailsRDD) {
        return detailsRDD.keyBy(requestDetails -> requestDetails.getHost()).distinct().count();
    }

    /**
     * Performs the sum of all RequestDetails.getSize().
     *
     * @param detailsRDD
     * @return sum of RequestDetails.getSize()
     */
    private Long getTotalSize(final JavaRDD<RequestDetails> detailsRDD) {
        return detailsRDD.map(requestDetails -> requestDetails.getSize()).reduce((size1, size2) -> size1 + size2);
    }

}
