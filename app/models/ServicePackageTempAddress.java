package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ServicePackageTempAddress extends Model {
    @Id
    public Integer id;
    public String address;
    public String province_id;
    public String district_id;
    public String ward_id;
    public Double latitude;
    public Double longitude;
    public Integer service_package_user_id;
    public Timestamp create_at;
    public Integer status;
    public Integer type;
    public Integer confirm_by;

    public ServicePackageTempAddress(String address, String province_id, String district_id, String ward_id,
                                     Double latitude, Double longitude, Integer service_package_user_id, Integer status,
                                     Integer type, Integer confirm_by){
        this.address = address;
        this.province_id = province_id;
        this.district_id = district_id;
        this.ward_id = ward_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.service_package_user_id = service_package_user_id;
        this.status = status;
        this.type = type;
        this.confirm_by = confirm_by;
    }

    public static Finder<Integer, ServicePackageTempAddress> find = new Finder<>(ServicePackageTempAddress.class);
}
