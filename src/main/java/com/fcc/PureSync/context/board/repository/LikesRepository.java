package com.fcc.PureSync.context.board.repository;

import com.fcc.PureSync.context.board.entity.Board;
import com.fcc.PureSync.context.board.entity.Likes;
import com.fcc.PureSync.context.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByMemberAndBoard(Member member, Board board);


    Long countByMemberAndBoard(Member member, Board board);
}
