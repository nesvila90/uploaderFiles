package com.indracompany.refis.contracts.web;

import com.indracompany.refis.contracts.model.entity.Document;
import com.indracompany.refis.contracts.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/document-router")
public class DocumentRouterApi {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = {"/uploadDocument"}, method = {
            org.springframework.web.bind.annotation.RequestMethod.POST})
    public Long uploadDocument(@RequestParam(name = "processName", required = false) String processName,
                               @RequestParam(name = "processVersion", required = false) String processVersion,
                               @RequestParam(name = "caseId", required = false) Long caseId,
                               @RequestParam(name = "activityName", required = false) String activityName,
                               @RequestParam(name = "activityId", required = false) Long activityId,
                               @RequestParam(name = "createdBy", required = false) String createdBy,
                               @RequestParam(name = "businessCaseId", required = false) Long businessCaseId,
                               @RequestParam(name = "identification", required = false) String identification,
                               @RequestParam(name = "type") String type, @RequestParam(name = "file") MultipartFile file)
            throws Exception {
        try {

            Document document = new Document();
            document.setProcessName(processName);
            document.setProcessVersion(processVersion);
            document.setCaseId(caseId);
            document.setActivityName(activityName);
            document.setActivityId(activityId);
            document.setDocumentType(type);
            document.setCreatedDate(new Date());
            document.setCreatedBy(createdBy);
            document.setIdentification(identification);
            document.setBusinessCaseId(businessCaseId);

            log.info("ACTION: uploadDocument, STEP: Antes de subir el documento");

             Long idDocumento = documentService.uploadDocumentFile(document, file);

            log.info("ACTION: uploadDocument, STEP: Despues de subir el documento");

            return idDocumento;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw e;
        }
    }
}
