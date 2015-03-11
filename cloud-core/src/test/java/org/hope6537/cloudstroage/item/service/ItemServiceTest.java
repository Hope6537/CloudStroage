/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.service;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.spring.SpringTestHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hope6537 on 2015/3/10.
 */
@ContextConfiguration("classpath:spring/spring-test.xml")
public class ItemServiceTest extends SpringTestHelper {

    @Autowired
    private ItemService itemService;

    public ItemInfo itemAppend() {
        return ItemInfo.getInstanceOfTest();
    }

    @Test
    public void testAdd() {
        assertTrue(itemService.addEntry(itemAppend()));
    }

    @Test
    public void testGet() {
        ItemInfo itemInfo = itemAppend();
        itemService.addEntry(itemInfo);
        String id = itemInfo.getItemId();
        ItemInfo queryItem = itemService.getEntryById(id);
        assertEquals(queryItem.getFileName(), itemInfo.getFileName());
    }

    @Test
    public void testGetList() {
        ItemInfo itemInfo = itemAppend();
        itemService.addEntry(itemInfo);
        ItemInfo queryItem = new ItemInfo();
        queryItem.setStatus(ApplicationConstant.STATUS_NORMAL);
        List<ItemInfo> itemInfoList = itemService.getEntryListByEntry(itemInfo);
        assertTrue(ApplicationConstant.notNull(itemInfoList));
    }

    @Test
    public void testUpdate() {
        ItemInfo itemInfo = itemAppend();
        itemService.addEntry(itemInfo);
        String id = itemInfo.getItemId();
        ItemInfo queryItem = itemService.getEntryById(id);
        queryItem.setAbsolutePath("/after");
        itemService.updateEntry(queryItem);
        assertEquals("/after", itemService.getEntryById(id).getAbsolutePath());
    }

    @Test
    public void testDelete() {
        ItemInfo itemInfo = itemAppend();
        itemService.addEntry(itemInfo);
        String id = itemInfo.getItemId();
        itemService.deleteEntry(itemInfo);
        assertTrue(ApplicationConstant.isNull(itemService.getEntryById(id)));
    }

    @Test
    public void testDisable() {
        ItemInfo itemInfo = itemAppend();
        itemService.addEntry(itemInfo);
        String id = itemInfo.getItemId();
        ItemInfo query = itemService.getEntryById(id);
        itemService.disableEntry(query);
        assertEquals(ApplicationConstant.STATUS_DIE, itemService.getEntryById(id).getStatus());
    }

}

