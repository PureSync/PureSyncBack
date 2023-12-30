package com.fcc.PureSync.context.board.entity;

import com.fcc.PureSync.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSeq;
    private String boardName;
    private String boardContents;
    @Builder.Default
    private LocalDateTime boardWdate=LocalDateTime.now();
    @Builder.Default
    private Long boardLikescount=0L;
    @Builder.Default
    private Integer boardStatus=1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_seq")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BoardFile> boardFile;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments;


}