package com.fcc.PureSync.context.member.service;

import com.fcc.PureSync.context.member.dto.FindPasswordDto;
import com.fcc.PureSync.context.member.dto.LoginDto;
import com.fcc.PureSync.context.member.dto.SignupDto;
import com.fcc.PureSync.context.member.entity.Body;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.context.member.repository.BodyRepository;
import com.fcc.PureSync.context.member.repository.MemberRepository;
import com.fcc.PureSync.context.sendmail.service.MailService;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.core.constant.MemberConstant;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.core.jwt.JwtUtil;
import com.fcc.PureSync.core.util.CheckUserRight;
import com.fcc.PureSync.core.util.RandomStringGenerator;
import com.fcc.PureSync.dto.AdminMemberDto;
import com.fcc.PureSync.dto.MemberDetailDto;
import com.fcc.PureSync.entity.MemberSearchCondition;
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

import java.util.HashMap;

import static com.fcc.PureSync.core.exception.CustomExceptionCode.*;

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
        Member inputMemberInfo = signupDto.toMember(passwordEncoder);
        Member signUpMember = memberRepository.save(inputMemberInfo);

        Body inputBody = Body.builder()
                .memSeq(signUpMember.getMemSeq())
                .bodyHeight(signupDto.getBodyHeight())
                .bodyWeight(signupDto.getBodyHeight())
                .bodyWishWeight(signupDto.getBodyWishWeight())
                .bodyWishConscal(signupDto.getBodyWishConscal())
                .bodyWishBurncal(signupDto.getBodyWishBurncal())
                .build();
        bodyRepository.save(inputBody);

        mailService.signUpByVerificationCode(inputMemberInfo.getMemEmail());

        String resultMsg = MemberConstant.SUCCESS_SIGNUP;
        return ResultDto.of(HttpStatus.CREATED.value(), HttpStatus.OK, resultMsg, new HashMap<>());
    }

    @Transactional(readOnly = true)
    public ResultDto login(LoginDto loginDto) {
        Member member = memberRepository.findByMemId(loginDto.getMemId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_ID));

        boolean checkLoginRight = CheckUserRight.checkLogin(member.getMemStatus());

        if(!checkLoginRight)
            throw new CustomException(BAD_REQUEST);

        if (!passwordEncoder.matches(loginDto.getMemPassword(), member.getMemPassword())) {
            throw new CustomException(NOT_FOUND_USER_PW);
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemId(), loginDto.getMemPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HashMap<String, Object> accessTokenMap = new HashMap<>();
        String accessToken = jwtUtil.createToken(member);
        String lockToken = jwtUtil.lockingToken(accessToken);
        accessTokenMap.put(MemberConstant.ACCESS_TOKEN, lockToken);
        String resultMsg = MemberConstant.SUCCESS_LOGIN;
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, resultMsg, accessTokenMap);
    }


    @Transactional(readOnly = true)
    public ResultDto checkDuplicate(String field, String value) {
        String resultMsg = "";

        switch (field) {
            case MemberConstant.MEMBER_ID:
                if (!memberRepository.findByMemId(value).isEmpty()) {
                    throw new CustomException(ALREADY_EXIST_ID);
                }
                resultMsg = MemberConstant.SUCCESS_DUPLICATE_ID;
                break;

            case MemberConstant.MEMBER_NICKNAME:
                if (!memberRepository.findByMemNick(value).isEmpty()) {
                    throw new CustomException(ALREADY_EXIST_NICK);
                }
                resultMsg = MemberConstant.SUCCESS_DUPLICATE_NICKNAME;
                break;

            case MemberConstant.MEMBER_EMAIL:
                if (!memberRepository.findByMemEmail(value).isEmpty()) {
                    throw new CustomException(ALREADY_EXIST_EMAIL);
                }
                resultMsg = MemberConstant.SUCCESS_DUPLICATE_EMAIL;
                break;

            default:
                throw new CustomException(BAD_REQUEST);
        }
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, resultMsg, new HashMap<>());
    }

    @Transactional
    public ResultDto searchPassword(FindPasswordDto findPasswordDto) {
        Member member = memberRepository.findByMemEmail(findPasswordDto.getMemEmail()).orElseThrow(() -> new CustomException(NOT_FOUND_EMAIL));
        String newPassword = RandomStringGenerator.generateRandomPassword(MemberConstant.MAX_PASSWORD_LENGTH);
        member.updatePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
        mailService.sendTemporaryPassword(findPasswordDto.getMemEmail(), newPassword);
        String resultMsg = MemberConstant.SUCCESS_TEMPORARYPASSWORD_SEND_MAIL;
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, resultMsg, new HashMap<>());
    }

    @Transactional(readOnly = true)
    public ResultDto searchId(String memEmail) {
        Member member = memberRepository.findByMemEmail(memEmail).orElseThrow(() -> new CustomException(NOT_FOUND_EMAIL));
        String memberId = member.getMemId();
        String resultMsg = MemberConstant.SUCCESS_SEARCH_ID;
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put(MemberConstant.MEMBER_ID, memberId);
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, resultMsg, resultMap);
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
