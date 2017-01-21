package rest.v1;


import database.models.FollowEntity;
import database.repositories.FollowRepository;
import database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rest.v1.models.ErrorJsonResponse;
import security.util.CurrentUserUtil;

import java.util.Set;

import static rest.RestEndpointConstants.*;

@RestController
@RequestMapping(value = V1 + "/user/" + USER_ID_PATH + "/following",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFollowingRest {
    private static Logger logger = Logger.getLogger(UserFollowingRest.class);

    @Autowired
    FollowRepository followRepository;

    @Autowired
    CurrentUserUtil currentUserUtil;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> getReceipts(@PathVariable(USER_ID) String userId) {

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


