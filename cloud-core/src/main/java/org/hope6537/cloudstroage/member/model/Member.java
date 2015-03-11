/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage 's Cloud Stroage System
 */

package org.hope6537.cloudstroage.member.model;

import org.hope6537.cloudstroage.basic.model.BasicModel;
import org.hope6537.context.ApplicationConstant;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class Member extends BasicModel {

    private static final long serialVersionUID = -1176042631816013694L;
    private String memberId;
    private String name;
    private String username;
    private String password;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
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
