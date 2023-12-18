package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.QnaComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {
    List<QnaComment> findByOrderByQnaCmtWdateDesc(Pageable pageable);
}