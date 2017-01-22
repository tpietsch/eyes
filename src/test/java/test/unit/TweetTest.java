package test.unit;

import com.eyes.authentication.AuthenticatedUserUtil;
import com.eyes.authentication.database.models.UserEntity;
import com.eyes.authentication.database.repositories.UserRepository;
import com.eyes.follow.database.models.FollowEntity;
import com.eyes.follow.database.repositories.FollowRepository;
import com.eyes.follow.rest.v1.UserFollowerRest;
import com.eyes.registration.rest.v1.RegistrationRest;
import com.eyes.tweet.database.models.TweetEntity;
import com.eyes.tweet.database.repositories.TweetRepository;
import com.eyes.tweet.database.repositories.TweetRepositoryImpl;
import com.eyes.tweet.rest.v1.UserTweetRest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;
import java.util.UUID;

public class TweetTest extends AppTest {

    @Qualifier("userRepository")
    @Autowired
    UserRepository userRepository;

    @Qualifier("tweetRepository")
    @Autowired
    TweetRepository tweetRepository;

    @Qualifier("tweetRepositoryImpl")
    @Autowired
    TweetRepositoryImpl tweetRepositoryImpl;

    @Qualifier("followRepository")
    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserFollowerRest userFollowerRest;

    @Autowired
    UserTweetRest userTweetRest;

    @Autowired
    AuthenticatedUserUtil authenticatedUserUtil;

    @Autowired
    RegistrationRest registration;

    private static final String USERNAME = "test-user-follow-tweet-count";

    public UserEntity createNewRandomUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(UUID.randomUUID().toString());
        return userRepository.save(userEntity);
    }

    public void userTweet(UserEntity userEntity) {
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.setTweet(UUID.randomUUID().toString());
        tweetEntity.setUserId(userEntity.getUserId());
        tweetRepositoryImpl.insert(tweetEntity);
//        tweetRepository.save(tweetEntity);
    }

    @Test
    @WithUserDetails(USERNAME)
    public void testUserFollow() {
        UserEntity user1 = createNewRandomUser();
        userTweet(user1);
        userTweet(authenticatedUserUtil.getAuthenticatedUserEntity());
        FollowEntity follow = new FollowEntity();
        userFollowerRest.getReceipts(user1.getUserId(),follow);
        assert ((List<?>)userTweetRest
                .getReceipts(authenticatedUserUtil
                        .getAuthenticatedUserEntity().getUserId(),null).getBody()).size() == 2;


    }
}