package com.eventhub.dti.infrastructure.category.controller;

import com.eventhub.dti.infrastructure.category.dto.CreateCategoryRequestDTO;
import com.eventhub.dti.infrastructure.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<CreateCategoryRequestDTO>> getAllCategories() {
    List<CreateCategoryRequestDTO> categories = categoryService.getAllCategories();
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreateCategoryRequestDTO> getCategoryById(@PathVariable Long id) {
    Optional<CreateCategoryRequestDTO> category = categoryService.getCategoryById(id);
    return category.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<CreateCategoryRequestDTO> createCategory(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO) {
    CreateCategoryRequestDTO createdCategory = categoryService.createCategory(createCategoryRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CreateCategoryRequestDTO> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO) {
    Optional<CreateCategoryRequestDTO> updatedCategory = categoryService.updateCategory(id, createCategoryRequestDTO);
    return updatedCategory.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    boolean isDeleted = categoryService.deleteCategory(id);
    return isDeleted ? ResponseEntity.noContent().build()
      : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
