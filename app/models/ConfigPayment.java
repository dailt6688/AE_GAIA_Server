package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConfigPayment extends Model {
    @Id
    public Integer id;
    public Integer so_du_balance;
    public Integer so_du_bonus;
    public Integer so_du_bonus_introduce_customer;
    public Integer min_total_payment;

    public static Finder<Integer, ConfigPayment> find = new Finder<>(ConfigPayment.class);
}
