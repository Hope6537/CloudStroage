/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.service;

import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.cloudstroage.item.dao.ItemTypeDao;
import org.hope6537.cloudstroage.item.model.ItemType;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public interface ItemTypeService extends BasicService<ItemType, ItemTypeDao> {

    ItemType getTypeByName(String name);

}
