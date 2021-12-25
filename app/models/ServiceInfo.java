package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServiceInfo extends Model {
    @Id
    public Integer id;
    public String name;
    public String des;
    public String link_icon;
    public Integer service_package_id;

    public static Finder<Integer, ServiceInfo> find = new Finder<>(ServiceInfo.class);
}
