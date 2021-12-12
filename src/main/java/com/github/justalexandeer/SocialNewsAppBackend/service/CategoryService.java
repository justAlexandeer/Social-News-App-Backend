package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long categoryId);

    void createCategory(String categoryName);

    void changeCategory(String categoryId, String categoryName);

    void deleteCategory(String categoryId);

    List<Category> findAllCategories();

    void setCategoryToPost(String postId, String categoryId);
}
