/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.service;

import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.cloudstroage.hander.dao.HanderDao;
import org.hope6537.cloudstroage.hander.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public interface HanderService extends BasicService<Hander, HanderDao> {


    public boolean addHander2ItemByWrapper(HanderItemWrapper handerItemWrapper);

    public void getSonHanderIds(Hander hander, Set<String> list);

    public boolean updateFolderName(Hander hander);

    public boolean deleteFolder(Hander hander);

    public Optional<Boolean> deleteMultiHander(List<Hander> handers);

    public boolean disableFolder(Hander hander);

    public Hander getSonHanderToHander(Hander hander);

    public List<Hander> getHanderByParentId(String parentId);

    public List<ZTreeModel> getZTreeHander(String parentId, String memberId);

    public List<Hander> getHanderListByMemberId(String memberId);

    public List<Hander> getHanderListByPath(String memberId, String fullPath);

    public List<Hander> getHanderListByParentHander(Hander hander);

    public HanderWrapper getWrapperByHanderId(Hander hander);

    public String getGrandParentId(String parentId);

    public List<HanderDownloadWrapper> getMultiDownloadLink(Set<String> ids, String memberId);

}
