/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.item.controller;

import com.alibaba.fastjson.JSON;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.utils.SpringWebTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Hope6537 on 2015/3/12.
 */
public class ItemControllerTest extends SpringWebTestHelper {

    @Autowired
    private ItemService itemService;

    private ItemInfo itemInfo;
    private Member loginMember;

    @Before
    public void preData() {
        loginMember = Member.getInstanceOfTest();
        itemInfo = ItemInfo.getInstanceOfTest();
        assertTrue(itemService.addEntry(itemInfo));
    }

    @Test
    public void testBlocked() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/item/toPage"))
                .andDo(print())
                .andReturn();
        assertEquals(result.getResponse().getRedirectedUrl(), ("/CloudStroage/page/toIndex"));
        assertEquals(result.getResponse().getStatus(), 302);
    }

    @Test
    public void testPage() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/item/toPage")
                        .sessionAttr("loginMember", loginMember))
                .andDo(print())
                .andReturn();
        assertTrue(result.getResponse().getStatus() == 200);
    }

    @Test
    public void testGetNormal() throws Exception {
        mockMvc.perform(
                get("/item").sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetSingle() throws Exception {
        mockMvc.perform(
                get("/item/" + itemInfo.getItemId())
                        .sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andExpect(jsonPath("$.returnData.model.itemId").value(itemInfo.getItemId()))
                .andDo(print())
                .andReturn();

    }

    @Test
    public void testAddMember() throws Exception {
        String request = JSON.toJSONString(itemInfo);
        mockMvc.perform(post("/item").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateMember() throws Exception {
        itemInfo.setAbsolutePath("_after");
        String request = JSON.toJSONString(itemInfo);
        mockMvc.perform(put("/item").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andDo(print())
                .andReturn();
        mockMvc.perform(
                get("/item/" + itemInfo.getItemId())
                        .sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andExpect(jsonPath("$.returnData.model.absolutePath").value("_after"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDisableMember() throws Exception {
        loginMember.setName("_after");
        String request = JSON.toJSONString(itemInfo);
        mockMvc.perform(delete("/item/disable").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andReturn();
        mockMvc.perform(
                get("/item/" + itemInfo.getItemId())
                        .sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andExpect(jsonPath("$.returnData.model.status").value(ApplicationConstant.STATUS_DIE))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteMember() throws Exception {
        String request = JSON.toJSONString(itemInfo);
        mockMvc.perform(delete("/item/delete").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andDo(print())
                .andReturn();
        mockMvc.perform(
                get("/item/" + itemInfo.getItemId())
                        .sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("ERROR"))
                .andDo(print())
                .andReturn();
    }

   /* @Test
    public void testFilter() throws Exception {
        //mockMvc = webAppContextSetup(wac).addFilter(Filter, "*//*").build();
        mockMvc.perform(get("/member/toPage"))
                .andExpect(request().attribute("filter", true));
    }*/
}
