package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductKtvLogBuy extends Model {

    @Id
    public Long id;
    public Integer admin_id;
    public Integer product_id;
    public Integer number;
    public Integer product_type;
    public Long money;
    public Float sale;
    public Long created;

    public ProductKtvLogBuy(Integer admin_id, Integer product_id, Integer number, Integer product_type, Long money, Float sale, Long created) {
        this.admin_id = admin_id;
        this.product_id = product_id;
        this.number = number;
        this.product_type = product_type;
        this.money = money;
        this.sale = sale;
        this.created = created;
    }

    public static Finder<Long, ProductKtvLogBuy> find = new Finder<>(ProductKtvLogBuy.class);
}
