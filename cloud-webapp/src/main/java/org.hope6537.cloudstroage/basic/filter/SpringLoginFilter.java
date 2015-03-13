/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.filter;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.member.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Hope6537 on 2015/3/12.
 */
@Component
public class SpringLoginFilter extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        logger.debug(requestUrl);
        if (null != allowUrls && allowUrls.length >= 1) {
            for (String url : allowUrls) {
                if (requestUrl.contains(url)) {
                    return true;
                }
            }
        }

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("loginMember");
        if (ApplicationConstant.notNull(member)) {
            return true;
        } else {
            response.sendRedirect("/" + ApplicationConstant.APPLICATION_NAME + "/page/toIndex");
            return false;
        }

    }


}
