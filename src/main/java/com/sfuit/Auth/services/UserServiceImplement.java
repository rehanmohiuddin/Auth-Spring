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
    public User validateUser(String email, String password) throws EtAuthException
    {
        if(email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String email, String name, String password, String dob, String phone, String otp, String is_verified) throws EtAuthException {

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");
        //Now, checking whether is already present in db or not,
        //for that, we are using getCountByEMail method declared in repo
        //if count> 0, email exists, cannot register again

        Integer count = userRepository.getCountByEmail(email);

        if(count>0)
            throw new EtAuthException("Email already in use");
        Integer userId = userRepository.create(email, name, password, dob, phone, otp, is_verified);
        return userRepository.findById(userId);
    }
}
