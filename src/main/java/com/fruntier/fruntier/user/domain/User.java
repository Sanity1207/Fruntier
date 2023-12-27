package com.fruntier.fruntier.user.domain;

import java.util.Date;

public class User {
    private Long userId;//유저 아이디(PK)
    private String name;//유저 이름
    private String password;//유저 패스 워드
    private String email;//유저 이메일
    private String phoneNumber; //유저 핸드폰 번호
    //private Image profile_picture;
    private String message;// 유저 프로필 메세지
    private Boolean isMan;//유저 성별
    private Tier tier;//유저 티어
    private Position position;//유저 등급(일반사용자, 관리자)
    private Date lastLoginDate; //마지막 접속 일자

    public User(){
        ;
    }
    public User(Long userId, String name, String password, String email, String phoneNumber, String message, Boolean isMan, Tier tier, Position position, Date lastLoginDate) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.isMan = isMan;
        this.tier = tier;
        this.position = position;
        this.lastLoginDate = lastLoginDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(Boolean man) {
        isMan = man;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}