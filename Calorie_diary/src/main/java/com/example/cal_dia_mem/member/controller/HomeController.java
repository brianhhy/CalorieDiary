package com.example.cal_dia_mem.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    //시작하기
    public String index(){ return "start";}
}