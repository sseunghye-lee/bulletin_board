package com.seung.pilot.commons.security;


import com.seung.pilot.commons.exception.FailEncryptException;
import com.seung.pilot.commons.utils.EncryptUtil;

public class EncryptService {

    public static String encryptPhoneNumber(String phoneNumber) throws FailEncryptException {

        if(phoneNumber == null) return "";

        try {
            return EncryptUtil.encryptAES256(phoneNumber.replaceAll("-", "").trim());
        } catch (Exception e) {
            throw new FailEncryptException(
                String.format("%s(%s)", "Fail AES256 Encrypt.", phoneNumber));
        }
    }

    public static String decryptPhoneNumber(String phoneNumber) throws FailEncryptException {

        if(phoneNumber == null) return "";

        try {
            return EncryptUtil.decryptAES256(phoneNumber);
        } catch (Exception e) {
            return phoneNumber;
        }
    }
}
