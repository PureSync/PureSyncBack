package com.fcc.PureSync.context.menu.dto;

import com.fcc.PureSync.context.menu.entity.Menu;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {
    private Long menuSeq;
    private String menuDate;
    private Integer menuWhen;
    private Double menuGram;
    @Builder.Default
    private LocalDateTime menuWdate = LocalDateTime.now();
    private Long memSeq;
    private Long foodSeq;

    public static MenuResponseDto toDto(Menu menu) {
        return MenuResponseDto.builder()
                .menuSeq(menu.getMenuSeq())
                .menuDate(menu.getMenuDate())
                .menuWhen(menu.getMenuWhen())
                .menuGram(menu.getMenuGram())
                .menuWdate(menu.getMenuWdate())
                .memSeq(menu.getMember().getMemSeq())
                .foodSeq(menu.getFood().getFoodSeq())
                .build();
    }

}
