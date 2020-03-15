package com.indracompany.refis.contracts.model.dto.audit;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The type Refis tracer.
 */
@Data
public class RefisTracer {

    private String caseId;
    private String processName;
    private int activityId;
    private String activityName;
    private String user;
    private LocalDateTime createDateActivity;
    private LocalDateTime assignDateActivity;
    private LocalDateTime endDateActivity;
    private LocalDateTime expirationDateActivity;
    private String activityStatus;
    private String comments;

}
