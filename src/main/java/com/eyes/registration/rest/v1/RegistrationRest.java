package com.eyes.registration.rest.v1;


import com.eyes.user.database.models.UserEntity;
import com.eyes.user.database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.eyes.error.ErrorJsonResponse;

import static com.eyes.configuration.RestEndpointConstants.V1;

@RestController
@RequestMapping(value = V1 + "/register",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationRest {
    private static Logger logger = Logger.getLogger(RegistrationRest.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> register(@RequestBody LoginCreds creds) {
        UserEntity userEntity = userRepository.findByEmail(creds.username);
        if (userEntity != null) {
            return ResponseEntity.status(409).body(new ErrorJsonResponse("User Exists"));
        }
        userEntity = new UserEntity();
        userEntity.setEmail(creds.username);
        userEntity.setPassword(bCryptPasswordEncoder.encode(creds.password));
        userEntity = userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    public static class LoginCreds {
        public String username;
        public String password;
    }


}


