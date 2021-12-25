package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServicePackageSquare extends Model {
    @Id
    public Integer id;
    public String name;

    public ServicePackageSquare(String name){
        this.name = name;
    }

    public static Finder<Integer, ServicePackageSquare> find = new Finder<>(ServicePackageSquare.class);
}
