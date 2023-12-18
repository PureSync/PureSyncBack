package com.fcc.PureSync.service;

import com.fcc.PureSync.common.constant.EmailConstant;
import com.fcc.PureSync.dto.*;
import com.fcc.PureSync.entity.Body;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.entity.MemberSearchCondition;
import com.fcc.PureSync.entity.MpMemRole;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.jwt.JwtUtil;
import com.fcc.PureSync.repository.BodyRepository;
import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.repository.MemberRoleRepository;
import com.fcc.PureSync.util.RandomStringGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.fcc.PureSync.exception.CustomExceptionCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BodyRepository bodyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;

    // 회원가입
    @Transactional(rollbackFor = Exception.class)
    public ResultDto signup(SignupDto signupDto) {
        Member inputMemberInfo = buildMemberFromSignupDto(signupDto);
        Long memSeq = memberRepository.save(inputMemberInfo).getMemSeq();
        //30초 대기 클로즈
        Body inputBody = buildBodyFromSignDtoAndSignupMember(signupDto, memSeq);
        bodyRepository.save(inputBody);
        mailService.signUpByVerificationCode(inputMemberInfo.getMemEmail());
        return handleResultDtoFromSignUp();
    }

    //회원 정보 빌드
    private Member buildMemberFromSignupDto(SignupDto dto) {
        String encoPassword = passwordEncoder.encode(dto.getMemPassword());
        return Member.builder()
                .memId(dto.getMemId())
                .memPassword(encoPassword)
                .memNick(dto.getMemNick())
                .memEmail(dto.getMemEmail())
                .memBirth(dto.getMemBirth())
                .memGender(dto.getMemGender())
                .memCreatedAt(LocalDateTime.now())
                .build();
    }

    //회원 신체 정보 빌드
    private Body buildBodyFromSignDtoAndSignupMember(SignupDto signupDto, Long memSeq) {
        return Body.builder()
                .memSeq(memSeq)
                .bodyHeight(signupDto.getBodyHeight())
                .bodyWeight(signupDto.getBodyHeight())
                .bodyWishWeight(signupDto.getBodyWishWeight())
                .bodyWishConscal(signupDto.getBodyWishConscal())
                .build();
    }

    private ResultDto handleResultDtoFromSignUp() {
        String msg = "회원가입 성공했습니다.";
        HashMap<String, Object> resultMap = new HashMap<>();
        return handleResultDto(msg, resultMap);
    }

    @Transactional
    public ResultDto login(LoginDto loginDto) {
        Member member = memberRepository.findByMemId(loginDto.getMemId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_ID));

        if (!passwordEncoder.matches(loginDto.getMemPassword(), member.getMemPassword())) {
            throw new CustomException(NOT_FOUND_USER_PW);
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemId(), loginDto.getMemPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HashMap<String, Object> accessTokenMap = new HashMap<>();
        String accessToken = jwtUtil.createToken(member);
        accessTokenMap.put("access_token", accessToken);

        return ResultDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("로그인 성공했습니다.")
                .data(accessTokenMap)
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto checkDuplicate(String field, String value) {
        ResultDto resultDto;
        switch (field) {
            case "memId":
                if (memberRepository.findByMemId(value).isEmpty()) {
                    resultDto = getResultDtoToDuplicate("사용가능한 아이디입니다.");
                } else {
                    throw new CustomException(ALREADY_EXIST_ID);
                }
                break;
            case "memNick":
                if (memberRepository.findByMemNick(value).isEmpty()) {
                    resultDto = getResultDtoToDuplicate("사용가능한 닉네임입니다.");
                } else {
                    throw new CustomException(ALREADY_EXIST_NICK);
                }
                break;
            case "memEmail":
                if (memberRepository.findByMemEmail(value).isEmpty()) {
                    resultDto = getResultDtoToDuplicate("사용가능한 이메일입니다.");
                } else {
                    throw new CustomException(ALREADY_EXIST_EMAIL);
                }
                break;
            default:
                throw new CustomException(BAD_REQUEST);
        }
        return resultDto;
    }


    @Transactional
    public ResultDto searchPassword(FindPasswordDto findPasswordDto) {
        Member member = memberRepository.findByMemEmail(findPasswordDto.getMemEmail()).orElseThrow(() -> new CustomException(NOT_FOUND_EMAIL));
        String newPassword = RandomStringGenerator.generateRandomPassword(12);
        updateTemporaryPassword(member, newPassword);
        mailService.sendTemporaryPassword(findPasswordDto.getMemEmail(), newPassword);
        return handleResultDtoFromFindPassword();
    }


    private ResultDto handleResultDtoFromFindPassword() {
        String msg = "임시 메일을 전송했습니다.";
        HashMap<String, Object> resultMap = new HashMap<>();
        return handleResultDto(msg, resultMap);
    }

    @Transactional
    private void updateTemporaryPassword(Member member, String newPassword) {
        member.updatePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public ResultDto searchId(String memEmail) {
        Member member = memberRepository.findByMemEmail(memEmail).orElseThrow(() -> new CustomException(NOT_FOUND_EMAIL));
        String memberId = member.getMemId();
        return handleResultDtoFromsearchId(memberId);
    }

    @Transactional(readOnly = true)
    private ResultDto handleResultDtoFromsearchId(String memberId) {
        String msg = "아이디 찾기 성공";
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("memId", memberId);
        return handleResultDto(msg, resultMap);
    }

    private ResultDto getResultDtoToDuplicate(String msg) {
        return ResultDto.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(msg)
                .build();
    }

    private ResultDto handleResultDto(String msg, HashMap<String, Object> map) {
        return ResultDto
                .builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(msg)
                .data(map)
                .build();
    }

    // 어드민을 제외한 회원목록
    public Page<AdminMemberDto> getMembers(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.searchMemberList(condition, pageable);
    }

    // 특정회원의 상세정보
    public MemberDetailDto getMemberDetail(Long seq) {
        return memberRepository.getMemberDetail(seq);
    }

    @Transactional
    public ResultDto updateStatus(Long memSeq, Integer statusCode) {
        HashMap<String, Object> map = new HashMap<>();
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        member.updateStatus(statusCode);
        member.updateModifyBy("admin"); // 로그인 구현완료후 수정 ===========================
        memberRepository.save(member);
        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.OK, "회원 상태 수정 성공", map);
    }

    public ResultDto buildResultDto(int code, HttpStatus status, String msg, HashMap<String, Object> map) {
        return ResultDto.builder()
                .code(code)
                .httpStatus(status)
                .message(msg)
                .data(map)
                .build();
    }
}
