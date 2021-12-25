package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogPaymentCartUser extends Model {

    @Id
    public Long id;
    public Integer user_id;
    public Long price;
    public String product_id_detail;
    public String detail_cart;
    public Integer status;
    public Long coupon_id;
    public String nguoi_giao;
    public String detail_step;
    public Timestamp created;

    public LogPaymentCartUser(Integer user_id, Long price, String product_id_detail,
                              String detail_cart, Integer status, long coupon_id, Timestamp created) {
        this.user_id = user_id;
        this.price = price;
        this.product_id_detail = product_id_detail;
        this.detail_cart = detail_cart;
        this.status = status;
        this.coupon_id = coupon_id;
        this.created = created;
    }

    public static Finder<Long, LogPaymentCartUser> find = new Finder<>(LogPaymentCartUser.class);
}
