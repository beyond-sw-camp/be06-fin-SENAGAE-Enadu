package org.example.backend.Category.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Res.SuperCategoryRes;
import org.example.backend.Category.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<SuperCategoryRes> getSuperList() {
        List<SuperCategoryRes> superList = new ArrayList<>();
        categoryRepository.findBySuperCategory_IdNull().forEach(category -> {
            SuperCategoryRes superCategoryRes = SuperCategoryRes.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .build();
            superList.add(superCategoryRes);
        });
        return superList;
    }
}
