package edu.hm.muse.controller;

public class ActiveUser {
    private final User user;
    private int token;
    private String sessionId;


    public ActiveUser(User user, int token) {
        this.user = user;
        this.token = token;
    }

    public ActiveUser(User user, int token, String sessionId) {
        this.user = user;
        this.token = token;
        this.sessionId = sessionId;
    }


    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public String getSessionId() {
        return sessionId;
    }
}
