package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CmsConfigPayment extends Model {
    @Id
    public Integer id;
    public Integer id_money;
    public Integer area_id;
    public Integer time;
    public Integer limit;
    public Float tyle;
    public String admin_id;

    public static Finder<Integer, CmsConfigPayment> find = new Finder<>(CmsConfigPayment.class);
}
