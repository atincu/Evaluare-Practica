package com.springapp.mvc.service.impl;

import Model.FinalModel;
import com.springapp.mvc.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by atincu on 5/28/2014.
 */

@Service
public class ModelMainService {
    @Autowired
    private ModelService modelService;

    public List<FinalModel> findModelByName(String movieName) {
        return modelService.find(movieName);
    }

    public List<FinalModel> findAllModels () {return modelService.findAll(); }

    public void insertModel(FinalModel model) {
        modelService.save(model);
    }

    public void deleteModel (FinalModel model) {modelService.remove(model);}
}
