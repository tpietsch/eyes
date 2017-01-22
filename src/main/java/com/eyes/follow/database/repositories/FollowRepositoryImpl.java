package com.eyes.follow.database.repositories;


import com.eyes.follow.database.models.FollowEntity;
import com.eyes.tweet.database.models.TweetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowRepositoryImpl implements FollowRepositoryInterface {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public FollowEntity createFollow(FollowEntity followEntity) {
        String SQL = "INSERT INTO follow (follow_id, follow_by_user_id, following_user_id, date_created) " +
                "VALUES (:followId, :followByUserId, :followingUserId,:dateCreated)";
        Map namedParameters = new HashMap();
        namedParameters.put("followId", followEntity.getFollowId());
        namedParameters.put("followByUserId", followEntity.getFollowByUserId());
        namedParameters.put("followingUserId", followEntity.getFollowingUserId());
        namedParameters.put("dateCreated", followEntity.getDateCreated());
        jdbcTemplate.update(SQL, namedParameters);
        return followEntity;
    }

    @Override
    public void testTransactionRollbacks(List<FollowEntity> followEntityList) {
        followEntityList.stream().forEach(followEntity -> {
           createFollow(followEntity);
        });
        throw new RuntimeException("Exception and Rollback Test");
    }

}