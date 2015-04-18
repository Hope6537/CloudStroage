/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.filter;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.member.service.MemberService;
import org.hope6537.security.AESLocker;
import org.hope6537.security.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Hope6537 on 2015/3/12.
 */
@Component
public class SpringLoginFilter extends HandlerInterceptorAdapter {

    public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberService memberService;

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        logger.debug(requestUrl);
        HttpSession session = request.getSession();
        if (null != allowUrls && allowUrls.length >= 1) {
            for (String url : allowUrls) {
                if (requestUrl.contains(url)) {
                    return true;
                }
            }
        }
        Member member = (Member) session.getAttribute("loginMember");
        Cookie[] cookies = request.getCookies();
        String cookieUsername = null;
        String cookiePassword = null;
        if (ApplicationConstant.notNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("CloudStroageLoginUsername")) {
                    cookieUsername = AESLocker.decrypt(cookie.getValue());
                }
                if (cookie.getName().equals("CloudStroageLoginValidate")) {
                    cookiePassword = cookie.getValue();
                }
            }
        }
        if (ApplicationConstant.notNull(member)) {
            //如果当前域已经登录 直接通过
            return true;
        } else {
            if (ApplicationConstant.notNull(cookieUsername, cookiePassword)) {
                Member queryMember = memberService.getMemberByUsername(cookieUsername);
                boolean result = MD5Util.string2MD5(queryMember.getPassword()).equals(cookiePassword);
                if (result) {
                    session.setAttribute("loginMember", queryMember);
                    return result;
                }
            }
        }


        Cookie clear1 = new Cookie("CloudStroageLoginUsername", null);
        clear1.setMaxAge(0);
        Cookie clear2 = new Cookie("CloudStroageLoginValidate", null);
        clear1.setMaxAge(0);
        response.addCookie(clear1);
        response.addCookie(clear2);
        response.sendRedirect("/" + ApplicationConstant.APPLICATION_NAME + "/login");
        return false;
    }
}
