package com.example.cal_dia_mem.profile.controller;

import com.example.cal_dia_mem.board.entity.BoardEntity;
import com.example.cal_dia_mem.profile.dto.ProfileDTO;
import com.example.cal_dia_mem.profile.entity.ProfileEntity;
import com.example.cal_dia_mem.profile.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @GetMapping("/profile/modify")

    // 프로필 수정 시 사용자 기존 데이터 출력
    public String memberProfile(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("sessionEmail");

        ProfileDTO profileDTO = profileService.modifyProfile(myEmail);
        double bminum = 1;
        if(!profileDTO.getCurrentWeight().equals("")&&!profileDTO.getHeight().equals("")) {
            bminum = Double.parseDouble(profileDTO.getCurrentWeight()) / (Double.parseDouble(profileDTO.getHeight()) / 100)
                    / (Double.parseDouble(profileDTO.getHeight()) / 100);
        }
        String bmi = String.format("%.1f", bminum);

        //목표 골격근
        if (!profileDTO.getMuscle().equals("") && !profileDTO.getPurposeMuscle().equals("")) {
            // 목표가 현재보다 크다면?--> 근육량 증량이 목표
            if(Integer.parseInt(profileDTO.getMuscle())<=Integer.parseInt(profileDTO.getPurposeMuscle())){
                Integer percentMuscle = Integer.parseInt(profileDTO.getMuscle()) * 100 / Integer.parseInt(profileDTO.getPurposeMuscle());
                model.addAttribute("percentMuscle",percentMuscle);
            }else{
                Integer percentMuscle = Integer.parseInt(profileDTO.getPurposeMuscle())* 100/Integer.parseInt(profileDTO.getMuscle());
                model.addAttribute("percentMuscle",percentMuscle);
            }

        }

        //목표 bmi
        if (!profileDTO.getCurrentWeight().equals("") && !profileDTO.getHeight().equals("") && !profileDTO.getPurposeBMI().equals("")) {

            //목표BMI가 현재BMI보다 크다면-->증량이 목적
            if(Double.parseDouble(profileDTO.getCurrentWeight()) / (Double.parseDouble(profileDTO.getHeight()) / 100)
                    / (Double.parseDouble(profileDTO.getHeight()) / 100)<=Double.parseDouble(profileDTO.getPurposeBMI())){

                double percentBmi = Double.parseDouble(profileDTO.getCurrentWeight()) / (Double.parseDouble(profileDTO.getHeight()) / 100)
                        / (Double.parseDouble(profileDTO.getHeight()) / 100) * 100 / Double.parseDouble(profileDTO.getPurposeBMI());

                Integer percentBMI = (int) percentBmi;
                model.addAttribute("percentBMI",percentBMI);
            }else{
                double current=Double.parseDouble(profileDTO.getCurrentWeight())/(Double.parseDouble(profileDTO.getHeight()) / 100)
                        / (Double.parseDouble(profileDTO.getHeight()) / 100);
                double percentBmi=(Double.parseDouble(profileDTO.getPurposeBMI())*100)/current;

                Integer percentBMI = (int) percentBmi;
                model.addAttribute("percentBMI",percentBMI);
            }
        }

        //목표 체지방
        if (!profileDTO.getPurposeBodyFat().equals("") && !profileDTO.getBodyFat().equals("")) {

            //목표 체지방이 현재 체지방보다 높다면 --> 벌크업이 목적
            if(Integer.parseInt(profileDTO.getBodyFat())<=Integer.parseInt(profileDTO.getPurposeBodyFat())){

                Integer percentBodyFat= Integer.parseInt(profileDTO.getBodyFat())*100/ Integer.parseInt(profileDTO.getPurposeBodyFat());
                model.addAttribute("percentBodyFat",percentBodyFat);

            }else{
                Integer percentBodyFat = Integer.parseInt(profileDTO.getPurposeBodyFat()) * 100 / Integer.parseInt(profileDTO.getBodyFat());
                model.addAttribute("percentBodyFat",percentBodyFat);
            }
        }
        //목표 체중
        if (!profileDTO.getCurrentWeight().equals("") && !profileDTO.getPurposeWeight().equals("")) {
            //목표 체중이 현재 체중보다 높다면 --> 증량이 목적
            if(Integer.parseInt(profileDTO.getCurrentWeight())<=Integer.parseInt(profileDTO.getPurposeWeight())){

                Integer percentWeight = Integer.parseInt(profileDTO.getCurrentWeight()) * 100 / Integer.parseInt(profileDTO.getPurposeWeight());
                model.addAttribute("percentWeight",percentWeight);
            }else {
                Integer percentWeight =Integer.parseInt(profileDTO.getPurposeWeight())*100 / Integer.parseInt(profileDTO.getCurrentWeight()) ;
                model.addAttribute("percentWeight",percentWeight);
            }
        }

        model.addAttribute("modifyProfile",profileDTO);
        model.addAttribute("bmi",bmi);
        return "/profile/profile";

    }

    @PostMapping("/profile/modify")
    public String profileUpdate(@ModelAttribute ProfileDTO profileDTO, HttpServletRequest request){

        profileService.save(profileDTO);
        request.setAttribute("message","회원 정보 수정이 완료되었습니다.");
        request.setAttribute("searchUrl","/index/call");
        return "/member/message";
    }
}
