package util;

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

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StandaloneInjector {
    public static void main(String[] args) {
        String jsonFilePath = "C:/Users/Administrator/Documents/NetBeansProjects/HouseFinder/resources/sorted.json";
        String version = "1.0";

        DataVersionDao dataVersionDao = new DataVersionDao();
        ProvinceDao provinceDao = new ProvinceDao();
        DistrictDao districtDao = new DistrictDao();
        WardDao wardDao = new WardDao();

        DataVersion latestVersion = dataVersionDao.getLatestVersion();
        if (latestVersion != null && version.equals(latestVersion.getVersion())) {
            System.out.println("Version " + version + " already exists, skipping injection.");
            return;
        }

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                System.out.println("Reading JSON file: " + jsonFilePath);
                FileReader reader = new FileReader(jsonFilePath);
                Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
                Map<String, Object> jsonData = new Gson().fromJson(reader, mapType);
                String jsonVersion = (String) jsonData.get("version");
                if (!version.equals(jsonVersion)) {
                    throw new IllegalArgumentException("JSON version " + jsonVersion + " does not match expected version " + version);
                }
                List<List<Object>> provinces = (List<List<Object>>) jsonData.get("provinces");

                wardDao.clearAll(conn);
                districtDao.clearAll(conn);
                provinceDao.clearAll(conn);
                System.out.println("Cleared existing data from provinces, districts, and wards.");

                for (List<Object> provinceData : provinces) {
                    Province province = new Province(
                        Integer.parseInt(provinceData.get(0).toString()),
                        provinceData.get(1).toString(),
                        provinceData.get(2).toString(),
                        provinceData.get(3).toString()
                    );
                    provinceDao.insertProvince(province, conn);
                    System.out.println("Inserted province: " + province.getName());

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
                        System.out.println("Inserted district: " + district.getName());

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
                            System.out.println("Inserted ward: " + ward.getName());
                        }
                    }
                }

                dataVersionDao.insertVersion(version);
                System.out.println("Successfully injected data for version: " + version);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                System.err.println("Error during JSON injection: " + e.getMessage());
                throw new RuntimeException("Failed to inject data from JSON: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database error during JSON injection: " + e.getMessage());
            throw new RuntimeException("Database error during data injection: " + e.getMessage());
        }
    }
}