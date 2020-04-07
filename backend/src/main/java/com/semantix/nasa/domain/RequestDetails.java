package com.semantix.nasa.domain;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents the data for each log line.
 *
 * @author frfontoura
 * @version 1.0 06/04/2020
 */
@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class RequestDetails implements Serializable {

    private static final long serialVersionUID = 9194712022971859706L;
    private final String host;
    private final LocalDate date;
    private final String url;
    private final String status;
    private final long size;

}
