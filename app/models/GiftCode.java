package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class GiftCode extends Model {
    @Id
    public Long id;
    public String code;
    public Integer type;
    public Long div;
    public Integer service_package_id;
    public String agent;
    public Timestamp expire_date;
    public Timestamp use_date;
    public Integer use_by;
    public Timestamp create_time;
    public Long create_by;
    public String area_id;
    public Integer service_package_user_id;

    public static Finder<Integer, GiftCode> find = new Finder<>(GiftCode.class);
}
