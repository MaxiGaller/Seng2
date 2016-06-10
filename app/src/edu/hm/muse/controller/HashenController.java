package edu.hm.muse.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mratusny on 10.06.16.
 */
public class HashenController {

    public static String hashen256(String mpwd) {
        String hpwd = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(mpwd.getBytes(), 0, mpwd.length());

            hpwd = new BigInteger(1, digest.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hpwd;
    }
}
