package com.app.service;

import java.util.List;

import com.app.pojos.Category;

public interface ICategoryService {

	Category addOrEditCategory(Category cat);

	List<Category> getAllCategories();

	String deleteCategoryById(Integer cid);

	Category findByName(String categoryName);

}
