package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.address.Ward;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;

public class WardRepositoryImpl extends Repository {
    private static WardRepositoryImpl instance;

    private WardRepositoryImpl() {
    }

    public static WardRepositoryImpl getInstance() {
        return (instance = instance == null ? new WardRepositoryImpl() : instance);
    }

    public List<Ward> getAllWard(int code) {
        return connector.withHandle(handle ->
                handle.createQuery("""
                                SELECT w.code, w.fullName
                                FROM wards AS w
                                WHERE w.districtCode = ?;
                                """)
                        .bind(0, code)
                        .mapToBean(Ward.class)
                        .list()
        );
    }
}
