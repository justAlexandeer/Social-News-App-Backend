package com.github.justalexandeer.SocialNewsAppBackend.api;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Category;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.service.CategoryService;
import com.github.justalexandeer.SocialNewsAppBackend.util.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;
    private final PermissionManager permissionManager;

    @Autowired
    public CategoryController(CategoryService categoryService, PermissionManager permissionManager) {
        this.categoryService = categoryService;
        this.permissionManager = permissionManager;
    }

    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> listOfCategory = categoryService.findAllCategories();
        return new ResponseEntity<>(listOfCategory, HttpStatus.OK);
    }

    @GetMapping("/getDefaultCategories")
    public ResponseEntity<Response<List<Category>>> getDefaultCategories() {
        return new ResponseEntity<>(categoryService.findAllDefaultCategories(), HttpStatus.OK);
    }

    @GetMapping("/setCategoryToPost")
    public ResponseEntity<Void> setCategoryToPost(
            HttpServletRequest request,
            @RequestParam(value = "postId") String postId,
            @RequestParam(value = "categoryId") String categoryId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(permissionManager.havePermissionToChangePost(authorizationHeader, postId)) {
            categoryService.setCategoryToPost(postId, categoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/createCategory")
    public ResponseEntity<Void> createCategory(
            HttpServletRequest request,
            @RequestParam(value = "categoryName") String categoryName
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.isAdmin(authorizationHeader)) {
            categoryService.createCategory(categoryName);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/changeCategory")
    public ResponseEntity<Void> changeCategory(
            HttpServletRequest request,
            @RequestParam(value = "categoryId") String categoryId,
            @RequestParam(value = "categoryName") String categoryName
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.isAdmin(authorizationHeader)) {
            categoryService.changeCategory(categoryId, categoryName);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/deleteCategory")
    public ResponseEntity<Void> deleteCategory(
            HttpServletRequest request,
            @RequestParam(value = "categoryId") String categoryId
    ) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (permissionManager.isAdmin(authorizationHeader)) {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
