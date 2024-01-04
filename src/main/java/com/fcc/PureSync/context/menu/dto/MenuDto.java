package com.fcc.PureSync.context.menu.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class  MenuDto {
//    private Long menu_seq;
    private String  menu_wdate;
    private Integer menu_when;
    private Long mem_seq;
    private Long food_seq;
    private Double menu_kcal;
    private  Double menu_total;
    private String menu_date;
    private String menu_gram;
    private Long menu_seq;

    private  Double menu_total_car;
    private  Double menu_total_pro;
    private  Double menu_total_fat;
    private  Double menu_total_sugar;
    private  Double menu_total_na;
    private  Double menu_total_cal;

    private  Double when_total;


    private  String food_name;
    private  String food_car;
    private  String food_pro;
    private  String food_fat;
    private  String food_sugar;
    private  String food_na;
    private  String food_col;
    private  String food_kcal;

}
