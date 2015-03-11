/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.service.impl;

import org.hope6537.cloudstroage.basic.service.impl.BasicServiceImpl;
import org.hope6537.cloudstroage.item.dao.ItemDao;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Hope6537 on 2015/3/10.
 */
@Service("itemService")
public class ItemServiceImpl extends BasicServiceImpl<ItemInfo, ItemDao> implements ItemService {

    @Autowired
    @Qualifier("itemDao")
    @Override
    public void setDao(ItemDao dao) {
        super.setDao(dao);
    }
}
