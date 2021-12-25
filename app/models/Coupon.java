package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Coupon extends Model {

    @Id
    public Long id;
    public Integer type;//1- giảm tiền, 2- giảm phần trăm với hạn mức
    public String coupon;
    public String description;
    public Float percent;
    public Integer money;
    public Integer han_muc;
    public Integer used;
    public String sender;
    public Timestamp created_at;

    public static Finder<Long, Coupon> find = new Finder<>(Coupon.class);
}
