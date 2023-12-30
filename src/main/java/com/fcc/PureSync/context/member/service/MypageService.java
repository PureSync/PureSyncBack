package com.fcc.PureSync.context.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fcc.PureSync.config.UserRoleConfig;
import com.fcc.PureSync.context.board.dto.BoardDto;
import com.fcc.PureSync.context.member.dto.MemberInfoDto;
import com.fcc.PureSync.context.member.dto.MemberInfoUpdateDto;
import com.fcc.PureSync.context.member.dto.MemberPasswordDto;
import com.fcc.PureSync.dto.*;
import com.fcc.PureSync.entity.*;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.context.board.repository.BoardRepository;
import com.fcc.PureSync.repository.BodyRepository;
import com.fcc.PureSync.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.fcc.PureSync.context.member.dto.MemberInfoDto.toDto;
import static com.fcc.PureSync.context.member.dto.MemberInfoUpdateDto.entityToDto;


@Service
@RequiredArgsConstructor
public class MypageService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BodyRepository bodyRepository;
    private final PasswordEncoder passwordEncoder;
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

    public ResultDto getMyInfo(Long memSeq) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        HashMap<String, Object> map = new HashMap<>();
        map.put("memberInfo", memberRepository.getMemberDetail(memSeq));

        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "내 정보 조회 성공", map);
    }

    public ResultDto getPosts(int pageNo, Long memSeq) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "boardWdate"));
        Page<BoardDto> postList = boardRepository.findAllByMemberAndBoardStatusIsNotOrderByBoardWdateDesc(member, 0, pageable)
                .map(BoardDto::BoardAllDetailDto);

        HashMap<String, Object> map = new HashMap<>();
        map.put("postList", postList);

        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "내 글 전체 조회 성공", map);
    }

    public ResultDto myPostsLikes(int pageNo, Long memSeq) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "boardWdate"));
        Page<BoardDto> likePostList = boardRepository.findAllByLikes_Member_MemSeqAndBoardStatusIsNotOrderByBoardWdateDesc(member.getMemSeq(), 0, pageable)
                .map(BoardDto::BoardAllDetailDto);


        HashMap<String, Object> map = new HashMap<>();
        map.put("likePostList", likePostList);

        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "좋아요 글 전체 조회 성공", map);
    }

    /*
     * 회원정보 업데이트 (기본 : 닉네임, 프로필이미지, 닉네임+프로필이미지)
     * */
    public ResultDto updateMemberInfo(Long memSeq, MemberInfoDto dto, MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        MemberInfoDto result = null;
        if (file == null || file.getOriginalFilename() == null) { // 파일 미존재 (닉네임만 업데이트 하는경우)
            System.out.println("닉네임만 업데이트 해옹~");
            map.put("result", updateOnlyNickname(member, dto));

        } else if (dto.getMemNick() == null && file != null) {  // 프로필 이미지만 업데이트 하는 경우
            System.out.println("프사만 업데이트 해옹~");
            map.put("result", updateOnlyProfileImg(member, file));

        } else if (dto.getMemNick() != null && member.getMemNick() != dto.getMemNick() && file != null) {  // 닉네임, 이미지 모두 업데이트
            System.out.println("둘다 업데이트 해옹");
            map.put("result", updateNicknameAndProfileImg(member, dto, file));
        }

        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "정보 수정 성공", map);
    }

    private MemberInfoDto updateOnlyNickname(Member member, MemberInfoDto dto) {
        member.updateNick(dto.getMemNick());
        member.updateModifyBy(member.getMemId());
        memberRepository.save(member);
        return toDto(member);
    }

    private MemberInfoDto updateOnlyProfileImg(Member member, MultipartFile file) {
        String fileName = uploadSingleFile(file);

        member.updateProfileImg(fileName);
        member.updateModifyBy(member.getMemId());
        memberRepository.save(member);

        return toDto(member);
    }

    private MemberInfoDto updateNicknameAndProfileImg(Member member, MemberInfoDto dto,  MultipartFile file) {
        String fileName = uploadSingleFile(file);

        member.updateNick(dto.getMemNick());
        member.updateProfileImg(fileName);
        member.updateModifyBy(member.getMemId());
        memberRepository.save(member);

//        return MemberInfoDto.builder()
//                .memNick(member.getMemNick())
//                .memImg(fileName)
//                .build();
        return toDto(member);
    }

    // 파일 업로드 후 파일명 리턴
    public String uploadSingleFile (MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String ext = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf(".") + 1);
        String storedFileName = null;

        if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif")) {
            storedFileName = UUID.randomUUID() + "." + ext;
            String key = "profileImg/" + storedFileName;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                // 적절하게 예외 처리
            }

        }
        return storedFileName;
    }

    // 내 신체정보 업데이트
    public ResultDto insertMemberBodyInfo(Long memSeq, MemberInfoUpdateDto dto) {
        HashMap<String, Object> map = new HashMap<>();
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        Body body = Body.builder()
                .memSeq(member.getMemSeq())
                .bodyHeight(dto.getBodyHeight())
                .bodyWeight(dto.getBodyWeight())
                .bodyWishWeight(dto.getBodyWishWeight())
                .bodyWishConscal(dto.getBodyWishConscal())
                .bodyWishBurncal(dto.getBodyWishBurncal())
                .build();

        bodyRepository.save(body);
        MemberInfoUpdateDto resultDto = entityToDto(body);
        map.put("updateInfo", resultDto);

        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "회원 신체 정보 수정 성공", map);
    }

    // 회원 비밀번호 변경
    public ResultDto updatePassword(Long memSeq, MemberPasswordDto dto) {
        HashMap<String, Object> map = new HashMap<>();
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        if (passwordEncoder.matches(dto.getOldPassword(), member.getMemPassword())) {
            // 입력한 기존 비밀번호와 DB에 있는 비밀번호가 일치할 경우
            updateTemporaryPassword(member, dto.getNewPassword());
            return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "패스워드 수정 성공", map);
        }

        throw new CustomException(CustomExceptionCode.NOT_FOUND_USER_PW);
    }

    public ResultDto deleteMember (Long memSeq){
        HashMap<String, Object> map = new HashMap<>();
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        member.updateStatus(UserRoleConfig.UserRole.DELETE.getLevel());
        memberRepository.save(member);
        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.OK, "회원 탈퇴 성공",  map);

    }
    private void updateTemporaryPassword(Member member, String newPassword) {
        member.updatePassword(passwordEncoder.encode(newPassword));
        member.updateModifyBy(member.getMemId());
        memberRepository.save(member);
    }



}