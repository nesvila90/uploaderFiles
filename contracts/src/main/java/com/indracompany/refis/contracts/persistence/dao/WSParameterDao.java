package com.indracompany.refis.contracts.persistence.dao;

import com.indracompany.refis.contracts.core.AbstractDao;
import com.indracompany.refis.contracts.model.entity.WSParameter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class WSParameterDao extends AbstractDao<WSParameter> {

    @Cacheable(value = "parameter.findByKey")
    public WSParameter findByKey(String key) throws Exception {
        try {
            Query query = this.em.createQuery("select e from " + WSParameter.class.getName() + " e where e.key = :key");
            query.setParameter("key", key);
            return (WSParameter) query.getSingleResult();
        } catch (Exception ex) {
            throw new Exception("Error obteniendo parametro: " + key);
        }
    }
}