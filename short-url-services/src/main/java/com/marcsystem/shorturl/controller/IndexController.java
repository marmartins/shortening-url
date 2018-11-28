package com.marcsystem.shorturl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

}
