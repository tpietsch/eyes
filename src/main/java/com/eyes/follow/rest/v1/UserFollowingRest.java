package com.eyes.follow.rest.v1;


import com.eyes.follow.database.models.FollowEntity;
import com.eyes.follow.database.repositories.FollowRepository;
import com.eyes.user.database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.eyes.error.ErrorJsonResponse;
import com.eyes.authentication.AuthenticatedUserUtil;

import java.util.Set;

import static com.eyes.configuration.RestEndpointConstants.*;

@RestController
@RequestMapping(value = V1 + "/user/" + USER_ID_PATH + "/following",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFollowingRest {
    private static Logger logger = Logger.getLogger(UserFollowingRest.class);

    @Autowired
    FollowRepository followRepository;

    @Autowired
    AuthenticatedUserUtil authenticatedUserUtil;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> getAllUsersFollowedByThisUser(@PathVariable(USER_ID) String userId) {
        try {
            Set<FollowEntity> follows = followRepository
                    .findFollowingByUserId(userId);
            follows.parallelStream().forEach(followEntity -> {
                followEntity.setFollowByUserEntityByUserId(userRepository.findOne(followEntity.getFollowByUserId()));
            });
            follows.parallelStream().forEach(followEntity -> {
                followEntity.setFollowingUserEntityByUserId(userRepository.findOne(followEntity.getFollowingUserId()));
            });
            return ResponseEntity.ok(follows);
        } catch (Exception e) {
            ErrorJsonResponse errorJsonResponse = new ErrorJsonResponse("Unexpected Error.");
            return ResponseEntity.status(500).body(errorJsonResponse);
        }

    }
}


