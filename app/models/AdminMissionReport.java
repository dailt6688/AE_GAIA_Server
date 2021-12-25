package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AdminMissionReport extends Model {

    @Id
    public Integer id;
    public Integer admin_id;
    public Integer admin_mission_id;
    public String content;
    public Integer service_package_user_id;
    public Timestamp created_at;

    public AdminMissionReport(int admin_id, int admin_mission_id, String content, int service_package_user_id, Timestamp created_at) {
        this.admin_id = admin_id;
        this.admin_mission_id = admin_mission_id;
        this.content = content;
        this.service_package_user_id = service_package_user_id;
        this.created_at = created_at;
    }

    public static Finder<Integer, AdminMissionReport> find = new Finder<>(AdminMissionReport.class);
}
