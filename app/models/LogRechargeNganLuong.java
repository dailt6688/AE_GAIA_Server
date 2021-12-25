package models;

import helper.S;
import io.ebean.Finder;
import io.ebean.Model;
import recharge.Status;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogRechargeNganLuong extends Model {

    @Id
    public Long id;
    public Integer type;
    public Integer type_account;
    public Integer account_id;
    public Integer tien_nap;
    public String fee;
    public String username_send_nap;
    public String merchant_id;
    public String transaction_id;
    public String bank_code;
    public String bank_name;
    public Integer is_add_money;
    public String temp_send;
    public Integer status;
    public String response_send;
    public String link_checkout;
    public String token;
    public String response_receive;
    public String description_send;
    public String description_receive;
    public String order_code;
    public String order_id;
    public String transaction_status;
    public String transaction_id_ngan_luong;
    public Timestamp created_at;

    public LogRechargeNganLuong(Integer type, Integer type_account, Integer account_id, Integer tien_nap, String fee, String username_send_nap, String merchant_id, String transaction_id, String bank_code, String bank_name, String temp_send, String response_send, String description_send, String link_checkout, String token, Timestamp created_at) {
        this.type = type;
        this.type_account = type_account;
        this.account_id = account_id;
        this.tien_nap = tien_nap;
        this.fee = fee;
        this.username_send_nap = username_send_nap;
        this.merchant_id = merchant_id;
        this.transaction_id = transaction_id;
        this.bank_code = bank_code;
        this.bank_name = bank_name;
        this.is_add_money = S.FALSE;
        this.temp_send = temp_send;
        this.status = Status.REQUIRE_PAYMENT;
        this.response_send = response_send;
        this.description_send = description_send;
        this.link_checkout = link_checkout;
        this.token = token;
        this.created_at = created_at;
    }

    public static Finder<Long, LogRechargeNganLuong> find = new Finder<>(LogRechargeNganLuong.class);
}
