package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserTempAddress extends Model {
    @Id
    public Integer id;
    public Integer user_id;
    public String address;
    public String province;
    public String district;
    public String ward;
    public String province_id;
    public String district_id;
    public String ward_id;
    public Double latitude;
    public Double longitude;

    public UserTempAddress(Integer user_id, String address, String province, String district, String ward, String province_id, String district_id, String ward_id, Double latitude, Double longitude){
        this.user_id = user_id;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.province_id = province_id;
        this.district_id = district_id;
        this.ward_id = ward_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Finder<Integer, UserTempAddress> find = new Finder<>(UserTempAddress.class);
}
