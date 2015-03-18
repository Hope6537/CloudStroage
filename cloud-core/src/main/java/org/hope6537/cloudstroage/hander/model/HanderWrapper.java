/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.model;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/15.
 */
public class HanderWrapper {

    private List<Hander> handers;

    public List<Hander> getHanders() {
        return handers;
    }

    public void setHanders(List<Hander> handers) {
        this.handers = handers;
    }
}