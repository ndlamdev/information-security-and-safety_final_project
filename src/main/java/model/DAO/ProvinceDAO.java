package model.DAO;

import model.bean.address.Province;

import java.util.List;

public class ProvinceDAO extends DAO{
    public List<Province> getAllProvince(){
        return connector.withHandle(handle ->
                handle.createQuery("SELECT pr.code, pr.fullName " +
                                "FROM `dia_chi`.provinces AS pr;")
                        .mapToBean(Province.class)
                        .list()
        );
    }
}
