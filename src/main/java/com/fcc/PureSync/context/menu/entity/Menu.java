package com.fcc.PureSync.context.menu.entity;

import com.fcc.PureSync.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//매개변수가 없는 생성자를 PROTECED  접근권한으로 만들어라  == 직접객체생성 하지 마라
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name="tb_menu_list")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuSeq;
    private String menuDate;
    private Integer menuWhen;
    private Double menuGram;
    @Builder.Default
    private LocalDateTime menuWdate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_seq")
    Member member;

    @ManyToOne()
    @JoinColumn(name = "food_seq")
    Food food;
    //    private Long memSeq;
    //      private Long foodSeq;



}
