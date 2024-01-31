package com.online.pasaronlineapp.repository;

import com.online.pasaronlineapp.domain.dao.CategoryDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDao, Long> {

    @Query("select c from CategoryDao c where c.categoryName = ?1")
    Optional<CategoryDao> findCategoryDaoByCategoryName(String name);

    @Query("select c from CategoryDao c")
    Page<CategoryDao> pageableCategory(Pageable pageable);

    @Query("select c from CategoryDao c where c.categoryName like %:keyword%")
    Page<CategoryDao> searchCategoryDaoByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
