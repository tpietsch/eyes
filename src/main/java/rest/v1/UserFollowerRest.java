package rest.v1;


import database.models.FollowEntity;
import database.repositories.FollowRepository;
import database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rest.v1.models.ErrorJsonResponse;
import security.util.CurrentUserUtil;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

import static rest.RestEndpointConstants.*;

@RestController
@RequestMapping(value = V1 + "/user/" + USER_ID_PATH + "/follow",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFollowerRest {
    private static Logger logger = Logger.getLogger(UserFollowerRest.class);

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
    public ResponseEntity<?> getReceipts(@PathVariable(USER_ID) String userId,
                                        @RequestBody FollowEntity followEntity) {
        String curreUserId = currentUserUtil.getCurrentUser().getUserId();
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
    public void getReceipts(@PathVariable(USER_ID) String userId, @PathVariable(FOLLOW_ID) String followId) {
       followRepository.delete(followId);
    }
}


