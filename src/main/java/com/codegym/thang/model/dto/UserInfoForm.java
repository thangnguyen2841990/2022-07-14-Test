package com.codegym.thang.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoForm {

    private String email;

    private String phoneNumber;

    private String address;

    private String birthDay;

}
