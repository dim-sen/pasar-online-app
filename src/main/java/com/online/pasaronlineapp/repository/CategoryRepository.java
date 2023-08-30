package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDao, Long> {

    @Query("select c from CategoryDao c where c.categoryName = :name")
    Optional<CategoryDao> findCategoryDaoByCategoryName(@Param("name") String name);
}
