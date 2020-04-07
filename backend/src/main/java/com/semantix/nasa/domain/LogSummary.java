package com.semantix.nasa.domain;

import lombok.*;
import scala.Tuple2;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Represents the summary data of the logs.
 *
 * @author frfontoura
 * @version 1.0 07/04/2020
 */
@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class LogSummary {

    private final Long uniqueHosts;
    private final Long totalSize;
    private final Long notFoundCount;
    private final List<Tuple2<String, Integer>> top5NotFound;
    private final List<Tuple2<LocalDate, Integer>> notFoundByDay;

    /**
     * @return an unmodifiable copy of top5NotFound list
     */
    public List<Tuple2<String, Integer>> getTop5NotFound() {
        return Collections.unmodifiableList(top5NotFound);
    }

    /**
     * @return an unmodifiable copy of notFoundByDay list
     */
    public List<Tuple2<LocalDate, Integer>> getNotFoundByDay() {
        return Collections.unmodifiableList(notFoundByDay);
    }

}
