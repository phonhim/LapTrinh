package com.example.LapTrinh.controller;

import com.example.LapTrinh.Entities.User;
import com.example.LapTrinh.Service.UserService;
import com.example.LapTrinh.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;
    public AuthController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        return "/login/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "/login/register";
    }
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/login/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
        //redirect:/register?success dung de arlert message
    }
    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "/index";
    }
    @GetMapping("/login")
    public String login(){
        return "/login/login";
    }


}
