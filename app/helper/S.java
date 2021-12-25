package helper;

public interface S {
    int ANDROID = 1;
    int IOS = 2;
    int WEB = 3;

    int TRUE = 1;
    int FALSE = 0;

    int TYPE_ACCOUNT_USER = 1;
    int TYPE_ACCOUNT_KTV = 2;

    int LEVEL_MANAGER = 3;
    int LEVEL_KTV = 4;
    int LEVEL_CSKH = 5;
    int LEVEL_CTV = 6;

    String GHEP = ":";
    String TACH_PHAY = ",";
    String TACH_SHIFT_TRU = "_";
    String TACH_SAO = "*";

    int LOG_ADD_MONEY_TIP_MAINTENANCE = 1;
    int LOG_RECHARGE_CARD = 2;
    int LOG_RECHARGE_BANK = 3;
    int LOG_ADD_MONEY_TIP_EMERGENCY = 4;
    int LOG_RECHARGE_VIA_KTV = 5;
    int LOG_ADD_MONEY_INTRODUCTION_USER = 6;
    int LOG_ADD_MONEY_SERVER = 7;
    int LOG_ADD_MONEY_GIFTCODE = 8;

    int LOG_MUA_GOI_DICH_VU = 11;
    int LOG_MUA_SAN_PHAM = 12;
    int LOG_TIP_MAINTENANCE = 13;
    int LOG_TIP_EMERGENCY = 14;
    int LOG_PAYMENT_CART = 15;
    int LOG_REQUIRE_PAYMENT = 16;
    int CONFIRM_RECHARGE_KTV = 17;
    int LOG_SUB_MONEY_SERVER = 18;

    int TYPE_BANK_LOCAL_ATM = 1;
    int TYPE_BANK_GLOBAL = 2;
    int TYPE_BANK_LOCAL_INTERNET_BANKING = 3;

    int TYPE_CARD_GLOBAL_VND = 1;
    int TYPE_CARD_GLOBAL_USD = 2;

    String MONEY_NAME = " DiV";
    String SENDER = "Server";
    String HOTLINE = Config.HOTLINE;

    double OFFSET_POSITION = 0.025;

    int ACCOUNT_UNLOCK = 1;
    int ACCOUNT_LOCK = 2;

    int ACCOUNT_DOING_ACTIVE = 1;
    int ACCOUNT_DOING_INACTIVE = 2;

    long MONEY_REGISTER_ACCOUNT = Config.MONEY_REGISTER_ACCOUNT;

    int EMERGENCY = 1;
    int CALL_COOPERATOR = 2;

    String IMAGE_FOLDER = Config.LINK_PORT_90;
    String EMERGENCY_FOLDER = Config.LINK_PORT_91;
    String COOPERATOR_FOLDER = Config.LINK_PORT_93;
    String URL_NODE_JS_HTTP = Config.LINK_PORT_3000;
}
