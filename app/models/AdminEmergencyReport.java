package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AdminEmergencyReport extends Model {
    @Id
    public Integer id;
    public Integer admin_id;
    public Integer admin_emergency_id;
    String content;
    Integer emergency_id;
    public Timestamp created_at;

    public AdminEmergencyReport(Integer admin_id, Integer admin_emergency_id, String content, Integer emergency_id, Timestamp created_at) {
        this.admin_id = admin_id;
        this.admin_emergency_id = admin_emergency_id;
        this.content = content;
        this.emergency_id = emergency_id;
        this.created_at = created_at;
    }

    public static Finder<Integer, AdminEmergencyReport> find = new Finder<>(AdminEmergencyReport.class);
}
