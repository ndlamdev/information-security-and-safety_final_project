package model.DAO;

import model.bean.address.District;
import model.bean.address.Province;
import model.bean.address.SubAddress;
import model.bean.address.Ward;

public class AdressDAO extends DAO{
    public Province getProvince(int code){
        return connector.withHandle(handle ->
                handle.createQuery("SELECT p.code, p.fullName FROM `dia_chi`.provinces p WHERE p.code = ?")
                        .bind(0, code)
                        .mapToBean(Province.class).findFirst().orElse(null)
                );
    }
    public District getDistrict(int code, int provinceCode){
        return connector.withHandle(handle ->
                handle.createQuery("SELECT d.code, d.fullName, d.provinceCode FROM `dia_chi`.districts d WHERE d.code = ? AND d.provinceCode = ?")
                        .bind(0, code)
                        .bind(1, provinceCode)
                        .mapToBean(District.class).findFirst().orElse(null)
        );
    }
    public Ward getWard(int code, int districtCode){
        return connector.withHandle(handle ->
                handle.createQuery("SELECT w.code, w.fullName, w.districtCode FROM `dia_chi`.wards w WHERE w.code = ? AND w.districtCode = ?")
                        .bind(0, code)
                        .bind(1, districtCode)
                        .mapToBean(Ward.class).findFirst().orElse(null)
        );
    }
}
