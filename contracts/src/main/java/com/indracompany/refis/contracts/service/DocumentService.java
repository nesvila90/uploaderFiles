package com.indracompany.refis.contracts.service;

import com.indracompany.refis.contracts.model.entity.Document;
import com.indracompany.refis.contracts.model.vo.DocumentFileVo;
import com.indracompany.refis.contracts.model.vo.WSParameterKey;
import com.indracompany.refis.contracts.persistence.dao.DocumentDao;
import com.indracompany.refis.contracts.persistence.dao.WSParameterDao;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private WSParameterDao wsParameterDao;

    public Document findById(Long id) {
        return documentDao.find(id);
    }

    public List<Document> findByIds(List<Long> ids) {
        return documentDao.findByIds(ids);
    }

    public List<Document> findByCase(Long caseId) {
        return documentDao.findByCase(caseId);
    }

    public List<Document> findByActivity(Long activityId) {
        return documentDao.findByActivity(activityId);
    }

    public List<Document> findByIdentification(String identification) {
        return documentDao.findByIdentification(identification);
    }

    public List<Document> findByBusinessCase(Long businessCaseId) {
        return documentDao.findByBusinessCase(businessCaseId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public boolean deleteDocument(long id) {
        Document document = this.findById(id);

        if (document != null) {
            File file = new File(document.getFilePath());
            if (file.delete()) {
                documentDao.delete(id);
            } else {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    /*
     * Use this method only if is necessary, better use uploadDocumentFile
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public Long uploadDocument(Document document, DocumentFileVo fileVo) throws Exception {

        InputStream is = null;
        OutputStream os = null;

        try {
            File targetDir = getTargetFile(fileVo);
            is = new FileInputStream(fileVo.getTempFile());
            os = new FileOutputStream(targetDir);
            IOUtils.copy(is, os);

            document.setFilePath(targetDir.getPath());
            document.setFileOriginalName(fileVo.getOriginalFileName());
            document.setFileSize(Long.valueOf(fileVo.getSize()));
            document.setFileContentType(fileVo.getContentType());
            documentDao.save(document);

            return document.getId();
        } finally {
            if (is != null) {
                is.close();
            }

            if (os != null) {
                os.close();
            }

            fileVo.getTempFile().delete();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public Long uploadDocumentFile(Document document, MultipartFile file) throws Exception {
        try {

            document.setFileOriginalName(file.getOriginalFilename());
            document.setFileSize(file.getSize());
            document.setFileContentType(file.getContentType());

            String targetPath = getTargetPath(document);
            Path path = Paths.get(targetPath);

            try (OutputStream os = Files.newOutputStream(path)) {
                os.write(file.getBytes());
                os.flush();
            }

            document.setFilePath(targetPath);
            documentDao.save(document);

            return document.getId();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public Long uploadDocumentMobile(Document document, List<DocumentFileVo> fileVoList) throws Exception {
        //DocumentFileVo fileVo = createPDF(document, fileVoList);
        DocumentFileVo fileVo = new DocumentFileVo();
        return uploadDocument(document, fileVo);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public void updateProcessData(Long id, String processName, String processVersion, Long caseId, String activityName,
                                  Long activityId) {

        Document document = documentDao.find(id);
        document.setProcessName(processName);
        document.setProcessName(processName);
        document.setProcessVersion(processVersion);
        document.setCaseId(caseId);
        document.setActivityName(activityName);
        document.setActivityId(activityId);

        documentDao.update(document);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public void updateBusinessCaseId(Long id, Long businessCaseId) {
        Document document = documentDao.find(id);
        document.setBusinessCaseId(businessCaseId);
        documentDao.update(document);
    }

    private File getTargetFile(DocumentFileVo fileVo) throws Exception {

        String documentBaseDir = this.wsParameterDao.findByKey(WSParameterKey.DOCUMENT_BASE_DIR.name()).getValue();
        //String documentBaseDir = "C:\\temp";
        File baseDir = new File(documentBaseDir);
        String year = String.valueOf(Calendar.getInstance().get(1));
        String month = String.valueOf(Calendar.getInstance().get(2) + 1);
        String day = String.valueOf(Calendar.getInstance().get(5));
        File targetDir = new File(baseDir + File.separator + year + File.separator + month + File.separator + day);

        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        StringBuilder sbFileName = new StringBuilder();

        sbFileName.append(FilenameUtils.getBaseName(fileVo.getOriginalFileName())).append("_")
                .append(System.currentTimeMillis()).append(".")
                .append(FilenameUtils.getExtension(fileVo.getOriginalFileName()));

        return new File(targetDir + File.separator + sbFileName.toString());
    }

    private String getTargetPath(Document document) throws Exception {

        //String documentBaseDir = "C:\\temp";
        String documentBaseDir = this.wsParameterDao.findByKey(WSParameterKey.DOCUMENT_BASE_DIR.name()).getValue();

        String year = String.valueOf(Calendar.getInstance().get(1));

        String month = String.valueOf(Calendar.getInstance().get(2) + 1);

        String day = String.valueOf(Calendar.getInstance().get(5));

        String dir = documentBaseDir + File.separator + year + File.separator + month + File.separator + day;

        File targetDir = new File(dir);

        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        dir += File.separator + FilenameUtils.getBaseName(document.getFileOriginalName()) + "_"
                + System.currentTimeMillis() + "." + FilenameUtils.getExtension(document.getFileOriginalName());
        return dir;
    }

}
