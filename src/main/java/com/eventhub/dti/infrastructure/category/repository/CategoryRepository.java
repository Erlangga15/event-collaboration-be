package com.eventhub.dti.infrastructure.category.repository;

import com.eventhub.dti.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
