package org.example.backend.Category.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Req.AddSubCategoryReq;
import org.example.backend.Category.Model.Res.SubCategoryRes;
import org.example.backend.Category.Model.Res.SuperCategoryRes;
import org.example.backend.Category.Service.CategoryService;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/super")
    public BaseResponse<List<SuperCategoryRes>> getSuperCategory(){
        return new BaseResponse<>(categoryService.getSuperList());
    }
    @GetMapping("/sub")
    public BaseResponse<List<SubCategoryRes>> getSubCategory(Long superCategoryId){
        return new BaseResponse<>(categoryService.getSubList(superCategoryId));
    }
    @PostMapping()
    public BaseResponse<Long> addSubCategory(Principal principal, @RequestBody AddSubCategoryReq addSubCategoryReq){
        if (principal == null) {
            throw new InvalidCategoryException(BaseResponseStatus.USER_NOT_LOGIN);
        }
        return new BaseResponse<>(categoryService.createSubCategory(addSubCategoryReq));
    }
}
