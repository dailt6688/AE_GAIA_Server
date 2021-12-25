package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SgAdmin extends Model {
    @Id
    public Integer id;
    public String command;
    public String status;

    public static Finder<Integer, SgAdmin> find = new Finder<>(SgAdmin.class);
}
