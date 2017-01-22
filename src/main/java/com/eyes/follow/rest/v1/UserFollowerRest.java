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

import java.sql.Timestamp;
import java.util.Set;

import static com.eyes.configuration.RestEndpointConstants.*;

@RestController
@RequestMapping(value = V1 + "/user/" + USER_ID_PATH + "/follow",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFollowerRest {
    private static Logger logger = Logger.getLogger(UserFollowerRest.class);

    @Autowired
    FollowRepository followRepository;

    @Autowired
    AuthenticatedUserUtil authenticatedUserUtil;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> getFollowersForUser(@PathVariable(USER_ID) String userId) {
        try {

            Set<FollowEntity> follows = followRepository
                    .findFollowersForUser(userId);
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


    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> createNewFollow(@PathVariable(USER_ID) String userId,
                                             @RequestBody FollowEntity followEntity) {
        if(userId.equals(authenticatedUserUtil.getAuthenticatedUserEntity().getUserId())){
            return ResponseEntity.status(403).body(new ErrorJsonResponse("Cannot Follow Self"));
        }
        String curreUserId = authenticatedUserUtil.getAuthenticatedUserEntity().getUserId();
        Set<FollowEntity> follows = followRepository.findByFollowerAndFollowingUserId(curreUserId,userId);
        if(follows.size() > 0){
            return ResponseEntity.ok(follows.iterator().next());
        }

        followEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        followEntity.setFollowingUserId(userId);
        followEntity.setFollowByUserId(curreUserId);
        return ResponseEntity.ok(followRepository.save(followEntity));
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/" + FOLLOW_ID_PATH)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> unfollow(@PathVariable(USER_ID) String userId, @PathVariable(FOLLOW_ID) String followId) {
        if (userId.equals(authenticatedUserUtil.getAuthenticatedUserEntity().getUserId())) {
            return ResponseEntity.status(403).body(new ErrorJsonResponse("Unfollow Self Not Allowed"));
        }
        followRepository.delete(followId);
        return ResponseEntity.status(200).build();
    }

}


