package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VnDistrict extends Model {
    @Id
    public String id;
    public String name;
    public String type;
    public String city_id;

    public VnDistrict(String name, String type, String city_id) {
        this.name = name;
        this.type = type;
        this.city_id = city_id;
    }

    public static Finder<String, VnDistrict> find = new Finder<>(VnDistrict.class);
}
