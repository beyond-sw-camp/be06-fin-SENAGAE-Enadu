package com.example.common.Category.Repository;

import com.example.common.Category.Model.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findBySuperCategory_IdNull();
    List<Category> findBySuperCategory_Id(Long id);
    Optional<Object> findByCategoryNameIgnoreCase(String categoryName);
}
