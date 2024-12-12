package com.example.sondage.Service.serviceUser;

import com.example.sondage.dto.OTP;

public interface OTPInterface {
    OTP GenerateOTp( );
    Boolean VerifierOTP ( String identification )  ;

    OTP ResendOTP(OTP existingOTP);
    void  DeleteOTP();
}
