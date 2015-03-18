/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.model.HanderWrapper;
import org.hope6537.page.annotation.MybatisRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/11.
 */
@MybatisRepository
public interface HanderDao extends BasicDao<Hander> {

    String getGrandParentId(String parentId);

    int deleteHanderByMemberAndItem(@Param("memberId") String memberId, @Param("itemId") String itemId);

    int deleteMultiHander(@Param("idSet") Set<String> idSet);

    int disableMultiHander(@Param("idSet") Set<String> idSet);

    List<Hander> getHanderListByPath(@Param("memberId") String memberId, @Param("fullPath") String fullPath);

    HanderWrapper getWrapperByHanderId(Hander hander);

}
