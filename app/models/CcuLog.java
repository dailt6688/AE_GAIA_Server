package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CcuLog extends Model {
    @Id
    public int id;
    public int ccu;
    public int ccu_users;
    public int ccu_ktv;
    public int ccu_cskh;
    public int ccu_ctv;
    public String ccu_info;
    public String action_user;
    public String time;
    public String date;
}
