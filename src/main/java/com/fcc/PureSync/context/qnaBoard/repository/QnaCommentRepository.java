package com.fcc.PureSync.context.qnaBoard.repository;

import com.fcc.PureSync.context.qnaBoard.entity.QnaComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {
    List<QnaComment> findByOrderByQnaCmtWdateDesc(Pageable pageable);
}