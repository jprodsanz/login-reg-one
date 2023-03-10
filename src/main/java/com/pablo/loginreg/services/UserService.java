package com.pablo.loginreg.services;

import com.pablo.loginreg.models.LoginUser;
import com.pablo.loginreg.models.User;
import com.pablo.loginreg.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User register(User u, BindingResult result){
        if (!u.getConfirmPass().equals(u.getPassword())) {
            result.rejectValue("confirmPass", null, "Error, passwords do not match");
        }
        if (result.hasErrors()) {
            return null;
        }
        return userRepo.save(u);
    }
    public User login(LoginUser l, BindingResult result) {
        Optional<User> optUser = userRepo.findByEmail(l.getEmail());
        if (optUser.isEmpty()) {
            return null;
        }
        User user = optUser.get();
        if (user.getPassword().equals(l.getPassword())) {
            return null;
        }
        return user;
    }
    public User findById(Long id) {
        Optional<User> optUser= userRepo.findById(id);
        if (optUser.isEmpty()) {
            return null;
        }
        return optUser.get();
    }
}
