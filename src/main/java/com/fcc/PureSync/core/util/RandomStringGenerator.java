package com.fcc.PureSync.core.util;



import com.fcc.PureSync.core.constant.RandomStringGeneratorConstant;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Component 롬북 사용 생성자만들어서 보내주기 @Requri = class private final 객체명 생성자 만들면서 파라미터 주입 롬북임 만들어준다. 생성자에서 final 초기화 가능.
public class RandomStringGenerator {
    private static final SecureRandom random = new SecureRandom();

    //임시 비밀 번호 보내주기
    public static String generateRandomPassword(Integer length) {
        List<String> passwordChars = new ArrayList<>();
        passwordChars.add(randomChar(RandomStringGeneratorConstant.ENG_UPPER_CASE));
        passwordChars.add(randomChar(RandomStringGeneratorConstant.ENG_LOWER_CASE));
        passwordChars.add(randomChar(RandomStringGeneratorConstant.DIGITS));
        passwordChars.add(randomChar(RandomStringGeneratorConstant.SPECIAL_CHARS));
        Stream<String> otherChars =
                Stream.generate(() ->
                        randomChar(RandomStringGeneratorConstant.ENG_UPPER_CASE + RandomStringGeneratorConstant.ENG_LOWER_CASE + RandomStringGeneratorConstant.DIGITS + RandomStringGeneratorConstant.SPECIAL_CHARS)).limit(length - 4);
        passwordChars.addAll(otherChars.collect(Collectors.toList()));
        Collections.shuffle(passwordChars);
        String newPassword = passwordChars.stream().collect(Collectors.joining());
        return newPassword;
    }
    
    //이메일 인증 시 사용

    public static String generateEmailVerificationCode(Integer length){
        List<String> verificationCode = new ArrayList<>();
        verificationCode.add(randomChar(RandomStringGeneratorConstant.ENG_UPPER_CASE));
        verificationCode.add(randomChar(RandomStringGeneratorConstant.ENG_LOWER_CASE));
        verificationCode.add(randomChar(RandomStringGeneratorConstant.DIGITS));
        Stream<String> otherChars =
                Stream.generate(() ->
                        randomChar(RandomStringGeneratorConstant.ENG_UPPER_CASE + RandomStringGeneratorConstant.ENG_LOWER_CASE + RandomStringGeneratorConstant.DIGITS)).limit(length - 3);
        verificationCode.addAll(otherChars.collect(Collectors.toList()));
        Collections.shuffle(verificationCode);
        String generateEmailVerificationCode = verificationCode.stream().collect(Collectors.joining());
        return generateEmailVerificationCode;
    }

    private static String randomChar(String characters) {
        return String.valueOf(characters.charAt(random.nextInt(characters.length())));
    }
    public static  Integer generatePasswordLength(){
        Integer randomLength = random.nextInt(RandomStringGeneratorConstant.MAX_PASSWORD_LENGTH - RandomStringGeneratorConstant.MIN_PASSWORD_LENGTH + 1) + RandomStringGeneratorConstant.MIN_PASSWORD_LENGTH;
        System.out.println(randomLength);
        return randomLength;
    }
}
