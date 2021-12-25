package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductRewardPoints extends Model {

    @Id
    public Integer id;
    public String description;
    public Integer product_id;
    public Integer number;
    public Integer price;
    public Integer is_show;

    public static Finder<Integer, ProductRewardPoints> find = new Finder<>(ProductRewardPoints.class);
}
