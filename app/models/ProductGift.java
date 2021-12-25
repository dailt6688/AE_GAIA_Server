package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ProductGift extends Model {

    @Id
    public Integer id;
    public Integer type;
    public String title;
    public String description;
    public Timestamp time_event_start;
    public Timestamp time_event_stop;
    public Integer number_day_use;
    public Integer product_id;
    public Integer number;
    public Integer service_package_id;

    public static Finder<Integer, ProductGift> find = new Finder<>(ProductGift.class);
}
