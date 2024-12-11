package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.address.Province;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;

public class ProvinceRepositoryImpl extends Repository {
    private static ProvinceRepositoryImpl instance;

    private ProvinceRepositoryImpl() {
    }

    public static ProvinceRepositoryImpl getInstance() {
        return (instance = instance == null ? new ProvinceRepositoryImpl() : instance);
    }
    
    public List<Province> getAllProvince(){
        return connector.withHandle(handle ->
                handle.createQuery("SELECT pr.code, pr.fullName " +
                                "FROM `dia_chi`.provinces AS pr;")
                        .mapToBean(Province.class)
                        .list()
        );
    }
}
