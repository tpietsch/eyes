package com.eyes.tweet.rest.v1;


import com.eyes.user.database.repositories.UserRepository;
import com.eyes.follow.database.models.FollowEntity;
import com.eyes.follow.database.repositories.FollowRepository;
import com.eyes.tweet.database.models.TweetEntity;
import com.eyes.tweet.database.repositories.TweetRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getTweetsForUserAndFollowedUsersTweets(@PathVariable(USER_ID) String userId,
                                                                    @RequestParam(required = false, name = "search") String searchTerm) {
        final Set<TweetEntity> tweets = new HashSet<>();
        List<TweetEntity> sortedTweets;

        if (!StringUtils.isEmpty(searchTerm)) {
            tweets.addAll(tweetRepository.findByTweetSearchText(userId, searchTerm));
        } else {
            tweets.addAll(tweetRepository.findTweets(userId));
        }

        addUserDataToTweets(userId, searchTerm, tweets);

        sortedTweets = sortTweetsByDate(tweets);


        return ResponseEntity.ok(sortedTweets);

    }

    private List<TweetEntity> sortTweetsByDate(Set<TweetEntity> tweets) {
        List<TweetEntity> sortedTweets;
        sortedTweets = new ArrayList<>(tweets);
        Collections.sort(sortedTweets, (o1, o2) -> {
                    if (o1.getDateCreated().before(o2.getDateCreated())) {
                        return 1;
                    } else if (o1.getDateCreated().after(o2.getDateCreated())) {
                        return -1;
                    } else {
                        return 0;
                    }
                }

        );
        return sortedTweets;
    }

    private void addUserDataToTweets(@PathVariable(USER_ID) String userId, @RequestParam(required = false, name = "search") String searchTerm, Set<TweetEntity> tweets) {
        Set<FollowEntity> follows = followRepository
                .findFollowingByUserId(userId);
        follows.stream().forEach(followEntity -> {
            String followingUserId = followEntity.getFollowingUserId();
            if (!StringUtils.isEmpty(searchTerm)) {
                tweets.addAll(tweetRepository.findByTweetSearchText(followingUserId, searchTerm));
            } else {
                tweets.addAll(tweetRepository.findTweets(followingUserId));
            }
        });
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    @PreAuthorize("@tweetSecurity.isUserTweetForCurrentUser(authentication,#userId)")
    public ResponseEntity<?> newTweet(@PathVariable(USER_ID) String userId, @RequestBody TweetEntity tweetEntity) {
        tweetEntity.setUserId(userId);
        tweetEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(tweetRepository.save(tweetEntity));
    }

}


