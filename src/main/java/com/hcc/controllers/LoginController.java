package com.hcc.controllers;

import com.hcc.entities.User;
import com.hcc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    // handles login  /api/auth/login
    @PostMapping("/api/auth/login")
    ResponseEntity<?> login(@RequestBody String username, @RequestBody String password) throws Exception {
        Authentication authObject;

        try {
            authObject = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authObject);
            User user = (User) authObject.getPrincipal();
            // set password to null so that we keep confidential information safe in our tokens
            user.setPassword(null);
            // use auth manager to generate a token to pass along
            String token = jwtUtils.generateToken(user);
            // create a response entity with auth header and a generated
            //  jwt token in the header, and the user in the response body
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(user);

        }catch (BadCredentialsException exception){
            throw new Exception("Credentials Invalid");
        }
    }

    // validates tokens  /api/auth/validate

    // take in a token

    // check if the user is null

    // validate the token

    // return a response w/ true or false
    //  if exception, return false w/ exception

}
