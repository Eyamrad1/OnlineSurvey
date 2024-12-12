package com.example.sondage.Controller;

import com.example.sondage.dto.ChangePasswordRequest;
import com.example.sondage.entity.User;
import com.example.sondage.Repository.UserRepository;
import com.example.sondage.Service.serviceUser.UserService;
import com.example.sondage.Service.serviceUser.UserServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class UserController {

    private UserService userService;
    private UserServiceImp userServiceImp;
    private final UserRepository userRepository;

    @GetMapping("/getAllUser")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User>  updateUser(@PathVariable("id") Integer idUser, @RequestBody User user){
        user.setIdUser(idUser);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer idUser){
        userRepository.deleteById(idUser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("forgetpassword/{email}")
    public ResponseEntity<?> userForgetPassword(@PathVariable("email") String email) {
        return userServiceImp.userforgetpassword(email);
    }

    @PutMapping("forgetpass/{username}")
    public ResponseEntity<?> forgetPassword(@PathVariable("username") String username, @RequestBody ChangePasswordRequest resetPass) {
        return userServiceImp.updatePassword(username, resetPass);
    }




    @PutMapping("forgetpassbyemail/{email}")
    public ResponseEntity<?> forgetPasswordbyemail(@PathVariable("email") String email, @RequestBody ChangePasswordRequest resetPass) {
        return userServiceImp.updatePasswordBymail(email, resetPass);
    }




    @GetMapping("/api/user/id")
    public Integer getUserId(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            // Assuming your UserDetails implementation has the user ID
            return userDetails.getIdUser();
        } else {
            // Handle the case when authentication is null or does not contain UserDetails
            return null; // or throw an exception, depending on your requirement
        }
    }
    }

