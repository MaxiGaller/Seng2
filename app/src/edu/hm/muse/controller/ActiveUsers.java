package edu.hm.muse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActiveUsers {

    @Autowired
    private Token token;

    Map<String, Integer> activeUserMap = new HashMap<>();

    public int getToken(String loginName) {
        int token = -1;
        token = activeUserMap.get(loginName);
        return token;
    }

    //setzt gleichzeitig Token
    public void setUser(String name, int token) {
        activeUserMap.put(name, token);
    }

    public void removeUser(String name) {
        activeUserMap.remove(name);
    }
}