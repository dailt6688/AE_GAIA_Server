package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class EvaluateCtv extends Model {
    @Id
    public Long id;
    public Integer user_id;
    public Integer admin_id;
    public Integer service_id;
    public Long collaborator_id;
    public Integer so_sao;
    public String content;
    public Timestamp created;

    public EvaluateCtv(int user_id, int admin_id, int service_id, long collaborator_id, int so_sao, String content, Timestamp created) {
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.service_id = service_id;
        this.collaborator_id = collaborator_id;
        this.so_sao = so_sao;
        this.content = content;
        this.created = created;
    }

    public static Finder<Long, EvaluateCtv> find = new Finder<>(EvaluateCtv.class);
}
