package com.fcc.PureSync.context.sendmail.vo;

import com.fcc.PureSync.context.sendmail.repository.VerificationCodeDao;
import com.fcc.PureSync.core.exception.CustomException;

import static com.fcc.PureSync.core.exception.CustomExceptionCode.NOT_FOUND_EMAIL;

public class EmailVo {
    private String email;
    private VerificationCodeDao verificationCodeDao;

    public EmailVo(String email, VerificationCodeDao verificationCodeDao) {
        this.email = email;
        this.verificationCodeDao = verificationCodeDao;
    }

    public boolean exists(){
        if(!verificationCodeDao.hasKey(email))
            throw new CustomException(NOT_FOUND_EMAIL);
        return true;
    }
}
