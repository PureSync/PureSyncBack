package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Body;
import com.fcc.PureSync.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyRepository extends JpaRepository<Body, Long> {

    Optional<Body> findByMemSeq(Long memSeq);
}
