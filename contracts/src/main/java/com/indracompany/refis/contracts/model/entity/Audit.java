package com.indracompany.refis.contracts.model.entity;

import com.indracompany.refis.contracts.core.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "AUDIT")
public class Audit extends AbstractEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "WS_DOCUMENT_ID_SEQ", sequenceName = "WS_DOCUMENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "WS_DOCUMENT_ID_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "PROCESS_NAME")
    private String processName;
    @Column(name = "PROCESS_VERSION")
    private String processVersion;
    @Column(name = "CASE_ID")
    private Long caseId;
    @Column(name = "ACTIVITY_NAME")
    private String activityName;
    @Column(name = "ACTIVITY_ID")
    private Long activityId;
    @Column(name = "USER")
    private String user;
    @Column(name = "CREATE_DATE_ACTIVITY")
    private LocalDateTime createDateActivity;
    @Column(name = "ASSIGN_DATE_ACTIVITY")
    private LocalDateTime assignDateActivity;
    @Column(name = "END_DATE_ACTIVITY")
    private LocalDateTime endDateActivity;
    @Column(name = "ACTIVITY_STATUS")
    private String activityStatus;
    @Column(name = "COMMENTS")
    private String comments;

}
