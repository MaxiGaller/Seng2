package edu.hm.muse.controller;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Token {
    private final SecureRandom RandomNumber;

    public Token() {
        RandomNumber = new SecureRandom();
    }

    public int getNewToken() {
        return RandomNumber.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        return RandomNumber.equals(token.RandomNumber);

    }

    @Override
    public int hashCode() {
        return RandomNumber.hashCode();
    }
}
