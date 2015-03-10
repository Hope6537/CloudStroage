package org.hope6537.cloudstroage.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

    public static final String PATH = "/admin/";

    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return PATH + "index";
    }
}
