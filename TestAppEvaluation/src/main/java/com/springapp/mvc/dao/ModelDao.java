package com.springapp.mvc.dao;

import Model.FinalModel;

import java.util.List;

/**
 * Created by atincu on 5/28/2014.
 */
public interface ModelDao {

    public List<FinalModel> find(String departmentName);

    public List<FinalModel> findAll();

    public void save(FinalModel toBeSaved);

    public void remove(FinalModel toBeRemoved);
}
