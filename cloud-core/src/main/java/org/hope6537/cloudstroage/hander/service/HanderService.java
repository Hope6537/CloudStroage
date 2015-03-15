/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.service;

import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.cloudstroage.hander.dao.HanderDao;
import org.hope6537.cloudstroage.hander.model.Hander;

import java.util.List;
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public interface HanderService extends BasicService<Hander, HanderDao> {


    public void getSonHanderIds(Hander hander, Set<String> list);

    public boolean updateFolderName(Hander hander);

    public boolean deleteFolder(Hander hander);

    public boolean disableFolder(Hander hander);

    public Hander getSonHanderToHander(Hander hander);

    public List<Hander> getHanderListByMemberId(String memberId);

    public List<Hander> getHanderListByPath(String memberId, String fullPath);

    public List<Hander> getHanderListByParentHander(Hander hander);

}
