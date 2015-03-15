/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
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

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
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
    public AjaxResponse validate(@RequestBody Member member) {
        return AjaxResponse.getInstanceByResult(true);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AjaxResponse login(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        if (ApplicationConstant.notNull(member)) {
            Member entry = memberService.getMemberByUsername(member.getUsername());
            if (ApplicationConstant.notNull(entry)) {
                if (AESLocker.encrypt(member.getPassword()).equals(entry.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("loginMember", entry);
                    session.setAttribute("cookies", "enabled");
                    String key = MD5Util.string2MD5(entry.getPassword());
                    Cookie _cookie = new Cookie("CloudStroageLoginValidate", key);
                    _cookie.setMaxAge(60 * 60 * 24 * 7);
                    _cookie.setDomain("cloud.hope6537.org");
                    response.addCookie(_cookie);
                    return AjaxResponse.getInstanceByResult(true).addReturnMsg("登录成功，正在跳转");
                }
            }
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }


}
