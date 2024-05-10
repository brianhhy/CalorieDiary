package com.example.cal_dia_mem.api.controller;

import com.example.cal_dia_mem.api.Service.ApiService;
import com.example.cal_dia_mem.api.dto.ApiDTO;
import jakarta.servlet.http.HttpSession;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@org.springframework.web.bind.annotation.RestController
@Controller
public class RestController {
    @Autowired
    ApiService apiService;
    @GetMapping("/api")
    public String foodData(Model model) throws ParseException {
        return "/api/fooddb";
    }

    @PostMapping("/api/foodData")
    public String callApi(Model model, @RequestParam String food_name, HttpSession session) throws ParseException{
        String myEmail=(String)session.getAttribute("sessionEmail");
        model.addAttribute("memberEmail",myEmail);
        String jsonStr;
       // System.out.println(food_name);
        jsonStr= apiService.callApi(food_name);
        List<ApiDTO> datalist = apiService.jsonParse(jsonStr);
        model.addAttribute("datalist",datalist);
       // System.out.println(datalist);
        return "/api/fooddb";
    }

}

