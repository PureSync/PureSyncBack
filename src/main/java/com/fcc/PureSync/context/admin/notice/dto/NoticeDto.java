package com.fcc.PureSync.context.admin.notice.dto;


import com.fcc.PureSync.core.BaseDto;
import lombok.*;

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
    private int rnum;


}