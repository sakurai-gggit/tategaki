package com.example.tategaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tategaki.entity.Tanka;

@Repository
public interface TankaRepository extends JpaRepository<Tanka, Long> {

	@Query(value = "SELECT * FROM tanka ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Tanka findRandomTanka();

	@Query(value = "SELECT * FROM tanka WHERE id != :currentId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Tanka findRandomTankaExcluding(@Param("currentId") Long currentId);
}
