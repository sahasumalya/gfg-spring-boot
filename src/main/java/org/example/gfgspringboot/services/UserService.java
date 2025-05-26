package org.example.gfgspringboot.services;

import org.example.gfgspringboot.models.Asset;
import org.example.gfgspringboot.models.User;
import org.example.gfgspringboot.repositories.AssetRepository;
import org.example.gfgspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(User user) {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                return "Email Already Exists";
            }
            userRepository.save(user);
            return "User Registered Successfully";
    }

    public String loginUser(User user) {
        if (userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()) != null) {
            return "Logged in Successfully";
        }
        return "User Not Found";
    }


    public String updatePasword(User user, String newPassword) {
        User daoUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (daoUser != null) {
            daoUser.setPassword(newPassword);
            userRepository.save(daoUser);
            return "Password Updated Successfully";
        }
        return "User Not Found";
    }

    public String addAsset(User user, Asset asset) {
        User daoUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(daoUser.getContributions() == null) {
            daoUser.setContributions(new ArrayList<>());
        }
        daoUser.getContributions().add(asset);
        userRepository.save(daoUser);
        return "Asset Added Successfully";
    }
}
