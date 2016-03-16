/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.context;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;

import java.util.Collection;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class ApplicationConstant extends org.hope6537.context.ApplicationConstant {

    public static final String FOLDER = "文件夹";
    public static final String FILE = "文件";

    public static final String FILE_STATUS_NORMAL = "云端已同步";
    public static final String FILE_STATUS_NO_CONTACT = "没有联系";
    public static final String FILE_STATUS_ONLY_SERVER = "只在服务器上";

    public static final String APPLICATION_NAME = "CloudStroage";
    public static final String ADMINPATH = "/admin/";
    public static final String FRONTPATH = "/front/";
    public static final String ADMIN_LOGINPATH = ADMINPATH + "/login";
    public static final String FRONT_LOGINPATH = FRONTPATH + "/login";

    public static final String GETTYPE_NORAML = "n";
    public static final String GETTYPE_BACK = "b";

    public static <T extends Collection<?>> AjaxResponse collectionCheck(T t) {
        if (notNull(t)) {
            if (t.size() != 0) {
                return AjaxResponse.getInstanceByResult(true).addAttribute("list", t);
            }
            return new AjaxResponse(ReturnState.WARNING, "空文件夹").addAttribute("empty", true);
        }
        return new AjaxResponse(ReturnState.ERROR, FAIL_CHN);
    }

    public static boolean notNull(Object... objc) {
        boolean res = objc != null;
        if (!res) {
            return false;
        }
        for (Object o : objc) {
            if (o instanceof String) {
                res = res && !((String) o).isEmpty();
            }
            if (o instanceof Collection) {
                res = res && !((Collection) o).isEmpty();
            }
            res = res && o != null;
        }
        return res;
    }
}

