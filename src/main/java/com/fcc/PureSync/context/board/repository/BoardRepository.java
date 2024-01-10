package com.fcc.PureSync.context.board.repository;

import com.fcc.PureSync.context.board.entity.Board;
import com.fcc.PureSync.context.member.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long boardSeq);

    Page<Board> findAll(Pageable pageable);

    Page<Board> findByBoardStatusNotOrderByBoardWdateDesc(Integer boardStatus,Pageable pageable);

    Long countByBoardStatusNot(Integer boardStatus);
    @Query("SELECT COUNT(a) FROM Likes a WHERE a.board = :board")
    Long countLikesByBoard(@Param("board") Board board);

    Page<Board> findAllByMemberAndBoardStatusIsNotOrderByBoardWdateDesc(Member member, Integer status, Pageable pageable);

    @Query("SELECT bd FROM Board bd JOIN Likes li ON bd.boardSeq = li.board.boardSeq WHERE li.member.memSeq = :memSeq AND bd.boardStatus <> :status ORDER BY bd.boardWdate DESC")
    Page<Board> findAllByLikes_Member_MemSeqAndBoardStatusIsNotOrderByBoardWdateDesc(@Param("memSeq") Long memSeq, @Param("status") Integer status, Pageable pageable);
}