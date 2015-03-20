/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.service;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.model.HanderItemWrapper;
import org.hope6537.cloudstroage.hander.model.HanderWrapper;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.member.service.MemberService;
import org.hope6537.spring.SpringTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hope6537 on 2015/3/12.
 */
@ContextConfiguration("classpath:spring/spring-test.xml")
public class HanderServiceTest extends SpringTestHelper {

    @Autowired
    private HanderService handerService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    private Member user;

    private ItemInfo wordItem;

    private ItemInfo excelItem;

    private Hander handerRoot;

    private Hander handerFolder;

    private Hander handerFile;

    @Before
    public void init() {
        user = Member.getInstanceOfTest();
        wordItem = ItemInfo.getInstanceOfTest();
        wordItem.setSha1("12345");
        excelItem = ItemInfo.getInstanceOfTest();
        excelItem.setSha1("22345");
        handerRoot = Hander.getInstanceFolderOfTest();
        handerRoot.setFileName("root");
        handerRoot.setParentId("-1");
        handerRoot.setFullPath("");
        handerFolder = Hander.getInstanceFolderOfTest();
        handerFile = Hander.getInstanceFileOfTest();
        assertTrue(memberService.addEntry(user));
        assertTrue(itemService.addEntry(wordItem));
        assertTrue(itemService.addEntry(excelItem));
    }

    @Test
    public void testAddEntry() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));
    }

    @Test
    public void testGetEntry() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));

        Hander queryFile = handerService.getEntryById(handerFile.getHanderId());
        assertEquals("a.txt", queryFile.getFileName());
        Hander queryFolder = handerService.getEntryById(handerFile.getParentId());
        assertEquals("usr", queryFolder.getFileName());
    }

    @Test
    public void testUpdateEntry() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));
        handerFolder.setFileName("tmp");
        //在修改的时候一定要resetHander以下，不然路径会出错！
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.updateEntry(handerFolder));
        handerFile.setFileName("b.txt");
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.updateEntry(handerFile));
        assertEquals(File.separator + "tmp", handerService.getEntryById(handerFolder.getHanderId()).getFullPath());
        assertEquals(File.separator + "tmp" + File.separator + "b.txt", handerService.getEntryById(handerFile.getHanderId()).getFullPath());
    }

    @Test
    public void testUpdateFolderName() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));

        handerFolder.setFileName("tmp");
        assertTrue(handerService.updateFolderName(handerFolder));

        assertEquals(File.separator + "tmp", handerService.getEntryById(handerFolder.getHanderId()).getFullPath());
        assertEquals(File.separator + "tmp" + File.separator + "a.txt", handerService.getEntryById(handerFile.getHanderId()).getFullPath());
    }

    @Test
    public void testDisableEntry() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));
        handerFile.disable();
        assertTrue(handerService.disableEntry(handerFile));
        assertEquals(ApplicationConstant.STATUS_DIE, handerService.getEntryById(handerFile.getHanderId()).getStatus());
    }

    @Test
    public void testDeleteFolder() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));
        handerFile.disable();
        assertTrue(handerService.disableEntry(handerFile));

        assertTrue(handerService.deleteFolder(handerRoot));
    }

    @Test
    public void testGetListEntry() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));

        Hander queryHander = new Hander();
        queryHander.setMemberId("-1");
        List<Hander> handerList = handerService.getEntryListByEntry(queryHander);
        assertTrue(handerList.size() == 3);

        assertTrue(handerService.deleteEntry(handerFile));
        handerRoot.setMemberId("-2");
        handerFolder.setMemberId("-2");
        assertTrue(handerService.updateEntry(handerRoot));
        assertTrue(handerService.updateEntry(handerFolder));

        List<Hander> handerList2 = handerService.getHanderListByMemberId("-2");
        assertTrue(handerList2.size() == 2);
    }

    @Test
    public void testGetEntryByPath() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));
        List<Hander> handers = handerService.getHanderListByPath("-1", File.separator + "usr");
        assertEquals(1, handers.size());
    }


    @Test
    public void testGetWrapper() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));
        Hander hander = new Hander();
        HanderWrapper wrapper = handerService.getWrapperByHanderId(hander);
        assertTrue(ApplicationConstant.notNull(wrapper.getHanders()));
    }

    @Test
    public void testHanderItemWrapper() {
        assertTrue(handerService.addEntry(handerRoot));
        handerFolder.resetHander(handerRoot);
        assertTrue(handerService.addEntry(handerFolder));
        handerFile.resetHander(handerFolder);
        assertTrue(handerService.addEntry(handerFile));

        Map<String, String> items = new HashMap<>();
        items.put("-1", "a.txt");
        items.put("-2", "b.txt");

        HanderItemWrapper handerItemWrapper = new HanderItemWrapper();
        handerItemWrapper.setItemIdAndName(items);
        handerItemWrapper.setMemberId(handerFile.getMemberId());
        handerItemWrapper.setParentId(handerFile.getParentId());

        assertTrue(handerService.addHander2ItemByWrapper(handerItemWrapper));
    }


}
