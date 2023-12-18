package com.fcc.PureSync.dto;


import com.fcc.PureSync.common.BaseDto;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoticeDto extends BaseDto {
    private Long notice_seq;
    private String notice_title;
    private String notice_contents;
    private String notice_wdate;
    private String notice_writer;


}