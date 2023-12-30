package com.fcc.PureSync.context.board.repository;

import com.fcc.PureSync.context.board.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCmtStatusNotOrderByCmtWdateDesc(Pageable pageable, Integer cmtStatus);

}