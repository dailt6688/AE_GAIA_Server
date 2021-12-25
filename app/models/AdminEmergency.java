package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdminEmergency extends Model {

    @Id
    public Integer id;
    public Long emergency_id;
    public Integer admin_id;
    public String position_start;
    public Long time_start_go;
    public Long time_start_job;
    public Long time_end;
    public String so_sao;
    public String khach_hang_danh_gia;

    public AdminEmergency(long emergency_id, Integer admin_id, String position_start, Long time_start_go,
                          Long time_start_job, Long time_end, String so_sao, String khach_hang_danh_gia){
        this.emergency_id = emergency_id;
        this.admin_id = admin_id;
        this.position_start = position_start;
        this.time_start_go = time_start_go;
        this.time_start_job = time_start_job;
        this.time_end = time_end;
        this.so_sao = so_sao;
        this.khach_hang_danh_gia = khach_hang_danh_gia;
    }

    public static Finder<Integer, AdminEmergency> find = new Finder<>(AdminEmergency.class);
}
