package fr.manu.courses.services;

import fr.manu.courses.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    Category addCategory(Category category);

    Page<Category> getCategoriesPageable(Pageable pageable);
}
