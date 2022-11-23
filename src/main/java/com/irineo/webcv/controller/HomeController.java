package com.irineo.webcv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @GetMapping("")
    public String index(HttpServletRequest request) {
        return "home-view";
    }
}
