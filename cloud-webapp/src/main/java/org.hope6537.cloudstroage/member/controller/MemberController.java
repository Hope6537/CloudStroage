/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.member.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    private static final String BASEPATH = ApplicationConstant.ADMINPATH;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/toPage")
    public String toPage() {
        return BASEPATH + "/member/manage";
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getMemberList() {
        logger.debug("用户业务-获取可用用户列表");
        List<Member> list = memberService.getNormalMember();
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{memberId}")
    @ResponseBody
    public AjaxResponse getSingleMember(@PathVariable String memberId) {
        logger.debug("用户业务-获取单个用户");
        if (ApplicationConstant.notNull(memberId)) {
            Member member = memberService.getEntryById(memberId);
            return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(member)).addAttribute("member", member);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addMember(@RequestBody Member member) {
        logger.debug("用户业务-添加用户");
        if (ApplicationConstant.notNull(member)) {
            return AjaxResponse.getInstanceByResult(memberService.addEntry(member));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResponse updateMember(@RequestBody Member member) {
        logger.debug("用户业务-更新用户");
        if (ApplicationConstant.notNull(member) && ApplicationConstant.notNull(member.getMemberId())) {
            return AjaxResponse.getInstanceByResult(memberService.updateEntry(member));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/disable")
    @ResponseBody
    public AjaxResponse disableMember(@RequestBody Member member) {
        logger.debug("用户业务-无效化用户");
        if (ApplicationConstant.notNull(member) && ApplicationConstant.notNull(member.getMemberId())) {
            return AjaxResponse.getInstanceByResult(memberService.disableEntry(member));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ResponseBody
    public AjaxResponse deleteMember(@RequestBody Member member) {
        logger.debug("用户业务-删除用户");
        if (ApplicationConstant.notNull(member) && ApplicationConstant.notNull(member.getMemberId())) {
            return AjaxResponse.getInstanceByResult(memberService.deleteEntry(member));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

}
