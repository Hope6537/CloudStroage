/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China 
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.controller;

import org.hope6537.cloudstroage.basic.controller.BasicController;
import org.hope6537.cloudstroage.hander.dao.HanderDao;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.service.HanderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@RestController
@RequestMapping("/hander")
public class HanderController extends BasicController<Hander, HanderDao, HanderService> {


    @Autowired
    @Qualifier("handerService")
    @Override
    public void setService(HanderService service) {
        super.setService(service);
    }

    @Override
    public String toPage() {
        return null;
    }
}
