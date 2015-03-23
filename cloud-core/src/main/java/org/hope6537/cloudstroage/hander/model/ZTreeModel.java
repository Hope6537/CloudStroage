/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.model;

/**
 * Created by Hope6537 on 2015/3/23.
 */
public class ZTreeModel {

    private String id;

    private String pid;

    private String name;

    private String isParent;

    public ZTreeModel() {

    }

    public ZTreeModel(String id, String pid, String name, String isParent) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.isParent = isParent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
}
