/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China 
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.service.HanderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@RestController
@RequestMapping("/hander")
public class HanderController {

    private static final String BASEPATH = ApplicationConstant.ADMINPATH;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private HanderService handerService;

    @RequestMapping(value = "/toPage")
    public String toPage() {
        return BASEPATH + "/hander/manage";
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getItemList() {
        logger.debug("关系业务-获取可用关系列表");
        Hander hander = new Hander();
        hander.setStatus(ApplicationConstant.STATUS_NORMAL);
        List<Hander> list = handerService.getEntryListByEntry(hander);
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{handerId}")
    @ResponseBody
    public AjaxResponse getSingleItem(@PathVariable String handerId) {
        logger.debug("关系业务-获取单个关系");
        if (ApplicationConstant.notNull(handerId)) {
            Hander hander = handerService.getEntryById(handerId);
            return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(hander)).addAttribute("hander", hander);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addItem(@RequestBody Hander hander) {
        logger.debug("关系业务-添加关系");
        if (ApplicationConstant.notNull(hander)) {
            return AjaxResponse.getInstanceByResult(handerService.addEntry(hander));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResponse updateItem(@RequestBody Hander hander) {
        logger.debug("关系业务-更新关系");
        if (ApplicationConstant.notNull(hander) && ApplicationConstant.notNull(hander.getItemId())) {
            return AjaxResponse.getInstanceByResult(handerService.updateEntry(hander));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/disable")
    @ResponseBody
    public AjaxResponse disableItem(@RequestBody Hander hander) {
        logger.debug("关系业务-无效化关系");
        if (ApplicationConstant.notNull(hander) && ApplicationConstant.notNull(hander.getItemId())) {
            return AjaxResponse.getInstanceByResult(handerService.disableEntry(hander));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ResponseBody
    public AjaxResponse deleteItem(@RequestBody Hander hander) {
        logger.debug("关系业务-删除关系");
        if (ApplicationConstant.notNull(hander) && ApplicationConstant.notNull(hander.getItemId())) {
            return AjaxResponse.getInstanceByResult(handerService.deleteEntry(hander));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

}
