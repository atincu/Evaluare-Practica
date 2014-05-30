package com.springapp.mvc.service.impl;

import Model.FinalModel;
import com.springapp.mvc.dao.ModelDao;
import com.springapp.mvc.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by atincu on 5/28/2014.
 */

@Service
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelDao modelDao;

    @Override
    public List<FinalModel> find(String movieName) {
        return modelDao.find(movieName);
    }

    @Override
    public List<FinalModel> findAll() {
        return modelDao.findAll();
    }

    @Override
    @Transactional
    public void save(FinalModel toBeSaved) {
        modelDao.save(toBeSaved);
    }

    @Override
    @Transactional
    public void remove(FinalModel toBeRemoved) {
        modelDao.remove(toBeRemoved);
    }

}
