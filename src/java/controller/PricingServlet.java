package controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.PostPricing;
import service.PostPricingService;
import service.impl.PostPricingServiceImpl;

@WebServlet("/api/pricing")
public class PricingServlet extends HttpServlet {
    private final PostPricingService pricingService = new PostPricingServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();
        List<PostPricing> pricingList = pricingService.getAllPricing();
        System.out.println("PricingServlet: Returning pricing data: " + pricingList);
        out.print(gson.toJson(pricingList));
        out.flush();
    }
}