package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.Model;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;

public class ModelRepositoryImpl extends Repository {
    private static ModelRepositoryImpl instance;

    private ModelRepositoryImpl() {
    }

    public static ModelRepositoryImpl getInstance() {
        return (instance = instance == null ? new ModelRepositoryImpl() : instance);
    }

    public List<Model> getModelsByProductId(int productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT m.id, m.urlImage, m.name, m.quantity, SUM(bd.quantity) AS totalQuantitySold "
                                + "FROM models AS m "
                                + "LEFT JOIN bill_details AS bd ON bd.modelId = m.id AND bd.productId = m.productId "
                                + "WHERE m.productId = :productId "
                                + "GROUP BY m.id, m.urlImage, m.name, m.quantity;")
                        .bind("productId", productId) // Bind giá trị cho :productId
                        .mapToBean(Model.class).list());
    }

    public Model getModel(int modelId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT m.id, m.urlImage, m.name, m.quantity, SUM(bd.quantity) AS totalQuantitySold "
                                + "FROM models AS m "
                                + "LEFT JOIN bill_details AS bd ON bd.modelId = m.id "
                                + "WHERE m.id = ?;")
                        .bind(0, modelId)
                        .mapToBean(Model.class)
                        .findFirst().orElse(null));
    }

    public int insert(Model model) {
        return connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO models(id, productId, name, urlImage, quantity) VALUES (?, ?, ?, ?, ?)")
                        .bind(0, model.getId())
                        .bind(1, model.getProductId())
                        .bind(2, model.getName())
                        .bind(3, model.getUrlImage())
                        .bind(4, model.getQuantity())
                        .execute()
        );
    }

    public int removeByProductId(int productId) {
        return connector.withHandle(handle ->
                handle.createUpdate("DELETE FROM models WHERE productId = ?")
                        .bind(0, productId)
                        .execute()
        );
    }

    public Model getModelForCart(int modelId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT m.id, m.urlImage, m.name  "
                                + "FROM models AS m "
                                + "WHERE m.id = ?;")
                        .bind(0, modelId)
                        .mapToBean(Model.class)
                        .findFirst().orElse(null));
    }
}
