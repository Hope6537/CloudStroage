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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 关系业务类
 */
@Service("handerService")
public class HanderServiceImpl extends BasicServiceImpl<Hander, HanderDao> implements HanderService {

    @Autowired
    @Qualifier("handerDao")
    @Override
    public void setDao(HanderDao dao) {
        super.setDao(dao);
    }


    /**
     * 根据包装器添加关系和文件之间的连接
     *
     * @param handerItemWrapper 包装器对象
     */
    @Override
    public boolean addHander2ItemByWrapper(HanderItemWrapper handerItemWrapper) {
        synchronized (this) {
            if (ApplicationConstant.notNull(handerItemWrapper)) {
                //如果包装器不为空，将其信息补全，确定hander的信息
                handerItemWrapper.wrapper();
                //调用dao层方法将其持久化，同时返回结果
                return dao.addHander2ItemByWrapper(handerItemWrapper) == handerItemWrapper.getItemIdAndName().size();
            }
        }
        return false;
    }

    /**
     * 更新hander文件夹的信息
     *
     * @param hander 目标hander
     *               assert handerId !=null
     */
    @Override
    public boolean updateFolderName(Hander hander) {
        //断言具有id
        assert ApplicationConstant.notNull(hander.getHanderId());
        //如果不是文件夹单纯的更新就可以了
        if (!hander.checkFolder()) {
            return this.updateEntry(hander);
        }
        //是文件夹的话需要获取子节点一起更新
        List<Hander> sonHanderList = getHanderListByParentHander(hander);
        synchronized (this) {
            //将本对象路径和父节点信息更新
            if (!hander.getParentId().equals("-1")) {
                hander.resetHander(this.getEntryById(hander.getParentId()));
            }
            //对于根节点对象，特例
            hander.setFullPath("root/" + hander.getFileName());
        }
        //将其子节点对象信息更新并持久化
        sonHanderList.forEach((sonHander) -> sonHander.resetHander(hander));
        synchronized (this) {
            //然后子节点进行更新
            Boolean sonRes = sonHanderList.parallelStream().map(this::updateFolderName).reduce((a, b) -> a && b).get();
            //最后更新自己
            return this.updateEntry(hander) && sonRes;
        }
    }

    /**
     * 删除hander文件夹的信息
     *
     * @param hander 目标hander
     *               assert handerId !=null
     */
    @Override
    public boolean deleteFolder(Hander hander) {
        if (!hander.checkFolder()) {
            return this.deleteEntry(hander);
        }
        Set<String> ids = new HashSet<>();
        //获得子节点的信息一起删除
        getSonHanderIds(hander, ids);
        return dao.deleteMultiHander(ids) == ids.size();

    }

    /**
     * 删除多个hander
     * 使用Lamada表达式调用上面的deleteFolder
     *
     * @param handerList 要删除的hander列表，有文件夹有文件，所以必须要Hander对象包装
     */
    @Override
    public synchronized Boolean deleteMultiHander(List<Hander> handerList) {
        return handerList.stream().map(this::deleteFolder).reduce((a, b) -> a && b).get();
    }

    /**
     * 回收hander，并进行文件夹处理
     *
     * @param hander 目标hander
     *               assert handerId !=null
     */
    @Override
    public boolean disableFolder(Hander hander) {
        if (!hander.checkFolder()) {
            return this.disableEntry(hander);
        }
        Set<String> ids = new HashSet<>();
        getSonHanderIds(hander, ids);
        return dao.disableMultiHander(ids) == ids.size();
    }

    /**
     * 将该hander的内部子handerList填满
     *
     * @param hander 要填充的hander
     */
    @Override
    public Hander getSonHanderToHander(Hander hander) {
        if (!hander.checkFolder()) {
            return hander;
        }
        synchronized (this) {
            Hander queryHander = new Hander();
            queryHander.setParentId(hander.getHanderId());
            //获得子节点
            List<Hander> sonHanderList = getEntryListByEntry(queryHander);
            //然后递归查找直到叶子节点为止
            sonHanderList.forEach(this::getSonHanderToHander);
            //然后将其返回
            hander.setSonHanderList(sonHanderList);
        }
        return hander;
    }

    /**
     * 根据父节点获取子节点集合
     *
     * @param parentId 父节点Id
     */
    @Override
    public List<Hander> getHanderByParentId(String parentId) {
        if (ApplicationConstant.notNull(parentId)) {
            Hander query = new Hander();
            query.setParentId(parentId);
            return dao.getEntryListByEntry(query);
        }
        return null;
    }

    /**
     * 获得ZTree模型
     *
     * @param memberId 所属用户Id
     * @param parentId 所属父节点
     */
    @Override
    public List<ZTreeModel> getZTreeHander(String parentId, String memberId) {
        if (ApplicationConstant.notNull(memberId)) {
            //如果没有传递parentId，那么说明是根节点
            if (ApplicationConstant.isNull(parentId)) {
                parentId = "-1";
            }
            //获得查找实例
            Hander hander = Hander.getInstanceOfParentId(parentId, memberId);
            hander.setFolder(ApplicationConstant.FOLDER);
            List<Hander> list = dao.getEntryListByEntry(hander);
            List<ZTreeModel> returnList = new LinkedList<>();
            //将其需要的信息进行包装
            list.forEach(item -> returnList.add
                    (new ZTreeModel(item.getHanderId(), item.getParentId(), item.getFileName(), "true")));
            return returnList;
        }
        return null;
    }

    /**
     * 根据Hander对象来获取子Hander集合
     *
     * @param hander hander对象
     */
    @Override
    public List<Hander> getHanderListByParentHander(Hander hander) {
        //多重检查是否为文件夹
        if (ApplicationConstant.notNull(hander.getFolder()) && !hander.checkFolder()) {
            return null;
        }
        Hander queryHander = new Hander();
        //当前Id为子节点Id
        queryHander.setParentId(hander.getHanderId());
        return this.getEntryListByEntry(queryHander);
    }

    /**
     * 根据Hander来获取包装器
     *
     * @param hander 查询过滤对象
     *               assert handerId != null
     */
    @Override
    public HanderWrapper getWrapperByHanderId(Hander hander) {
        //就是之前写的SQL语句
        return dao.getWrapperByHander(hander);
    }

    /**
     * 得到当前节点的祖父节点
     *
     * @param parentId 要获取的id的孙子id
     */
    @Override
    public String getGrandParentId(String parentId) {
        return dao.getGrandParentId(parentId);
    }

    /**
     * 得到多重下载链接
     *
     * @param ids      handerId集合
     * @param memberId 所属用户Id，用于校验
     */
    @Override
    public List<HanderDownloadWrapper> getMultiDownloadLink(Set<String> ids, String memberId) {
        if (ApplicationConstant.notNull(ids) && ApplicationConstant.notNull(memberId)) {
            return this.dao.getMultiDownloadLink(ids, memberId);
        }
        return null;
    }

    /**
     * 得到当前hander下子节点的id集合，并赋给第二个set对象
     *
     * @param hander 当前父节点
     * @param set    要传递的集合,因为需要递归查找，所以需要外部表
     */
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

    /**
     * 根据用户Id来得到Hander集合
     *
     * @param memberId 所属用户Id
     */
    @Override
    public List<Hander> getHanderListByMemberId(String memberId) {
        Hander queryHander = new Hander();
        queryHander.setMemberId(memberId);
        return this.getEntryListByEntry(queryHander);
    }

    /**
     * 根据路径获取Hander集合
     *
     * @param memberId 所属用户
     * @param fullPath 全路径
     */
    @Override
    public List<Hander> getHanderListByPath(String memberId, String fullPath) {
        if (ApplicationConstant.isNull(fullPath)) {
            fullPath = "root";
        }
        return dao.getHanderListByPath(memberId, fullPath);
    }

}
