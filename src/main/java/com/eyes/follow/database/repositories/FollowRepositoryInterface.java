package com.eyes.follow.database.repositories;

import com.eyes.follow.database.models.FollowEntity;
import com.eyes.tweet.database.models.TweetEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tpietsch on 1/22/17.
 */
public interface FollowRepositoryInterface {

    @Transactional
    FollowEntity createFollow(FollowEntity followEntity);

    @Transactional
    void testTransactionRollbacks(List<FollowEntity> followEntityList);

}
