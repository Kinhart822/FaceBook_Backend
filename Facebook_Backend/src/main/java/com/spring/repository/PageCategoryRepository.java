package com.spring.repository;

import com.spring.entities.PageCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageCategoryRepository extends CrudRepository<PageCategory, Integer> {
}
