package models;

import helper.Utils;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ChatInfo extends Model {

    @Id
    public Long id;
    public Integer type_account;
    public Integer account_id;
    public Integer account_id_chat;
    public String content;
    public Integer id_cskh;
    public String fullname;
    public Integer type_account_chat;
    public Integer is_read;
    public Timestamp created_time;

    public ChatInfo(Integer type_account, Integer account_id, Integer account_id_chat,
                    String content, Integer id_cskh, Integer is_read) {
        this.type_account = type_account;
        this.account_id = account_id;
        this.account_id_chat = account_id_chat;
        this.content = content;
        this.id_cskh = id_cskh;
        this.type_account_chat = type_account;
        this.is_read = is_read;
        this.created_time = Utils.getTimeStamp();
    }

    public static Finder<Long, ChatInfo> find = new Finder<>(ChatInfo.class);
}
