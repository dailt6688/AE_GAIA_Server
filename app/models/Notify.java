package models;

import helper.S;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Notify extends Model {
    @Id
    public Long id;
    public String sender;
    public Integer type_account;
    public Long account_id;
    public String title;
    public String content;
    public Integer is_read;
    public Integer is_delete;
    public Timestamp created;

    public Notify(String sender, Integer type_account, long account_id, String title, String content, Timestamp created) {
        this.sender = sender;
        this.type_account = type_account;
        this.account_id = account_id;
        this.title = title;
        this.content = content;
        this.is_read = S.FALSE;
        this.is_delete = S.FALSE;
        this.created = created;
    }

    public static Finder<Long, Notify> find = new Finder<>(Notify.class);
}
