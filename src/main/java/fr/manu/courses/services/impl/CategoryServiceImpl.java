package fr.manu.courses.services.impl;

import fr.manu.courses.dao.CategoryDao;
import fr.manu.courses.models.Category;
import fr.manu.courses.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getCategories() {
        return categoryDao.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public Page<Category> getCategoriesPageable(Pageable pageable) {
        return categoryDao.findAll(pageable);
    }
}
