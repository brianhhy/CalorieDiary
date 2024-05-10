package com.example.cal_dia_mem.diary.controller;

import com.example.cal_dia_mem.api.dto.ApiDTO;
import com.example.cal_dia_mem.diary.dto.DiaryDTO;
import com.example.cal_dia_mem.diary.entity.DiaryEntity;
import com.example.cal_dia_mem.diary.service.DiaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    @PostMapping("/diary/save")
    public String savaData(@ModelAttribute DiaryDTO diaryDTO, HttpServletRequest request) {
        diaryService.save(diaryDTO);
        request.setAttribute("message","저장 되었습니다.");
        request.setAttribute("searchUrl","/diary");

        return "/member/message";
    }

    @GetMapping("/diary")
    public String diary(HttpServletRequest request, Model model, HttpSession session){
        String myEmail = (String) session.getAttribute("sessionEmail");
        Date todayDate = new Date(System.currentTimeMillis());
        // 오늘 날짜와 멤버 이메일을 매개변수로 회원 별 오늘 섭취한 음식 및 영양성분 받아오기
        List<DiaryDTO> list=diaryService.callDiary(todayDate,myEmail);
        double Carboydrate = diaryService.totalCarbohydrate(list);
        double protien = diaryService.totalProtein(list);
        double fat = diaryService.totalFat(list);
        double sugars = diaryService.totalSugars(list);
        double salt = diaryService.totalSalt(list);
        double kcal= diaryService.totalKcal(list);
        //int id = list.get(0).getId();

        //System.out.println(id);

        model.addAttribute("kcal",kcal);
        model.addAttribute("list",list);
        model.addAttribute("Carboydrate",Carboydrate);
        model.addAttribute("protien",protien);
        model.addAttribute("fat",fat);
        model.addAttribute("sugars",sugars);
        model.addAttribute("salt",salt);


        return "/camera/diary";
    }

    @GetMapping("/diary/delete")
    public String boardDelete(Integer id){
        diaryService.diaryDelete(id);
        return "redirect:/diary";
    }


}
