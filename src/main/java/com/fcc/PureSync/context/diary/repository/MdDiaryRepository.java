package com.fcc.PureSync.context.diary.repository;

import com.fcc.PureSync.context.diary.entity.MdDiary;
import com.fcc.PureSync.context.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MdDiaryRepository extends JpaRepository <MdDiary, Long> {

    @EntityGraph(attributePaths = {"emotion"})
    List<MdDiary> findAllByMemberOrderByDyDateDescDyWdateDesc(Member member, Pageable pageable);

}
