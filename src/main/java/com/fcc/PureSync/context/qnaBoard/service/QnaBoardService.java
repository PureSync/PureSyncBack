package com.fcc.PureSync.context.qnaBoard.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fcc.PureSync.context.qnaBoard.dto.QnaBoardDto;
import com.fcc.PureSync.context.qnaBoard.dto.QnaBoardFileDto;
import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.qnaBoard.entity.QnaBoard;
import com.fcc.PureSync.context.qnaBoard.entity.QnaBoardFile;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.context.qnaBoard.repository.QnaBoardFileRepository;
import com.fcc.PureSync.context.qnaBoard.repository.QnaBoardRepository;
import com.fcc.PureSync.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.fcc.PureSync.context.qnaBoard.dto.QnaBoardDto.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaBoardService {

    private final QnaBoardRepository qnaBoardRepository;
    private final QnaBoardFileRepository qnaBoardFileRepository;
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
     * qnaBoardStatus가 false면 NOT_FOUND_BOARD
     */
    private void qnaBoardStatusChk(QnaBoard qnaBoard) {
        if (qnaBoard.getQnaBoardStatus() == 0) {
            throw new CustomException(CustomExceptionCode.ALREADY_DELETED_ARTICLE);
        }
    }

    public ResultDto createQnaBoard(String memId, QnaBoardDto qnaBoardDto, List<MultipartFile> file) {
        Member member = memberRepository.findByMemId(memId)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        System.out.println("*******************" + qnaBoardDto.getQnaBoardName());
        System.out.println("*******************" + qnaBoardDto.getQnaBoardContents());
        QnaBoard qnaBoard = QnaBoard.builder()
                .qnaBoardName(qnaBoardDto.getQnaBoardName())
                .qnaBoardContents(qnaBoardDto.getQnaBoardContents())
                .member(member)
                .build();

        qnaBoardRepository.save(qnaBoard);

        Long qna_board_seq = qnaBoard.getQnaBoardSeq();
        System.out.println("qna_board_seq : " + qna_board_seq);
        HashMap<String, Object> map = new HashMap<>();
        /**
         * 파일 존재 o
         */
        if (file != null) {
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

                // 저장될 파일 이름
                String storedFileName = UUID.randomUUID() + "." + ext;

                // 저장할 디렉토리 경로 + 파일 이름
                String key = "qnaBoardImage/" + storedFileName;

                try (InputStream inputStream = fileSave.getInputStream()) {
                    amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
                    //throw new
                }

                String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();

                QnaBoardFile qnaBoardFile = QnaBoardFile.builder()
                        .qnaBoardFileName(storedFileName)
                        .fileUrl(storeFileUrl)
                        .qnaBoard(qnaBoard)
                        .qnaBoardFileSize(fileSave.getSize())
                        .build();

                qnaBoardFileRepository.save(qnaBoardFile);
                storedFileNameList.add(storedFileName);
                originalFileNameList.add(originalFilename);

                QnaBoardFileDto qnaBoardFileDto = QnaBoardFileDto.toDto(qnaBoardFile);
                QnaBoardDto qnaBoardDtoResult = toDto(qnaBoard);

                map.put("qnaBoard", qnaBoardDtoResult);
                map.put("qnaBoardFile", storedFileNameList);

            });
            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 생성 성공", map);
        } else {
            /**
             * 파일 존재 x
             */
            System.out.println("222222222222222222222222222222");
            QnaBoardDto qnaBoardDtoResult = toDto(qnaBoard);

            map.put("qnaBoard", qnaBoardDtoResult);

            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 생성 성공", map);
        }
    }

    public ResultDto updateQnaBoard(String memId, Long qnaBoardSeq, QnaBoardDto qnaBoardDto, List<MultipartFile> file) throws IOException {
        Member member = memberRepository.findByMemId(memId)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        QnaBoard qnaBoard = qnaBoardRepository.findById(qnaBoardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        qnaBoardStatusChk(qnaBoard);

        QnaBoard updatedQnaBoard = QnaBoard.builder()
                .qnaBoardSeq(qnaBoard.getQnaBoardSeq())
                .qnaBoardName(qnaBoardDto.getQnaBoardName())
                .qnaBoardContents(qnaBoardDto.getQnaBoardContents())
                .qnaBoardWdate(qnaBoard.getQnaBoardWdate())
                .member(member)
                .build();

        qnaBoardRepository.save(updatedQnaBoard);
        HashMap<String, Object> map = new HashMap<>();
        /**
         * 파일 존재 o
         */
        if (file != null) {
            List<QnaBoardFile> filesToDelete = qnaBoardFileRepository.findAllByQnaBoard_QnaBoardSeq(qnaBoardSeq);
            for (QnaBoardFile fileToDelete : filesToDelete) {
                deleteFileFromS3(fileToDelete.getQnaBoardFileName());
                qnaBoardFileRepository.delete(fileToDelete);
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
                String key = "qnaBoardImage/" + storedFileName;

                try (InputStream inputStream = fileSave.getInputStream()) {
                    amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
                    //throw new
                }

                String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();

                QnaBoardFile qnaBoardFile = QnaBoardFile.builder()
                        .qnaBoardFileName(storedFileName)
                        .fileUrl(storeFileUrl)
                        .qnaBoard(qnaBoard)
                        .qnaBoardFileSize(fileSave.getSize())
                        .build();

                qnaBoardFileRepository.save(qnaBoardFile);
                storedFileNameList.add(storedFileName);
                originalFileNameList.add(originalFilename);

                QnaBoardFileDto qnaBoardFileDto = QnaBoardFileDto.toDto(qnaBoardFile);
                QnaBoardDto qnaBoardDtoResult = toDto(qnaBoard);

                map.put("qnaBoard", qnaBoardDtoResult);
                map.put("qnaBoardFile", storedFileNameList);

            });
            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 수정 성공", map);
        } else {
            /**
             * 파일 존재 x
             */

            map.put("updatedQnaBoard", toDto(updatedQnaBoard));

            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "게시판 수정 성공", map);
        }

    }

    public ResultDto deleteQnaBoard(String memId, Long qnaBoardSeq) {
        Member member = memberRepository.findByMemId(memId)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        QnaBoard qnaBoard = qnaBoardRepository.findById(qnaBoardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        qnaBoardStatusChk(qnaBoard);

        QnaBoard updatedQnaBoard = QnaBoard.builder()
                .qnaBoardSeq(qnaBoard.getQnaBoardSeq())
                .qnaBoardName(qnaBoard.getQnaBoardName())
                .qnaBoardContents(qnaBoard.getQnaBoardContents())
                .qnaBoardWdate(qnaBoard.getQnaBoardWdate())
                .qnaBoardStatus(0)
                .member(member)
                .build();

        qnaBoardRepository.save(updatedQnaBoard);

        HashMap<String, Object> map = new HashMap<>();
        map.put("updatedQnaBoard", toDto(updatedQnaBoard));
        return ResultDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시판 삭제 성공")
                .data(map)
                .build();
    }
    @Transactional(readOnly = true)
    public ResultDto detailQnaBoard(String memId, Long qnaBoardSeq) {
        QnaBoard qnaBoard = qnaBoardRepository.findById(qnaBoardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        QnaBoardDto qnaBoardDetailDto = QnaBoardDto.QnaBoardDetailDto(qnaBoard);

        HashMap<String, Object> map = new HashMap<>();
        map.put("qnaBoardDetailDto", qnaBoardDetailDto);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "게시판 조회 성공", map);
    }

    //n+1 문제 쿼리문 똑같은 것 반복
    public ResultDto findAllQnaBoard(String memId, Pageable pageable) {
        List<QnaBoard> qnaBoardPage = qnaBoardRepository.findByQnaBoardStatusOrderByQnaBoardWdateDesc(1, pageable).getContent();
        List<QnaBoardDto> qnaBoardDetailDtoList = qnaBoardPage.stream()
                .map(QnaBoardDto::QnaBoardAllDetailDto)
                .toList();
        Long totalPost = qnaBoardRepository.countByQnaBoardStatusNot(0);
        int pageSize = pageable.getPageSize();
        long totalPages = (totalPost + pageSize - 1) / pageSize;
        HashMap<String, Object> map = new HashMap<>();
        map.put("qnaBoardPage", qnaBoardDetailDtoList);
        map.put("totalPages",totalPages);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "게시판 전체 조회 성공", map);
    }

    public ResultDto findFileChk(String memId, Long qnaBoardSeq, Pageable pageable) {
        QnaBoard qnaBoard = qnaBoardRepository.findById(qnaBoardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_BOARD));
        Long qnaBoardId = qnaBoard.getQnaBoardSeq();
        List<QnaBoardFile> qnaBoardFile = qnaBoardFileRepository.findAllByQnaBoard_QnaBoardSeq(qnaBoardId, pageable).getContent();
        List<QnaBoardFileDto> qnaBoardFileDtoList = qnaBoardFile.stream()
                .map(QnaBoardFileDto::toDto)
                .toList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("findQnaBoardFile", qnaBoardFileDtoList);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "게시판 파일 조회 성공", map);
    }

    // S3에서 파일 삭제 메소드
    private void deleteFileFromS3(String fileName) {
        try {
            amazonS3Client.deleteObject(bucket, "qnaBoardImage/" + fileName);
        } catch (AmazonServiceException e) {
            // 파일 삭제 실패 처리 로직 추가
        }
    }
}