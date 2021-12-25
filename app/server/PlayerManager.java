package server;

import tmp.MoneyLogBean;
import helper.S;
import helper.Utils;
import models.Admin;
import models.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    public static Map<Integer, MoneyLogBean> mapUser = Collections.synchronizedMap(new HashMap<>());
    public static Map<Integer, MoneyLogBean> mapAdmin = Collections.synchronizedMap(new HashMap<>());

    public static void putMapUser(User user, int device) {
        try {
            int user_id = user.id;
            String username = user.username;
            long money_old = user.balance;
            int type = 2;
            MoneyLogBean log = null;
            userLogout(user);
            log = new MoneyLogBean(user_id, username, money_old, type, device);
            mapUser.put(user_id, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void putMapAdmin(Admin admin, int device) {
        try {
            int user_id = admin.id;
            String username = admin.username;
            long money_old = admin.balance;
            int type = 1;
            if (admin.level == S.LEVEL_CTV) {
                type = S.LEVEL_CTV;
            }
            MoneyLogBean log = null;
            adminLogout(admin);
            log = new MoneyLogBean(user_id, username, money_old, type, device);
            mapAdmin.put(user_id, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void userLogout(User user) {
        MoneyLogBean log = mapUser.remove(user.id);
        if (log != null) {
            log.setTimeOut();
            log.setMoney_new(user.balance);
            LogsManager.addMoneyLog(log);
        }
    }

    public static void adminLogout(Admin admin) {
        MoneyLogBean log = mapAdmin.remove(admin.id);
        if (log != null) {
            log.setTimeOut();
            log.setMoney_new(admin.balance);
            LogsManager.addMoneyLog(log);
        }
    }

    public static void userChangeBalance(User user, String description) {
        MoneyLogBean log = mapUser.get(user.id);
        if (log != null) {
            log.setMoney_new(user.balance);
            log.setDescription(description);
        }
    }

    public static void adminChangeBalance(Admin admin, String description) {
        MoneyLogBean log = mapAdmin.get(admin.id);
        if (log != null) {
            log.setMoney_new(admin.balance);
            log.setDescription(description);
        }
    }

    public static int[] countAndroidAllDevice() {
        int count[] = new int[]{0, 0};
        for (MoneyLogBean log : mapUser.values()) {
            if (log.device == S.ANDROID) {
                count[0] += 1;
            } else if (log.device == S.IOS) {
                count[1] += 1;
            }
        }
        for (MoneyLogBean log : mapAdmin.values()) {
            if (log.device == S.ANDROID) {
                count[0] += 1;
            } else if (log.device == S.IOS) {
                count[1] += 1;
            }
        }
        return count;
    }
}
