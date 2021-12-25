package models;

import helper.S;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServicePackageMaintenance extends Model {
    @Id
    public Integer id;
    public Integer service_package_user_id;
    public String des;
    public Integer status;
    public Long time;
    public String admin_id;
    public Integer number;
    public String des_cancel;
    public Integer evaluate_admin;
    public Integer bonus_ktv;

    public ServicePackageMaintenance(Integer service_package_user_id, String des, Integer status, Long time,
                                     String admin_id, Integer number, String des_cancel) {
        this.service_package_user_id = service_package_user_id;
        this.des = des;
        this.status = status;
        this.time = time;
        this.admin_id = admin_id;
        this.number = number;
        this.des_cancel = des_cancel;
        this.evaluate_admin = S.FALSE;
        this.bonus_ktv = S.FALSE;
    }

    public static Finder<Integer, ServicePackageMaintenance> find = new Finder<>(ServicePackageMaintenance.class);

}
