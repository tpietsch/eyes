package test.unit;

import com.eyes.user.database.models.UserEntity;
import com.eyes.follow.database.models.FollowEntity;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FollowTest extends AppTest {

    @Test
    @WithUserDetails(USERNAME)
    public void testIdempotentFollow() {
        UserEntity user = createNewRandomUser();
        FollowEntity followEntity1 = new FollowEntity();
        followEntity1.setFollowingUserId(user.getUserId());
        followEntity1.setFollowByUserId(authenticatedUserUtil.getAuthenticatedUserEntity().getUserId());
        FollowEntity followEntity2 = new FollowEntity();
        followEntity2.setFollowingUserId(user.getUserId());
        followEntity2.setFollowByUserId(authenticatedUserUtil.getAuthenticatedUserEntity().getUserId());
        userFollowerRest.createNewFollow(user.getUserId(), followEntity1);
        userFollowerRest.createNewFollow(user.getUserId(), followEntity2);
        assert ((Set<?>) userFollowingRest
                .getAllUsersFollowedByThisUser(authenticatedUserUtil
                        .getAuthenticatedUserEntity().getUserId()).getBody()).size() == 1;
    }

    @Test(expected = AccessDeniedException.class)
    @WithUserDetails(USERNAME)
    public void testUserCannotCreateFollowForOtherUser() {
        testUserFollowByNotCurrentUserId();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testAdminCanCreateFollowForOtherUser() {
        testUserFollowByNotCurrentUserId();
    }

    private void testUserFollowByNotCurrentUserId() {
        UserEntity user1 = createNewRandomUser();
        FollowEntity follow = new FollowEntity();
        follow.setFollowingUserId(authenticatedUserUtil.getAuthenticatedUserEntity().getUserId());
        follow.setFollowByUserId(user1.getUserId());
        userFollowerRest.createNewFollow(user1.getUserId(), follow);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testRollbacksForFollows(){
        List<FollowEntity> list = new ArrayList<>();
        list.add(createNewRandomFollow());
        list.add(createNewRandomFollow());
        list.add(createNewRandomFollow());
        try {
            followRepository.testTransactionRollbacks(list);
        }catch (RuntimeException e){
            list.stream().forEach(followEntity -> {
                assert followRepository.findOne(followEntity.getFollowId()) == null;
            });
        }
    }

    private FollowEntity createNewRandomFollow() {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollowByUserId(createNewRandomUser().getUserId());
        followEntity.setFollowingUserId(createNewRandomUser().getUserId());
        return followEntity;
    }


}