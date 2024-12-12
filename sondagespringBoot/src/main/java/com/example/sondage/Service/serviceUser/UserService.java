package com.example.sondage.Service.serviceUser;

import com.example.sondage.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void deleteUser(Integer userId);
    List<User> getAllUser();
    User updateUser(User user);
    //public updatePassword(String username, ChangePasswordRequest updatePasswordDto);

    //String forgetPassword(String email);


    public Optional<User> getCurrentUser() ;

    public Integer getUserId(Authentication authentication);
}
