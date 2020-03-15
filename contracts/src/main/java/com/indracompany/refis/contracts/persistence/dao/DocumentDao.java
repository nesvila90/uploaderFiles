package com.indracompany.refis.contracts.persistence.dao;

import com.indracompany.refis.contracts.core.AbstractDao;
import com.indracompany.refis.contracts.model.entity.Document;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class DocumentDao extends AbstractDao<Document> {

    public List<Document> findByIds(List<Long> ids) {
        Query query = this.em.createQuery("select e from " + Document.class.getName() + " e where e.id in (:ids)");

        query.setParameter("ids", ids);

        return query.getResultList();
    }

    public List<Document> findByCase(Long caseId) {
        Query query = this.em.createQuery("select e from " + Document.class.getName() + " e where e.caseId = :caseId");

        query.setParameter("caseId", caseId);

        return query.getResultList();
    }

    public List<Document> findByActivity(Long activityId) {
        Query query = this.em.createQuery("select e from " + Document.class.getName() + " e where e.activityId = :activityId");

        query.setParameter("activityId", activityId);

        return query.getResultList();
    }

    public List<Document> findByIdentification(String identification) {
        Query query = this.em.createQuery("select e from " + Document.class.getName() + " e where e.identification = :identification");

        query.setParameter("identification", identification);

        return query.getResultList();
    }

    public List<Document> findByBusinessCase(Long businessCaseId) {
        Query query = this.em.createQuery("select e from " + Document.class.getName() + " e where e.businessCaseId = :businessCaseId");

        query.setParameter("businessCaseId", businessCaseId);

        return query.getResultList();
    }
}
