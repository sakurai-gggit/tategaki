package com.example.tategaki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tategaki.entity.Tanka;

@Repository
public interface TankaRepository extends JpaRepository<Tanka, Long> {

	@Query(value = "SELECT * FROM tanka WHERE category_id=:categoryId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Tanka findRandomTankaByCategory(@Param("categoryId") Long categoryId);

	@Query(value = "SELECT * FROM tanka WHERE category_id=:categoryId AND tanka_id != :currentId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Tanka findRandomTankaByCategoryExcluding(@Param("categoryId") Long categoryId, @Param("currentId") Long currentId);

	List<Tanka> findByCategory_categoryId(Long categoryId);
}
