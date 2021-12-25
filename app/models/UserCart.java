package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class UserCart extends Model {

    @Id
    public Integer id;
    public Integer user_id;
    public String info_cart;
    public String info_cart_wait_buy;
    public Timestamp time_change;

    public UserCart(Integer user_id, String info_cart, Timestamp time_change) {
        this.user_id = user_id;
        this.info_cart = info_cart;
        this.time_change = time_change;
    }

    public static Finder<Long, UserCart> find = new Finder<>(UserCart.class);
}
