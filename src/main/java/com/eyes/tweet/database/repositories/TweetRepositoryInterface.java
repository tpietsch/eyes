package com.eyes.tweet.database.repositories;

import com.eyes.tweet.database.models.TweetEntity;

/**
 * Created by tpietsch on 1/22/17.
 */
public interface TweetRepositoryInterface {
    public void insert(TweetEntity tweetEntity);
}
