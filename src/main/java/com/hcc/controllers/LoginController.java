package com.hcc.controllers;

import com.hcc.dtos.AuthCredentialRequest;
import com.hcc.entities.User;
import com.hcc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    // handles login  /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialRequest authCredentialRequest) throws Exception {
        Authentication authObject;

        try {
            authObject = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(
                            authCredentialRequest.getUsername(), authCredentialRequest.getPassword()));
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
                    .body(user.getUsername());

        }catch (BadCredentialsException exception){
            throw new Exception("Credentials Invalid");
        }
    }

    // validates tokens  /api/auth/validate
    @GetMapping("/validate")
    ResponseEntity<?> validate(@RequestParam String token, @AuthenticationPrincipal User user) {

        boolean isValid;
        // check if the user is null and validate token
        if (user != null) {
            isValid = jwtUtils.validateToken(token, user);
        } else {
            return ResponseEntity.ok(false);
        }

       return ResponseEntity.ok(isValid);
    }
}
