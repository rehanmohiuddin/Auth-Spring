package com.sfuit.Auth.contorller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sfuit.Auth.Constants;
import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.services.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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

    private final static String ACCOUNT_SID = "ACcf820ee0c53043c3b0cc092c9e138c4d";
    private final static String AUTH_TOKEN = "dc1911ae00029a5e4b4316add054d1da";

    @Autowired
    UserService userService;
    @Value("${jwt.secret}")
    private String secret;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String >> loginUser(@RequestBody Map<String, Object> userMap)
    {

        String phone = (String) userMap.get("phone");
        String otp = (String) userMap.get("otp");
        User user = userService.validateUser(phone, otp);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap)
    {
        int min = 1000;
        int max = 9999;
        int random_num = (int)Math.floor(Math.random()*(max-min+1)+min);

        String email = (String) userMap.get("email");
        String name = (String) userMap.get("name");
        String dob = (String) userMap.get("dob");
        String phone = (String) userMap.get("phone");
        String otp = Integer.toString(random_num);
        String is_verified = (String) userMap.get("is_verified");

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+919381596810"),
                new PhoneNumber("+14195065758"),
                "Your 4-digit otp is \n" + otp +"\n valid for 5 minutes only")
                .create();

        Algorithm algorithm = Algorithm.HMAC256(Constants.API_SECRET_KEY);
        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("email",email)
                .withClaim("name",name)
                .withClaim("dob",dob)
                .withClaim("phone",phone)
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.TOKEN_VALIDITY))
                .sign(algorithm);

        User user= userService.registerUser(email, name, dob, phone, otp, is_verified, token);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    //JWT token is created, user is parameter and returns map<string,string>
    private Map<String, String > generateJWTToken(User user)
    {

        Map<String, String> map = new HashMap<>();
            Algorithm algorithm = Algorithm.HMAC256(Constants.API_SECRET_KEY);
            String token = JWT.create()
                    .withIssuer("auth0")
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
