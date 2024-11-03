package model.DAO;

import model.bean.address.Ward;

import java.util.List;

public class WardDAO extends DAO{
    public List<Ward> getAllWard(int code) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT w.code, w.fullName " +
                                "FROM `dia_chi`.wards AS w " +
                                "WHERE w.districtCode = ?;")
                        .bind(0, code)
                        .mapToBean(Ward.class)
                        .list()
        );
    }
}
