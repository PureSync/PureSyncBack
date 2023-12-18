package com.fcc.PureSync.dto;

import lombok.*;

@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SummaryDto {
    private Long mem_seq;
    private String mem_gender;
    private String mem_birth;
    private Double body_weight;
    private Double body_height;
    private Double body_base;
}
