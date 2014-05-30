package com.springapp.mvc.service;

import Model.FinalModel;

import java.util.List;

/**
 * Created by atincu on 5/28/2014.
 */
public interface ModelService {

    public List<FinalModel> find(String movieName);

    public List<FinalModel> findAll();

    public void save(FinalModel toBeSaved);

    public void remove(FinalModel toBeRemoved);
}
