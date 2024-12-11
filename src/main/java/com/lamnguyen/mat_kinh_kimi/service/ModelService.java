package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.ModelRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.Model;

import java.util.List;

public class ModelService {
    private static ModelService instance;
    private final ModelRepositoryImpl MODEL_REPOSITORY;

    private ModelService() {
        MODEL_REPOSITORY = ModelRepositoryImpl.getInstance();
    }

    public static ModelService getInstance() {
        return (instance = instance == null ? new ModelService() : instance);
    }

    public List<Model> getModelsByProductId(int productId) {
        return MODEL_REPOSITORY.getModelsByProductId(productId);
    }

    public Model getModel(int modelId) {
        return MODEL_REPOSITORY.getModel(modelId);
    }

    public Model getModelForCart(int modelId) {
        return MODEL_REPOSITORY.getModel(modelId);
    }

    public boolean insert(int productId, List<Model> models) {
        boolean result = true;
        for (Model model : models) {
            model.setProductId(productId);
            result &= MODEL_REPOSITORY.insert(model) == 1 ? true : false;
        }
        return result;
    }

    public boolean update(int productId, List<Model> models) {
        MODEL_REPOSITORY.removeByProductId(productId);
        return insert(productId, models);
    }
}
