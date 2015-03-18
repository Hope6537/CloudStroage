/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.context;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class ApplicationConstant extends org.hope6537.context.ApplicationConstant {

    public static final String FOLDER = "文件夹";
    public static final String FILE = "文件";
    public static final String APPLICATION_NAME = "CloudStroage";
    public static final String ADMINPATH = "/admin/";
    public static final String FRONTPATH = "/front/";
    public static final String ADMIN_LOGINPATH = ADMINPATH + "/login";
    public static final String FRONT_LOGINPATH = FRONTPATH + "/login";

    public static final String GETTYPE_NORAML = "n";
    public static final String GETTYPE_BACK = "b";
}

