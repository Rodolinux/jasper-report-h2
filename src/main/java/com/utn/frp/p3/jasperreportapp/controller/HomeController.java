package com.utn.frp.p3.jasperreportapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showIndex() {
        return "index"; // nombre del archivo sin extensión
    }
}