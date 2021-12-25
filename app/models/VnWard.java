package models;

import io.ebean.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VnWard extends Model {
    @Id
    public String id;
    public String name;
    public String type;
    public String district_id;


    public VnWard(String name, String type, String district_id) {
        this.name = name;
        this.type = type;
        this.district_id = district_id;
    }

    public static Finder<String, VnWard> find = new Finder<>(VnWard.class);
}
