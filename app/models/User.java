package models;

import helper.S;
import io.ebean.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class User extends Model {
    @Id
    public Integer id;
    public String username;
    public String password;
    public String fullname;
    public String phone;
    public String email;
    public String address;
    public Integer rank;
    public Integer score;
    public Long balance;
    public Integer reward_point;
    public Integer status;
    public Timestamp created_at;
    public String fcm_token;
    public String link_avatar;
    public Integer device;
    public Long total_money_charging_card;
    public Long total_money_charging_bank;
    public String province;
    public String district;
    public String ward;
    public String province_id;
    public String district_id;
    public String ward_id;
    public Integer first_login;
    public String birthday;
    public Integer cancel_call_ctv;
    public Integer cancel_call_emergency;
    public String ip_current;
    public String imei;
    public Integer add_money_register;
    public Double latitude;
    public Double longitude;

    public User(String username, String password, String fullname, String phone, String email, String address, Integer rank,
                Integer score, Long balance, Integer status, Timestamp created_at, Integer device) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.rank = rank;
        this.score = score;
        this.balance = balance;
        this.reward_point = 0;
        this.status = status;
        this.created_at = created_at;
        this.device = device;
        this.total_money_charging_card = 0L;
        this.total_money_charging_bank = 0L;
        this.first_login = null;
        this.add_money_register = S.FALSE;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public static Finder<Integer, User> find = new Finder<>(User.class);
}
