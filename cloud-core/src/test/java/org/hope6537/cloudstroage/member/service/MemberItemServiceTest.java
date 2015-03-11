/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.member.service;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.spring.SpringTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hope6537 on 2015/3/11.
 */
@ContextConfiguration("classpath:spring/spring-test.xml")
public class MemberItemServiceTest extends SpringTestHelper {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    private Member member;
    private ItemInfo itemFolder;
    private ItemInfo itemFile;

    public Member memberAppend() {
        Member member = Member.getInstanceOfTest();
        return member;
    }

    public ItemInfo itemAppend() {
        return ItemInfo.getInstanceOfTest();
    }

    @Before
    public void init() {
        member = memberAppend();
        itemFolder = itemAppend();
        itemFile = itemAppend();
        assertTrue(memberService.addEntry(member));
        assertTrue(itemService.addEntry(itemFolder));
        itemFile.setFolder(ApplicationConstant.FILE);
        itemFile.setParentId(itemFolder.getItemId());
        assertTrue(itemService.addEntry(itemFile));
    }

    @Test
    public void testPut() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(itemFile);
        itemInfos.add(itemFolder);
        assertTrue(memberService.putItem(member.getMemberId(), itemInfos));
    }

    @Test
    public void testDelete() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(itemFile);
        itemInfos.add(itemFolder);
        assertTrue(memberService.putItem(member.getMemberId(), itemInfos));
        assertTrue(memberService.deleteItem(member.getMemberId(), itemInfos));
    }

    @Test
    public void testDeleteFolder() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(itemFolder);
        assertTrue(memberService.putItem(member.getMemberId(), itemInfos));
        assertTrue(memberService.deleteItem(member.getMemberId(), itemInfos));
    }

    @Test
    public void testGetItemByMember() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(itemFile);
        itemInfos.add(itemFolder);
        assertTrue(memberService.putItem(member.getMemberId(), itemInfos));
        List<ItemInfo> list = memberService.getItemsByMember(member.getMemberId());
        assertEquals(list.size(), 2);
    }

    @Test
    public void testGetItemByPath() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(itemFile);
        itemInfos.add(itemFolder);
        assertTrue(memberService.putItem(member.getMemberId(), itemInfos));

    }


}
