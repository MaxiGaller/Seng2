package edu.hm.muse.controller;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Created by ifw14130 on 03.06.2016.
 */
@Component
public class Token {
    private final SecureRandom CSRNPG;

    public Token() {
        CSRNPG = new SecureRandom();
    }

    public int getNewToken() {
        return CSRNPG.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        return CSRNPG.equals(token.CSRNPG);

    }

    @Override
    public int hashCode() {
        return CSRNPG.hashCode();
    }
}
