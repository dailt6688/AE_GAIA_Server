package models;

import helper.S;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class CollaboratorMission extends Model {
    @Id
    public Long id;
    public Integer user_id;
    public Integer service_id;
    public String phone;
    public String address;
    public String details;
    public String images;
    public Double latitude;
    public Double longitude;
    public Integer status;
    public Integer send_ktv;
    public Integer admin_id;
    public Integer evaluate_ctv;
    public Timestamp created;
    public String note;
    public String reason_cancel;

    public CollaboratorMission(Integer user_id, Integer service_id, String phone, String address, String details, String images, Double latitude, Double longitude, int send_ktv, Timestamp created) {
        this.user_id = user_id;
        this.service_id = service_id;
        this.phone = phone;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.details = details;
        this.images = images;
        this.send_ktv = send_ktv;
        this.status = S.FALSE;
        this.admin_id = null;
        this.evaluate_ctv = S.FALSE;
        this.created = created;
    }

    public static Finder<Long, CollaboratorMission> find = new Finder<>(CollaboratorMission.class);
}
