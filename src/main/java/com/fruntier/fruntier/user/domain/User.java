package com.fruntier.fruntier.user.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //유저 구분자(PK)
    private String username; //the id of the user. identifiable
    private String name;//유저 이름
    private String password;//유저 패스 워드
    private String email;//유저 이메일
    private String address;
    private String phoneNumber; //유저 핸드폰 번호
    //private Image profile_picture;
    private String message;// 유저 프로필 메세지
    private Boolean isMale;//유저 성별
    private Tier tier;//유저 티어
    private Position position;//유저 등급(일반사용자, 관리자)
    private Date lastLoginDate; //마지막 접속 일자

    @OneToMany(mappedBy = "user2")
    private List<Friendship> friendshipList = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<FriendRequest> friendRequestToList = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<FriendRequest> friendRequestFromList = new ArrayList<>();


    public User() {
        ;
    }

    public User(Long id, String username, String name,
                String password, String email, String phoneNumber,
                String message, Boolean isMale, Tier tier, Position position, Date lastLoginDate) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.isMale = isMale;
        this.tier = tier;
        this.position = position;
        this.lastLoginDate = lastLoginDate;
    }

    public static User fromUsername(String key) {
        User user = new User();
        user.setUsername(key);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Friendship> getFriendshipList() {
        return friendshipList;
    }

    public void setFriendshipList(List<Friendship> friendshipList) {
        this.friendshipList = friendshipList;
    }

    public List<FriendRequest> getFriendRequestToList() {
        return friendRequestToList;
    }

    public void setFriendRequestToList(List<FriendRequest> friendRequestToList) {
        this.friendRequestToList = friendRequestToList;
    }

    public List<FriendRequest> getFriendRequestFromList() {
        return friendRequestFromList;
    }

    public void setFriendRequestFromList(List<FriendRequest> friendRequestFromList) {
        this.friendRequestFromList = friendRequestFromList;
    }
}
