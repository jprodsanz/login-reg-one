package com.pablo.loginreg.controllers;

import com.pablo.loginreg.models.LoginUser;
import com.pablo.loginreg.models.User;
import com.pablo.loginreg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MainController {
   @Autowired
    private UserService userServ;
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("loginUser", new LoginUser());
        return "index.jsp";
    }
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user")
            User user, BindingResult result, Model model, HttpSession session) {
        // run register method
        User newUser = userServ.register(user, result);
        if (newUser == null) {
            model.addAttribute("loginUser", new LoginUser());
            return "index.jsp";
        }
        //  add user to DB
        // log user in
        session.setAttribute("userId", newUser.getId());
        return "redirect:/dashboard";
    }
    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("loginUser")
            LoginUser loginuser, BindingResult result, Model model , HttpSession session) {
        // run login method
        User user = userServ.login(loginuser, result);
        if (user == null) {
            model.addAttribute("user", new User());
            return "index.jsp";
        }
        session.setAttribute("userId", user.getId());
        return "redirect:/dashboard";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        User loggedUser = userServ.findById(id);
        model.addAttribute("user", loggedUser);
        return "dashboard.jsp";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("userId", null);
        return "redirect:/";
    }
}
