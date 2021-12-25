package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product extends Model {

    @Id
    public Integer id;
    public Integer type;
    public String name;
    public String descriptions;
    public String link_icon;
    public Long price;
    public Float sale;
    public Integer is_new;
    public Integer shop;
    public Integer number;
    public Integer is_show;
    public Long created;


    public static Finder<Integer, Product> find = new Finder<>(Product.class);
}
