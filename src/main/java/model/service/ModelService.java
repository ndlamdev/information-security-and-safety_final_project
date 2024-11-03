package model.service;

import model.DAO.ModelDAO;
import model.bean.Model;

import java.util.List;

public class ModelService {
    private static ModelService instance;

    public static ModelService getInstance() {
        return instance == null ? new ModelService() : instance;
    }

    public List<Model> getModelsByProductId(int productId) {
        return ModelDAO.getInstance().getModelsByProductId(productId);
    }

    public Model getModel(int modelId) {
        return ModelDAO.getInstance().getModel(modelId);
    }

    public Model getModelForCart(int modelId) {
        return ModelDAO.getInstance().getModel(modelId);
    }

    public boolean insert(int productId, List<Model> models) {
        boolean result = true;
        for (Model model : models) {
            model.setProductId(productId);
            result &= ModelDAO.getInstance().insert(model) == 1 ? true : false;
        }
        return result;
    }

    public boolean update(int productId, List<Model> models) {
        ModelDAO.getInstance().removeByProductId(productId);
        return insert(productId, models);
    }
}
