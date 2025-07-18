package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import service.AddressService;
import service.impl.AddressServiceImpl;

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    private AddressService addressService;

    @Override
    public void init() throws ServletException {
        addressService = new AddressServiceImpl();
        try {
            addressService.injectAdministrativeUnitsFromJson("resources/sorted.json", "1.0");
        } catch (Exception e) {
            throw new ServletException("Không thể inject dữ liệu: " + e.getMessage());
        }
    }
}