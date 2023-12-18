package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Board;
import com.fcc.PureSync.entity.Likes;
import com.fcc.PureSync.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByMemberAndBoard(Member member, Board board);


    Long countByMemberAndBoard(Member member, Board board);
}
