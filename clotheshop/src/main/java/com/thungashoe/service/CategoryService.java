package com.thungashoe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.thungashoe.domain.entity.Category;
import com.thungashoe.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
	
	public List<Category> getParentCategories() {
        return categoryRepository.findByParentIsNull();
    }
	
	public Category getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElse(null);
    }
	
	public void addCategory(Category category) {
		category.setIsDeleted(false);
        categoryRepository.save(category);
    }
	
	public void updateCategory(Long categoryId, Category updatedCategory) {
        Category existingCategory = getCategoryById(categoryId);
        if (existingCategory != null) {
        	updatedCategory.setId(categoryId);
            categoryRepository.save(updatedCategory);
        }
    }
	
	public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        if (category != null) {
        	try {
                categoryRepository.delete(category);
        	}catch (DataIntegrityViolationException e) {
        		category.setIsDeleted(!category.getIsDeleted());
                categoryRepository.save(category);
			}
        }
    }
}
