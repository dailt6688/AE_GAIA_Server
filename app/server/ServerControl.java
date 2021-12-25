package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import helper.Config;
import helper.S;
import helper.Utils;
import models.*;
import nodeJs.NodeJs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class ServerControl {
    private static ServerControl instance = null;
    private boolean serverLocal = false;

    public static ServerControl getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new ServerControl();
            instance.runThreadGroup();
            return instance;
        }
    }

    public ServerControl() {
        reload();
    }

    public void runThreadGroup() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("\n--> " + ip.getHostAddress() + "\n");
            serverLocal = ip.getHostAddress().contains("192.168.1.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        runSaveMoneyLogThread();
        runSaveCcuLogThread();
        runSgAdminThread();
        runAddSalaryThread();
    }

    private void readFileJson() {
        String location = "conf/ConfigServer.json";
        JsonParser parser = new JsonParser();
        try {
            Object obj = parser.parse(new FileReader(location));
            JsonObject data = (JsonObject) obj;
            Config.IP_SERVER = data.get("ip_server").getAsString();
            Config.KEY_FIREBASE = data.get("key_firebase").getAsString();
            Config.MONEY_REGISTER_ACCOUNT = data.get("money_register_account").getAsLong();
            Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU = data.get("money_thuong_nhap_ma_gioi_thieu").getAsLong();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void runSaveMoneyLogThread() {
        if (!serverLocal) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(80L * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //save logMoney
                        LogsManager.moneyLogsBuffer = new ArrayList<>();
                        LogsManager.moneyLogsBuffer.addAll(LogsManager.moneyLogs);
                        LogsManager.moneyLogs.clear();
                        LogsManager.saveMoneyLogs(LogsManager.moneyLogsBuffer);
                    }
                }
            }).start();
        }
    }

    private void runSaveCcuLogThread() {
        if (!serverLocal) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(Config.TIME_WRITE_CCU_LOG * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

//                        String[] currentTime = Utils.getCurrentTime().split(" ");
//                        int ccu_users = PlayerManager.mapUser.size();
//                        int ccu_ctv = 0;
//                        for (MoneyLogBean l : PlayerManager.mapAdmin.values()) {
//                            if (l.type == S.LEVEL_CTV) {
//                                ccu_ctv += 1;
//                            }
//                        }
//                        int ccu_ktv = PlayerManager.mapAdmin.size() - ccu_ctv;
//                        int ccu = ccu_users + ccu_ktv + ccu_ctv;
//                        int[] counts = PlayerManager.countAndroidAllDevice();
//                        Utils.debug("CCU: "+ccu+" - counts[0]: "+counts[0]+" - counts[1]: "+counts[1]);
//                        int webCount = ccu - (counts[0] + counts[1]);
//
//
//                        CcuLog log = new CcuLog();
//                        log.ccu = ccu;
//                        log.android = counts[0];
//                        log.ios = counts[1];
//                        log.ccu_web = webCount;
//                        log.ccu_users = ccu_users;
//                        log.ccu_ktv = ccu_ktv;
//                        log.ccu_ctv = ccu_ctv;
//                        log.date = currentTime[0];
//                        log.time = currentTime[1];
//                        log.save();
//
//                        System.out.println("--> " + currentTime[1] + "    -  Khach hang: " + ccu_users + " - Admin: " + ccu_ktv + " - Cong tac vien: " + ccu_ctv);

                        NodeJs.getInstance().emitGetInfoCCU();
                    }
                }
            }).start();
        }
    }

    private void runAddSalaryThread() {
        if (!serverLocal) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Utils.getTimeMiniSecondsCachTimePlay(1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.debug("-> KIEM TRA UNG LUONG CHO KTV");

                    try {
                        String strMonth = Utils.getCurrentTime().split(" ")[0].split("-")[1];
                        int month = Integer.parseInt(strMonth);
                        List<Admin> lstAdmin = Admin.find.query().where().eq("status", S.TRUE).findList();
                        String note = "Tạm tính lương tháng " + month;
                        for (Admin admin : lstAdmin) {
                            if (admin.salary_add_month != null) {
                                if (admin.salary_add_month != month) {
                                    admin.balance += admin.salary;
                                    admin.balance_wait_payment += admin.salary;
                                    admin.salary_add_month = month;
                                    admin.update();
                                    CmsAddMoneyLogs log = new CmsAddMoneyLogs(S.TYPE_ACCOUNT_KTV, admin.id, note, 1,
                                            admin.salary, 1, Utils.getTimeStamp(), S.SENDER);
                                    log.save();
                                    PlayerManager.adminChangeBalance(admin, note);
                                }
                            } else {
                                admin.balance += admin.salary;
                                admin.balance_wait_payment += admin.salary;
                                admin.salary_add_month = month;
                                admin.update();
                                CmsAddMoneyLogs log = new CmsAddMoneyLogs(S.TYPE_ACCOUNT_KTV, admin.id, note, 1,
                                        admin.salary, 1, Utils.getTimeStamp(), S.SENDER);
                                log.save();
                                PlayerManager.adminChangeBalance(admin, note);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void runSgAdminThread() {
        if (!serverLocal) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            Thread.sleep(Config.TIME_WRITE_CCU_LOG * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            List<SgAdmin> lstSgAdmin = SgAdmin.find.query().where().eq("status", "s").findList();
                            if (lstSgAdmin.size() > 0) {
                                for (SgAdmin sg : lstSgAdmin) {
                                    if (sg.command.equals("reload")) {
                                        sg.status = "";
                                        sg.update();
                                        reload();
                                    }
                                    if (sg.command.equals("shutdown")) {
                                        NodeJs.getInstance().emitShutdownServer();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    private void reload() {
        loadConfig();
    }

    private void loadConfig() {
        ConfigServer config = Utils.getConfigServer();
        if (config != null) {
            Config.LINK_URL_RECHARGE_CARD = config.link_url_recharge_card;
            Config.PARTNER_CARD = config.partner_card;
            Config.PASSWORD_PARTNER_CARD = config.password_partner_card;

            Config.EPAY_LINK_URL_RECHARGE = config.epay_link_url_recharge;
            Config.EPAY_MERCHANT_CODE = config.epay_merchant_code;
            Config.EPAY_MERCHANT_SEND_KEY = config.epay_merchant_send_key;
            Config.EPAY_MERCHANT_RECEIVE_KEY = config.epay_merchant_receive_key;
            Config.EPAY_ISSUER_ID = config.epay_issuer_id;
            Config.EPAY_RESPONSE_LINK = config.epay_response_link;

            Config.IP_SERVER = config.ip_server;
            Config.KEY_FIREBASE = config.key_firebase;
            Config.MONEY_REGISTER_ACCOUNT = config.money_register_account;
            Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU = config.money_thuong_nhap_ma_gioi_thieu;

            Config.LINK_PORT_90 = config.link_port_90;
            Config.LINK_PORT_91 = config.link_port_91;
            Config.LINK_PORT_93 = config.link_port_93;
            Config.LINK_PORT_3000 = config.link_port_3000;

            Config.NL_URL_RECHARGE = config.nl_url_recharge;
            Config.NL_MERCHANT_ID_SEND = config.nl_merchant_id_send;
            Config.NL_MERCHANT_PASSWORD_SEND = config.nl_merchant_password_send;
            Config.NL_MERCHANT_ID_CHECK = config.nl_merchant_id_check;
            Config.NL_MERCHANT_PASSWORD_CHECK = config.nl_merchant_password_check;
            Config.NL_RECEIVER_EMAIL = config.nl_receiver_email;
            Config.NL_RETURN_URL = config.nl_return_url;
            Config.NL_CANCEL_URL = config.nl_cancel_url;

            Config.TIME_WRITE_CCU_LOG = config.time_write_ccu_log;

            Config.GMAIL_SEND = config.gmail_send;
            Config.GMAIL_PASSWORD = config.gmail_password;
        }

        Information info = Information.find.query().where().eq("type", 5).setMaxRows(1).findOne();
        if (info != null) {
            Config.HOTLINE = info.content;
        }

        List<SgAdmin> lstSgAdmin = SgAdmin.find.query().where().eq("status", "s").findList();
        if (lstSgAdmin.size() > 0) {
            for (SgAdmin sg : lstSgAdmin) {
                sg.status = "";
                sg.update();
            }
        }
        Utils.debug("Reload config complete");
        NodeJs.getInstance().emitReloadConfig();
        System.gc();
    }
}
