package org.example.gfgspringboot.controllers;

import org.example.gfgspringboot.models.*;
import org.example.gfgspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/users/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());

        user.setRole(UserRole.MEMBER);
        user.setStatus(UserStatus.ACTIVE);
        return ResponseEntity.ok(userService.registerUser(user));

    }

    @PostMapping("/v1/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        return ResponseEntity.ok(userService.loginUser(user));

    }

    @PostMapping("/v1/users/updatePassword")
    public ResponseEntity<String> login(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        User user = new User();
        user.setUsername(updatePasswordRequest.getUsername());
        user.setPassword(updatePasswordRequest.getOldPassword());
        return ResponseEntity.ok(userService.updatePasword(user, updatePasswordRequest.getNewPassword()));

    }


    @PostMapping("/v1/users/addAsset")
    public ResponseEntity<String> addAsset(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        User user = new User();
        user.setUsername(updatePasswordRequest.getUsername());
        user.setPassword(updatePasswordRequest.getOldPassword());
        Asset asset = new Asset();
        asset.setName("cesvesfd");
        asset.setType(AssetType.BOOK);
        AssetMetaData assetMetaData = new AssetMetaData();
        assetMetaData.setAuthor("aecdwacew");
        assetMetaData.setGenre("funy");
        assetMetaData.setPublishDate(new Date());
        asset.setMetaData(assetMetaData);
        return ResponseEntity.ok(userService.addAsset(user, asset));

    }
}
