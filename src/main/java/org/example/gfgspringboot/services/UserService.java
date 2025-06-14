package org.example.gfgspringboot.services;

import org.example.gfgspringboot.models.Asset;
import org.example.gfgspringboot.models.User;
import org.example.gfgspringboot.repositories.AssetRepository;
import org.example.gfgspringboot.repositories.UserRepository;
import org.example.gfgspringboot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final AssetRepository assetRepository;
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, AssetRepository assetRepository) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
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
            JwtUtil jwtUtil = new JwtUtil();
            return jwtUtil.generateToken(user.getUsername());
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

    public String addAsset(Long userId, Asset asset) {
        Optional<User> daoUser = userRepository.findById(userId);
        if (daoUser.isPresent()) {
            asset.setContributor(daoUser.get());
        } else{
            return "User Not Found";
        }

        assetRepository.save(asset);
        return "Asset Added Successfully";
    }

    public List<Asset> getAllAssetsForUser(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return user.getContributions();
        }
        return new ArrayList<>();
    }

    public void getPagebaleUsers(){
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<User> page = userRepository.findAll(pageRequest);

    }
}
