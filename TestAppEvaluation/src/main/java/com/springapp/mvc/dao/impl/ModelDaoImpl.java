package com.springapp.mvc.dao.impl;

import Model.FinalModel;
import com.springapp.mvc.dao.ModelDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by atincu on 5/28/2014.
 */
@Repository
public class ModelDaoImpl implements ModelDao {
    @PersistenceContext
    private EntityManager entityManager;

    private String QUERY_FIND_TV_BY_NAME = "Select d from FinalModel d where d.title like :tv_title or d.overview like :tv_title or d.genre like :tv_title or d.actors like :tv_title";
    private String QUERY_FIND_ALL = "Select d from FinalModel d";


    public List<FinalModel> findAll() {
        return entityManager.createQuery(QUERY_FIND_ALL)
                .getResultList();
    }

    public List<FinalModel> find(String movieName) {
        movieName = "%"+movieName.toLowerCase()+"%";
        return entityManager.createQuery(QUERY_FIND_TV_BY_NAME)
                .setParameter("tv_title", movieName)
                .getResultList();
    }

    @Override
    public void save(FinalModel toBeSaved) {
        entityManager.persist(toBeSaved);
        entityManager.flush();
    }

    @Override
    public void remove(FinalModel toBeRemoved) {
        // To be implemented
        entityManager.remove(entityManager.merge(toBeRemoved));
        entityManager.flush();
    }

}
