package com.example.android.adventurequencher;

public class User {

    private static String name;
    private static String email;
    private static String pwd;
    private static Integer exp;
    private static String rewards;

    public User(String name, String email, String pwd, Integer exp,
                String rewards)
    {
        this.setName(name);
        this.setEmail(email);
        this.setPwd(pwd);
        this.setExp(exp);
        this.setRewards(rewards);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }
}
