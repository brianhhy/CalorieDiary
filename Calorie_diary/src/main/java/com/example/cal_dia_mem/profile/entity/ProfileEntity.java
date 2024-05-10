package com.example.cal_dia_mem.profile.entity;

import com.example.cal_dia_mem.member.dto.MemberDTO;
import com.example.cal_dia_mem.profile.dto.ProfileDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Setter
@Getter
@Table(name="memberProfile")
public class ProfileEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String memberEmail;
    @Column
    private String memberName;
    @Column
    private String gender;
    @Column
    private String height;
    @Column
    private String currentWeight;
    @Column
    private String purposeWeight;
    @Column
    private String purposeBMI;
    @Column
    private String muscle;
    @Column
    private String purposeMuscle;
    @Column
    private String bodyFat;
    @Column
    private String purposeBodyFat;
    public static ProfileEntity toprofileEntiy(ProfileDTO profileDTO){

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(profileDTO.getId());
        profileEntity.setMemberEmail(profileDTO.getMemberEmail());
        profileEntity.setMemberName(profileDTO.getMemberName());
        profileEntity.setGender(profileDTO.getGender());
        profileEntity.setHeight(profileDTO.getHeight());
        profileEntity.setCurrentWeight(profileDTO.getCurrentWeight());
        profileEntity.setPurposeWeight(profileDTO.getPurposeWeight());
        profileEntity.setPurposeBMI(profileDTO.getPurposeBMI());
        profileEntity.setMuscle(profileDTO.getMuscle());
        profileEntity.setBodyFat(profileDTO.getBodyFat());
        profileEntity.setPurposeMuscle(profileDTO.getPurposeMuscle());
        profileEntity.setPurposeBodyFat(profileDTO.getPurposeBodyFat());

        return profileEntity;
    }


}
