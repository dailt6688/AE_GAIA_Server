package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class CmsAddMoneyLogs extends Model {
    @Id
    public Integer id;
    public Integer type;
    public Integer userid;
    public String note;
    public Integer money_id;
    public Long money;
    public Integer sub;
    public Timestamp created_at;
    public String admin_name;

    public CmsAddMoneyLogs(Integer type, Integer userid, String note, Integer money_id, long money, Integer sub, Timestamp created_at, String admin_name) {
        this.type = type;
        this.userid = userid;
        this.note = note;
        this.money_id = money_id;
        this.money = money;
        this.sub = sub;
        this.created_at = created_at;
        this.admin_name = admin_name;
    }

    public static Finder<Integer, CmsAddMoneyLogs> find = new Finder<>(CmsAddMoneyLogs.class);
}
