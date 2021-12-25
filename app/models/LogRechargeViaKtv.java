package models;

import helper.S;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogRechargeViaKtv extends Model {

    @Id
    public Long id;
    public Integer user_id;
    public Integer money;
    public String description;
    public String description_response;
    public Integer admin_id;
    public Integer status;
    public Integer is_add_money;
    public Timestamp created_at;

    public LogRechargeViaKtv(Integer user_id, Integer money, String description, Integer admin_id, Timestamp created) {
        this.user_id = user_id;
        this.money = money;
        this.description = description;
        this.admin_id = admin_id;
        this.status = 1;
        this.is_add_money = S.FALSE;
        this.created_at = created;
    }

    public static Finder<Long, LogRechargeViaKtv> find = new Finder<>(LogRechargeViaKtv.class);
}
