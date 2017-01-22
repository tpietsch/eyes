package com.eyes.follow.rest;

import com.eyes.authentication.AuthenticatedUserUtil;
import com.eyes.follow.database.models.FollowEntity;
import com.eyes.tweet.database.models.TweetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by tpietsch on 1/21/17.
 */
@Component
public class FollowSecurity {
    @Autowired
    AuthenticatedUserUtil authenticatedUserUtil;

    public boolean isFollowByCurrentUser(FollowEntity followEntity){
        return followEntity.getFollowByUserId().equals(authenticatedUserUtil.getAuthenticatedUserEntity().getUserId());
    }

}
