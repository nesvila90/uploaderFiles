package com.indracompany.refis.contracts.web;

import com.indracompany.refis.contracts.model.entity.Document;
import com.indracompany.refis.contracts.model.vo.DocumentFileVo;
import com.indracompany.refis.contracts.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;


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


    @RequestMapping(value = {"/uploadDocumentMobile"}, method = {
            org.springframework.web.bind.annotation.RequestMethod.POST})
    public Long uploadDocumentMobile(@RequestParam(name = "processName", required = false) String processName,
                                     @RequestParam(name = "processVersion", required = false) String processVersion,
                                     @RequestParam(name = "caseId", required = false) Long caseId,
                                     @RequestParam(name = "activityName", required = false) String activityName,
                                     @RequestParam(name = "activityId", required = false) Long activityId,
                                     @RequestParam(name = "createdBy", required = false) String createdBy,
                                     @RequestParam(name = "businessCaseId", required = false) Long businessCaseId,
                                     @RequestParam(name = "identification", required = false) String identification,
                                     @RequestParam(name = "type") String type, @RequestParam(name = "file") List<MultipartFile> files)
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

            log.info("ACTION: uploadDocumentMobile, STEP: Antes de subir el documento");

            Long idDocumento;

            if (files.size() == 1) {
                idDocumento = this.documentService.uploadDocumentFile(document, files.get(0));
            } else if (files.size() > 1) {
                idDocumento = this.documentService.uploadDocumentMobile(document, getDocumentFileVoList(files));
            } else {
                throw new Exception("No files uploaded");
            }

            log.info("ACTION: uploadDocumentMobile, STEP: Despues de subir el documento");

            return idDocumento;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw e;
        }
    }

    @RequestMapping(value = {"/updateProcessData"}, method = {
            org.springframework.web.bind.annotation.RequestMethod.POST})
    public void updateProcessData(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "processName") String processName,
                                  @RequestParam(name = "processVersion") String processVersion, @RequestParam(name = "caseId") Long caseId,
                                  @RequestParam(name = "activityName") String activityName,
                                  @RequestParam(name = "activityId") Long activityId) throws Exception {
        try {
            this.documentService.updateProcessData(id, processName, processVersion, caseId, activityName, activityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @RequestMapping
    public void updateBusinessCaseId(Long id, Long businessCaseId) throws Exception {
        try {
            this.documentService.updateBusinessCaseId(id, businessCaseId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @RequestMapping(value = {"/download/{id}"}, method = {
            org.springframework.web.bind.annotation.RequestMethod.GET})
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        try {
            log.info("ACTION: download, STEP: Antes de obtener documento de la BD");

            Document document = this.documentService.findById(id);

            log.info("ACTION: download, STEP: Despues de obtener documento la BD");

            File documentFile = new File(document.getFilePath());

            response.setContentLength(document.getFileSize().intValue());

            response.setContentType("application/download");

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + URLEncoder.encode(document.getFileOriginalName(), "UTF-8") + "\"");

            log.info("ACTION: download, STEP: Antes de crear los buffers de entrada y salida");

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(documentFile));

            // dont close this stream - becase this close automatic for servelet live cicle
            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            log.info("ACTION: download, STEP: Despues de crear los buffers");

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            log.info("ACTION: download, STEP: Despues de terminar de escribir en el buffer de salida");

            outStream.flush();
            inStream.close();

        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw e;
        }
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public void view(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        try {

            log.info("ACTION: view, STEP: Antes de obtener documento de la BD");

            Document document = documentService.findById(id);

            log.info("ACTION: view, STEP: Despues de obtener documento de la BD");

            File documentFile = new File(document.getFilePath());

            response.setContentLength(document.getFileSize().intValue());

            // DOC, DOCX, XLS, XLSX, PPT, PPTX se descargan
            if (document.getFileContentType().equals("application/msword")
                    || document.getFileContentType()
                    .equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    || document.getFileContentType().equals("application/vnd.ms-excel")
                    || document.getFileContentType()
                    .equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    || document.getFileContentType().equals("application/vnd.ms-powerpoint")
                    || document.getFileContentType()
                    .equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {

                response.setContentType("application/download");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + URLEncoder.encode(document.getFileOriginalName(), "UTF-8") + "\"");
            }

            // Visualizan
            else {
                response.setContentType(document.getFileContentType());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + URLEncoder.encode(document.getFileOriginalName(), "UTF-8") + "\"");
            }

            log.info("ACTION: download, STEP: Antes de crear los buffers de entrada y salida");

            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(documentFile));

            // dont close this stream - becase this close automatic for servelet live cicle
            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            log.info("ACTION: download, STEP: Despues de crear los buffers");

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            log.info("ACTION: download, STEP: Despues de terminar de escribir en el buffer de salida");

            outStream.flush();
            inStream.close();

        } catch (Throwable t) {
            throw t;
        }
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Map<String, String>> exceptionHandler(Throwable t) {
        StringBuilder sbErrors = new StringBuilder();
        addExceptionError(sbErrors, t);

        Map<String, String> bodyMap = new HashMap();
        bodyMap.put("error", sbErrors.toString());
        bodyMap.put("status",
                HttpStatus.INTERNAL_SERVER_ERROR.value() + " - " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        return new ResponseEntity(bodyMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addExceptionError(StringBuilder sbErrors, Throwable t) {
        if (t != null) {
            if (sbErrors.length() > 0) {
                sbErrors.append(" - ");
            }
            sbErrors.append(t.getClass().getName() + ": " + t.getMessage());
            if (t.getCause() != null) {
                addExceptionError(sbErrors, t.getCause());
            }
        }
    }

    private DocumentFileVo getDocumentFileVo(MultipartFile multipartFile) throws Exception {
        if (multipartFile != null) {
            File tempFile = File.createTempFile("document_svc_file_" + String.valueOf(System.currentTimeMillis()),
                    ".tmp");

            FileOutputStream fos = new FileOutputStream(tempFile);

            IOUtils.copy(multipartFile.getInputStream(), fos);

            fos.flush();

            fos.close();

            return new DocumentFileVo(multipartFile.getOriginalFilename(), multipartFile.getSize(),
                    multipartFile.getContentType(), tempFile);
        }
        return null;
    }

    private List<DocumentFileVo> getDocumentFileVoList(List<MultipartFile> multipartFile) throws Exception {
        List<DocumentFileVo> list = new ArrayList();

        for (MultipartFile mpf : multipartFile) {
            list.add(getDocumentFileVo(mpf));
        }

        return list;
    }
}
