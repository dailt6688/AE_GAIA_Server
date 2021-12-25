package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConfigServer extends Model {

    @Id
    public Integer id;
    public String link_url_recharge_card;
    public String partner_card;
    public String password_partner_card;

    public String epay_link_url_recharge;
    public String epay_merchant_code;
    public String epay_merchant_send_key;
    public String epay_merchant_receive_key;
    public String epay_issuer_id;
    public String epay_response_link;

    public String key_firebase;
    public Long money_register_account;
    public Long money_thuong_nhap_ma_gioi_thieu;

    public String ip_server;
    public String link_port_3000;
    public String link_port_90;
    public String link_port_91;
    public String link_port_93;

    public String nl_url_recharge;
    public String nl_merchant_id_send;
    public String nl_merchant_password_send;
    public String nl_merchant_id_check;
    public String nl_merchant_password_check;
    public String nl_receiver_email;
    public String nl_return_url;
    public String nl_cancel_url;

    public Integer time_write_ccu_log;

    public String gmail_send;
    public String gmail_password;

    public static Finder<Integer, ConfigServer> find = new Finder<>(ConfigServer.class);
}
