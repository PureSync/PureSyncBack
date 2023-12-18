package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCmtStatusOrderByCmtWdateDesc(Pageable pageable, Integer cmtStatus);

}