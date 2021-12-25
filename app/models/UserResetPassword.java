package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class UserResetPassword extends Model {
    @Id
    public Integer id;
    public String username;
    public String email;
    public String otp;
    public Timestamp expired_time;

    public UserResetPassword(String username, String email, String otp, Timestamp expired_time){
        this.username = username;
        this.email = email;
        this.otp = otp;
        this.expired_time = expired_time;
    }

    public static Finder<Integer, UserResetPassword> find = new Finder<>(UserResetPassword.class);
}
