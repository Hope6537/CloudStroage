/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.model;

import org.hope6537.cloudstroage.basic.model.BasicModel;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public class Hander extends BasicModel {

    private String memberId;

    private String itemId;

    private String parentId;

    private String fileName;

    private String fullPath;

    public Hander() {
    }

    public Hander(String memberId, String itemId, String parentId, String fileName, String fullPath, String status) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.parentId = parentId;
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.status = status;
    }


    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
