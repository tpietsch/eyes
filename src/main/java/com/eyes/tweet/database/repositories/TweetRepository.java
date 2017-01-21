package com.eyes.tweet.database.repositories;


import com.eyes.tweet.database.models.TweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, String> {

    @Query("select tweet from TweetEntity tweet where tweet.userId = ?1 and " +
            "(tweet.tweet like CONCAT('%',?2,'%') or ?2 is null)")
    Set<TweetEntity> findByTweetTextPagedByDate(String userId, String searchTerm);

    @Query("select tweet from TweetEntity tweet where tweet.userId = ?1")
    Set<TweetEntity> findTweetsPaged(String userId);
}