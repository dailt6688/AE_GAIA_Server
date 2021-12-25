package models;

import helper.S;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AdminRequirePayment extends Model {
    @Id
    public Long id;
    public Long admin_id;
    public Integer level;
    public Integer type;
    public Integer balance_wait_payment;
    public Integer bonus_wait_payment;
    public Integer bonus_introduce_customer_wait_payment;
    public Long vnd;
    public String conversion_rate;
    public Long total_money_payment;
    public Long account_bank_admin_id;
    public String description;
    public String bank_info;
    public Integer status;
    public Timestamp created;

    public AdminRequirePayment(long admin_id, Integer level, Integer type, Integer balance_wait_payment,
                               Integer bonus_wait_payment, Integer bonus_introduce_customer_wait_payment, long total_money_payment, long vnd, String conversion_rate,
                               long account_bank_admin_id, String description, Timestamp created) {
        this.admin_id = admin_id;
        this.level = level;
        this.type = type;
        this.balance_wait_payment = balance_wait_payment;
        this.bonus_wait_payment = bonus_wait_payment;
        this.bonus_introduce_customer_wait_payment = bonus_introduce_customer_wait_payment;
        this.total_money_payment = total_money_payment;
        this.vnd = vnd;
        this.conversion_rate = conversion_rate;
        this.account_bank_admin_id = account_bank_admin_id;
        this.description = description;
        this.status = S.TRUE;
        this.created = created;
    }

    public static Finder<Long, AdminRequirePayment> find = new Finder<>(AdminRequirePayment.class);

}
