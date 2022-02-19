package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Category;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long categoryId);

    void createCategory(String categoryName);

    void createDefaultCategory(String categoryName);

    void changeCategory(String categoryId, String categoryName);

    void deleteCategory(String categoryId);

    List<Category> findAllCategories();

    Response<List<Category>> findAllDefaultCategories();

    void setCategoryToPost(String postId, String categoryId);

}
