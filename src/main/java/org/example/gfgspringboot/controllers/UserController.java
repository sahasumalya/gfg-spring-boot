package org.example.gfgspringboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.gfgspringboot.models.*;
import org.example.gfgspringboot.pubsub.RedisMessagePublisher;
import org.example.gfgspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.redis.connection.ReactiveStreamCommands.AddStreamRecord.body;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    @Qualifier(value="redisConnectionFactory2")
    private LettuceConnectionFactory redisConnectionFactory2;

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        //model.addAttribute("message", "You are successfully logged in.");
        return "home";
    }
    @PostMapping("/v1/users/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if(redisCacheManager.getCache("user").get(registerRequest.getEmail()) != null) {
            return ResponseEntity.ok("User already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());

        user.setRole(UserRole.MEMBER);
        user.setStatus(UserStatus.ACTIVE);
        redisMessagePublisher.publish("one new user found");
        System.out.println("controller thread:"+Thread.currentThread().getName());
        ObjectMapper objectMapper = new ObjectMapper();

        Map map = objectMapper.convertValue(user, Map.class);
        redisCacheManager.getCache("user").put(user.getEmail(), map);
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory2);
        //Object obj = template.opsForValue().get(user.getEmail());
       // Objects.requireNonNull(redisCacheManager.getCache("user")).put(user.getUsername(), map);
        return ResponseEntity.ok(userService.registerUser(user));

    }

    @GetMapping("/session-expired")
    public String sessionExpired() {
        return "session-expired";
    }

    @PostMapping("/v1/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        String token = userService.loginUser(user);
        HttpHeaders headers = new HttpHeaders();
        // token="+token
        headers.add("Set-Cookie","token=" + token+ "; Max-Age=604800; Path=/; Secure; HttpOnly;");
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).headers(headers).body("Login Success");

        return responseEntity;

    }

    @PostMapping("/v1/users/updatePassword")
    public ResponseEntity<String> login(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        User user = new User();
        user.setUsername(updatePasswordRequest.getUsername());
        user.setPassword(updatePasswordRequest.getOldPassword());
        return ResponseEntity.ok(userService.updatePasword(user, updatePasswordRequest.getNewPassword()));

    }


    @PostMapping("/v1/users/addAsset")
    public ResponseEntity<String> addAsset(@RequestBody AddAssetRequest addAssetRequest) {
        Asset asset = new Asset();
        asset.setName(addAssetRequest.getAssetName());
        asset.setType(addAssetRequest.getAssetType());
        AssetMetaData assetMetaData = new AssetMetaData();
        assetMetaData.setAuthor(addAssetRequest.getAuthor());
        assetMetaData.setGenre(addAssetRequest.getGenre());
        asset.setMetaData(assetMetaData);
        return ResponseEntity.ok(userService.addAsset(addAssetRequest.getUserId(), asset));

    }
    /*
       Asset--> [ contributor---> List of contribution]
     */

    @GetMapping("/v1/users/getContributions")
    public ResponseEntity<List<Asset>> getContributions(@RequestParam String username) {
        return ResponseEntity.ok(userService.getAllAssetsForUser(username));
    }
}
