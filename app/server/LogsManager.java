package server;

import tmp.MoneyLogBean;
import models.MoneyLog;

import java.util.ArrayList;
import java.util.List;

public class LogsManager {

    public static List<MoneyLogBean> moneyLogs = new ArrayList<>();
    public static List<MoneyLogBean> moneyLogsBuffer;

    /*Gom Log*/
    public static synchronized void addMoneyLog(MoneyLogBean moneylog) {
        moneyLogs.add(moneylog);
    }

    /*Save Log*/

    public static void saveMoneyLogs(List<MoneyLogBean> logs) {
        if (logs.size() > 0) {
            MoneyLog moneyLog;
            for (MoneyLogBean log : logs) {
                moneyLog = new MoneyLog(log.getUser_id(), log.getUsername(), log.getMoney_old(),
                        log.getMoney_new(), log.getMoney_update(), log.getDescription(),
                        log.getLogin_time(), log.getLogout_time(), log.getType());
                moneyLog.save();
            }
        }
    }
}
