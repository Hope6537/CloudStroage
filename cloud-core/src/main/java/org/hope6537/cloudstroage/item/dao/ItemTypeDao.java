/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.dao;

import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.item.model.ItemType;
import org.hope6537.page.annotation.MybatisRepository;

/**
 * Created by Hope6537 on 2015/3/11.
 */
@MybatisRepository
public interface ItemTypeDao extends BasicDao<ItemType> {

    ItemType getTypeByName(String name);

}
