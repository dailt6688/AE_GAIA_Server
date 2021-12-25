package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Information extends Model {

    @Id
    public Integer id;
    public String title;
    public String intro;
    public String content;
    public Integer type;
    public Long created;
    public String link;
    public String img;

    public static Finder<Integer, Information> find = new Finder<>(Information.class);
}
