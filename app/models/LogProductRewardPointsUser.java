package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogProductRewardPointsUser extends Model {
    @Id
    public Long id;
    public Integer product_reward_points_id;
    public Integer user_id;
    public Timestamp created;

    public LogProductRewardPointsUser(Integer product_reward_points_id, Integer user_id, Timestamp created) {
        this.product_reward_points_id = product_reward_points_id;
        this.user_id = user_id;
        this.created = created;
    }
}
