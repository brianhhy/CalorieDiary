package com.example.cal_dia_mem.profile.service;

import com.example.cal_dia_mem.board.entity.BoardEntity;
import com.example.cal_dia_mem.member.repository.MemberRepository;
import com.example.cal_dia_mem.profile.dto.ProfileDTO;
import com.example.cal_dia_mem.profile.entity.ProfileEntity;
import com.example.cal_dia_mem.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private final ProfileRepository profileRepository;

    // 회원 가입 시 회원 기본 정보 저장
    public void save(ProfileDTO profileDTO) {

        ProfileEntity profileEntity = ProfileEntity.toprofileEntiy(profileDTO);
        profileRepository.save(profileEntity);
    }

    // 프로필 수정을 위한 함수
    public ProfileDTO modifyProfile(String myEmail) {
        Optional<ProfileEntity> optionalProfileEntity = profileRepository.findByMemberEmail(myEmail);
        if (optionalProfileEntity.isPresent()) {
            return ProfileDTO.toProfileDTO(optionalProfileEntity.get());
        } else {
            return null;
        }
    }

    // 멤버 이메일별 회원의 성별을 리턴
    public String returnGen(String myEmail){

        Optional<ProfileEntity> optionalProfileEntity = profileRepository.findByMemberEmail(myEmail);
        if(optionalProfileEntity.isPresent()){
            ProfileEntity profileEntity = optionalProfileEntity.get();
            return profileEntity.getGender();
        }

        else return "m";
    }

    // 멤버 이메일별 회원의 현제 체중 리턴
    public String returnCurrentWeight(String myEmail){

        Optional<ProfileEntity> optionalProfileEntity = profileRepository.findByMemberEmail(myEmail);
        if(optionalProfileEntity.isPresent()){
            ProfileEntity profileEntity = optionalProfileEntity.get();
            return profileEntity.getCurrentWeight();
        }
        else return "1";
    }
}
