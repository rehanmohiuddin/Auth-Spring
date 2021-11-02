package com.sfuit.Auth.filtres;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sfuit.Auth.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader != null)
        {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if(authHeaderArr.length > 1 && authHeaderArr[1] != null)
            {
                String token = authHeaderArr[1];
                try {
                    Algorithm algorithm = Algorithm.HMAC256(Constants.API_SECRET_KEY);
                    JWTVerifier verifier = JWT.require(algorithm)
                            .withIssuer("auth0")
                            .build();
                    DecodedJWT jwt = verifier.verify(token);
                    httpRequest.setAttribute("data", jwt.getPayload());
                }catch (Exception e)
                {
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), e.toString());
                    return;
                }
            }
            else
            {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be a Bearer [token]");
                return;
            }
        }
        else
        {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
