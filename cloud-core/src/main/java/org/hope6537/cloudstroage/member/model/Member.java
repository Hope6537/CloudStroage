package org.hope6537.cloudstroage.member.model;

import org.hope6537.context.ApplicationConstant;

import java.io.Serializable;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class Member implements Serializable {

    private Integer memberId;
    private String name;
    private String username;
    private String password;
    private String status;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Member() {
    }

    public Member(String name, String username, String password, String status) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public static Member getInstanceOfTest() {
        return new Member("_test", "_testUsername", "_testPassword", ApplicationConstant.STATUS_NORMAL);
    }
}
