package com.example.gonzalezdariomyikea.controllers;
import com.example.gonzalezdariomyikea.models.User;
import com.example.gonzalezdariomyikea.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login()
    {
        return "auth/login";
    }
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.newUser(user);
        return "redirect:/auth/login";
    }
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserById(email);
        List<User> users = userService.getAllUsers();
        users.remove(user);
        model.addAttribute("users", users);
        return "auth/users";
    }
    @GetMapping("/deleteUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return "redirect:/usuarios";
    }


}
