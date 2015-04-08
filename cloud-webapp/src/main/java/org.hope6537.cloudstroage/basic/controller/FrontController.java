/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.controller;

import com.alibaba.fastjson.JSON;
import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.hander.service.HanderService;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.member.service.MemberService;
import org.hope6537.security.AESLocker;
import org.hope6537.security.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class FrontController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private HanderService handerService;

    //private final Map<String, String> cookies = new HashMap<>();


    /**
     * 跳转登陆页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return ApplicationConstant.FRONTPATH + "login";
    }

    /**
     * 跳转导航界面
     */
    @RequestMapping(value = "/guide", method = RequestMethod.GET)
    public String guide() {
        return ApplicationConstant.FRONTPATH + "guide";
    }

    /**
     * 校验用户，根据md5和用户id
     */
    @RequestMapping(value = "/{md5}/{memberId}/validate", method = RequestMethod.GET)
    public String toValidate(@PathVariable String md5, @PathVariable String memberId) {
        //TODO:补全代码
        return ApplicationConstant.FRONTPATH + "validate";
    }

    /**
     * 校验用户
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse validate(@RequestBody Member member) {
        //TODO:补全代码
        return AjaxResponse.getInstanceByResult(true);
    }


    /**
     * 主页获取信息，用于处理值，便于前台Ajax处理
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        //获取用户对象、跳转方式、和父节点ID
        Member member = BasicController.getLoginMember(request);
        String type = request.getParameter("t");
        String parentId = request.getParameter("p");
        //检查类型，如果没有那么就是普通获取当前父节点下的文件而已
        if (ApplicationConstant.isNull(type)) {
            type = ApplicationConstant.GETTYPE_NORAML;
        }
        //默认指向根目录
        if (ApplicationConstant.isNull(parentId)) {
            parentId = "-1";
        }
        //根据类型处理父节点，是祖父节点？（返回上一项），还是普通的父节点（展现当前页面）
        switch (type) {
            case ApplicationConstant.GETTYPE_BACK:
                parentId = handerService.getGrandParentId(parentId);
                break;
            case ApplicationConstant.GETTYPE_NORAML:
                break;
        }
        //返回信息
        request.setAttribute("member", JSON.toJSONString(member));
        request.setAttribute("parentId", parentId);
        return ApplicationConstant.FRONTPATH + "index";
    }


    /**
     * 注册控制器
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse register(@RequestBody Member member) {
        //如果当前用户信息有效
        if (ApplicationConstant.notNull(member)) {
            String password = AESLocker.decrypt(member.getPassword());
            member.setPassword(AESLocker.encrypt(password));
            member.setStatus(ApplicationConstant.STATUS_JUDGE);
            //更新状态并存储
            return AjaxResponse.getInstanceByResult(memberService.addEntry(member));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.FAILCHN);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse login(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        //如果当前登录信息有效
        if (ApplicationConstant.notNull(member)) {
            //只会出现唯一结果
            Member entry = memberService.getMemberByUsername(member.getUsername());
            //如果查询出来的结果不为空
            if (ApplicationConstant.notNull(entry)) {
                //那么进行对比
                if (AESLocker.encrypt(AESLocker.decrypt(member.getPassword())).equals(entry.getPassword())) {
                    //将信息填写到session中
                    HttpSession session = request.getSession();
                    String username = AESLocker.encrypt(entry.getUsername());
                    String password = MD5Util.string2MD5(entry.getPassword());
                    session.setAttribute("loginMember", entry);
                    //如果用户使用cookie
                    if (member.getRemember()) {
                        session.setAttribute("cookies", "enabled");
                        setCookies(response, username, password);
                    }
                    //最后响应
                    return AjaxResponse.getInstanceByResult(true).addReturnMsg("登录成功，正在跳转");
                }
            }
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    /**
     * 登出操作
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Member member = BasicController.getLoginMember(request);
        if (ApplicationConstant.notNull(member)) {
            HttpSession session = request.getSession();
            //销毁session
            session.setAttribute("loginMember", null);
            //同时销毁cookie
            if (ApplicationConstant.notNull(session.getAttribute("cookies"))) {
                clearCookies(response);
            }
        }
        return "front/login";
    }

    /**
     * 锁定操作
     */
    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public String lock(HttpServletRequest request, HttpServletResponse response) {
        Member member = BasicController.getLoginMember(request);
        //用户不为空
        if (ApplicationConstant.notNull(member)) {
            HttpSession session = request.getSession();
            //session清除，这样就算重定向其他页面也完蛋
            session.setAttribute("loginMember", null);
            //同时向锁屏界面打出基本信息
            request.setAttribute("name", member.getName());
            request.setAttribute("memberId", member.getMemberId());
            //如果有cookie，也清除
            if (ApplicationConstant.notNull(session.getAttribute("cookies"))) {
                clearCookies(response);
            }
        }
        return "front/lock";
    }

    /**
     * 解锁操作,就是登陆流程再来一遍，不用输入账号而已
     */
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse unlock(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        return login(member, request, response);
    }

    /**
     * 设置cookies
     */
    private static void setCookies(HttpServletResponse response, String username, String password) {
        Cookie _username = new Cookie("CloudStroageLoginUsername", username);
        _username.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(_username);
        Cookie _cookie = new Cookie("CloudStroageLoginValidate", password);
        _cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(_cookie);
    }

    /**
     * 清除cookies
     */
    private static void clearCookies(HttpServletResponse response) {
        Cookie _username = new Cookie("CloudStroageLoginUsername", null);
        _username.setMaxAge(0);
        Cookie _cookie = new Cookie("CloudStroageLoginValidate", null);
        _cookie.setMaxAge(0);
        response.addCookie(_username);
        response.addCookie(_cookie);
    }

}
