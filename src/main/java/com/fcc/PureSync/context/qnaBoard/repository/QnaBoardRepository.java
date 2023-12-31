package com.fcc.PureSync.context.qnaBoard.repository;

import com.fcc.PureSync.context.qnaBoard.entity.QnaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnaBoardRepository extends JpaRepository<QnaBoard, Long> {
    Optional<QnaBoard> findById(Long qnaBoardSeq);

    Page<QnaBoard> findAll(Pageable pageable);

    Page<QnaBoard> findByQnaBoardStatusNotOrderByQnaBoardWdateDesc(Integer qnaBoardStatus, Pageable pageable);

    Long countByQnaBoardStatusNot(Integer qnaBoardStatus);
}