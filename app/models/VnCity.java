package models;

import io.ebean.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VnCity extends Model {
    @Id
    public String id;
    public String name;
    public String type;
    public Integer area;

    public static Finder<String, VnCity> find = new Finder<>(VnCity.class);
}
