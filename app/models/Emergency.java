package models;

import helper.S;
import helper.Utils;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Emergency extends Model {
    @Id
    public Long id;
    public Integer user_id;
    public Integer service_package_user_id;
    public String phone;
    public String des;
    public String des_cancel;
    public String address;
    public Double latitude;
    public Double longitude;
    public String images;
    public Integer status;
    public String partner_id;
    public Integer evaluate_partner;
    public Integer bonus_ktv;
    public Long time;
    public String note;

    public Emergency(Integer user_id, Integer service_package_user_id, String phone, String des, String address,
                     Double latitude, Double longitude, String images, Integer status, String partner_id) {
        this.user_id = user_id;
        this.service_package_user_id = service_package_user_id;
        this.phone = phone;
        this.des = des;
        this.des_cancel = null;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.status = status;
        this.partner_id = partner_id;
        this.time = Utils.getCurrentMiniseconds();
        this.evaluate_partner = S.FALSE;
        this.bonus_ktv = S.FALSE;
    }

    public static Finder<Long, Emergency> find = new Finder<>(Emergency.class);
}
