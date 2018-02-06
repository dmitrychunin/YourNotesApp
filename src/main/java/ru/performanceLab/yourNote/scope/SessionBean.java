package ru.performanceLab.yourNote.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

public class SessionBean {
    private final String md5 = "secret string value" + String.valueOf(LocalTime.now().toSecondOfDay());

    public SessionBean() {
//        try {
//            byte[] bytesOfMessage = md5.getBytes("UTF-8");
//
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] thedigest = md.digest(bytesOfMessage);
//            md5 = new String(thedigest);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    public String getMd5() {
        return md5;
    }
}
