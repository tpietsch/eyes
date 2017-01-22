package com.eyes.tweet.rest.v1;


import com.eyes.follow.database.models.FollowEntity;
import com.eyes.tweet.database.models.TweetEntity;
import com.eyes.follow.database.repositories.FollowRepository;
import com.eyes.tweet.database.repositories.TweetRepository;
import com.eyes.authentication.database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.eyes.error.ErrorJsonResponse;

import java.sql.Timestamp;
import java.util.*;

import static com.eyes.configuration.RestEndpointConstants.*;

@RestController
@RequestMapping(value = V1 + "/user/" + USER_ID_PATH + "/tweet",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserTweetRest {
    private static Logger logger = Logger.getLogger(UserTweetRest.class);

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> getReceipts(@PathVariable(USER_ID) String userId,
                                         @RequestParam(required = false, name = "search") String searchTerm) {
        final Set<TweetEntity> tweets = new HashSet<>();
        List<TweetEntity> sortedTweets;
        try {
            if (!StringUtils.isEmpty(searchTerm)) {
                tweets.addAll(tweetRepository.findByTweetTextPagedByDate(userId, searchTerm));
            } else {
                tweets.addAll(tweetRepository.findTweetsPaged(userId));
            }

            Set<FollowEntity> follows = followRepository
                    .findFollowingByUserId(userId);
            follows.stream().forEach(followEntity -> {
                String followingUserId = followEntity.getFollowingUserId();
                if (!StringUtils.isEmpty(searchTerm)) {
                    tweets.addAll(tweetRepository.findByTweetTextPagedByDate(followingUserId, searchTerm));
                } else {
                    tweets.addAll(tweetRepository.findTweetsPaged(followingUserId));
                }
            });
            sortedTweets = new ArrayList<>(tweets);
            Collections.sort(sortedTweets, new Comparator<TweetEntity>() {
                        @Override
                        public int compare(TweetEntity o1, TweetEntity o2) {
                            if (o1.getDateCreated().before(o2.getDateCreated())) {
                                return 1;
                            } else if (o1.getDateCreated().after(o2.getDateCreated())) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }

            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            ErrorJsonResponse errorJsonResponse = new ErrorJsonResponse("Unexpected Error.");
            return ResponseEntity.status(500).body(errorJsonResponse);
        }

        //TODO (include self-tweets and people being followed by the user)
        return ResponseEntity.ok(sortedTweets);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    //TODO securit only allow tweets to user for current logged in user
    public ResponseEntity<?> newTweet(@PathVariable(USER_ID) String userId, @RequestBody TweetEntity tweetEntity) {
        tweetEntity.setUserId(userId);
        tweetEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(tweetRepository.save(tweetEntity));
    }

}


