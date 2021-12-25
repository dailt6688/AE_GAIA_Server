package models;

import helper.Utils;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class TransactionHistoryAdmin extends Model {
    @Id
    public Long id;
    public Integer admin_id;
    public Integer type;
    public Long transaction_id;
    public Integer status;
    public Long price;
    public String descriptions;
    public Timestamp created_time;

    public TransactionHistoryAdmin(Integer admin_id, Integer type, long transaction_id, Integer status, long price, String descriptions) {
        this.admin_id = admin_id;
        this.type = type;
        this.transaction_id = transaction_id;
        this.status = status;
        this.price = price;
        this.descriptions = descriptions;
        this.created_time = Utils.getTimeStamp();
    }

    public static Finder<Integer, TransactionHistoryAdmin> find = new Finder<>(TransactionHistoryAdmin.class);
}
