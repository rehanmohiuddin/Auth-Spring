package com.sfuit.Auth.contorller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sfuit.Auth.Constants;
import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;
    @Value("${jwt.secret}")
    private String secret;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String >> loginUser(@RequestBody Map<String, Object> userMap)
    {

        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap)
    {
        String email = (String) userMap.get("email");
        String name = (String) userMap.get("name");
        String password = (String) userMap.get("password");
        String dob = (String) userMap.get("dob");
        String phone = (String) userMap.get("phone");
        String otp = (String) userMap.get("otp");
        String is_verified = (String) userMap.get("is_verified");

        User user = userService.registerUser(email, name, password, dob, phone, otp, is_verified);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    //JWT token is created, user is parameter and returns map<string,string>
    private Map<String, String > generateJWTToken(User user)
    {

        Map<String, String> map = new HashMap<>();
            Algorithm algorithm = Algorithm.HMAC256(Constants.API_SECRET_KEY);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId",user.getUserId())
                    .withClaim("email",user.getEmail())
                    .withClaim("name",user.getName())
                    .withClaim("dob",user.getDob())
                    .withClaim("phone",user.getPhone())
                    .withExpiresAt(new Date(System.currentTimeMillis() + Constants.TOKEN_VALIDITY))
                    .sign(algorithm);
            map.put("token", token);
            return map;
    }
}
