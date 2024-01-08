package com.fcc.PureSync.context.member.repository;

import com.fcc.PureSync.context.member.entity.Body;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyRepository extends JpaRepository<Body, Long> {

    Optional<Body> findByMemSeq(Long memSeq);
}
