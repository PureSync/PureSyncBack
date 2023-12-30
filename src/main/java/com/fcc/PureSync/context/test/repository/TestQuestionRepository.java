package com.fcc.PureSync.context.test.repository;

import com.fcc.PureSync.context.test.entity.TestQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestQuestionRepository extends JpaRepository<TestQuestion,Long> {
    List<TestQuestion> findByTestSeq(Long testSeq, Pageable pageable);
    List<TestQuestion> findByTestSeq(Long testSeq);
    List<TestQuestion> findByTestSeqOrderByQueSeqAsc(Long testSeq);
}
