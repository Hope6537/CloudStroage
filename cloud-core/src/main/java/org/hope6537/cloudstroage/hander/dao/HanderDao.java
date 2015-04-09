/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.model.HanderDownloadWrapper;
import org.hope6537.cloudstroage.hander.model.HanderItemWrapper;
import org.hope6537.cloudstroage.hander.model.HanderWrapper;
import org.hope6537.page.annotation.MybatisRepository;

import java.util.List;
import java.util.Set;

/**
 * 用户文件关系
 */
@MybatisRepository
public interface HanderDao extends BasicDao<Hander> {

    /**
     * 通过包装器建立Item和Hander的关系
     *
     * @param handerItemWrapper 包装器，内嵌hander对象和item主键的集合
     */
    int addHander2ItemByWrapper(HanderItemWrapper handerItemWrapper);

    /**
     * 通过用户和文件id来删除联系
     * 实际上是删除hander
     *
     * @param itemId
     * @param memberId
     */
    int deleteHanderByMemberAndItem(@Param("memberId") String memberId, @Param("itemId") String itemId);

    /**
     * 多重彻底删除文件
     *
     * @param idSet handerId的集合
     */
    int deleteMultiHander(@Param("idSet") Set<String> idSet);

    /**
     * 多重回收文件
     *
     * @param idSet handerId的集合
     */
    int disableMultiHander(@Param("idSet") Set<String> idSet);

    /**
     * 得到当前parentId的父节点
     * 即客户端调用该方法时将会传递本hander的handerId，从而获取当前的祖父节点，以便前端实现返回上一级菜单
     */
    String getGrandParentId(String parentId);

    /**
     * 通过文件路径来获取Hander列表
     */
    List<Hander> getHanderListByPath(@Param("memberId") String memberId, @Param("fullPath") String fullPath);

    /**
     * 通过包装器来获得符合hander对象的hander和item集合
     *
     * @param hander 过滤查询hander对象
     */
    HanderWrapper getWrapperByHander(Hander hander);

    /**
     * 得到多重下载的链接
     * 将item和hander的数据联系起来
     *
     * @param ids      需要多重下载的hander
     * @param memberId 当前文件拥有者，用于校验
     */
    List<HanderDownloadWrapper> getMultiDownloadLink(@Param("ids") Set<String> ids, @Param("memberId") String memberId);

}
