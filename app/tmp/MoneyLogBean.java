package tmp;

import helper.Utils;

import java.sql.Timestamp;

public class MoneyLogBean {

    private long user_id;
    private String username;
    private long money_old;
    private long money_new;
    private long money_update;
    private StringBuilder description = new StringBuilder();
    private Timestamp login_time;
    private Timestamp logout_time;
    public int type;//1: ktv, 2: khach hang, 6: CTV
    public int device;

    public MoneyLogBean(long user_id, String username, long money_old, int type, int device) {
        this.user_id = user_id;
        this.username = username;
        this.money_old = money_old;
        this.type = type;
        this.device = device;
        this.login_time = Utils.getTimeStamp();
    }

    public void setTimeOut() {
        this.logout_time = Utils.getTimeStamp();
    }

    public long getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public long getMoney_old() {
        return money_old;
    }

    public long getMoney_new() {
        return money_new;
    }

    public long getMoney_update() {
        money_update = 0;
        if (money_new != money_old) {
            money_update = money_new - money_old;
        }
        return money_update;
    }

    public String getDescription() {
        return description.toString();
    }

    public Timestamp getLogin_time() {
        return login_time;
    }

    public Timestamp getLogout_time() {
        return logout_time;
    }

    public int getType() {
        return type;
    }

    public void setMoney_new(long money_new) {
        this.money_new = money_new;
    }

    public void setDescription(String description) {
        this.description.append(description).append(", ");
    }
}
