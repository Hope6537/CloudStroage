/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.model;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.date.DateFormatCalculate;

import java.util.Map;
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/20.
 */
public class HanderItemWrapper {

    private String memberId;

    private String parentId;

    private Map<String, String> itemIdAndName;

    private Hander hander;

    /**
     * 补全hander的信息。因为memberId和parentId决定文件位置，itemIdAndName来确定建立的中间表的关系
     */
    public void wrapper() {
        if (ApplicationConstant.notNull(memberId, parentId, itemIdAndName)) {
            hander = new Hander();
            hander.setFolder(ApplicationConstant.FILE);
            hander.setMemberId(memberId);
            hander.setParentId(parentId);
            hander.setStatus(ApplicationConstant.STATUS_NORMAL);
            hander.setUpdateDate(DateFormatCalculate.createNowTime());
        }
    }

    /**
     * 得到item的主键集合
     */
    public Set<String> getItemIds() {
        if (ApplicationConstant.notNull(getItemIdAndName())) {
            return getItemIdAndName().keySet();
        }
        return null;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Map<String, String> getItemIdAndName() {
        return itemIdAndName;
    }

    public void setItemIdAndName(Map<String, String> itemIdAndName) {
        this.itemIdAndName = itemIdAndName;
    }

    public Hander getHander() {
        return hander;
    }

    public void setHander(Hander hander) {
        this.hander = hander;
    }
}
