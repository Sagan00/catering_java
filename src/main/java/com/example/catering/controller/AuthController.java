package com.example.catering.controller;
import jakarta.validation.Valid;
import com.example.catering.dto.UserDto;
import com.example.catering.entity.User;
import com.example.catering.service.UserService;
import com.example.catering.entity.Bmr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }
    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }
    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/menu")
    public String menu(){
        return "menu";
    }
    @GetMapping("/main")
    public String main(){
        return "main";
    }
    @GetMapping("/order")
    public String order(){
        return "order";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
    @GetMapping("/calcbmi")
    public String calcbmi(){
        return "calcbmi";
    }
    @PostMapping("/calculateBMR")
    public String calculateBMR(Model model, int age, String gender, double weight, double height, String physicalActivity, String goal ) {
        double bmr = Bmr.calculateBMRValue(age, gender, weight, height, physicalActivity, goal);
        model.addAttribute("bmr", bmr);
        return "calcbmi";
    }


}