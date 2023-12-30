package com.fcc.PureSync.context.test.repository;

import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.context.test.entity.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestAnswerRepository extends JpaRepository<TestAnswer,Long> {
    TestAnswer findByMemberAndTestSeq(Member member, int testSeq);
}
