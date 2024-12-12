package com.example.sondage.Service.serviceUser;

import com.example.sondage.dto.OTP;
import com.example.sondage.Repository.OTPRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor

public class OTPServiceIMP implements OTPInterface {
    private OTPRepository otpRepository;

    @Override
    public OTP GenerateOTp() {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 15); // Set the expiration time to 15 minutes
        Date expiredDate = calendar.getTime();

        OTP otpObject = new OTP();
        otpObject.setIdentification(String.valueOf(otp));
        otpObject.setExpiredDate(expiredDate);
        otpRepository.save(otpObject);
        return otpObject;
    }

    @Override
    public Boolean VerifierOTP(String identification) {
        OTP otp = otpRepository.findByIdentification(identification);
        if (otp == null)
            return false;
        Date expiredDate = otp.getExpiredDate();
        Date now = new Date();
        return now.before(expiredDate);
    }

    @Override
    public OTP ResendOTP(OTP existingOTP) {
        Date now = new Date();
        if (existingOTP.getExpiredDate().before(now))
            return GenerateOTp();
        else {
            return existingOTP;
        }
    }

    @Override
    public void DeleteOTP() {
        otpRepository.deleteAll();
    }
}
