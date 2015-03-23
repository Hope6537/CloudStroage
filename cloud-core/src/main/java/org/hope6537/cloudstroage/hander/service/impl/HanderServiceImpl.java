/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.service.impl;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.basic.service.impl.BasicServiceImpl;
import org.hope6537.cloudstroage.hander.dao.HanderDao;
import org.hope6537.cloudstroage.hander.model.*;
import org.hope6537.cloudstroage.hander.service.HanderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Hope6537 on 2015/3/11.
 */
@Service("handerService")
public class HanderServiceImpl extends BasicServiceImpl<Hander, HanderDao> implements HanderService {

    @Autowired
    @Qualifier("handerDao")
    @Override
    public void setDao(HanderDao dao) {
        super.setDao(dao);
    }


    @Override
    public boolean addHander2ItemByWrapper(HanderItemWrapper handerItemWrapper) {
        synchronized (handerItemWrapper) {
            if (ApplicationConstant.notNull(handerItemWrapper)) {
                handerItemWrapper.wrapper();
                return dao.addHander2ItemByWrapper(handerItemWrapper) == handerItemWrapper.getItemIdAndName().size();
            }
        }
        return false;
    }

    @Override
    public boolean updateFolderName(Hander hander) {
        if (!hander.checkFolder()) {
            return this.updateEntry(hander);
        }
        List<Hander> sonHanderList = getHanderListByParentHander(hander);
        if (!hander.getParentId().equals("-1")) {
            hander.resetHander(this.getEntryById(hander.getParentId()));
        }
        hander.setFullPath("root/" + hander.getFileName());
        sonHanderList.forEach((sonHander) -> sonHander.resetHander(hander));
        sonHanderList.forEach((sonHander) -> this.updateFolderName(sonHander));
        return this.updateEntry(hander);
    }

    @Override
    public boolean deleteFolder(Hander hander) {
        if (!hander.checkFolder()) {
            return this.deleteEntry(hander);
        }
        Set<String> ids = new HashSet<>();
        getSonHanderIds(hander, ids);
        return dao.deleteMultiHander(ids) == ids.size();

    }

    @Override
    public Optional<Boolean> deleteMultiHander(List<Hander> handers) {
        return handers.stream().map(this::deleteFolder).reduce((a, b) -> a && b);
    }

    @Override
    public boolean disableFolder(Hander hander) {
        if (!hander.checkFolder()) {
            return this.disableEntry(hander);
        }
        Set<String> ids = new HashSet<>();
        getSonHanderIds(hander, ids);
        return dao.disableMultiHander(ids) == ids.size();
    }

    @Override
    public Hander getSonHanderToHander(Hander hander) {
        if (!hander.checkFolder()) {
            return hander;
        }
        Hander queryHander = new Hander();
        queryHander.setParentId(hander.getHanderId());
        List<Hander> sonHanderList = getEntryListByEntry(queryHander);
        sonHanderList.forEach(this::getSonHanderToHander);
        hander.setSonHanderList(sonHanderList);
        return hander;
    }

    @Override
    public List<Hander> getHanderByParentId(String parentId) {
        if (ApplicationConstant.notNull(parentId)) {
            Hander query = new Hander();
            query.setParentId(parentId);
            return dao.getEntryListByEntry(query);
        }
        return null;
    }

    @Override
    public List<ZTreeModel> getZTreeHander(String parentId, String memberId) {
        if (ApplicationConstant.notNull(memberId)) {
            if (ApplicationConstant.isNull(parentId)) {
                parentId = "-1";
            }
            Hander hander = Hander.getInstanceOfParentId(parentId, memberId);
            hander.setFolder(ApplicationConstant.FOLDER);
            List<Hander> list = dao.getEntryListByEntry(hander);
            List<ZTreeModel> returnList = new LinkedList<>();
            list.forEach(item -> {
                returnList.add(new ZTreeModel(item.getHanderId(), item.getParentId(), item.getFileName(), "true"));
            });
            return returnList;
        }
        return null;
    }

    @Override
    public List<Hander> getHanderListByParentHander(Hander hander) {
        if (ApplicationConstant.notNull(hander.getFolder()) && !hander.checkFolder()) {
            return null;
        }
        Hander queryHander = new Hander();
        queryHander.setParentId(hander.getHanderId());
        return this.getEntryListByEntry(queryHander);
    }

    @Override
    public HanderWrapper getWrapperByHanderId(Hander hander) {
        return dao.getWrapperByHander(hander);
    }

    @Override
    public String getGrandParentId(String parentId) {
        return dao.getGrandParentId(parentId);
    }

    @Override
    public List<HanderDownloadWrapper> getMultiDownloadLink(Set<String> ids, String memberId) {
        if (ApplicationConstant.notNull(ids) && ApplicationConstant.notNull(memberId)) {
            return this.dao.getMultiDownloadLink(ids, memberId);
        }
        return null;
    }


    @Override
    public synchronized void getSonHanderIds(Hander hander, Set<String> set) {
        if (!hander.checkFolder()) {
            set.add(hander.getHanderId());
        }
        set.add(hander.getHanderId());
        Hander queryHander = new Hander();
        queryHander.setParentId(hander.getHanderId());
        List<Hander> sonHanderList = getHanderListByParentHander(hander);
        if (ApplicationConstant.notNull(sonHanderList)) {
            sonHanderList.forEach((sonHander) -> set.add(sonHander.getHanderId()));
            sonHanderList.forEach((sonHander) -> getSonHanderIds(sonHander, set));
        }
    }

    @Override
    public List<Hander> getHanderListByMemberId(String memberId) {
        Hander queryHander = new Hander();
        queryHander.setMemberId(memberId);
        return this.getEntryListByEntry(queryHander);
    }

    @Override
    public List<Hander> getHanderListByPath(String memberId, String fullPath) {
        if (ApplicationConstant.isNull(fullPath)) {
            fullPath = "root";
        }
        return dao.getHanderListByPath(memberId, fullPath);
    }

}
