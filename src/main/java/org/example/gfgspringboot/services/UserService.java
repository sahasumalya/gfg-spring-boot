package org.example.gfgspringboot.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.example.gfgspringboot.models.Asset;
import org.example.gfgspringboot.models.User;
import org.example.gfgspringboot.repositories.AssetRepository;
import org.example.gfgspringboot.repositories.UserRepository;
import org.example.gfgspringboot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@EnableRetry
public class UserService {

    private final AssetRepository assetRepository;
    private UserRepository userRepository;
    private static int numberOfCalls = 0;

    @Autowired
    public UserService(UserRepository userRepository, AssetRepository assetRepository) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
    }

    @Retry(name = "default")
    @CircuitBreaker(name = "webClientCircuitBreaker", fallbackMethod = "fallbackWebAPICircuitBreaker")
    public String registerUser(User user) {
            String token = callUnstableAPI();
            if (userRepository.findByEmail(user.getEmail()) != null) {
                return "Email Already Exists";
            }
            userRepository.save(user);
            return "User Registered Successfully";
    }
    public String callUnstableAPI() {
        try{
            return callWebAPI();
        } catch (RuntimeException e){
            throw e;
        }

    }

    public String fallbackWebAPI(User user, Exception e) {
        return "fallbackWebAPI";
    }
    public String fallbackWebAPICircuitBreaker(User user, Exception e) {
        return "fallbackWebAPICircuitBreaker";
    }
    private String callWebAPI()  {
        numberOfCalls++;
       /*if(numberOfCalls % 3 == 0) {
            return "success";
        }*/
        throw new RuntimeException("test exception");
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
