package org.example.gfgspringboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.example.gfgspringboot.clients.WeatherClient;
import org.example.gfgspringboot.models.*;
import org.example.gfgspringboot.pubsub.ProducerService;
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
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.redis.connection.ReactiveStreamCommands.AddStreamRecord.body;

@Slf4j
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

   // @Autowired
    //private WeatherClient weatherClient;
   @Autowired
   private ProducerService producerService;


   private WeatherClient weatherClient;

   private static int numberOfCalls  = 0;
    @Autowired
    public UserController(UserService userService, WeatherClient weatherClient) {
        this.userService = userService;
        this.weatherClient = weatherClient;
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
    @GetMapping("/v1/weather")
    public String getWeather(@RequestParam String city) throws IOException, ParseException {
        try{
            String weather = weatherClient.getWeather(city);
            log.info("request recieved weather " + city);
            return weather;
        } catch (ParseException e) {
            e.printStackTrace();
        }

       return null;
    }
    @PostMapping("/v1/users/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) throws IOException, ParseException {
       // weatherClient.getWeather("paris");

        //producerService.sendMessage("1");
        //producerService.sendMessage("2");
        //producerService.sendMessage("3");


        if(redisCacheManager.getCache("user").get(registerRequest.getEmail()) != null) {
            return ResponseEntity.ok("User already exists");
        }
        //CloseableHttpClient client = HttpClients.createDefault();
       // String response = weatherClient.getCurrentWeather("london", "4d13d7ea7e6f4f789ae163958251706");
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());

        user.setRole(UserRole.MEMBER);
        user.setStatus(UserStatus.ACTIVE);
        return ResponseEntity.ok(userService.registerUser(user));
        /*redisMessagePublisher.publish("one new user found");
        System.out.println("controller thread:"+Thread.currentThread().getName());
        ObjectMapper objectMapper = new ObjectMapper();

        Map map = objectMapper.convertValue(user, Map.class);
        redisCacheManager.getCache("user").put(user.getEmail(), map);
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory2);*/
        //Object obj = template.opsForValue().get(user.getEmail());
       // Objects.requireNonNull(redisCacheManager.getCache("user")).put(user.getUsername(), map);

      //return ResponseEntity.ok("User Registered Successfully");
    }



    /*@GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        // 'principal' contains the user attributes from the provider (Google)
        return principal.getAttributes();
    }*/

    @GetMapping("/session-expired")
    public String sessionExpired() {
        return "session-expired";
    }

    @GetMapping("/login/oauth2/code/github")
    public String oathredirect() {
        return "successful login";
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
