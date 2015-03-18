/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.controller;

import com.alibaba.fastjson.JSON;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.service.HanderService;
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
public class HanderControllerTest extends SpringWebTestHelper {

    @Autowired
    private HanderService handerService;

    private Member loginMember;
    private Hander hander;

    private Hander root;
    private Hander folder;
    private Hander file1;
    private Hander file2;

    @Before
    public void preData() {
        loginMember = Member.getInstanceOfTest();
        hander = Hander.getInstanceFileOfTest();
        hander.resetHander(Hander.getInstanceRootOfTest());
        assertTrue(handerService.addEntry(hander));
    }


    public void initHander() {
        root = Hander.getInstanceFolderOfTest();
        root.resetHander(Hander.getInstanceRootOfTest());
        root.setFileName("_root");
        root.setParentId("1");
        assertTrue(handerService.addEntry(root));

        folder = Hander.getInstanceFolderOfTest();
        folder.resetHander(root);
        assertTrue(handerService.addEntry(folder));

        file1 = Hander.getInstanceFileOfTest();
        file1.resetHander(folder);
        assertTrue(handerService.addEntry(file1));

        file2 = Hander.getInstanceFileOfTest();
        file2.resetHander(folder);
        assertTrue(handerService.addEntry(file2));
    }

    @Test
    public void testBlocked() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/hander/toPage"))
                .andDo(print())
                .andReturn();
        assertEquals(result.getResponse().getRedirectedUrl(), ("/CloudStroage/page/toIndex"));
        assertEquals(result.getResponse().getStatus(), 302);
    }

    @Test
    public void testPage() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/hander/toPage").sessionAttr("loginMember", loginMember))
                .andDo(print())
                .andReturn();
        assertTrue(result.getResponse().getStatus() == 200);
    }

    @Test
    public void testGetNormal() throws Exception {
        mockMvc.perform(
                get("/hander").sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetSingle() throws Exception {
        mockMvc.perform(
                get("/hander/" + hander.getHanderId())
                        .sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andExpect(jsonPath("$.returnData.model.handerId").value(hander.getHanderId()))
                .andDo(print())
                .andReturn();

    }

    @Test
    public void testAddHander() throws Exception {
        String request = JSON.toJSONString(hander);
        mockMvc.perform(post("/hander").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andReturn();
    }

    @Test
    public void testUpdateHander() throws Exception {
        hander.setFileName("_after");
        String request = JSON.toJSONString(hander);
        mockMvc.perform(put("/hander").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andReturn();
        mockMvc.perform(
                get("/hander/" + hander.getHanderId())
                        .sessionAttr("loginMember", loginMember))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andExpect(jsonPath("$.returnData.model.fileName").value("_after"))
                .andReturn();
    }

    @Test
    public void testDisableHander() throws Exception {
        String request = JSON.toJSONString(hander);
        mockMvc.perform(delete("/hander/disable").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.returnState").value("OK"))

                .andReturn();
        mockMvc.perform(
                get("/hander/" + hander.getHanderId())
                        .sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andExpect(jsonPath("$.returnData.model.status").value(ApplicationConstant.STATUS_DIE))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteHander() throws Exception {
        String request = JSON.toJSONString(hander);
        mockMvc.perform(delete("/hander/delete").sessionAttr("loginMember", loginMember)
                .contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.returnState").value("OK"))
                .andDo(print())
                .andReturn();
        mockMvc.perform(
                get("/hander/" + hander.getHanderId())
                        .sessionAttr("loginMember", loginMember))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.returnState").value("ERROR"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteMultiHander() throws Exception {
        String arr = "[" + file1.getHanderId() + "," + file2.getHanderId() + "]";
        String request = JSON.toJSONString(arr);


    }
}
