package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductType extends Model {
    @Id
    public Integer id;
    public String name;

    public static Finder<Integer, ProductType> find = new Finder<>(ProductType.class);
}
