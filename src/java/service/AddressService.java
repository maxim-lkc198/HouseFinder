/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.District;
import model.Province;
import model.Ward;

import java.util.List;

public interface AddressService {
    List<Province> getProvinces();
    List<District> getDistrictsByProvinceId(int provinceId);
    List<Ward> getWardsByDistrictId(int districtId);
    void injectAdministrativeUnitsFromJson(String jsonFilePath, String version);
}
