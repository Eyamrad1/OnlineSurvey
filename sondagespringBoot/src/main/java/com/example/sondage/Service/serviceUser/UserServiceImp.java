package com.example.sondage.Service.serviceUser;

import com.example.sondage.dto.ChangePasswordRequest;
import com.example.sondage.entity.User;
import com.example.sondage.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Aspect
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailUtil;

    @Autowired
    private OTPInterface otpInterface;

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getIdUser()).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    public ResponseEntity<?> updatePassword(String username, ChangePasswordRequest updatePasswordDto) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {

            user.get().setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
            userRepository.save(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    public ResponseEntity<?> userforgetpassword(String identifier) {
        Optional<User> user = userRepository.findByUsername(identifier);
        if (!user.isPresent()) {
            user = userRepository.findByEmail(identifier);
        }

        if (user.isPresent()) {
            String verificationCode = otpInterface.GenerateOTp().getIdentification();
            String newLine = "<br/>"; // HTML line break
            String htmlMessage = "<div style='border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;'>"
                    + "A password reset attempt has been made. " + newLine
                    + "<strong>Verification Code:</strong>" +
                    "<p>NB: This code is valid for 15 minutes </p> " + verificationCode + newLine
                    + "</div>";
            try {
                emailUtil.send(user.get().getEmail(), "Password Reset Request for " + user.get().getUsername(), htmlMessage);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updatePasswordBymail(String identifier, ChangePasswordRequest updatePasswordDto) {
        Optional<User> user = userRepository.findByUsername(identifier);
        if (!user.isPresent()) {
            user = userRepository.findByEmail(identifier);
        }

        if (user.isPresent()) {
            Boolean verif = otpInterface.VerifierOTP(updatePasswordDto.getCode());
            if (!verif) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                user.get().setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
                userRepository.save(user.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username);
    }

    public Integer getUserId(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            return userDetails.getIdUser();
        } else {
            return null; // or throw an exception
        }
    }

}
