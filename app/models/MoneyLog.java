package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class MoneyLog extends Model {
    @Id
    public Long id;
    public Long user_id;
    public String username;
    public Long money_old;
    public Long money_new;
    public Long money_update;
    public String description;
    public Timestamp login_time;
    public Timestamp logout_time;
    public Integer type;

    public MoneyLog(Long user_id, String username, Long money_old, Long money_new, Long money_update, String description, Timestamp login_time, Timestamp logout_time, Integer type) {
        this.user_id = user_id;
        this.username = username;
        this.money_old = money_old;
        this.money_new = money_new;
        this.money_update = money_update;
        this.description = description;
        this.login_time = login_time;
        this.logout_time = logout_time;
        this.type = type;
    }
}
