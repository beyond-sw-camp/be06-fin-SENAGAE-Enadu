package org.example.backend.Category.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Res.SuperCategoryRes;
import org.example.backend.Category.Service.CategoryService;
import org.example.backend.Common.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/super")
    public BaseResponse<List<SuperCategoryRes>> getSuperCategory(){
        categoryService.getSuperList();
        return new BaseResponse<>(categoryService.getSuperList());
    }
}
