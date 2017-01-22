package test.unit;

import com.eyes.user.database.models.UserEntity;
import com.eyes.follow.database.models.FollowEntity;
import com.eyes.tweet.database.models.TweetEntity;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;
import java.util.UUID;

public class TweetTest extends AppTest {


    public void userTweet(UserEntity userEntity) {
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.setTweet(UUID.randomUUID().toString());
        tweetEntity.setUserId(userEntity.getUserId());
        tweetRepository.saveTweet(tweetEntity);
    }

    @Test
    @WithUserDetails(USERNAME)
    public void testUserSeesTweetsAndFollowedTweets() {
        UserEntity user1 = createNewRandomUser();
        userTweet(user1);
        userTweet(authenticatedUserUtil.getAuthenticatedUserEntity());
        FollowEntity follow = new FollowEntity();
        userFollowerRest.createNewFollow(user1.getUserId(), follow);
        assert ((List<?>) userTweetRest
                .getTweetsForUserAndFollowedUsersTweets(authenticatedUserUtil
                        .getAuthenticatedUserEntity().getUserId(), null).getBody()).size() == 2;
    }

    @WithUserDetails(USERNAME)
    @Test(expected = AccessDeniedException.class)
    public void testUserCannotTweetForOtherUser() {
        UserEntity user1 = createNewRandomUser();
        userTweetRest.newTweet(user1.getUserId(), new TweetEntity());
    }


}