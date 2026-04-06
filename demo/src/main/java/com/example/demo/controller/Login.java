package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.repository.UserRepo;
import jakarta.servlet.http.*;




@Controller
public class Login {

    @Autowired
    private UserRepo userRepo;
    
    @GetMapping("/")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postMethodName(HttpServletRequest request,
        HttpServletResponse response, Model model) throws IOException {
        
        HttpSession ses = request.getSession(false);
        if (ses!=null && ses.getAttribute("email") != null) {
            model.addAttribute("email", ses.getAttribute("email"));
            return "already";
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User user = userRepo.findByEmailAndPassword(email, password);
        
        if (user == null) {
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("email", user.getEmail());
        model.addAttribute("email", user.getEmail());
        
        return "home";
        
    }
    
    
}
