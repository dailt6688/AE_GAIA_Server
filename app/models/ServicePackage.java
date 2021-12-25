package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServicePackage extends Model {
    @Id
    public Integer id;
    public Integer parent_id;
    public String name;
    public Integer time_id;
    public Integer square_id;
    public String des;
    public Integer price;
    public Float sale;
    public String icon;
    public Integer number_maintenance;
    public String benefit;


    public ServicePackage(Integer parent_id, String name, Integer time_id, Integer square_id, String des, Integer price,
                          String icon, Integer number_maintenance) {
        this.parent_id = parent_id;
        this.name = name;
        this.time_id = time_id;
        this.square_id = square_id;
        this.des = des;
        this.price = price;
        this.icon = icon;
        this.number_maintenance = number_maintenance;
    }


    public static Finder<Integer, ServicePackage> find = new Finder<>(ServicePackage.class);
}
