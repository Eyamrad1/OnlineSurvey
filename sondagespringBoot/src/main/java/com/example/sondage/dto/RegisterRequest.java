package com.example.sondage.dto;

import com.example.sondage.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {

    private String username;
    private String email;
    private String password;
    private RoleName roleName;



}
