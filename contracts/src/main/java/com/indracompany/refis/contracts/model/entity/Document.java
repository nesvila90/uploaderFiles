package com.indracompany.refis.contracts.model.entity;

import com.indracompany.refis.contracts.core.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "WS_DOCUMENT")
public class Document extends AbstractEntity {
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
    @Column(name = "DOCUMENT_TYPE")
    private String documentType;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "IDENTIFICATION")
    private String identification;
    @Column(name = "BUSINESS_CASE_ID")
    private Long businessCaseId;
    @Column(name = "FILE_PATH")
    private String filePath;
    @Column(name = "FILE_ORIGINAL_NAME")
    private String fileOriginalName;
    @Column(name = "FILE_SIZE")
    private Long fileSize;
    @Column(name = "FILE_CONTENT_TYPE")
    private String fileContentType;

}
