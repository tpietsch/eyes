package com.eyes.tweet.database.repositories;

import com.eyes.tweet.database.models.TweetEntity;

import java.util.List;

/**
 * Created by tpietsch on 1/22/17.
 */
public interface TweetRepositoryInterface {
    public TweetEntity saveTweet(TweetEntity tweetEntity);

    List<TweetEntity> findByTweetSearchText(String userId, String searchTerm);
}
