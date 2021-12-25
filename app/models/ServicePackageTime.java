package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServicePackageTime extends Model {
    @Id
    public Integer id;
    public String name;
    public Long time;

    public ServicePackageTime(String name, Long time){
        this.name = name;
        this.time = time;
    }

    public static Finder<Integer, ServicePackageTime> find = new Finder<>(ServicePackageTime.class);
}