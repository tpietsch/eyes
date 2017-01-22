package test.unit;

import com.eyes.authentication.AuthenticatedUserUtil;
import com.eyes.configuration.EyesRootConfiguration;
import com.eyes.user.database.models.UserEntity;
import com.eyes.user.database.repositories.UserRepository;
import com.eyes.follow.database.repositories.FollowRepository;
import com.eyes.follow.rest.v1.UserFollowerRest;
import com.eyes.follow.rest.v1.UserFollowingRest;
import com.eyes.registration.rest.v1.RegistrationRest;
import com.eyes.tweet.database.repositories.TweetRepository;
import com.eyes.tweet.database.repositories.TweetRepositoryImpl;
import com.eyes.tweet.rest.v1.UserTweetRest;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {EyesRootConfiguration.class})
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class AppTest {
    private static Logger logger = Logger.getLogger(AppTest.class);
    public static final String USERNAME = "test-user-follow-tweet-count";


    @Autowired
    UserFollowingRest userFollowingRest;

    @Qualifier("userRepository")
    @Autowired
    UserRepository userRepository;

    @Qualifier("tweetRepository")
    @Autowired
    TweetRepository tweetRepository;

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

    public UserEntity createNewRandomUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(UUID.randomUUID().toString());
        return userRepository.save(userEntity);
    }

    @Test
    public void testInMemoryDbWorks() {
        UserEntity userEntity = createNewRandomUser();
        userRepository.save(userEntity);
        assert userRepository.findOne(userEntity.getUserId()) != null;
    }

}
