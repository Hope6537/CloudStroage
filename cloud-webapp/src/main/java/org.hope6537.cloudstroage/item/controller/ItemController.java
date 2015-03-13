/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China 
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.controller;

import org.hope6537.cloudstroage.basic.controller.BasicController;
import org.hope6537.cloudstroage.item.dao.ItemDao;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@RestController
@RequestMapping("/item")
public class ItemController extends BasicController<ItemInfo, ItemDao, ItemService> {


    @Autowired
    @Qualifier("itemService")
    @Override
    public void setService(ItemService service) {
        super.setService(service);
    }

    @Override
    public String toPage() {
        return "/item/manage";
    }

}
