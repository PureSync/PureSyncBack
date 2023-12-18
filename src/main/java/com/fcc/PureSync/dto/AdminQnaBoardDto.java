package com.fcc.PureSync.dto;


import com.fcc.PureSync.common.BaseDto;
import lombok.*;

@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AdminQnaBoardDto extends BaseDto {
    // 유저 보드
    private Long qna_board_seq;
    private String qna_board_name;  // 제목
    private String qna_board_contents;
    private String qna_board_wdate;
    private Integer qna_board_status;
    private Long mem_seq;
    private String mem_id;
    private String mem_img;

    private String qna_board_status_text;

    // 유저 댓글
    private Long qna_cmt_seq;
    private String qna_cmt_writer;
    private String qna_cmt_contents;
    private String qna_cmt_wdate;

    // 유저 파일
    private Long qna_board_file_seq;
    private String qna_board_file_name;
    private String file_url;
}
