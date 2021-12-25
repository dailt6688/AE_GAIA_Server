package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServicePackageUser extends Model {
    @Id
    public Integer id;
    public Integer user_id;
    public Integer service_package_id;
    public Long start_time;
    public Long end_time;
    public String address;
    public Double latitude;
    public Double longitude;
    public String province_id;
    public String district_id;
    public String ward_id;

    public ServicePackageUser(Integer userId, Integer servicePackageId, Long startTime, Long endTime, String address,
                              double latitude, double longitude, String province_id, String district_id, String ward_id) {
        this.user_id = userId;
        this.service_package_id = servicePackageId;
        this.start_time = startTime;
        this.end_time = endTime;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province_id = province_id;
        this.district_id = district_id;
        this.ward_id = ward_id;
    }

    public static Finder<Integer, ServicePackageUser> find = new Finder<>(ServicePackageUser.class);
}
