/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.DataVersionDao;
import dao.DistrictDao;
import dao.ProvinceDao;
import dao.WardDao;
import model.DataVersion;
import model.District;
import model.Province;
import model.Ward;
import service.AddressService;
import util.DBContext;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {
    private ProvinceDao provinceDao;
    private DistrictDao districtDao;
    private WardDao wardDao;
    private DataVersionDao dataVersionDao;

    public AddressServiceImpl() {
        provinceDao = new ProvinceDao();
        districtDao = new DistrictDao();
        wardDao = new WardDao();
        dataVersionDao = new DataVersionDao();
    }

    @Override
    public List<Province> getProvinces() {
        return provinceDao.getAllProvinces();
    }

    @Override
    public List<District> getDistrictsByProvinceId(int provinceId) {
        return districtDao.getDistrictsByProvinceId(provinceId);
    }

    @Override
    public List<Ward> getWardsByDistrictId(int districtId) {
        return wardDao.getWardsByDistrictId(districtId);
    }

    @Override
    public void injectAdministrativeUnitsFromJson(String jsonFilePath, String version) {
        DataVersion latestVersion = dataVersionDao.getLatestVersion();
        if (latestVersion != null && version.equals(latestVersion.getVersion())) {
            return; // Skip if version already exists
        }

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Read JSON file
                InputStreamReader reader = new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream(jsonFilePath)
                );
                Type listType = new TypeToken<List<List<Object>>>() {}.getType();
                List<List<Object>> provinces = new Gson().fromJson(reader, listType);

                // Clear existing data
                wardDao.clearAll(conn);
                districtDao.clearAll(conn);
                provinceDao.clearAll(conn);

                // Inject provinces
                for (List<Object> provinceData : provinces) {
                    Province province = new Province(
                        Integer.parseInt(provinceData.get(0).toString()),
                        provinceData.get(1).toString(),
                        provinceData.get(2).toString(),
                        provinceData.get(3).toString()
                    );
                    provinceDao.insertProvince(province, conn);

                    // Inject districts
                    List<List<Object>> districts = (List<List<Object>>) provinceData.get(4);
                    for (List<Object> districtData : districts) {
                        District district = new District(
                            Integer.parseInt(districtData.get(0).toString()),
                            districtData.get(1).toString(),
                            districtData.get(2).toString(),
                            districtData.get(3).toString(),
                            province.getId()
                        );
                        districtDao.insertDistrict(district, conn);

                        // Inject wards
                        List<List<Object>> wards = (List<List<Object>>) districtData.get(4);
                        for (List<Object> wardData : wards) {
                            Ward ward = new Ward(
                                Integer.parseInt(wardData.get(0).toString()),
                                wardData.get(1).toString(),
                                wardData.get(2).toString(),
                                wardData.get(3).toString(),
                                district.getId()
                            );
                            wardDao.insertWard(ward, conn);
                        }
                    }
                }

                // Insert new version
                dataVersionDao.insertVersion(version);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("Failed to inject data from JSON: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error during data injection: " + e.getMessage());
        }
    }
}