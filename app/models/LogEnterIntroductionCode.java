package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogEnterIntroductionCode extends Model {
    @Id
    public Integer id;
    public Integer user_id;
    public Integer recipient_id;
    public Integer recipient_type; //1: user, 2: admin
    public Long money;
    public Timestamp created_at;

    public LogEnterIntroductionCode(Integer user_id, Integer recipient_id, Integer recipient_type, Long money, Timestamp created_at) {
        this.user_id = user_id;
        this.recipient_id = recipient_id;
        this.recipient_type = recipient_type;
        this.money = money;
        this.created_at = created_at;
    }

    public static Finder<Integer, LogEnterIntroductionCode> find = new Finder<>(LogEnterIntroductionCode.class);
}
