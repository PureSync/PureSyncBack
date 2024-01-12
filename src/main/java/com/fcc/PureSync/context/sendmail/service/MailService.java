package com.fcc.PureSync.context.sendmail.service;

import com.fcc.PureSync.context.sendmail.vo.EmailVo;
import com.fcc.PureSync.core.constant.EmailConstant;
import com.fcc.PureSync.context.sendmail.repository.VerificationCodeDao;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.context.member.repository.MemberRepository;
import com.fcc.PureSync.core.util.RandomStringGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.fcc.PureSync.core.exception.CustomExceptionCode.*;

@RequiredArgsConstructor
@Service
public class MailService {
    /*
     * 회원 가입 시 인증 코드 전송
     * 메일로 간 인증코드 링크 누를 때 멤버 상태 변경
     * 비밀 번호 찾기 시 임시 비밀 번호 전송
     * Java SimpleMailMessage 이용한 메일 전송
     * JPA를 사용하지 않고 Redis 사용
     *
     * */
    @Value("${spring.redis.host}")
    private String RedisHost;
    private final VerificationCodeDao verificationCodeDao;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    //회원 가입 시 코드 링크
    @Async
    public ResultDto signUpByVerificationCode(String newMemberEmail) {
        String verifcationCode = RandomStringGenerator.generateEmailVerificationCode(EmailConstant.EMAIL_VERIFICATION_CODE_LENGTH);
        String txt = String.format(EmailConstant.EMAIL_VERIFICATION_LINK, EmailConstant.AWS_DOMAIN, verifcationCode, newMemberEmail);
        verificationCodeDao.saveVerificationCode(newMemberEmail, verifcationCode);
        sendMail(newMemberEmail, EmailConstant.EMAIL_TITLE, txt);
        HashMap<String, Object> resultMap = new HashMap<>();
        return ResultDto.of(HttpStatus.ACCEPTED.value(), HttpStatus.OK, "", resultMap);
    }

    //링크 코드 비교
    @Transactional
    public ResultDto checkVerificationCode(String email, String certificationNumber) {
        EmailVo emailVo = new EmailVo(email, verificationCodeDao);
        if (!emailVo.exists()) {
            throw new CustomException(NOT_FOUND_USER_LINK_CODE);
        }
        verificationCodeDao.deleteVerificationCode(email);
        Member member = memberRepository.findByMemEmail(email).orElseThrow(() -> new CustomException(NOT_FOUND_EMAIL));
        member.enabledMemberLevel();
        memberRepository.save(member);
        HashMap<String, Object> resultMap = new HashMap<>();
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, EmailConstant.SUCCESS_VERIFICATION_CODE, resultMap);
    }

    //임시 비밀번호 전송
    @Async
    public void sendTemporaryPassword(String memberEmail, String newPassword) {
        sendMail(memberEmail, EmailConstant.TEMPORARY_PASSWORD, newPassword);
    }

    //mailForm
    private SimpleMailMessage createEmailForm(String toEmail, String title, String txt) {
        SimpleMailMessage mailForm = new SimpleMailMessage();
        mailForm.setTo(toEmail);
        mailForm.setSubject(title);
        mailForm.setText(txt);
        return mailForm;
    }

    //메일 보내기 회원 가입시 인증 , 비밀번호 찾기 시 임시 비밀 번호 보내주기.
    private void sendMail(String toEmail, String title, String txt) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, txt);
        try {
            javaMailSender.send(emailForm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(SEND_ERROR);
        }
    }
}
