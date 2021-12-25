package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReadNotifySystem extends Model {
    @Id
    public Long id;
    public Integer type_account;
    public Integer account_id;
    public String notify_system;

    public ReadNotifySystem(Integer type_account, Integer account_id, String notify_system) {
        this.type_account = type_account;
        this.account_id = account_id;
        this.notify_system = notify_system;
    }

    public static Finder<Long, ReadNotifySystem> find = new Finder<>(ReadNotifySystem.class);
}
