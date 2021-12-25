package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alert extends Model {
    @Id
    public Integer id;
    public String message;

    public static Finder<Integer, Alert> find = new Finder<>(Alert.class);
}
