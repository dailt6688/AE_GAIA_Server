package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductUser extends Model {

    @Id
    public Long id;
    public Integer user_id;
    public Integer product_id;
    public Integer number;

    public ProductUser(Integer user_id, Integer product_id, Integer number) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.number = number;
    }

    public static Finder<Long, ProductUser> find = new Finder<>(ProductUser.class);
}
