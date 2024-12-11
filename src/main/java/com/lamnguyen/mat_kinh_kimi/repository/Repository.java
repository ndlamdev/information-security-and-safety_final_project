package com.lamnguyen.mat_kinh_kimi.repository;

import com.lamnguyen.mat_kinh_kimi.config.db.JDBIConnector;
import com.lamnguyen.mat_kinh_kimi.model.CategoryGroup;
import org.jdbi.v3.core.Jdbi;

public abstract class Repository {
    protected Jdbi connector;

    public Repository() {
        connector = JDBIConnector.get();
    }

    private void registerMapper() {
        connector.registerRowMapper(CategoryGroup.class,
                    (rs, ctx) -> {
                    return CategoryGroup.builder()
                            .id(rs.getInt("CategoryGroup_id"))
                            .name(rs.getString("CategoryGroup_name"))
                            .build();
                });
    }
}
