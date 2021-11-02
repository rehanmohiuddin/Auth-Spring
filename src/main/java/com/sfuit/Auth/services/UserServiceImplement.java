package com.sfuit.Auth.services;

import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.exceptions.EtAuthException;
import com.sfuit.Auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Pattern;

@Service
@Transactional // if the methods within this class are called, either commits or rollback
public class UserServiceImplement implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String phone, String otp) throws EtAuthException
    {
        return userRepository.findByPhoneandOTP(phone, otp);
    }

    @Override
    public User registerUser(String email, String name, String dob, String phone, String otp, String is_verified, String token) throws EtAuthException {

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Pattern phone_pattern = Pattern.compile("^?[5-9][0-9]{9}$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");
        if(!phone_pattern.matcher(phone).matches())
            throw new EtAuthException("Invalid phone number format");
        //Now, checking whether is already present in db or not,
        //for that, we are using getCountByEMail method declared in repo
        //if count> 0, email exists, cannot register again

        Integer count_email = userRepository.getCountByEmail(email);
        Integer count_phone = userRepository.getCountByPhone(phone);


        if(count_email>0)
            throw new EtAuthException("Email already in use");
        if(count_phone>0)
            throw new EtAuthException("Phone number already in use");
        Integer userId = userRepository.create(email, name, dob, phone, otp, is_verified, token);
        return userRepository.findById(userId);
    }
}
