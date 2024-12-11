package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.AddressRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.repository.impl.DistrictRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.repository.impl.ProvinceRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.repository.impl.WardRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.address.District;
import com.lamnguyen.mat_kinh_kimi.model.address.Province;
import com.lamnguyen.mat_kinh_kimi.model.address.Ward;

import java.util.List;

public class AddressService {
    private static AddressService instance;
    private final ProvinceRepositoryImpl PROVINCE_REPOSITORY;
    private final DistrictRepositoryImpl DISTRICT_REPOSITORY;
    private final WardRepositoryImpl WARD_REPOSITORY;
    private final AddressRepositoryImpl ADDRESS_REPOSITORY;

    private AddressService() {
        PROVINCE_REPOSITORY = ProvinceRepositoryImpl.getInstance();
        DISTRICT_REPOSITORY = DistrictRepositoryImpl.getInstance();
        WARD_REPOSITORY = WardRepositoryImpl.getInstance();
        ADDRESS_REPOSITORY = AddressRepositoryImpl.getInstance();
    }

    public static AddressService getInstance() {
        return (instance = instance == null ? new AddressService() : instance);
    }

    public List<Province> getAllProvider() {
        return PROVINCE_REPOSITORY.getAllProvince();
    }

    public List<District> getAllDistrict(int code) {
        return DISTRICT_REPOSITORY.getAllDistrict(code);
    }

    public List<Ward> getAllWard(int code) {
        return WARD_REPOSITORY.getAllWard(code);
    }

    public String getAddress(int provinceCode, int districtCode, int wardCode) {
        Province province = ADDRESS_REPOSITORY.getProvince(provinceCode);
        District district = ADDRESS_REPOSITORY.getDistrict(districtCode, provinceCode);
        Ward ward = ADDRESS_REPOSITORY.getWard(wardCode, districtCode);
        return ward.getFullName() + " - " + district.getFullName() + " - " + province.getFullName();
    }
}
