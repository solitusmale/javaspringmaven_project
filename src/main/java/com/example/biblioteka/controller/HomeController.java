package com.example.biblioteka.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities());
        }
        return "home"; // home.html Thymeleaf template
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // napravi admin.html template
    }

    @GetMapping("/user")
    public String userPage() {
        return "user"; // napravi user.html template
    }
}
