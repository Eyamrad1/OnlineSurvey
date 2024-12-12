package com.example.sondage.dto;

import com.example.sondage.entity.RoleName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private String token;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private Integer idUser;
    private RoleName roleName;



}
