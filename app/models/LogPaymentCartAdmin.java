package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogPaymentCartAdmin extends Model {
    @Id
    public Long id;
    public Integer admin_id;
    public Long price;
    public String product_id_detail;
    public String detail_cart;
    public Integer status;
    public Long coupon_id;
    public String nguoi_giao;
    public String detail_step;
    public Timestamp created;

    public LogPaymentCartAdmin(Integer admin_id, Long price, String product_id_detail,
                               String detail_cart, Integer status, long coupon_id, Timestamp created) {
        this.admin_id = admin_id;
        this.price = price;
        this.product_id_detail = product_id_detail;
        this.detail_cart = detail_cart;
        this.status = status;
        this.coupon_id = coupon_id;
        this.created = created;
    }

    public static Finder<Long, LogPaymentCartAdmin> find = new Finder<>(LogPaymentCartAdmin.class);
}
