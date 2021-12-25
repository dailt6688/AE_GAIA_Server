package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Appliance extends Model {
    @Id
    public Integer id;
    public String name;
    public String manufacturer;
    public String model;
    public Integer quantity;
    public Long time; //thoi gian dan tem
    public Integer type;
    public Integer service_package_user_id;
    public Integer status; //1: dang cho dan tem, 2: da dan tem

    public Appliance(String name, String manufacturer, String model, Integer quantity, Long time, Integer type, Integer service_package_user_id, Integer status){
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.quantity = quantity;
        this.time = time;
        this.type = type;
        this.service_package_user_id = service_package_user_id;
        this.status = status;
    }

    public static Finder<Integer, Appliance> find = new Finder<>(Appliance.class);

}
