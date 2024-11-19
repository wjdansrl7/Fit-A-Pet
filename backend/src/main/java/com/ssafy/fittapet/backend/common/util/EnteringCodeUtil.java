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

    private static final String SECRET_KEY = "1234567890123456";

    public static String encrypt(Long code) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDateTime = currentTime.format(formatter);

        byte[] encrypted = cipher.doFinal((formattedDateTime + code).getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static Long isCodeValid(String code) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(code));
        String decryptedString = new String(decrypted);
        Long guildId = Long.parseLong(decryptedString.substring(12));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(decryptedString.substring(0, 12), formatter);

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);

        if (!duration.isNegative() && duration.toHours() < 24) return guildId;
        else return -1L;
    }
}
