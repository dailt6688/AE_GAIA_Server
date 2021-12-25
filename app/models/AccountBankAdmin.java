package models;

import helper.Utils;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AccountBankAdmin extends Model {

    @Id
    public Long id;
    public Integer level;
    public Long admin_id;
    public String chu_tai_khoan;
    public String so_tai_khoan;
    public String tinh_thanh;
    public String bank_name;
    public String chi_nhanh;
    public Timestamp created_at;

    public AccountBankAdmin(Integer level, long admin_id, String chu_tai_khoan,
                            String so_tai_khoan, String tinh_thanh, String bank_name, String chi_nhanh) {
        this.level = level;
        this.admin_id = admin_id;
        this.chu_tai_khoan = chu_tai_khoan;
        this.so_tai_khoan = so_tai_khoan;
        this.tinh_thanh = tinh_thanh;
        this.bank_name = bank_name;
        this.chi_nhanh = chi_nhanh;
        this.created_at = Utils.getTimeStamp();
    }

    public static Finder<Long, AccountBankAdmin> find = new Finder<>(AccountBankAdmin.class);
}
