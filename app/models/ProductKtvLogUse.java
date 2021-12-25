package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductKtvLogUse extends Model {
    @Id
    public Long id;
    public Integer admin_id;
    public Integer product_id;
    public Integer number;
    public Long created;

    public ProductKtvLogUse(Integer admin_id, Integer product_id, Integer number, Long created) {
        this.admin_id = admin_id;
        this.product_id = product_id;
        this.number = number;
        this.created = created;
    }

    public static Finder<Long, ProductKtvLogUse> find = new Finder<>(ProductKtvLogUse.class);
}
