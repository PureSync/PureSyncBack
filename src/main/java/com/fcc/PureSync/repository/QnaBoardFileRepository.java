package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.QnaBoardFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QnaBoardFileRepository extends JpaRepository<QnaBoardFile, Long> {
    Optional<QnaBoardFile> findByQnaBoard_QnaBoardSeq(Long aLong);

    List<QnaBoardFile> findAllByQnaBoard_QnaBoardSeq(Long aLong);

    Page<QnaBoardFile> findAllByQnaBoard_QnaBoardSeq(Long qnaBoardSeq, Pageable pageable);
}