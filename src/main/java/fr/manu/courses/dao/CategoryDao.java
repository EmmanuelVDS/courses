package fr.manu.courses.dao;

import fr.manu.courses.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {

    Page<Category> findAll(Pageable pageable);
}
