/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.utils;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//XML风格
@RunWith(SpringJUnit4ClassRunner.class)
//测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的；value指定web应用的根
@WebAppConfiguration(value = "/src/main/webapp")
//@ContextHierarchy：指定容器层次，即spring-core.xml是父容器，而spring-mvc.xml是子容器
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:spring/spring-core.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:spring/spring-database.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:spring/spring-mvc.xml"),
})

//注解风格  
//@RunWith(SpringJUnit4ClassRunner.class)  
//@WebAppConfiguration(value = "src/main/webapp")  
//@ContextHierarchy({  
//        @ContextConfiguration(name = "parent", classes = AppConfig.class),  
//        @ContextConfiguration(name = "child", classes = MvcConfig.class)  
//})  
public class SpringWebTestHelper {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}  