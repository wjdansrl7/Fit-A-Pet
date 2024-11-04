package com.ssafy.fittapet.backend.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class EnteringCodeUtil {
    // todo : 해당 키 숨기기
    private static final String SECRET_KEY = "1234567890123456";

    public static String encrypt(Long code) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // 데이터 암호화
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDateTime = currentTime.format(formatter);

        byte[] encrypted = cipher.doFinal((formattedDateTime+code).getBytes());

        // Base64 인코딩 후 문자열로 반환
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static Long isCodeValid(String code) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // 인코딩된 암호화 데이터를 디코딩하고 복호화?
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(code));
        String decryptedString = new String(decrypted);
        Long groupId = Long.parseLong(decryptedString.substring(12));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(decryptedString.substring(0,12), formatter);

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);

        if(!duration.isNegative() && duration.toHours() < 24) return groupId;
        else return -1L;
    }
}
