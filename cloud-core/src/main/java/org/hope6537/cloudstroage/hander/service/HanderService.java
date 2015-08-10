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
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public interface HanderService extends BasicService<Hander, HanderDao> {


    /**
     * 根据包装器添加关系和文件之间的连接
     *
     * @param handerItemWrapper 包装器对象
     */
    boolean addHander2ItemByWrapper(HanderItemWrapper handerItemWrapper);

    /**
     * 更新hander文件夹的信息
     *
     * @param hander 目标hander
     *               assert handerId !=null
     */
    boolean updateFolderName(Hander hander);

    /**
     * 删除hander文件夹的信息
     *
     * @param hander 目标hander
     *               assert handerId !=null
     */
    boolean deleteFolder(Hander hander);

    /**
     * 删除多个hander
     * 使用Lamada表达式调用上面的deleteFolder
     *
     * @param handerList 要删除的hander列表，有文件夹有文件，所以必须要Hander对象包装
     */
    Boolean deleteMultiHander(List<Hander> handerList);

    /**
     * 回收hander，并进行文件夹处理
     *
     * @param hander 目标hander
     *               assert handerId !=null
     */
    boolean disableFolder(Hander hander);

    /**
     * 将该hander的内部子handerList填满
     *
     * @param hander 要填充的hander
     */
    Hander getSonHanderToHander(Hander hander);

    /**
     * 得到当前hander下子节点的id集合，并赋给第二个set对象
     *
     * @param hander 当前父节点
     * @param set    要传递的集合,因为需要递归查找，所以需要外部表
     */
    void getSonHanderIds(Hander hander, Set<String> set);

    /**
     * 根据父节点获取子节点集合
     *
     * @param parentId 父节点Id
     */
    List<Hander> getHanderByParentId(String parentId);

    /**
     * 获得ZTree模型
     *
     * @param memberId 所属用户Id
     * @param parentId 所属父节点
     */
    List<ZTreeModel> getZTreeHander(String parentId, String memberId);

    /**
     * 根据用户Id来得到Hander集合
     *
     * @param memberId 所属用户Id
     */
    List<Hander> getHanderListByMemberId(String memberId);

    /**
     * 根据路径获取Hander集合
     *
     * @param memberId 所属用户
     * @param fullPath 全路径
     */
    List<Hander> getHanderListByPath(String memberId, String fullPath);

    /**
     * 根据Hander对象来获取子Hander集合
     *
     * @param hander hander对象
     */
    List<Hander> getHanderListByParentHander(Hander hander);

    /**
     * 根据Hander来获取包装器
     *
     * @param hander 查询过滤对象
     *               assert handerId != null
     */
    HanderWrapper getWrapperByHanderId(Hander hander);

    /**
     * 得到当前节点的祖父节点
     *
     * @param parentId 要获取的id的孙子id
     */
    String getGrandParentId(String parentId);

    /**
     * 得到多重下载链接
     *
     * @param ids      handerId集合
     * @param memberId 所属用户Id，用于校验
     */
    List<HanderDownloadWrapper> getMultiDownloadLink(Set<String> ids, String memberId);

}
