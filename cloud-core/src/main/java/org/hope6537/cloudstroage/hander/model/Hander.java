/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.model;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.basic.model.BasicModel;

import java.io.File;
import java.util.List;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public class Hander extends BasicModel {

    private String handerId;

    private String memberId;

    private String itemId;

    private String parentId;

    private String fileName;

    private String folder;

    private String fullPath;

    private List<Hander> sonHanderList;

    public Hander() {
    }

    public boolean checkFolder() {
        return getFolder().equals(ApplicationConstant.FOLDER);
    }

    public static Hander getRootHander(String memberId) {
        Hander hander = new Hander();
        hander.setParentId("-1");
        hander.setMemberId(memberId);
        return hander;
    }

    public static Hander getInstanceRootOfTest() {
        Hander root =
                new Hander("-1", "1", "-1", "/", "/", ApplicationConstant.STATUS_NORMAL, ApplicationConstant.FOLDER);
        root.setHanderId("1");
        return root;

    }

    public static Hander getInstanceFolderOfTest() {
        return new Hander("-1", "1", "1", "usr", null, ApplicationConstant.STATUS_NORMAL, ApplicationConstant.FOLDER);
    }

    public static Hander getInstanceFileOfTest() {
        return new Hander("-1", "1", null, "a.txt", null, ApplicationConstant.STATUS_NORMAL, ApplicationConstant.FILE);
    }

    public void resetHander(Hander hander) {
        this.setParentId(hander.getHanderId());
        this.setFullPath(hander.getFullPath() + File.separator + getFileName());
    }


    public Hander(String memberId, String itemId, String parentId, String fileName, String fullPath, String status, String folder) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.parentId = parentId;
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.status = status;
        this.folder = folder;
    }

    public String getHanderId() {
        return handerId;
    }

    public void setHanderId(String handerId) {
        this.handerId = handerId;
    }

    @Override
    public String commonId() {
        return getHanderId();
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

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<Hander> getSonHanderList() {
        return sonHanderList;
    }

    public void setSonHanderList(List<Hander> sonHanderList) {
        this.sonHanderList = sonHanderList;
    }
}
