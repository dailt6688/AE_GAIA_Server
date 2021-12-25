package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class CollaboratorMissionReport extends Model {
    @Id
    public Long id;
    public Integer admin_id;
    public Long collaborator_mission_id;
    public String content;
    public Timestamp created_at;

    public CollaboratorMissionReport(Integer admin_id, long collaborator_mission_id, String content, Timestamp created_at) {
        this.admin_id = admin_id;
        this.collaborator_mission_id = collaborator_mission_id;
        this.content = content;
        this.created_at = created_at;
    }
}
