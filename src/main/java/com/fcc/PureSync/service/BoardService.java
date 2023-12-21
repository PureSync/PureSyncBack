package com.fcc.PureSync.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fcc.PureSync.dto.BoardDto;
import com.fcc.PureSync.dto.BoardFileDto;
import com.fcc.PureSync.dto.CommentDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.entity.Board;
import com.fcc.PureSync.entity.BoardFile;
import com.fcc.PureSync.entity.Comment;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.BoardFileRepository;
import com.fcc.PureSync.repository.BoardRepository;
import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.util.FileUploadUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.fcc.PureSync.dto.BoardDto.toDto;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    @Value("${fileUploadPath}")
    String fileUploadPath;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ResultDto buildResultDto(int code, HttpStatus status, String msg, HashMap<String, Object> map) {
        return ResultDto.builder()
                .code(code)
                .httpStatus(status)
                .message(msg)
                .data(map)
                .build();
    }

    /**
     * boardStatus가 false면 NOT_FOUND_BOARD
     */
    private void boardStatusChk(Board board) {
        if (board.getBoardStatus() == 0) {
            throw new CustomException(CustomExceptionCode.ALREADY_DELETED_ARTICLE);
        }
    }

    @Transactional
    public ResultDto createBoard(BoardDto boardDto, List<MultipartFile> file,String id) {
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        System.out.println("파일 정보 확인: " + file);
        System.out.println("*******************" + boardDto.getBoardName());
        System.out.println("*******************" + boardDto.getBoardContents());
        Board board = Board.builder()
                .boardName(boardDto.getBoardName())
                .boardContents(boardDto.getBoardContents())
                .member(member)
                .build();

        boardRepository.save(board);
        System.out.println("파일존재여부 : " + file);
        Long board_seq = board.getBoardSeq();
        System.out.println("board_seq : " + board_seq);
        HashMap<String, Object> map = new HashMap<>();
        /**
         * 파일 존재 o
         */
        if (file != null && !file.isEmpty()) {
            System.out.println("********************************************");

            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();


            file.forEach(fileSave -> {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(fileSave.getContentType());
                objectMetadata.setContentLength(fileSave.getSize());

                String originalFilename = fileSave.getOriginalFilename();

                int index = 0;
                // file 형식이 잘못된 경우를 확인
                try {
                    assert originalFilename != null;
                    index = originalFilename.lastIndexOf(".");
                } catch (StringIndexOutOfBoundsException e) {
                    //throw new
                }
                String ext = originalFilename.substring(index + 1);
                String storedFileName = UUID.randomUUID() + "." + ext;

                // 저장할 디렉토리 경로 + 파일 이름
                String key = "fileUpload/" + storedFileName;

                try (InputStream inputStream = fileSave.getInputStream()) {
                    amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
                    //throw new
                }

                String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();

                BoardFile boardFile = BoardFile.builder()
                        .boardfileName(storedFileName)
                        .fileUrl(storeFileUrl)
                        .board(board)
                        .boardfileSize(fileSave.getSize())
                        .build();

                boardFileRepository.save(boardFile);
                storedFileNameList.add(storedFileName);
                originalFileNameList.add(originalFilename);

                BoardFileDto boardFileDto = BoardFileDto.toDto(boardFile);
                BoardDto boardDtoResult = toDto(board);

                map.put("board", boardDtoResult);
                map.put("boardFile", storedFileNameList);

            });
            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 생성 성공", map);
        } else {
            /**
             * 파일 존재 x
             */
            System.out.println("파일 존재 x");
            BoardDto boardDtoResult = toDto(board);

            map.put("board", boardDtoResult);

            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 생성 성공", map);
        }
    }

    public ResultDto updateBoard(Long boardSeq, BoardDto boardDto, List<MultipartFile> file,String id) throws IOException {

        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        boardStatusChk(board);


        Board updatedBoard = Board.builder()
                .boardSeq(board.getBoardSeq())
                .boardName(boardDto.getBoardName())
                .boardContents(boardDto.getBoardContents())
                .boardWdate(board.getBoardWdate())
                .member(member)
                .build();

        boardRepository.save(updatedBoard);
        HashMap<String, Object> map = new HashMap<>();
        /**
         * 파일 존재 o
         */
        if (file != null) {
            List<BoardFile> filesToDelete = boardFileRepository.findAllByBoard_BoardSeq(boardSeq);
            for (BoardFile fileToDelete : filesToDelete) {
                deleteFileFromS3(fileToDelete.getBoardfileName());
                boardFileRepository.delete(fileToDelete);
            }

            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();


            file.forEach(fileSave -> {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(fileSave.getContentType());
                objectMetadata.setContentLength(fileSave.getSize());

                String originalFilename = fileSave.getOriginalFilename();

                int index = 0;
                // file 형식이 잘못된 경우를 확인
                try {
                    assert originalFilename != null;
                    index = originalFilename.lastIndexOf(".");
                } catch (StringIndexOutOfBoundsException e) {
                    //throw new
                }

                String ext = originalFilename.substring(index + 1);

                // 저장될 파일 이름
                String storedFileName = UUID.randomUUID() + "." + ext;

                // 저장할 디렉토리 경로 + 파일 이름
                String key = "fileUpload/" + storedFileName;

                try (InputStream inputStream = fileSave.getInputStream()) {
                    amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
                    //throw new
                }

                String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();

                BoardFile boardFile = BoardFile.builder()
                        .boardfileName(storedFileName)
                        .fileUrl(storeFileUrl)
                        .board(board)
                        .boardfileSize(fileSave.getSize())
                        .build();

                boardFileRepository.save(boardFile);
                storedFileNameList.add(storedFileName);
                originalFileNameList.add(originalFilename);

                BoardFileDto boardFileDto = BoardFileDto.toDto(boardFile);
                BoardDto boardDtoResult = toDto(board);

                map.put("board", boardDtoResult);
                map.put("boardFile", storedFileNameList);

            });
            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 수정 성공", map);
        } else {
            /**
             * 파일 존재 x
             */

            map.put("updatedBoard", toDto(updatedBoard));

            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 수정 성공", map);
        }

    }

    public ResultDto deleteBoard(Long boardSeq, String id) {
        //id = "aaa";//////////////////////////////////////////////
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        boardStatusChk(board);

        Board updatedBoard = Board.builder()
                .boardSeq(board.getBoardSeq())
                .boardName(board.getBoardName())
                .boardContents(board.getBoardContents())
                .boardWdate(board.getBoardWdate())
                .boardStatus(0)
                .member(member)
                .build();

        boardRepository.save(updatedBoard);

        HashMap<String, Object> map = new HashMap<>();
        map.put("updatedBoard", toDto(updatedBoard));
        return ResultDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시판 삭제 성공")
                .data(map)
                .build();
    }
    @Transactional(readOnly = true)
    public ResultDto detailBoard(Long boardSeq, String id) {
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        BoardDto boardDetailDto = BoardDto.BoardDetailDto(board);


        HashMap<String, Object> map = new HashMap<>();
        map.put("boardDetailDto", boardDetailDto);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "게시판 조회 성공", map);
    }

    //쿼리문 수정 필요. N+1 문제
    public ResultDto findAllBoard(Pageable pageable, String id) {
        List<Board> boardPage = boardRepository.findByBoardStatusNotOrderByBoardWdateDesc(0, pageable).getContent();
        List<BoardDto> boardDetailDtoList = boardPage.stream()
                .map(BoardDto::BoardAllDetailDto)
                .toList();
        Long totalPost = boardRepository.countByBoardStatusNot(0);
        int pageSize = pageable.getPageSize();
        long totalPages = (totalPost + pageSize - 1) / pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("boardPage", boardDetailDtoList);
        map.put("totalPages",totalPages);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "게시판 전체 조회 성공", map);
    }


    public ResultDto findFileChk(Long boardSeq, Pageable pageable) {
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_BOARD));
        Long boardId = board.getBoardSeq();
        List<BoardFile> boardFile = boardFileRepository.findAllByBoard_BoardSeq(boardId, pageable).getContent();
        List<BoardFileDto> boardFileDtoList = boardFile.stream()
                .map(BoardFileDto::toDto)
                .toList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("findBoardFile", boardFileDtoList);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "게시판 파일 조회 성공", map);
    }

    // S3에서 파일 삭제 메소드
    private void deleteFileFromS3(String fileName) {
        try {
            amazonS3Client.deleteObject(bucket, "fileUpload/" + fileName);
        } catch (AmazonServiceException e) {
            // 파일 삭제 실패 처리 로직 추가
        }
    }
}