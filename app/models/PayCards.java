package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PayCards extends Model {

    @Id
    public Long id;
    public Integer game_id;
    public String trans_id;
    public String session_id;
    public Long user_id;
    public String username;
    public String request;
    public String response;
    public Date requested_at;
    public Date responsed_at;
    public Integer status;
    public Date created_at;
    public Integer response_status;
    public String provider_code;
    public String card_code;
    public String card_seri;
    public Integer price;
    public Integer conversion_price;
    public Integer provider_id;

    public PayCards(String trans_id, long user_id, String username, String request, String provider_code,
                    String card_code, String card_seri, Integer provider_id) {
        this.game_id = 1;
        this.trans_id = trans_id;
        this.user_id = user_id;
        this.username = username;
        this.request = request;
        this.provider_code = provider_code;
        this.card_code = card_code;
        this.card_seri = card_seri;
        this.provider_id = provider_id;
        this.status = -999;
        this.requested_at = new Date();
        this.created_at = new Date();
    }

    public static Finder<Long, PayCards> find = new Finder<>(PayCards.class);
}
