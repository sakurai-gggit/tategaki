package com.example.tategaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tategaki.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
