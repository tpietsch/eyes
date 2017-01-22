package com.eyes.tweet;

import com.eyes.authentication.AuthenticatedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by tpietsch on 1/21/17.
 */
@Component
public class TweetSecurity {
    @Autowired
    AuthenticatedUserUtil authenticatedUserUtil;

    public boolean isUserTweetForCurrentUser(Authentication authentication,String userId){
        return authenticatedUserUtil.getAuthenticatedUserEntity().getUserId().equals(userId);
    }
}
