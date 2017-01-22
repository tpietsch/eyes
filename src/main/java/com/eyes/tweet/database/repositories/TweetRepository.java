package com.eyes.tweet.database.repositories;


import com.eyes.tweet.database.models.TweetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TweetRepository extends CrudRepository<TweetEntity, String>,TweetRepositoryInterface {

    @Query("select tweet from TweetEntity tweet where tweet.userId = ?1")
    Set<TweetEntity> findTweets(String userId);

}