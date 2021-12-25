package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogKtvConfirmRechargeFromUser extends Model {
    @Id
    public Long id;
    public Integer admin_id;
    public Integer balance_tru;
    public Integer so_tien_da_tru_am;
    public String description;
    public Timestamp created_at;

    public LogKtvConfirmRechargeFromUser(Integer admin_id, Integer balance_tru, Integer so_tien_da_tru_am, String description, Timestamp created) {
        this.admin_id = admin_id;
        this.balance_tru = balance_tru;
        this.so_tien_da_tru_am = so_tien_da_tru_am;
        this.description = description;
        this.created_at = created;
    }

    public static Finder<Long, LogKtvConfirmRechargeFromUser> find = new Finder<>(LogKtvConfirmRechargeFromUser.class);
}
