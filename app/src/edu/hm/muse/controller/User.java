package edu.hm.muse.controller;

import java.util.Arrays;


public class User {
    private int userId;
    private String loginName;
    private String name;
    private String password;
    private byte[] salt;

    public User(int userId, String loginName, String name, String password, byte[] salt) {
        this.userId = userId;
        this.loginName = loginName;
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    public int getUserId() {
        return userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId == user.userId && loginName.equals(user.loginName) &&
                name.equals(user.name) &&
                password.equals(user.password) && Arrays.equals(salt, user.salt);

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + loginName.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + Arrays.hashCode(salt);
        return result;
    }
}
