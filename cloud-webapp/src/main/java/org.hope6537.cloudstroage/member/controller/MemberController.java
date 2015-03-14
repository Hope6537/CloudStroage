/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.member.controller;

import org.hope6537.cloudstroage.basic.controller.BasicController;
import org.hope6537.cloudstroage.member.dao.MemberDao;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BasicController<Member, MemberDao, MemberService> {

    @Autowired
    @Qualifier("memberService")
    @Override
    public void setService(MemberService service) {
        super.setService(service);
    }

    @Override
    public String toPage() {
        return BASEPATH + "member/manage";
    }

}
