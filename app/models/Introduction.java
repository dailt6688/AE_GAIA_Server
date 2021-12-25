package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Introduction extends Model {
    @Id
    public Integer id;
    public String link;

    public static Finder<Integer, Introduction> find = new Finder<>(Introduction.class);
}
