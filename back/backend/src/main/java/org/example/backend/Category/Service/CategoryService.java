package org.example.backend.Category.Service;

import lombok.RequiredArgsConstructor;
import com.example.common.Category.Model.Entity.Category;
import org.example.backend.Category.Model.Req.AddSubCategoryReq;
import org.example.backend.Category.Model.Res.SubCategoryRes;
import org.example.backend.Category.Model.Res.SuperCategoryRes;
import com.example.common.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidCategoryException;
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

    public List<SubCategoryRes> getSubList(Long superCategoryId) {
        List<SubCategoryRes> subList = new ArrayList<>();
        categoryRepository.findBySuperCategory_Id(superCategoryId).forEach(category -> {
            SubCategoryRes subCategoryRes = SubCategoryRes.builder()
                    .id(category.getId())
                    .categoryName(category.getCategoryName())
                    .build();
            subList.add(subCategoryRes);
        });
        return subList;
    }

    public Long createSubCategory(AddSubCategoryReq addSubCategoryReq) {
        if (categoryRepository.findByCategoryNameIgnoreCase(addSubCategoryReq.getCategoryName()).isPresent()) {
            throw new InvalidCategoryException(BaseResponseStatus.CATEGORY_DUPLICATE_NAME);
        }
        if (categoryRepository.findById(addSubCategoryReq.getSuperCategoryId()).isEmpty()){
            throw new InvalidCategoryException(BaseResponseStatus.CATEGORY_NOT_FOUND_SUPER_CATEGORY);
        }
        Category category = Category.builder()
                .categoryName(addSubCategoryReq.getCategoryName())
                .superCategory(Category.builder().id(addSubCategoryReq.getSuperCategoryId()).build())
                .build();
        return categoryRepository.save(category).getId();
    }
}
