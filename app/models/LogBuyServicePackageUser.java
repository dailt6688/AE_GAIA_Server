package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class LogBuyServicePackageUser extends Model {
    @Id
    public Long id;
    public Integer user_id;
    public Integer service_package_id;
    public Timestamp created;

    public LogBuyServicePackageUser(int user_id, int service_package_id, Timestamp created) {
        this.user_id = user_id;
        this.service_package_id = service_package_id;
        this.created = created;
    }

    public static Finder<Long, LogBuyServicePackageUser> find = new Finder<>(LogBuyServicePackageUser.class);
}
