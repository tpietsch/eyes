package test.unit;

import com.eyes.user.database.models.UserEntity;
import com.eyes.follow.database.models.FollowEntity;
import org.junit.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Set;

public class FollowTest extends AppTest {

    @Test
    @WithUserDetails(USERNAME)
    public void testIdempotentFollow() {
        UserEntity user = createNewRandomUser();
        userFollowerRest.createNewFollow(user.getUserId(), new FollowEntity());
        userFollowerRest.createNewFollow(user.getUserId(), new FollowEntity());
        assert ((Set<?>) userFollowingRest
                .getAllUsersFollowedByThisUser(authenticatedUserUtil
                        .getAuthenticatedUserEntity().getUserId()).getBody()).size() == 1;
    }


}