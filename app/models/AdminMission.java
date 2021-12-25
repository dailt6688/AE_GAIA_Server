package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdminMission extends Model {
    @Id
    public Integer id;
    public Integer admin_id;
    public Integer service_package_maintenance_id;
    public String name;
    public String position_start;
    public Long time_start_go;
    public Long time_start_job;
    public Long time_end;
    public String so_sao;
    public String khach_hang_danh_gia;

    public AdminMission(Integer admin_id, Integer service_package_maintenance_id, String name) {
        this.admin_id = admin_id;
        this.service_package_maintenance_id = service_package_maintenance_id;
        this.name = name;
    }

    public static Finder<Integer, AdminMission> find = new Finder<>(AdminMission.class);
}
