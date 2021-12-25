package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TypeCard extends Model {
    @Id
    public Integer id;
    public String name_card;
    public String provider_code;
    public Integer status;
    public Integer type_card;
    public String link_icon_card;

    public static Finder<Integer, TypeCard> find = new Finder<>(TypeCard.class);
}
