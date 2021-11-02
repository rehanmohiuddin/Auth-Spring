package com.sfuit.Auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class Constants {


    public static final String API_SECRET_KEY = "Reh4nPr2t77kR0Hit4B56SoeKOmERItsapror8sfuitusersapikey";
    public static final long TOKEN_VALIDITY = 5* 60 * 1000;
    public static final long UPDATED_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;

}
