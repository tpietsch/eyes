package database.repositories;


import database.models.FollowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {

    @Query("select follow from FollowEntity follow where follow.followingUserId=?1 order by follow.dateCreated desc")
    Set<FollowEntity> findFollowersForUser(String userId);

    @Query("select follow from FollowEntity follow where follow.followByUserId=?1 order by follow.dateCreated desc")
    Set<FollowEntity> findFollowingByUserId(String userId);

    @Query("select follow from FollowEntity follow " +
            "where follow.followByUserId=?1 and follow.followingUserId = ?2")
    Set<FollowEntity> findByFollowerAndFollowingUserId(String curreUserId, String userId);
}