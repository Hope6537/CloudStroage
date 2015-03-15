/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.model;

/**
 * Created by Hope6537 on 2015/3/15.
 */
public class HanderWrapper {

    private String id;

    private String name;

    private String level;

    private String type;

    private Hander hander;

    public HanderWrapper(Hander hander) {
        this.hander = hander;
        id = hander.getHanderId();
        name = hander.getFileName();
        level = "1";
        type = hander.checkFolder() ? "folder" : "file";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hander getHander() {
        return hander;
    }

    public void setHander(Hander hander) {
        this.hander = hander;
    }
}
