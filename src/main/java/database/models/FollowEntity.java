package database.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "follow")
public class FollowEntity {
    private String followId = UUID.randomUUID().toString();
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());
    private String followByUserId;
    private String followingUserId;
    @JsonProperty("followingUserEntity")
    private transient UserEntity followingUserEntityByUserId;
    @JsonProperty("followByUserEntity")
    private transient UserEntity followByUserEntityByUserId;

    @Id
    @Column(name = "follow_id")
    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    @Basic
    @Column(name = "date_created")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "follow_by_user_id")
    public String getFollowByUserId() {
        return followByUserId;
    }

    public void setFollowByUserId(String followByUserId) {
        this.followByUserId = followByUserId;
    }

    @Basic
    @Column(name = "following_user_id")
    public String getFollowingUserId() {
        return followingUserId;
    }

    public void setFollowingUserId(String followingUserId) {
        this.followingUserId = followingUserId;
    }

    @Transient
    public UserEntity getFollowingUserEntityByUserId() {
        return followingUserEntityByUserId;
    }

    public void setFollowingUserEntityByUserId(UserEntity followingUserEntityByUserId) {
        this.followingUserEntityByUserId = followingUserEntityByUserId;
    }

    @Transient
    public UserEntity getFollowByUserEntityByUserId() {
        return followByUserEntityByUserId;
    }

    public void setFollowByUserEntityByUserId(UserEntity useFollowByUserEntityId){
        this.followByUserEntityByUserId = useFollowByUserEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowEntity that = (FollowEntity) o;

        if (followId != null ? !followId.equals(that.followId) : that.followId != null) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        if (followByUserId != null ? !followByUserId.equals(that.followByUserId) : that.followByUserId != null)
            return false;
        if (followingUserId != null ? !followingUserId.equals(that.followingUserId) : that.followingUserId != null)
            return false;
        if (followingUserEntityByUserId != null ? !followingUserEntityByUserId.equals(that.followingUserEntityByUserId) : that.followingUserEntityByUserId != null)
            return false;
        return followByUserEntityByUserId != null ? followByUserEntityByUserId.equals(that.followByUserEntityByUserId) : that.followByUserEntityByUserId == null;

    }

    @Override
    public int hashCode() {
        int result = followId != null ? followId.hashCode() : 0;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (followByUserId != null ? followByUserId.hashCode() : 0);
        result = 31 * result + (followingUserId != null ? followingUserId.hashCode() : 0);
        result = 31 * result + (followingUserEntityByUserId != null ? followingUserEntityByUserId.hashCode() : 0);
        result = 31 * result + (followByUserEntityByUserId != null ? followByUserEntityByUserId.hashCode() : 0);
        return result;
    }
}
