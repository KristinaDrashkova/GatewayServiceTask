package com.musala.gateway.dao;

import com.musala.gateway.entities.BaseEntity;
import com.musala.gateway.utils.ModelParser;
import com.musala.gateway.utils.ValidationUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BaseDaoImpl<E> implements BaseDao<E> {
    @PersistenceContext(name = "gateway")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll(String selectedClass) {
        return em.createQuery("FROM " + selectedClass).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E findById(long id, String className) throws ClassNotFoundException {
        Class<E> currentClass = (Class<E>) Class.forName("com.musala.gateway.entities." + className);
        return em.find(currentClass, id);
    }

    @Override
    public <E> void save(E entity) {
//        try {
            em.persist(entity);
//        } catch (ConstraintViolationException e) {
//            e.getMessage();
//        }
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public <M> void update(long id, M dto, String classToMap) throws ClassNotFoundException {
        BaseEntity baseEntity = (BaseEntity) this.findById(id, classToMap);
        M objectFromDto = (M) ModelParser.getInstance().map(dto, Class.forName("com.musala.gateway.entities." + classToMap));
        if (ValidationUtil.isValid(objectFromDto)) {
            em.remove(baseEntity);
            em.persist(objectFromDto);
        }
    }
}
