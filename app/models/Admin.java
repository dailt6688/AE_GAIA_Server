package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Admin extends Model {
    @Id
    public Integer id;
    public String username;
    public String password;
    public Integer level;
    public String rank;
    public String fullname;
    public String phone;
    public String address;
    public String email;
    public Integer status;//lock account
    public Long balance;
    public Long balance_wait_payment;
    public Long bonus;
    public Long bonus_wait_payment;
    public Long bonus_introduce_customer;
    public Long bonus_introduce_customer_wait_payment;
    public Timestamp created;
    public String create_by;
    public String fcm_token;
    public Integer is_active;//1: làm việc, 2: không làm việc
    public Integer work_status;
    public Double latitude;
    public Double longitude;
    public String position;
    public Timestamp time_update;
    public String link_avatar;
    public Integer device;
    public String province;
    public String district;
    public String ward;
    public String province_id;
    public String district_id;
    public String district_work_id;
    public String ward_id;
    public String service_id;
    public Integer han_muc;
    public Integer so_tien_da_tru_am;
    public String birthday;
    public Integer salary;
    public Integer salary_add_month;


    public Long getBalance() {
        return balance;
    }

    public Admin(String username, String password, Integer level, String fullname, String phone,
                 String address, String email, Integer status, Timestamp created, Integer is_active, String link_avatar,
                 Integer device, String province_id, String district_id, String ward_id, String birthday) {
        int t = 0;
        long t1 = 0L;
        this.username = username;
        this.password = password;
        this.level = level;
        this.rank = null;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.status = status;
        this.created = created;
        this.create_by = null;
        this.fcm_token = null;
        this.is_active = 2;
        this.work_status = 1;
        this.is_active = is_active;
        this.latitude = null;
        this.longitude = null;
        this.position = null;
        this.link_avatar = link_avatar;
        this.time_update = null;
        this.device = device;
        this.province_id = province_id;
        this.district_id = district_id;
        this.ward_id = ward_id;
        this.birthday = birthday;
        this.balance = t1;
        this.balance_wait_payment = t1;
        this.bonus = t1;
        this.bonus_wait_payment = t1;
        this.bonus_introduce_customer = t1;
        this.bonus_introduce_customer_wait_payment = t1;
        this.han_muc = t;
        this.so_tien_da_tru_am = t;
    }

    public static Finder<Integer, Admin> find = new Finder<>(Admin.class);
}
