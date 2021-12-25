package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;

@Entity
public class Banner extends Model {
    public Integer id;
    public String img;
    public String route;
    public String name;
    public String description;

    public static Finder<Integer, Banner> find = new Finder<>(Banner.class);
}
