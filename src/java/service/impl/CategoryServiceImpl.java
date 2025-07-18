package service.impl;

import dao.CategoryDao;
import model.Category;
import service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;

    public CategoryServiceImpl() {
        categoryDao = new CategoryDao();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }
}