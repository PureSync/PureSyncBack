package com.fcc.PureSync.service;

import com.fcc.PureSync.common.EmailVerificationResponse;
import com.fcc.PureSync.common.constant.EmailConstant;
import com.fcc.PureSync.dao.VerificationCodeDao;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.fcc.PureSync.exception.CustomExceptionCode.*;

@RequiredArgsConstructor
@Service
public class MailService {

    @Value("${spring.redis.host}")
    private String RedisHost;
    private final VerificationCodeDao verificationCodeDao;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    //회원 가입 시 코드 링크
    @Async
    public void signUpByVerificationCode(String newMemberEmail) {
        String linkCode = RandomStringGenerator.generateEmailVerificationCode(EmailConstant.EMAIL_VERIFICATION_CODE_LENGTH);
        System.out.println("링크코드~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + linkCode);
        handleSignUpByVerificationCode(newMemberEmail, linkCode);
        EmailVerificationResponse emailVerificationResponse = new EmailVerificationResponse(newMemberEmail, linkCode);
//        return  handleSignUpByVerificationCodeMap(emailVerificationResponse);
    }
    //회원 가입시 코드 링크 핸들링
    private void handleSignUpByVerificationCode(String newMemberEmail, String linkCode) {
//        String txt = String.format("%s/api/mail/verify?verificationCode=%s&email=%s", EmailConstant.LOCAL_DOMAIN, linkCode,newMemberEmail);
        String txt = String.format("%s/api/mail/verify?verificationCode=%s&email=%s", EmailConstant.AWS_DOMAIN, linkCode,newMemberEmail);
        System.out.println("레디스 호스트%%%%%%%%%%%%%%%%%%%%%%%%%%%" + RedisHost);
        verificationCodeDao.saveVerificationCode(newMemberEmail, linkCode);
        sendMail(newMemberEmail, EmailConstant.EMAIL_TITLE, txt);
    }

    //링크 코드 비교
    public ResultDto checkVerificationCode(String email, String certificationNumber) {
        if (!isVerify(email, certificationNumber)) {
            throw new CustomException(NOT_FOUND_USER_LINK_CODE);
        }
        verificationCodeDao.deleteVerificationCode(email);
        return changeMemberLevel(email);
    }

    //유저 레벨 활성화로 변경
    private ResultDto changeMemberLevel(String email) {
        Member member = memberRepository.findByMemEmail(email).orElseThrow(() -> new CustomException(NOT_FOUND_EMAIL));
        member.enabledMemberLevel();
        memberRepository.save(member);
        return buildChangeMemberLevel();
    }


    @Transactional(readOnly = true)
    private boolean isVerify(String email, String certificationNumber) {
        boolean validatedEmail = isEmailExists(email);
        if (!isEmailExists(email)) {
            throw new CustomException(NOT_FOUND_EMAIL);
        }
        return (validatedEmail &&
                verificationCodeDao.getVerificationCode(email).equals(certificationNumber));
    }

    private boolean isEmailExists(String email) {
        return verificationCodeDao.hasKey(email);
    }

    //임시 비밀번호 전송
    @Async
    public void  sendTemporaryPassword(String memberEmail, String newPassword){
        sendMail(memberEmail, "임시비밀번호", newPassword);
    }


    //메일 보내기 회원 가입시 인증 , 비밀번호 찾기 시 임시 비밀 번호 보내주기.

    public void sendMail(String toEmail, String title, String txt) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, txt);
        try {
            javaMailSender.send(emailForm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(SEND_ERROR);

        }
    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String txt) {
        SimpleMailMessage mailForm = new SimpleMailMessage();
        mailForm.setTo(toEmail);
        mailForm.setSubject(title);
        mailForm.setText(txt);
        return mailForm;
    }

    private ResultDto handleSignUpByVerificationCodeMap(EmailVerificationResponse emailVerificationResponse) {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("emailVerificationResponse", emailVerificationResponse);
        String msg = "인증 링크 전송 성공";
        return handleResultDto(msg, resultMap);
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

    private ResultDto buildChangeMemberLevel() {
        String msg = "성공했습니다.";
        HashMap<String, Object> map = new HashMap<>();
        return ResultDto
                .builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(msg)
                .data(map)
                .build();
    }

}
