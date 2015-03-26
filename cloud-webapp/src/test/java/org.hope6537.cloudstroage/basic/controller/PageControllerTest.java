/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.controller;

import org.hamcrest.CoreMatchers;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.utils.SpringWebTestHelper;
import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

/**
 * Created by Hope6537 on 2015/3/12.
 */
public class PageControllerTest extends SpringWebTestHelper {

    public void testView() throws Exception {
        //mockMvc.perform 执行一个请求
        MvcResult result = mockMvc.perform(
                //MockMvcRequestBuilders.get("/user/1") 构造一个请求
                get("/page/toIndex"))
                //ResultActions.andExpect 添加执行完成后的断言
                //ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
                //比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息
                //ResultActions.andReturn表示执行完成后返回相应的结果。
                .andExpect(view().name("/admin/index"))
                .andDo(print())
                .andReturn();
    }

    public void testNormal() throws Exception {
        //测试普通控制器
        mockMvc.perform(get("/user/{id}", 1)) //执行请求
                .andExpect(model().attributeExists("user")) //验证存储模型数据
                .andExpect(view().name("user/view")) //验证viewName
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/view.jsp"))//验证视图渲染时forward到的jsp
                .andExpect(status().isOk())//验证状态码
                .andDo(print()); //输出MvcResult到控制台
    }

    public void test404() throws Exception {
        //找不到控制器，404测试
        MvcResult result = mockMvc.perform(get("/user2/{id}", 1)) //执行请求
                .andDo(print())
                .andExpect(status().isNotFound()) //验证控制器不存在
                .andReturn();
        Assert.assertNull(result.getModelAndView()); //自定义断言
    }

    public void testValidation() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/{id}", 1))//执行请求
                .andReturn(); //返回MvcResult
        Assert.assertNotNull(result.getModelAndView().getModel().get("user")); //自定义断言
    }

   /* */

    /**
     * 验证请求参数绑定到模型数据及Flash属性
     *//*
    public void testParameters() throws Exception {
        mockMvc.perform(post("/user").param("name", "zhang")) //执行传递参数的POST请求(也可以post("/user?name=zhang"))
                .andExpect(handler().handlerType(PageController.class)) //验证执行的控制器类型
                .andExpect(handler().methodName("create")) //验证执行的控制器方法名
                .andExpect(model().hasNoErrors()) //验证页面没有错误
                .andExpect(flash().attributeExists("success")) //验证存在flash属性
                .andExpect(view().name("redirect:/user")); //验证视图
    }
*/
    public void testFileUpload() throws Exception {
        //文件上传
        byte[] bytes = new byte[]{1, 2};
        mockMvc.perform(fileUpload("/user/{id}/icon", 1L).file("icon", bytes)) //执行文件上传
                .andExpect(model().attribute("icon", bytes)) //验证属性相等性
                .andExpect(view().name("success")); //验证视图
    }

    public void testJson() throws Exception {
        String requestBody = "{\"id\":1, \"name\":\"zhang\"}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //验证响应contentType
                .andExpect(jsonPath("$.id").value(1)); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/

        String errorBody = "{id:1, name:zhang}";
        MvcResult result = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(errorBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(status().isBadRequest()) //400错误请求
                .andReturn();

        Assert.assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(result.getResolvedException().getClass()));//错误的请求内容体
    }

    public void testException() throws Exception {
        //异常处理
        MvcResult result = mockMvc.perform(get("/user/exception")) //执行请求
                .andExpect(status().isInternalServerError()) //验证服务器内部错误
                .andReturn();

        Assert.assertTrue(IllegalArgumentException.class.isAssignableFrom(result.getResolvedException().getClass()));
    }

    public void testStatic() throws Exception {
        //静态资源
        mockMvc.perform(get("/static/app.js")) //执行请求
                .andExpect(status().isOk()) //验证状态码200
                .andExpect(content().string(CoreMatchers.containsString("var")));//验证渲染后的视图内容包含var

        mockMvc.perform(get("/static/app1.js")) //执行请求
                .andExpect(status().isNotFound());  //验证状态码404
    }

    /**
     * 此处注意request().asyncResult一定是在第一次请求发出；然后第二次通过asyncDispatch进行异步请求。
     */
    public void testAjax() throws Exception {
        //Callable
        MvcResult result = mockMvc.perform(get("/user/async1?id=1&name=zhang")) //执行请求
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(CoreMatchers.instanceOf(Member.class))) //默认会等10秒超时
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        //DeferredResult
        result = mockMvc.perform(get("/user/async2?id=1&name=zhang")) //执行请求
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(CoreMatchers.instanceOf(Member.class)))  //默认会等10秒超时
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    public void testFilter() throws Exception {
        //mockMvc = webAppContextSetup(wac).addFilter(Filter, "/*").build();
        mockMvc.perform(get("/user/1"))
                .andExpect(request().attribute("filter", true));
    }
}
