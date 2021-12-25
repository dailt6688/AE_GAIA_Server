package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductKtv extends Model {

    @Id
    public Long id;
    public Integer admin_id;
    public Integer product_id;
    public Integer number;

    public ProductKtv(Integer admin_id, Integer product_id, Integer number) {
        this.admin_id = admin_id;
        this.product_id = product_id;
        this.number = number;
    }

    public static Finder<Long, ProductKtv> find = new Finder<>(ProductKtv.class);
}
