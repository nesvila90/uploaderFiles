package com.indracompany.refis.contracts.core;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao<E extends AbstractEntity> {
    @PersistenceContext
    protected EntityManager em;
    protected Class<E> entityType;

    protected AbstractDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityType = ((Class) genericSuperclass.getActualTypeArguments()[0]);
    }

    public List<E> findAll() {
        return this.em.createQuery("SELECT e FROM " + this.entityType.getName() + " e").getResultList();
    }

    public E find(Long id) {
        return (E) this.em.find(this.entityType, id);
    }

    public E save(E entity) {
        this.em.persist(entity);

        return entity;
    }

    public E update(E entity) {
        return (E) this.em.merge(entity);
    }

    public void delete(Long id) {
        this.em.remove(find(id));
    }

    public void deleteMany(List<Long> ids) {
        for (Long id : ids) {
            this.em.remove(find(id));
        }
    }
}
