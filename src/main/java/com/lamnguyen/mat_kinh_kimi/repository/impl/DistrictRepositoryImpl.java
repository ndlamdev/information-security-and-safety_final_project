package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.address.District;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;

public class DistrictRepositoryImpl extends Repository {
    private static DistrictRepositoryImpl instance;

    private DistrictRepositoryImpl() {
    }

    public static DistrictRepositoryImpl getInstance() {
        return (instance = instance != null ? instance : new DistrictRepositoryImpl());
    }

    public List<District> getAllDistrict(int code) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT d.code, d.fullName FROM districts AS d WHERE d.provinceCode = ?;
                                """)
                        .bind(0, code)
                        .mapToBean(District.class)
                        .list()
        );
    }
}
