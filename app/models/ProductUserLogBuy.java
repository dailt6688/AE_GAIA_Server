package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductUserLogBuy extends Model {

    @Id
    public Long id;
    public Integer user_id;
    public Integer product_id;
    public Integer number;
    public Integer product_type;
    public Long money;
    public Float sale;
    public Long created;

    public ProductUserLogBuy(Integer user_id, Integer product_id, Integer number, Integer product_type, Long money, Float sale, Long created) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.number = number;
        this.product_type = product_type;
        this.money = money;
        this.sale = sale;
        this.created = created;
    }

    public static Finder<Long, ProductUserLogBuy> find = new Finder<>(ProductUserLogBuy.class);
}
