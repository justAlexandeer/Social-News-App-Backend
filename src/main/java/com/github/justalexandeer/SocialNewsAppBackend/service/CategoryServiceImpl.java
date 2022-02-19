package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Category;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Post;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.repository.CategoryRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.getById(categoryId);
    }

    @Override
    public void createCategory(String categoryName) {
        Category category = new Category(null, categoryName, false);
        categoryRepository.save(category);
    }

    @Override
    public void createDefaultCategory(String categoryName) {
        Category category = new Category(null, categoryName, true);
        categoryRepository.save(category);
    }

    @Override
    public void changeCategory(String categoryId, String categoryName) {
        Category category = categoryRepository.getById(Long.valueOf(categoryId));
        category.setName(categoryName);
    }

    @Override
    public void deleteCategory(String categoryId) {
        deleteCategoryFromAllPosts(Long.valueOf(categoryId));
        categoryRepository.deleteById(Long.valueOf(categoryId));
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Response<List<Category>> findAllDefaultCategories() {
        return new Response<>("success", null, categoryRepository.findAllByIsDefaultTrue());
    }

    @Override
    public void setCategoryToPost(String postId, String categoryId) {
        Post post = postRepository.getById(Long.valueOf(postId));
        Category category = categoryRepository.getById(Long.valueOf(categoryId));
        post.setCategory(category);
    }

    private void deleteCategoryFromAllPosts(Long categoryId) {
        List<Post> listOfPostsWithThisCategory = postRepository.findAllByCategoryId(categoryId);
        listOfPostsWithThisCategory.forEach(post -> {
            post.setCategory(null);
        });
    }
}
