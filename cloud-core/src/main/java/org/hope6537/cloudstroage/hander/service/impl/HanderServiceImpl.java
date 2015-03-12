/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.service.impl;

import org.hope6537.cloudstroage.basic.service.impl.BasicServiceImpl;
import org.hope6537.cloudstroage.hander.dao.HanderDao;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.service.HanderService;
import org.hope6537.context.ApplicationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public boolean updateFolderName(Hander hander, String newName) {
        if (!hander.isAFolder()) {
            hander.setFileName(newName);
            return this.updateEntry(hander);
        }
        List<Hander> sonHanderList = getHanderListByParentHander(hander);
        hander.setFileName(newName);
        hander.resetHander(this.getEntryById(hander.getParentId()));
        sonHanderList.forEach((sonHander) -> sonHander.resetHander(hander));
        sonHanderList.forEach((sonHander) -> this.updateFolderName(sonHander, sonHander.getFileName()));
        List<String> names = new ArrayList<>();
        List<String> words = sonHanderList
                .stream()
                .flatMap(hander1 -> Stream.of(hander1.getFileName()))
                .collect(Collectors.toList());

        return this.updateEntry(hander);
    }

    @Override
    public boolean deleteFolder(Hander hander) {
        if (!hander.isAFolder()) {
            return this.deleteEntry(hander);
        }
        Set<String> ids = new HashSet<>();
        getSonHanderIds(hander, ids);
        return dao.deleteMultiHander(ids) == ids.size();

    }

    @Override
    public Hander getSonHanderToHander(Hander hander) {
        if (!hander.isAFolder()) {
            return hander;
        }
        Hander queryHander = new Hander();
        queryHander.setParentId(hander.getHanderId());
        List<Hander> sonHanderList = getEntryListByEntry(queryHander);
        sonHanderList.forEach((sonHander) -> getSonHanderToHander(sonHander));
        hander.setSonHanderList(sonHanderList);
        return hander;
    }

    @Override
    public List<Hander> getHanderListByParentHander(Hander hander) {
        if (!hander.isAFolder()) {
            return null;
        }
        Hander queryHander = new Hander();
        queryHander.setParentId(hander.getHanderId());
        return this.getEntryListByEntry(queryHander);
    }


    @Override
    public synchronized void getSonHanderIds(Hander hander, Set<String> set) {
        if (!hander.isAFolder()) {
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
        return dao.getHanderListByPath(memberId, fullPath);
    }

}
