/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:59 AM - 11/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.CategoryGroup;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;

import java.util.List;

public class GroupCategoryRepositoryImpl extends Repository {
    private static GroupCategoryRepositoryImpl instance;

    private GroupCategoryRepositoryImpl() {
    }

    public static GroupCategoryRepositoryImpl getInstance() {
        return (instance = instance == null ? new GroupCategoryRepositoryImpl() : instance);
    }

    public List<CategoryGroup> findAll() {
        return connector.withHandle(handle -> {
            String sql = """
                         SELECT\s
                             g.id AS CategoryGroup_id,
                             g.name AS CategoryGroup_name,
                             c.id AS Category_id,
                             c.name AS Category_name
                         FROM\s
                             category_groups g
                         LEFT JOIN\s
                             categories c ON g.id = c.categoryGroupId
                    \s""";
            return handle.createQuery(sql)
                    .mapTo(CategoryGroup.class)
                    .list();
        });
    }
}
