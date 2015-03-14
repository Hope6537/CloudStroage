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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return ApplicationConstant.ADMINPATH + "index";
    }

    @RequestMapping(value = "toLogin")
    public String toLogin() {
        return ApplicationConstant.ADMINPATH + "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
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
