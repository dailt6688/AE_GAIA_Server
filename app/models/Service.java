package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Service extends Model {

    @Id
    public Integer id;
    public String name;
    public String link_icon;
    public String link_image;
    public String description;

    public static Finder<Integer, Service> find = new Finder<>(Service.class);
}
