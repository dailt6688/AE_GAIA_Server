package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AdminCart extends Model {

    @Id
    public Long id;
    public Integer admin_id;
    public String info_cart;
    public String info_cart_wait_buy;
    public Timestamp time_change;

    public AdminCart(Integer admin_id, String info_cart, Timestamp time_change) {
        this.admin_id = admin_id;
        this.info_cart = info_cart;
        this.time_change = time_change;
    }

    public static Finder<Long, AdminCart> find = new Finder<>(AdminCart.class);
}
