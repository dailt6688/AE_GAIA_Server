package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BankInfo extends Model {
    @Id
    public Integer id;
    public String code;
    public String bank_name;
    public Integer status;
    public String link_icon;

    public static Finder<Integer, BankInfo> find = new Finder<>(BankInfo.class);
}
