package com.eventhub.dti.infrastructure.category.service;

import com.eventhub.dti.entity.Category;
import com.eventhub.dti.infrastructure.category.dto.CreateCategoryRequestDTO;
import com.eventhub.dti.infrastructure.category.dto.CreateCategoryResponseDTO;
import com.eventhub.dti.infrastructure.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CreateCategoryResponseDTO createCategoryResponseDTO;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository, CreateCategoryResponseDTO createCategoryResponseDTO) {
    this.categoryRepository = categoryRepository;
    this.createCategoryResponseDTO = createCategoryResponseDTO;
  }

  public List<CreateCategoryRequestDTO> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream()
      .map(createCategoryResponseDTO::toDTO)
      .toList();
  }

  public Optional<CreateCategoryRequestDTO> getCategoryById(Long id) {
    Optional<Category> category = categoryRepository.findById(id);
    return category.map(createCategoryResponseDTO::toDTO);
  }

  public CreateCategoryRequestDTO createCategory(CreateCategoryRequestDTO createCategoryRequestDTO) {
    Category category = createCategoryResponseDTO.toEntity(createCategoryRequestDTO);
    category = categoryRepository.save(category);
    return createCategoryResponseDTO.toDTO(category);
  }

  public Optional<CreateCategoryRequestDTO> updateCategory(Long id, CreateCategoryRequestDTO createCategoryRequestDTO) {
    if (categoryRepository.existsById(id)) {
      createCategoryRequestDTO.setId(id);
      Category category = createCategoryResponseDTO.toEntity(createCategoryRequestDTO);
      category = categoryRepository.save(category);
      return Optional.of(createCategoryResponseDTO.toDTO(category));
    }
    return Optional.empty();
  }

  public boolean deleteCategory(Long id) {
    if (categoryRepository.existsById(id)) {
      categoryRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
