package com.example.sondage.Controller;

import com.example.sondage.dto.OTP;
import com.example.sondage.Service.serviceUser.OTPInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/OTP")
@CrossOrigin("http://localhost:4200")
public class OTPController {
    @Autowired
    OTPInterface otpInterface;
    @PostMapping("/GenerateOTP")
    public OTP GenerateOTp() {
        return otpInterface.GenerateOTp();
    }
    @PostMapping("/VerifierOTP/{identification}")
    public Boolean VerifierOTP(@PathVariable("identification") String identification)  {
        return otpInterface.VerifierOTP(identification);
    }
    @PostMapping("/ResendOTP")
    public OTP ResendOTP(@RequestBody OTP existingOTP) {
        return otpInterface.ResendOTP(existingOTP);
    }
    @DeleteMapping("/DeleteOTP")
    public void DeleteOTP() {
        otpInterface.DeleteOTP();
    }
}
