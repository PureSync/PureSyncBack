package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Positive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositiveRepository extends JpaRepository<Positive, Long> {
    List<Positive> findAll();
}
