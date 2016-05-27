package edu.hm.muse.controller;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Society on 13.05.2016.
 */
@Component
public class SaltErstellen {

    SecureRandom salt;

    private SaltErstellen() {
        try {
            salt = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] getNextSalt() {
        byte[] randomBytes = new byte[32];
        salt.nextBytes(randomBytes);
        return randomBytes;
    }

    public String byteArrayToString(byte[] bytes) {

        String output;
        try {
            BigInteger bigInt = new BigInteger(1, bytes);
            output = bigInt.toString(32);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return "0";
        }
        return output;
    }
}