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

/**
 * Created by Hope6537 on 2015/3/15.
 */
@Controller
@RequestMapping("/")
public class FrontController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private HanderService handerService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        Member member = BasicController.getLoginMember(request);
        String type = request.getParameter("t");
        String parentId = request.getParameter("p");
        if (ApplicationConstant.isNull(type)) {
            type = ApplicationConstant.GETTYPE_NORAML;
        }
        if (ApplicationConstant.isNull(parentId)) {
            parentId = "-1";
        }
        if (ApplicationConstant.notNull(member)) {
            request.setAttribute("member", JSON.toJSONString(member));
            switch (type) {
                case ApplicationConstant.GETTYPE_BACK:
                    parentId = handerService.getGrandParentId(parentId);
                    break;
                case ApplicationConstant.GETTYPE_NORAML:
                    break;
            }
            request.setAttribute("parentId", parentId);
        }
        return ApplicationConstant.FRONTPATH + "index";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return ApplicationConstant.FRONTPATH + "login";
    }

    @RequestMapping(value = "/guide", method = RequestMethod.GET)
    public String guide() {
        return ApplicationConstant.FRONTPATH + "guide";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse register(@RequestBody Member member) {
        if (ApplicationConstant.notNull(member)) {
            String password = AESLocker.decrypt(member.getPassword());
            member.setPassword(AESLocker.encrypt(password));
            member.setStatus(ApplicationConstant.STATUS_JUDGE);
            return AjaxResponse.getInstanceByResult(memberService.addEntry(member));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.FAILCHN);
    }

    @RequestMapping(value = "/{md5}/{memberId}/validate", method = RequestMethod.GET)
    public String toValidate(@PathVariable String md5, @PathVariable String memberId) {
        return ApplicationConstant.FRONTPATH + "validate";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse validate(@RequestBody Member member) {
        return AjaxResponse.getInstanceByResult(true);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse login(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        if (ApplicationConstant.notNull(member)) {
            Member entry = memberService.getMemberByUsername(member.getUsername());
            if (ApplicationConstant.notNull(entry)) {
                if (AESLocker.encrypt(AESLocker.decrypt(member.getPassword())).equals(entry.getPassword())) {
                    HttpSession session = request.getSession();
                    String username = AESLocker.encrypt(entry.getUsername());
                    String key = MD5Util.string2MD5(entry.getPassword());
                    session.setAttribute("loginMember", entry);
                    session.setAttribute("cookies", "enabled");
                    Cookie _username = new Cookie("CloudStroageLoginUsername", username);
                    _username.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(_username);
                    Cookie _cookie = new Cookie("CloudStroageLoginValidate", key);
                    _cookie.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(_cookie);
                    return AjaxResponse.getInstanceByResult(true).addReturnMsg("登录成功，正在跳转");
                }
            }
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Member member = BasicController.getLoginMember(request);
        if (ApplicationConstant.notNull(member)) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", null);
            Cookie _username = new Cookie("CloudStroageLoginUsername", null);
            _username.setMaxAge(0);
            Cookie _cookie = new Cookie("CloudStroageLoginValidate", null);
            _cookie.setMaxAge(0);
            response.addCookie(_username);
            response.addCookie(_cookie);
        }
        return "login";
    }

    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public String lock(HttpServletRequest request, HttpServletResponse response) {
        Member member = BasicController.getLoginMember(request);
        if (ApplicationConstant.notNull(member)) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", null);
            Cookie _lock = new Cookie("locked", AESLocker.encrypt(member.getPassword()));
            _lock.setMaxAge(0);
            response.addCookie(_lock);
        }
        return "lock";
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse unlock(@RequestBody String unlock, HttpServletRequest request, HttpServletResponse response) {
        String cookieUsername = null;
        String cookiePassword = null;
        Cookie[] cookies = request.getCookies();
        if (ApplicationConstant.notNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("CloudStroageLoginUsername")) {
                    cookieUsername = AESLocker.decrypt(cookie.getValue());
                }
                if (cookie.getName().equals("CloudStroageLoginValidate")) {
                    cookiePassword = cookie.getValue();
                }
            }
            Member member = memberService.getMemberByUsername(cookieUsername);
            if (member.getPassword().endsWith(cookiePassword) && cookiePassword.equals(unlock)) {
                return AjaxResponse.getInstanceByResult(true);
            }
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.FAILCHN);
    }


}
