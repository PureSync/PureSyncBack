package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.TestQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestQuestionRepository extends JpaRepository<TestQuestion,Long> {
    List<TestQuestion> findByTestSeq(Long testSeq, Pageable pageable);
    List<TestQuestion> findByTestSeq(Long testSeq);
    List<TestQuestion> findByTestSeqOrderByQueSeqAsc(Long testSeq);
}
