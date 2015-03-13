package org.hope6537.cloudstroage.basic.controller;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {


    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return ApplicationConstant.ADMINPATH + "index";
    }
}
