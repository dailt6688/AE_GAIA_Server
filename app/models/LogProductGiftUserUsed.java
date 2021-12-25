package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogProductGiftUserUsed extends Model {

    @Id
    public Integer id;
    public Integer product_gift_id;
    public Integer user_id;
    public Integer used;
    public Timestamp created;

    public LogProductGiftUserUsed(Integer product_gift_id, Integer user_id, Timestamp created, int used) {
        this.product_gift_id = product_gift_id;
        this.user_id = user_id;
        this.created = created;
        this.used = used;
    }

    public static Finder<Integer, LogProductGiftUserUsed> find = new Finder<>(LogProductGiftUserUsed.class);
}
