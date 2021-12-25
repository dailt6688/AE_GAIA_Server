package models;

import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Partner {
    @Id
    public Integer id;
    public String username;
    public String password;
    public String fullname;
    public String phone;
    public String address;
    public Integer rank;
    public Long balance;
    public Long bonus;
    public Integer status;
    public Long created;
    public Integer create_by;
    public Integer manager;
    public Integer group_id;

    public Partner(String username, String password, String fullname, String phone, String address, Integer rank,
                   Long balance, Long bonus, Integer status, Long created, Integer create_by, Integer manager, Integer group_id){
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.rank = rank;
        this.balance = balance;
        this.bonus = bonus;
        this.status = status;
        this.created = created;
        this.create_by = create_by;
        this.manager = manager;
        this.group_id = group_id;
    }

    public static Finder<Integer, Partner> find = new Finder<>(Partner.class);
}
