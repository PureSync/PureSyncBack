package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Board;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.entity.Likes;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long boardSeq);

    Page<Board> findAll(Pageable pageable);

    Page<Board> findByBoardStatusOrderByBoardWdateDesc(Integer boardStatus,Pageable pageable);

    @Query("SELECT COUNT(a) FROM Likes a WHERE a.board = :board")
    Long countLikesByBoard(@Param("board") Board board);

    List<Board> findAllByMemberAndBoardStatusOrderByBoardWdateDesc(Member member, Integer status);

    @Query("SELECT bd FROM Board bd JOIN Likes li ON bd.boardSeq = li.board.boardSeq WHERE li.member.memSeq = :memSeq " +
            "order by bd.boardWdate desc")
    List<Board> findBoardsByMemberLikes(@Param("memSeq") Long memSeq);
}