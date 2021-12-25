package models;

import helper.S;
import io.ebean.Finder;
import io.ebean.Model;
import recharge.Status;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogRechargeEpay extends Model {
    @Id
    public Long id;
    public Integer type;
    public Integer type_account;
    public Integer account_id;
    public Long tien_nap;
    public String fee;
    public String username_send_nap;
    public String issuer_id;
    public String transaction_id;
    public String bank_code;
    public String bank_name;
    public Integer is_add_money;
    public String temp_send;
    public String mac_send;
    public String mac_receive;
    public Integer status;
    public String response_send;
    public String link_checkout;
    public String response_receive;
    public String description_send;
    public String description_receive;
    public Timestamp created_at;

    public LogRechargeEpay(int type, int type_account, Integer account_id, long tien_nap,
                           String fee, String username_send_nap, String issuer_id, String transaction_id,
                           String bank_code, String bank_name, String temp_send, String mac_send, String response_send, String description_send, String link_checkout, Timestamp created) {
        this.type = type;
        this.type_account = type_account;
        this.account_id = account_id;
        this.tien_nap = tien_nap;
        this.fee = fee;
        this.username_send_nap = username_send_nap;
        this.issuer_id = issuer_id;
        this.transaction_id = transaction_id;
        this.bank_code = bank_code;
        this.bank_name = bank_name;
        this.temp_send = temp_send;
        this.mac_send = mac_send;
        this.status = Status.REQUIRE_PAYMENT;
        this.response_send = response_send;
        this.description_send = description_send;
        this.link_checkout = link_checkout;
        this.is_add_money = S.FALSE;
        this.created_at = created;
    }

    public static Finder<Long, LogRechargeEpay> find = new Finder<>(LogRechargeEpay.class);
}
