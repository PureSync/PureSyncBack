package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.BoardFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {


    Optional<BoardFile> findByBoard_BoardSeq(Long aLong);

    List<BoardFile> findAllByBoard_BoardSeq(Long aLong);

    Page<BoardFile> findAllByBoard_BoardSeq(Long boardSeq, Pageable pageable);
}