package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductUserLogUse extends Model {
    @Id
    public Long id;
    public Integer user_id;
    public Integer product_id;
    public Integer number;
    public Long created;

    public ProductUserLogUse(Integer user_id, Integer product_id, Integer number, Long created) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.number = number;
        this.created = created;
    }

    public static Finder<Long, ProductUserLogUse> find = new Finder<>(ProductUserLogUse.class);
}
