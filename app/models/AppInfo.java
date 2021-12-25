package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppInfo extends Model {
    public String link_app;
    public String link_fanpage;
    public String version;
    public Integer show_shop;
    public Integer show_recharge;
    public Integer show_register_ctv;
    public Integer show_tim_tho_quanh_day;

    public static Finder<Integer, AppInfo> find = new Finder<>(AppInfo.class);
}
