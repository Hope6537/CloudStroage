/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China 
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    private static final String BASEPATH = ApplicationConstant.ADMINPATH;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/toPage")
    public String toPage() {
        return BASEPATH + "/item/manage";
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getItemList() {
        logger.debug("文件业务-获取可用文件列表");
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setStatus(ApplicationConstant.STATUS_NORMAL);
        List<ItemInfo> list = itemService.getEntryListByEntry(itemInfo);
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{itemInfoId}")
    @ResponseBody
    public AjaxResponse getSingleItem(@PathVariable String itemInfoId) {
        logger.debug("文件业务-获取单个文件");
        if (ApplicationConstant.notNull(itemInfoId)) {
            ItemInfo itemInfo = itemService.getEntryById(itemInfoId);
            return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(itemInfo)).addAttribute("itemInfo", itemInfo);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addItem(@RequestBody ItemInfo itemInfo) {
        logger.debug("文件业务-添加文件");
        if (ApplicationConstant.notNull(itemInfo)) {
            return AjaxResponse.getInstanceByResult(itemService.addEntry(itemInfo));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResponse updateItem(@RequestBody ItemInfo itemInfo) {
        logger.debug("文件业务-更新文件");
        if (ApplicationConstant.notNull(itemInfo) && ApplicationConstant.notNull(itemInfo.getItemId())) {
            return AjaxResponse.getInstanceByResult(itemService.updateEntry(itemInfo));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/disable")
    @ResponseBody
    public AjaxResponse disableItem(@RequestBody ItemInfo itemInfo) {
        logger.debug("文件业务-无效化文件");
        if (ApplicationConstant.notNull(itemInfo) && ApplicationConstant.notNull(itemInfo.getItemId())) {
            return AjaxResponse.getInstanceByResult(itemService.disableEntry(itemInfo));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ResponseBody
    public AjaxResponse deleteItem(@RequestBody ItemInfo itemInfo) {
        logger.debug("文件业务-删除文件");
        if (ApplicationConstant.notNull(itemInfo) && ApplicationConstant.notNull(itemInfo.getItemId())) {
            return AjaxResponse.getInstanceByResult(itemService.deleteEntry(itemInfo));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

}
