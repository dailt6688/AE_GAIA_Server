package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class CollaboratorRegister extends Model {

    @Id
    public Integer id;
    public Integer user_id;
    public String fullname;
    public String birthday;
    public String phone;
    public String address;
    public String works;
    public Integer status;
    public String admin_check;
    public Timestamp created_at;

    public CollaboratorRegister(Integer user_id, String fullname, String birthday, String phone, String address, String works, Integer status, Timestamp created_at) {
        this.user_id = user_id;
        this.fullname = fullname;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.works = works;
        this.status = status;
        this.admin_check = null;
        this.created_at = created_at;
    }

    public static Finder<Integer, CollaboratorRegister> find = new Finder<>(CollaboratorRegister.class);
}
