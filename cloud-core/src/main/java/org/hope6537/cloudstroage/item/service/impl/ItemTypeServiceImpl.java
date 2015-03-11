/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.service.impl;

import org.hope6537.cloudstroage.basic.service.impl.BasicServiceImpl;
import org.hope6537.cloudstroage.item.dao.ItemTypeDao;
import org.hope6537.cloudstroage.item.model.ItemType;
import org.hope6537.cloudstroage.item.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Hope6537 on 2015/3/11.
 */
@Service("itemTypeService")
public class ItemTypeServiceImpl extends BasicServiceImpl<ItemType, ItemTypeDao> implements ItemTypeService {

    @Autowired
    @Qualifier("itemTypeDao")
    @Override
    public void setDao(ItemTypeDao dao) {
        super.setDao(dao);
    }

    @Override
    public ItemType getTypeByName(String name) {
        return null;
    }
}
