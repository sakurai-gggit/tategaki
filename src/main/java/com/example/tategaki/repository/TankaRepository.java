package com.example.tategaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tategaki.entity.Tanka;

@Repository
public interface TankaRepository extends JpaRepository<Tanka, Long> {

}
