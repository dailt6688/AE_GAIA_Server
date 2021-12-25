package controllers.user;

import com.fasterxml.jackson.databind.JsonNode;
import helper.*;
import helper.Error;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.*;
import nodeJs.NodeJs;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import recharge.Recharge;
import recharge.ResultRecharge;
import recharge.Status;
import recharge.epay.Reponse;
import recharge.nganluong.ResponseCheck;
import recharge.nganluong.ResponseSend;
import server.GmailSend;
import server.PlayerManager;
import server.ServerControl;
import server.VerifyOpenID;
import views.html.bank_info_recharge;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserController extends Controller {
    public UserController() {
        ServerControl.getInstance();
    }

    public Result userController() {
        ApiResponse res;
        try {
            JsonNode req = request().body().asJson();
            int user_id = req.get(Key.USER_ID).asInt();
            String token = req.get(Key.TOKEN).asText();
            int cmd = req.get(Key.CMD).asInt();
            switch (cmd) {
                case Cmd.LIST_PACKAGE:
                    return new ServicePackageController(null).listPackage();
                case Cmd.GET_DETAIL_SERVICE_PACKAGE_INFO:
                    return onGetDetailServicePackageInfo();
                case Cmd.ABOUT_US:
                    return AboutUs();
                case Cmd.POLICY:
                    return Policy();
                case Cmd.QUESTIONS:
                    return onQuestions();
                case Cmd.SWITCHBOARD_CARE:
                    return onSwitchboardCare();
                case Cmd.GET_LIST_SERVICES:
                    return onGetListServices();
                case Cmd.GET_LIST_SERVICE_BY_LOCATION:
                    return onGetListServiceByLocation(req);
                case Cmd.GET_LIST_CTV_BY_SERVICE_ID:
                    return onGetListCtvByServiceId(req);
                case Cmd.GET_LIST_ALERT:
                    return onGetListAlert();
                case Cmd.GET_LINK_INTRODUCTION:
                    return onGetLinkIntroduction();
                case Cmd.GET_DETAIL_EVALUATE_CTV:
                    return onGetDetailEvaluateCtv(req);
                case Cmd.GET_LIST_BANNER:
                    return onGetListBanner();
                case Cmd.BENEFIT_COLLABORATOR:
                    return onBenefitCollaborator();
                case Cmd.GET_CODE_RESET_PASSWORD:
                    return onGetCodeResetPassword(req);
                case Cmd.RESET_PASSWORD:
                    return onResetPassword(req);
                case Cmd.CHECK_INVITE_CODE:
                    return onCheckInviteCode(req);
                case Cmd.GET_RULE_EMERGENCY:
                    return onGetRuleEmergency();
                default:
                    break;
            }

            User user = User.find.byId(user_id);
            Boolean checkToken = JWTUntils.validateJWT(token, user);
            if (user == null) {
                res = new ApiResponse(Error.ERROR, Error.USER_NOT_EXIST, null);
            } else if (!checkToken) {
                res = new ApiResponse(Error.INVALID_TOKEN, "", null);
            } else {
                switch (cmd) {
                    case Cmd.GET_PACKAGE:
                        return new ServicePackageController(user).userGetPackage(user);
                    case Cmd.GET_LIST_APPLIANCE:
                        return new ServicePackageController(user).getListAppliance();
                    case Cmd.ADD_APPLIANCE:
                        return new ServicePackageController(user).userAddAppliance();
                    case Cmd.DEL_APPLIANCE:
                        return new ServicePackageController(user).userDeleteAppliance();
                    case Cmd.APPOINTMENT_TIME:
                        return new ServicePackageController(user).appointmentTime();
                    case Cmd.LIST_PACKAGE_USER:
                        return new ServicePackageController(user).listPackageUser();
                    case Cmd.SERVICE_PACKAGE_INFO:
                        return new ServicePackageController(user).info();
                    case Cmd.USER_SEND_EMERGENCY:
                        return new EmergencyController(user).UserSendEmergency(user);
                    case Cmd.UPDATE_BALANCE:
                        return onUpdateBalance(user);
                    case Cmd.UPDATE_USER:
                        return onUpdateUser(user);
                    case Cmd.USER_CHANGE_PASSWORD:
                        return onChangePassword(user);
                    case Cmd.MAINTENANCE_SCHEDULE:
                        return new ServicePackageController(user).MaintenanceSchedule();
                    case Cmd.CONFIRM_MET_PARTNER:
                        return new ServicePackageController(user).ConfirmMetPartner();
                    case Cmd.CANCEL_MAINTENANCE:
                        return new ServicePackageController(user).CancelMaintenance();
                    case Cmd.GET_POSITION_ADMIN:
                        return onGetPositionAdmin();
                    case Cmd.USER_LOGOUT:
                        return onUserLogout(user);
                    case Cmd.EVALUATE_KTV:
                        return onEvaluateKtv();
                    case Cmd.BONUS_KTV:
                        return onBonusKtv(user);
                    case Cmd.GET_INFO_ADMIN_BY_ID:
                        return onGetInfoAdminById();
                    case Cmd.GET_LIST_MAINTENANCE_CURRENT:
                        return new ServicePackageController(user).GetListMaintenanceCurrent();
                    case Cmd.UPDATE_FCM_TOKEN_USER:
                        return onUpdateFcmTokenUser(user);
                    case Cmd.INFO_APP:
                        return onInfoApp(user, req);
                    case Cmd.GET_LIST_NOTIFY_USER:
                        return onGetListNotifyUser(user_id);
                    case Cmd.DELETE_NOTIFY_USER:
                        return onDeleteNotifyUser();
                    case Cmd.READ_NOTIFY_USER:
                        return onReadNotifyUser();
                    case Cmd.GET_INFO_SERVICE_PACKAGE_USER_BY_MAINTENANCE_ID:
                        return new ServicePackageController(user).onGetInfoServicePackageUserByMaintenanceId();
                    case Cmd.USER_GET_LIST_EMERGENCY:
                        return new EmergencyController(user).UserGetListEmergency();
                    case Cmd.GET_INFO_EMERGENCY:
                        return new EmergencyController(user).onGetInfoEmergency();
                    case Cmd.GET_LOG_TRANSACTION_USER:
                        return onGetLogTransactionUser(user_id);
                    case Cmd.GET_LOG_MAINTENANCE_AND_EMERGENCY_USER:
                        return onGetLogMaintenanceAndEmergencyUser(user_id);
                    case Cmd.EVALUATE_KTV_EMERGENCY:
                        return onEvaluateKtvEmergency();
                    case Cmd.BONUS_KTV_EMERGENCY:
                        return onBonusKtvEmergency(user);
                    case Cmd.CONFIRM_MET_PARTNER_EMERGENCY:
                        return onConfirmMetPartnerEmergency();
                    case Cmd.USER_CHAT:
                        return onUserChat(user);
                    case Cmd.GET_LOG_CHAT_USER:
                        return onGetLogChatUser(user_id);
                    case Cmd.LIST_PRODUCT_USER:
                        return onListProductUser();
                    case Cmd.GET_PRODUCT_DETAIL:
                        return onGetProductDetail(req);
                    case Cmd.GET_LIST_PRODUCT_USER:
                        return onGetListProductUser(user_id);
                    case Cmd.USE_PRODUCT_USER:
                        return onUseProductUser(user);
                    case Cmd.GET_LIST_PRODUCT_ID_USER:
                        return onGetListProductIdUser();
                    case Cmd.GET_LIST_PRODUCT_USER_BY_TYPE:
                        return onGetListProductUserByType();
                    case Cmd.UPDATE_ADDRESS_USER:
                        return onUpdateAddressUser(user);
                    case Cmd.GET_INFO_TYPE_CARD_USER:
                        return onInfoGetTypeCard();
                    case Cmd.RECHARGE_CARD_USER:
                        return onRechargeCardUser(user);
                    case Cmd.GET_INFO_RECHARGE_BANK_USER:
                        return onGetInfoRechargeBank();
                    case Cmd.GET_LINK_VERIFY_RECHARGE_BANK:
                        return onRechargeBankUser(user);
                    case Cmd.GET_DETAIL_LOG_TRANSACTION_USER:
                        return onGetDetailLogTransactionUser(user_id);
                    case Cmd.GET_INFO_CART:
                        return onGetInfoCart(user_id);
                    case Cmd.UPDATE_CART:
                        return onUpdateCart(user_id);
                    case Cmd.PAYMENT_CART:
                        return onPaymentCart(user, req);
                    case Cmd.RECHARGE_VIA_KTV:
                        return onRechargeViaKtv(user, req);
                    case Cmd.GET_INFO_ACCOUNT_USER:
                        return onGetInfoAccountUser(user);
                    case Cmd.GET_LIST_PRODUCT_GIFT:
                        return onGetListProductGift(user_id);
                    case Cmd.USE_GIFT:
                        return onUseGift(user, req);
                    case Cmd.GET_LIST_SHOP_REWARD_POINT:
                        return onGetListShopRewardPoint();
                    case Cmd.CHANGE_REWARD_POINT:
                        return onChangeRewardPoint(user);
                    case Cmd.CALL_COLLABORATOR:
                        return onCallCollaborator(user, req);
                    case Cmd.EVALUATE_CTV:
                        return onEvaluateCtv(user_id, req);
                    case Cmd.GET_INFO_MISSION_COLLABORATOR:
                        return onGetInfoMissionCollaborator(req);
                    case Cmd.UPDATE_CART_WAIT_BUY:
                        return onUpdateCartWaitBuy(user_id, req);
                    case Cmd.REGISTER_RECEIVE_GIFT_PRODUCT:
                        return onRegisterReceiveGiftProduct(user_id, req);
                    case Cmd.BECOME_COOPERATOR:
                        return onBecomeCooperator(user_id, req);
                    case Cmd.CHECK_REGISTER_COOPERATOR:
                        return checkRegisterCooperator(user_id);
                    case Cmd.CANCEL_CALL_COLLABORATOR:
                        return onCancelCallCollaborator(user, req);
                    case Cmd.CANCEL_CALL_EMERGENCY:
                        return onCancelCallEmergency(user, req);
                    case Cmd.ENTER_THE_REFERRAL_CODE:
                        return onEnterTheReferralCode(user, req);
                    case Cmd.CHECK_COUPON:
                        return onCheckCoupon(req);
                    case Cmd.GET_LINK_VERIFY_BAO_KIM:
                        return onGetLinkVerifyBaoKim(user, req);
                    case Cmd.UPDATE_NOTIFY_SYSTEM_USER:
                        return onUpdateNotifySystemUser(user_id, req);
                    case Cmd.UPDATE_POSITION_WHEN_REGITER_ACCOUNT:
                        return onUpdatePositionWhenRegisterAccount(user, req);
                    case Cmd.ACTIVE_GIFTCODE:
                        return onActiveGiftcode(user, req);
                    case Cmd.INFO_EVALUATE_MAINTENANCE:
                        return onInfoEvaluateMaintenance(user, req);
                    case Cmd.INFO_EVALUATE_EMERGENCY:
                        return onInfoEvaluateEmergency(user, req);
                    case Cmd.USER_CHANGE_SERVICE_PACKAGE_ADDRESS:
                        return new ServicePackageController(user).changeServicePackageAddress(req);
                    case Cmd.CHECK_WAITING_CONFIRM_SERVICE_PACKAGE_ADDRESS:
                        return new ServicePackageController(user).checkWaitingConfirmServicePackage(req);
                    case Cmd.GET_GIFTCODE_SERVICE_PACKAGE:
                        return onGetGiftcodeServicePackage(user, req);
                    case Cmd.CHECK_IS_READ_CHAT:
                        return onCheckIsReadChat(user);
                    default:
                        res = new ApiResponse(Error.ERROR, Error.CMD_NOT_EXISTS, null);
                }
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    //create user
    public Result register() {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            String username = req.get("username").asText().trim();
            String password = req.get("password").asText();
            int device = req.get("device").asInt();
            String imei = req.get("imei").asText();
            String invite_code = req.get("invite_code").asText();

            String check = username.substring(0, 3);
            if (username.length() < 4) {
                return errorInvalid(res, "Tên tài khoản phải từ 4 ký tự trở lên");
            }
            if (check.equals("fb_") || check.equals("gg_")) {
                return errorInvalid(res, "Tên tài khoản có chứa ký tự không hợp lệ");
            }

            List<User> count = User.find.query().where().eq("imei", imei).setMaxRows(3).findList();
            int size = count.size();
            size = 0;
            if (size > 0) {
                if (size >= 3) {
                    return errorInvalid(res, "Quý khách đã đăng ký quá số tài khoản quy định");
                } else {
                    int ck = 0;
                    for (User u : count) {
                        if (u.username.contains("gg_") || u.username.contains("fb_")) {
                            ck += 1;
                        }
                    }
                    if (size - ck > 0) {
                        return errorInvalid(res, "Quý khách đã đăng ký quá số tài khoản quy định");
                    }
                }
            }

            List<User> users = User.find.query().where().eq("username", username).setMaxRows(1).findList();
            if (users.size() > 0) {
                res = new ApiResponse(Error.ERROR, Error.USER_EXIST, null);
                return ok(Json.toJson(res));
            }
            User user = new User(username, password, "", "", "", "", 1, 0, S.MONEY_REGISTER_ACCOUNT, 1, Utils.getTimeStamp(), device);
            user.device = device;
            user.imei = imei;
            user.save();

            Map recipient = onCheckInviteCode(invite_code);
            if(recipient != null){
                user.balance = S.MONEY_REGISTER_ACCOUNT + Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU;
                Integer recipient_id = Integer.parseInt(recipient.get("id").toString());
                LogEnterIntroductionCode log = LogEnterIntroductionCode.find.query().where().eq("user_id", user.id).setMaxRows(1).findOne();
                System.out.println(log);
                if (log == null) {
                    if(recipient.get("type").equals("user")){
                        System.out.println(user.id);
                        log = new LogEnterIntroductionCode(user.id, recipient_id, 1, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, Utils.getTimeStamp());
                        log.save();
                        User user_recived = (User)recipient.get("user");
                        String descriptions = "Quý khách đã nhận được " + Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU + "DIV khi giới thiệu được khách hàng mã số: " + user.id;
                        TransactionHistoryUser tran = new TransactionHistoryUser(user_recived.id, S.LOG_ADD_MONEY_INTRODUCTION_USER, log.id, S.TRUE, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, descriptions);
                        tran.save();
                        System.out.println(tran);
                        user_recived.balance += Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU;
                        user_recived.update();
                        System.out.println(user_recived.balance);
                        PushNotification.getInstance().SendOneDevice(user_recived.fcm_token, "Giới thiệu khách hàng", descriptions);
                        NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user_recived.id);
                    }
                    else{
                        Admin admin = (Admin)recipient.get("partner");
                        log = new LogEnterIntroductionCode(user.id, recipient_id, 2, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, Utils.getTimeStamp());
                        log.save();
                        String descriptions = "Bạn đã nhận được " + Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU  + " giới thiệu được khách hàng mã số: " + user.id;
                        TransactionHistoryAdmin tran = new TransactionHistoryAdmin(admin.id, S.LOG_ADD_MONEY_INTRODUCTION_USER, log.id, S.TRUE, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, descriptions);
                        tran.save();
                        admin.bonus_introduce_customer += Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU;
                        admin.update();
                        PushNotification.getInstance().SendOneDevice(admin.fcm_token, "Giới thiệu khách hàng", descriptions);
                        NodeJs.getInstance().emitUpdateMoney(Utils.getTypeAccountBenNodeJs(admin.level), admin.id);
                    }
                }
            }
            user.update();
            user.password = "";
            String token = JWTUntils.genJWT(user);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", user);
            res = new ApiResponse(Error.SUCCESS, "", map);
            PlayerManager.putMapUser(user, device);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result loginOpenId() {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            int type = req.get("type").asInt();
            int device = req.get("device").asInt();
            String token = req.get("token").asText();
            String fullname = req.get("fullname").asText();
            String imei = req.get("imei").asText();
            if (type == 1)//facebook
            {
                JsonNode data = VerifyOpenID.verifyFacebook(token);
                if (data != null) {
                    String id = data.get("id").asText();
                    String fbId = "fb_" + id;
                    User user = User.find.query().where().eq("username", fbId).findOne();
                    boolean isLogin = true;
                    if (user == null) {
                        user = new User(fbId, "", fullname, "", "", "", 1, 0, S.MONEY_REGISTER_ACCOUNT, S.ACCOUNT_UNLOCK, Utils.getTimeStamp(), device);
                        user.device = device;
                        user.first_login = S.TRUE;
                        String link_avatar = "https://graph.facebook.com/" + id + "/picture?type=large";
                        user.link_avatar = link_avatar;
                        user.imei = imei;
                        user.save();
                        isLogin = false;
                    } else {
                        boolean check = false;
                        if (user.device != device) {
                            user.device = device;
                            check = true;
                        }
//                        if (user.link_avatar == null || !user.link_avatar.equals(link_avatar)) {
//                            user.link_avatar = link_avatar;
//                            check = true;
//                        }
                        if (user.first_login != null) {
                            if (user.first_login == S.TRUE) {
                                user.first_login = S.FALSE;
                                check = true;
                            }
                        } else {
                            user.first_login = S.TRUE;
                            check = true;
                        }
                        if (check) {
                            user.update();
                        }
                    }

                    if (user.status == S.ACCOUNT_LOCK) {
                        NodeJs.getInstance().emitLockAccount(S.TYPE_ACCOUNT_USER, user.id);
                        return errorInvalid(res, "Tài khoản của bạn đã bị khóa");
                    }

                    user.password = "";
                    String tokenAccount = JWTUntils.genJWT(user);
                    Map<String, Object> map = new HashMap<>();
                    map.put("IsLogin", isLogin);
                    map.put("token", tokenAccount);
                    map.put("user", user);
                    PlayerManager.putMapUser(user, device);
                    return processSuccess(res, map);
                } else {
                    Utils.debug("Loi dich chuoi Json tu facebook");
                    return errorInvalid(res, "Lỗi xác thực tài khoản");
                }
            } else if (type == 2) {//google+
                JsonNode data = VerifyOpenID.verifyGooglePlus(token);
                if (data != null) {
                    String ggId = "gg_" + data.get("sub").asText();
                    String email = data.get("email").asText();
                    User user = User.find.query().where().eq("username", ggId).findOne();
                    boolean isLogin = true;
                    if (user == null) {
                        user = new User(ggId, "", fullname, "", email, "", 1, 0, S.MONEY_REGISTER_ACCOUNT, S.ACCOUNT_UNLOCK, Utils.getTimeStamp(), device);
                        user.device = device;
                        String link_avatar = data.get("picture").asText();
                        user.link_avatar = link_avatar;
                        user.first_login = S.TRUE;
//                        user.imei = imei;
                        user.save();
                        isLogin = false;
                    } else {
                        boolean check = false;
                        if (user.device != device) {
                            user.device = device;
                            check = true;
                        }
                        if (!user.fullname.equals(fullname)) {
                            user.fullname = fullname;
                            check = true;
                        }
//                        if (user.link_avatar == null || !user.link_avatar.equals(link_avatar)) {
//                            user.link_avatar = link_avatar;
//                            check = true;
//                        }
                        if (user.first_login != null) {
                            if (user.first_login == S.TRUE) {
                                user.first_login = S.FALSE;
                                check = true;
                            }
                        } else {
                            user.first_login = S.TRUE;
                            check = true;
                        }
                        if (check) {
                            user.update();
                        }
                    }

                    if (user.status == S.ACCOUNT_LOCK) {
                        NodeJs.getInstance().emitLockAccount(S.TYPE_ACCOUNT_USER, user.id);
                        return errorInvalid(res, "Tài khoản của bạn đã bị khóa");
                    }

                    user.password = "";
                    String tokenAccount = JWTUntils.genJWT(user);
                    Map<String, Object> map = new HashMap<>();
                    map.put("IsLogin", isLogin);
                    map.put("token", tokenAccount);
                    map.put("user", user);
                    PlayerManager.putMapUser(user, device);
                    return processSuccess(res, map);
                } else {
                    Utils.debug("Loi dich chuoi Json tu Google+");
                    return errorInvalid(res, "Lỗi xác thực tài khoản");
                }
            } else {
                return errorInvalid(res, "Chưa xử lý đăng nhập kiểu này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result onGetListAddress(Integer code, String id) {
        ApiResponse res;
        Map<String, Object> map = new HashMap<>();
        switch (code) {
            case Cmd.LIST_CITY:
                List<VnCity> cities = VnCity.find.query().where().eq("is_show", S.TRUE).findList();
                map.put("cities", cities);
                break;
            case Cmd.LIST_DISTRICT:
                List<VnDistrict> districts = VnDistrict.find.query().where().eq("city_id", id).eq("is_show", S.TRUE).findList();
                map.put("districts", districts);
                break;
            case Cmd.LIST_WARD:
                List<VnWard> wards = VnWard.find.query().where().eq("district_id", id).eq("is_show", S.TRUE).findList();
                map.put("wards", wards);
                break;
        }
        res = new ApiResponse(Error.SUCCESS, "", map);
        return ok(Json.toJson(res));
    }

    //update balance
    public Result onUpdateBalance(User user) {
        ApiResponse res;
        try {
//            JsonNode req = request().body().asJson();
////
//            long balance = req.get("balance").asInt();
////
//            user.balance = balance;
//            user.update();
            res = new ApiResponse(Error.ERROR, "Không được update trực tiếp", null);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    //update profile
    public Result onUpdateUser(User user) {
        ApiResponse res;
        try {
            JsonNode req = request().body().asJson();
            String fullname = req.get("fullname").asText();
            String phone = req.get("phone").asText();
            String email = req.get("email").asText();
            String birthday = req.get("birthday").asText();
            user.fullname = fullname;
            user.phone = phone;
            user.email = email;
            user.birthday = birthday;
            user.update();
            user.password = "";

            if (user.add_money_register == S.FALSE) {
                if (S.MONEY_REGISTER_ACCOUNT > 0) {
                    String title = "Thưởng xác nhận tài khoản";
                    String content = "Hệ thống tặng quý khách " + Utils.formatMoney(S.MONEY_REGISTER_ACCOUNT) + S.MONEY_NAME + " khi xác nhận tài khoản";
                    CmsAddMoneyLogs add = new CmsAddMoneyLogs(S.TYPE_ACCOUNT_USER, user.id, content, 1, S.MONEY_REGISTER_ACCOUNT, 1, Utils.getTimeStamp(), S.SENDER);
                    add.save();

                    TransactionHistoryUser tran = new TransactionHistoryUser(user.id, S.LOG_ADD_MONEY_SERVER, add.id, S.TRUE, S.MONEY_REGISTER_ACCOUNT, content);
                    tran.save();

                    Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, title, content, Utils.getTimeStamp());
                    notify.save();

                    NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user.id);
                    PushNotification.getInstance().SendOneDevice(user.fcm_token, title, content);

                    user.add_money_register = S.TRUE;
                    user.update();
                }

//                String sql = "SELECT COUNT(*) AS number FROM user WHERE imei = " + user.imei;
//                JsonNode dt2 = Json.toJson(RawQuery.getResultQuery(sql));
//                long number = dt2.get("number").asLong();
                long number = User.find.query().where().eq("imei", user.imei).findCount();
                if (number == 1) {
                    ProductGift productGift = ProductGift.find.byId(5);
                    if (productGift != null) {
                        LogProductGiftUserUsed log = new LogProductGiftUserUsed(productGift.id, user.id, Utils.getTimeStamp(), S.FALSE);
                        log.save();

                        Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, "Khuyễn mãi", "Chúc mừng quý khách đã được tặng gói bảo dưỡng (" + productGift.title + ")", Utils.getTimeStamp());
                        notify.save();
                    }
                }
            }

            res = new ApiResponse(Error.SUCCESS, "", user);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result login() {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            String username = req.get("username").asText();
            String password = req.get("password").asText();
            int device = req.get("device").asInt();
            //username, balance, status, phone, address, rank, score, email
            List<User> users = User.find.query().where().eq("username", username).eq("password", password).setMaxRows(1).findList();
            if (users.size() == 0) {
                res = new ApiResponse(Error.ERROR, Error.INVALID_ACCOUNT, null);
                return ok(Json.toJson(res));
            }
            User user = users.get(0);

            if (user.status == S.ACCOUNT_LOCK) {
                NodeJs.getInstance().emitLockAccount(S.TYPE_ACCOUNT_USER, user.id);
                return errorInvalid(res, "Tài khoản của bạn đã bị khóa");
            }

            if (user.device != device) {
                user.device = device;
                user.update();
            }
            if (user.first_login != null) {
                if (user.first_login == S.TRUE) {
                    user.first_login = S.FALSE;
                    user.update();
                }
            } else {
                user.first_login = S.TRUE;
                user.update();
            }

            user.password = "";
            System.out.println(user.username);
            String token = JWTUntils.genJWT(user);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", user);
            res = new ApiResponse(Error.SUCCESS, "", map);
            PlayerManager.putMapUser(user, device);
//            PushNotification.getInstance().SendOneDevice(user.fcm_token, "Title test", "Noi dung body");
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }


    public Result onChangePassword(User user) {
        ApiResponse res;
        try {
            JsonNode req = request().body().asJson();
            String oldPassword = req.get("oldPassword").asText();
            String password = req.get("password").asText();
            if (!user.password.equals(oldPassword)) {
                res = new ApiResponse(Error.ERROR, Error.INVALID_PASSWORD, null);
            } else {
                user.password = password;
                user.update();
                res = new ApiResponse(Error.SUCCESS, "", null);
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result onGetPositionAdmin() {
        ApiResponse res = null;
        try {
            int adminId = request().body().asJson().get("admin_id").asInt();
            Admin ktv = Admin.find.byId(adminId);
            if (ktv != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("latitude", ktv.latitude);
                map.put("longitude", ktv.longitude);
                map.put("position", ktv.position);
                return processSuccess(res, map);
            } else {
                return errorInvalidBodyData(res, "Không có kỹ thuật viên này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result processSuccess(ApiResponse res, Object dataResponse) {
        res = new ApiResponse(Error.SUCCESS, "", dataResponse);
        return ok(Json.toJson(res));
    }

    private Result processSuccess(ApiResponse res, String message, Object dataResponse) {
        res = new ApiResponse(Error.SUCCESS, message, dataResponse);
        return ok(Json.toJson(res));
    }

    private Result errorInvalidBodyData(ApiResponse res) {
        res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
        return ok(Json.toJson(res));
    }

    private Result errorInvalidBodyData(ApiResponse res, String msg) {
        res = new ApiResponse(Error.ERROR, msg, null);
        return ok(Json.toJson(res));
    }

    private Result errorInvalid(ApiResponse res, String errorMessage) {
        res = new ApiResponse(Error.ERROR, errorMessage, null);
        return ok(Json.toJson(res));
    }

    private Result onUserLogout(User user) {
        ApiResponse res = null;
        PlayerManager.userLogout(user);
        return processSuccess(res, null);
    }

    public Result onEvaluateKtv() {
        ApiResponse res = null;
        try {
            JsonNode reg = request().body().asJson();
            int service_package_maintenance_id = reg.get("service_package_maintenance_id").asInt();
            String soSao = reg.get("so_sao").asText();
            String content = reg.get("content").asText();
            ServicePackageMaintenance maintenance = ServicePackageMaintenance.find.byId(service_package_maintenance_id);
            if (maintenance != null) {
                if (maintenance.evaluate_admin == null || maintenance.evaluate_admin == S.FALSE) {
                    maintenance.evaluate_admin = S.TRUE;
                    maintenance.save();
                    String[] arrSao = soSao.split(S.TACH_PHAY);
                    int totalStar = 0;
                    int saoCong = 0;
                    if (arrSao.length > 1) {
                        for (String a : arrSao) {
                            totalStar += Integer.parseInt(a);
                        }
                        saoCong = Math.round(((float) totalStar / (float) arrSao.length));
                    }

                    List<AdminMission> lst = AdminMission.find.query().where().eq("service_package_maintenance_id", service_package_maintenance_id).findList();
                    if (lst != null && lst.size() > 0) {
                        for (AdminMission a : lst) {
                            a.so_sao = soSao;
                            a.khach_hang_danh_gia = content;
                            a.update();
                            Admin admin = Admin.find.byId(a.admin_id);
                            if (admin != null) {
                                if (admin.rank != null) {
                                    String[] arr = admin.rank.split(S.TACH_SHIFT_TRU);
                                    if (arr.length == 2) {
                                        int soLanDanhGia = Integer.parseInt(arr[0]) + 1;
                                        int _soSao = Integer.parseInt(arr[1]) + saoCong;
                                        admin.rank = soLanDanhGia + S.TACH_SHIFT_TRU + _soSao;
                                    } else {
                                        admin.rank = 1 + "_" + saoCong;
                                    }
                                } else {
                                    admin.rank = 1 + "_" + saoCong;
                                }
                                admin.update();
                            }
                        }
                        return processSuccess(res, null);
                    } else {
                        return errorInvalid(res, Error.KHONG_CO_MISSION_BAO_TRI_NAY);
                    }
                } else {
                    return errorInvalid(res, "Bạn đã đánh giá nhiệm vụ này rồi");
                }
            } else {
                return errorInvalid(res, "Bạn chưa đăng ký bảo trì gói này");
            }
        } catch (NullPointerException e) {
            Utils.debug("Force tu day");
            return errorInvalidBodyData(res);
        }
    }

    public Result onBonusKtv(User user) {
        ApiResponse res = null;
        try {
            JsonNode reg = request().body().asJson();
            int service_package_maintenance_id = reg.get("service_package_maintenance_id").asInt();
            int bonus = Math.abs(reg.get("bonus").asInt());
            ServicePackageMaintenance maintenance = ServicePackageMaintenance.find.byId(service_package_maintenance_id);
            if (maintenance != null) {
                String adminIds = maintenance.admin_id;
                String[] arrAdmin = adminIds.split(S.TACH_PHAY);
                if (adminIds.trim().length() > 0) {
                    if (arrAdmin.length > 0) {
                        if (maintenance.bonus_ktv == null || maintenance.bonus_ktv == 0) {
                            if (user.balance >= bonus) {
                                int size = arrAdmin.length;
                                int moneyRewardKtv = bonus / size;
                                String title = "Nhận thưởng";
                                String description = "Bạn được khách hàng có id:" + user.id + " Tên: " + user.fullname
                                        + " thưởng cho mã nhiệm vụ: " + maintenance.id + " - " + maintenance.des;
                                for (int i = 0; i < size; i++) {
                                    Admin admin = Admin.find.byId(Integer.parseInt(arrAdmin[i]));
                                    if (admin != null) {
                                        long newBonus = admin.bonus + moneyRewardKtv;
                                        admin.bonus = newBonus;
                                        admin.save();

                                        TransactionHistoryAdmin logTransaction = new TransactionHistoryAdmin(admin.id, S.LOG_ADD_MONEY_TIP_MAINTENANCE, service_package_maintenance_id, S.TRUE, moneyRewardKtv,
                                                description);
                                        logTransaction.save();
                                        Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_KTV, admin.id, title, description, Utils.getTimeStamp());
                                        notify.save();
                                        PlayerManager.adminChangeBalance(admin, "bạn được thưởng: " + Utils.formatMoney(moneyRewardKtv) + ", từ nhiệm vụ: " + service_package_maintenance_id);
                                        PushNotification.getInstance().SendOneDevice(admin.fcm_token, title, description);
                                        NodeJs.getInstance().emitUpdateMoney(Utils.getTypeAccountBenNodeJs(admin.level), admin.id);
                                    }
                                }

                                long newBalance = user.balance - bonus;
                                user.balance = newBalance;
                                user.update();
                                title = "Thưởng cho KTV";
                                description = "Quý khách đã thưởng cho các KTV tham gia bảo trì mã nhiệm vụ: " + maintenance.id
                                        + " - " + maintenance.des;
                                TransactionHistoryUser logTransaction = new TransactionHistoryUser(user.id, S.LOG_TIP_MAINTENANCE, service_package_maintenance_id, S.TRUE, -bonus,
                                        description);
                                logTransaction.save();
                                PlayerManager.userChangeBalance(user, "bạn thưởng cho KTV: " + Utils.formatMoney(bonus) + ", mã nhiệm vụ: " + service_package_maintenance_id);
                                Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, title, description, Utils.getTimeStamp());
                                notify.save();
                                PushNotification.getInstance().SendOneDevice(user.fcm_token, title, description);
                                NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user.id);
                                Map<String, Object> map = new HashMap<>();
                                map.put("newBalance", newBalance);

                                maintenance.bonus_ktv = bonus;
                                maintenance.update();
                                return processSuccess(res, map);
                            } else {
                                return errorInvalid(res, "Bạn không đủ tiền để thưởng, số dư hiện tại của bạn là: " + user.balance);
                            }
                        } else {
                            return errorInvalid(res, "Bạn đã thưởng cho công việc này rồi");
                        }
                    } else {
                        return errorInvalid(res, Error.KHONG_CO_KTV_BAO_TRI_MISSION_NAY);
                    }
                } else {
                    return errorInvalid(res, "Không có KTV bảo trì gói này");
                }
            } else {
                return errorInvalid(res, Error.KHONG_CO_MISSION_BAO_TRI_NAY);
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result onGetInfoAdminById() {
        ApiResponse res = null;
        try {
            int adminId = request().body().asJson().get("adminId").asInt();
            Admin admin = Admin.find.byId(adminId);
            if (admin != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("fullname", admin.fullname);
                map.put("phone", admin.phone);
                map.put("link_avatar", admin.link_avatar);
                return processSuccess(res, map);
            } else {
                return errorInvalid(res, Error.KHONG_CO_KTV_NAY);
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res, null);
        }
    }

    Result AboutUs() {
        ApiResponse res = null;
        Information info = Information.find.query().where().eq("type", 2).setMaxRows(1).findOne();
        String about_us = "Text giới thiệu về chúng tôi";
        if (info != null) {
            about_us = info.content;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("about_us", about_us);
        return processSuccess(res, map);
    }

    Result Policy() {
        ApiResponse res = null;
        Information info = Information.find.query().where().eq("type", 4).setMaxRows(1).findOne();
        String policy = "Text điều khoản sử dụng";
        if (info != null) {
            policy = info.content;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("policy", policy);
        return processSuccess(res, map);
    }

    public Result onUpdateFcmTokenUser(User user) {
        ApiResponse res = null;
        try {
            String fcm_token = request().body().asJson().get("fcm_token").asText();
            user.fcm_token = fcm_token;
            user.update();
            return processSuccess(res, null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res, null);
        }
    }

    public Result onSwitchboardCare() {
        ApiResponse res = null;
        try {
            Information info = Information.find.query().where().eq("type", 5).setMaxRows(1).findOne();
            if (info != null) {
                return processSuccess(res, info);
            } else {
                return errorInvalid(res, "Không có thông tin chăm sóc khách hàng");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res, null);
        }
    }

    public Result onQuestions() {
        ApiResponse res = null;
        List<Question> questions = Question.find.all();
        res = new ApiResponse(Error.SUCCESS, "", questions);
        return ok(Json.toJson(res));
    }

    public Result updateAvatar() {
        ApiResponse res;
        String user_id = request().getHeader(Key.USER_ID);
        String token = request().getHeader(Key.TOKEN);
        User user = User.find.byId(Integer.parseInt(user_id));
        Boolean checkToken = JWTUntils.validateJWT(token, user);
        if (user == null) {
            res = new ApiResponse(Error.ERROR, Error.USER_NOT_EXIST, null);
        } else if (!checkToken) {
            res = new ApiResponse(Error.INVALID_TOKEN, "", null);
        } else {
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart imgPart = body.getFile("img");
//            String fileName = imgPart.getFilename();
//            String contentType = imgPart.getContentType();
            if (imgPart != null) {
                File file = (File) imgPart.getFile();
                File newFile = new File("public/images/avatar_user/" + user_id + ".jpg");
                file.renameTo(newFile);
                user.link_avatar = S.IMAGE_FOLDER + user_id + ".jpg";
                user.update();
                res = new ApiResponse(Error.SUCCESS, "", user.link_avatar);
            } else {
                res = new ApiResponse(Error.ERROR, "UpdateAvatar error", null);
            }
        }
        return ok(Json.toJson(res));
    }

    public Result onInfoApp(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            String version = req.get("version").asText();
            AppInfo info = AppInfo.find.query().where().eq("version", version).eq("provider_id", user.device).setMaxRows(1).findOne();
            user.ip_current = request().remoteAddress();
            user.update();
            if (info != null) {
                return processSuccess(res, info);
            } else {
                return errorInvalid(res, "Không có thông tin AppInfo");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result onGetListNotifyUser(Integer userId) {
        ApiResponse res = null;
        try {
            Map<String, Object> map = new HashMap<>();
            List<Notify> lst = Notify.find.query().where()
                    .eq("type_account", S.TYPE_ACCOUNT_USER)
                    .eq("account_id", userId)
                    .eq("is_delete", S.FALSE).findList();
            map.put("notify_user", lst);

            List<Information> lst1 = Information.find.query().where().eq("type", 1).findList();
            map.put("notify_system", lst1);

            ReadNotifySystem noti = ReadNotifySystem.find.query().where()
                    .eq("type_account", S.TYPE_ACCOUNT_USER)
                    .eq("account_id", userId).setMaxRows(1).findOne();
            map.put("status_read_notify_system", noti != null ? noti.notify_system : "");

            return processSuccess(res, map);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res, null);
        }
    }

    public Result onDeleteNotifyUser() {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            long userId = req.get(Key.USER_ID).asLong();
            long id_notify = req.get("id_notify").asLong();

            Notify notify = Notify.find.byId(id_notify);
            if (notify != null) {
                if (notify.account_id == userId && notify.type_account == S.TYPE_ACCOUNT_USER) {
                    notify.is_delete = S.TRUE;
                    notify.update();
                    return processSuccess(res, id_notify);
                } else {
                    return errorInvalid(res, "Bạn không có thông báo này");
                }
            } else {
                return errorInvalid(res, "Bạn không có thông báo này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res, null);
        }
    }

    public Result onReadNotifyUser() {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            long userId = req.get(Key.USER_ID).asLong();
            long id_notify = req.get("id_notify").asLong();
            int status = req.get("status").asInt();

            Notify notify = Notify.find.byId(id_notify);
            if (notify != null) {
                if (notify.account_id == userId && notify.type_account == S.TYPE_ACCOUNT_USER) {
                    notify.is_read = status;
                    notify.save();
                    return processSuccess(res, id_notify);
                } else {
                    return errorInvalid(res, "Bạn không có thông báo này");
                }
            } else {
                return errorInvalid(res, "Bạn không có thông báo này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res, null);
        }
    }

    public Result onGetLogTransactionUser(Integer userId) {
        ApiResponse res = null;
        try {
            List<TransactionHistoryUser> lst = TransactionHistoryUser.find.query().where().eq("user_id", userId).findList();
            return processSuccess(res, lst);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res, null);
        }
    }

    public Result onGetLogMaintenanceAndEmergencyUser(Integer userId) {
        ApiResponse res = null;
        String sql = "SELECT a.id AS ma_bao_tri, a.des AS mo_ta, a.time AS created_time, a.number, a.status, c.name AS ten_goi" +
                " FROM service_package_maintenance AS a" +
                " INNER JOIN service_package_user AS b ON a.service_package_user_id = b.id" +
                " INNER JOIN service_package AS c ON c.id = b.service_package_id" +
                " WHERE b.user_id =" + userId;
        List<SqlRow> lstMaintenance = getListResultQuery(sql);

        sql = "SELECT a.id AS ma_cuu_ho, a.des mo_ta, a.status, a.time AS created_time" +
                " FROM emergency a" +
                " WHERE a.user_id=" + userId;
        List<SqlRow> lstEmergency = getListResultQuery(sql);

        sql = "SELECT a.id, a.service_id,a.details,a.images,a.created, a.status" +
                " FROM collaborator_mission a" +
                " WHERE a.user_id=" + userId;
        List<SqlRow> lstCallCollaborator = getListResultQuery(sql);
        Map<String, Object> map = new HashMap<>();
        map.put("maintenance", lstMaintenance);
        map.put("emergency", lstEmergency);
        map.put("call_collaborator", lstCallCollaborator);

        return processSuccess(res, map);
    }

    private static List<SqlRow> getListResultQuery(String sql) {
        return RawQuery.getListResultQuery(sql);
    }

    private static SqlRow getResultQuery(String sql) {
        return RawQuery.getResultQuery(sql);
    }

    public Result onEvaluateKtvEmergency() {
        ApiResponse res = null;
        try {
            JsonNode reg = request().body().asJson();
            long emergency_id = reg.get("emergency_id").asLong();
            String soSao = reg.get("soSao").asText();
            String content = reg.get("content").asText();
            Emergency maintenance = Emergency.find.byId(emergency_id);
            if (maintenance != null) {
                if (maintenance.evaluate_partner == null || maintenance.evaluate_partner == S.FALSE) {
                    maintenance.evaluate_partner = S.TRUE;
                    maintenance.save();
                    String[] arrSao = soSao.split(S.TACH_PHAY);
                    int totalStar = 0;
                    int saoCong = 0;
                    if (arrSao.length > 1) {
                        for (String a : arrSao) {
                            totalStar += Integer.parseInt(a);
                        }
                        saoCong = Math.round(((float) totalStar / (float) arrSao.length));
                    }

                    List<AdminEmergency> lst = AdminEmergency.find.query().where().eq("emergency_id", emergency_id).findList();
                    if (lst != null && lst.size() > 0) {
                        for (AdminEmergency a : lst) {
                            a.so_sao = soSao;
                            a.khach_hang_danh_gia = content;
                            a.update();

                            Admin admin = Admin.find.byId(a.admin_id);
                            if (admin != null) {
                                if (admin.rank != null) {
                                    String[] arr = admin.rank.split(S.TACH_SHIFT_TRU);
                                    if (arr.length == 2) {
                                        int soLanDanhGia = Integer.parseInt(arr[0]) + 1;
                                        int _soSao = Integer.parseInt(arr[1]) + saoCong;
                                        admin.rank = soLanDanhGia + S.TACH_SHIFT_TRU + _soSao;
                                    } else {
                                        admin.rank = 1 + "_" + saoCong;
                                    }
                                } else {
                                    admin.rank = 1 + "_" + saoCong;
                                }
                                admin.update();
                            }
                        }
                        return processSuccess(res, null);
                    } else {
                        return errorInvalid(res, Error.KHONG_CO_MISSION_BAO_TRI_NAY);
                    }
                } else {
                    return errorInvalid(res, "Bạn đã đánh giá nhiệm vụ này rồi");
                }
            } else {
                return errorInvalid(res, "Bạn không cứu hộ nhiệm vụ này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result onBonusKtvEmergency(User user) {
        ApiResponse res = null;
        try {
            JsonNode reg = request().body().asJson();
            long emergency_id = reg.get("emergency_id").asLong();
            int bonus = Math.abs(reg.get("bonus").asInt());
            Emergency emergency = Emergency.find.byId(emergency_id);
            if (emergency != null) {
                String adminIds = emergency.partner_id;
                String[] arrAdmin = adminIds.split(S.TACH_PHAY);
                if (adminIds.trim().length() > 0) {
                    if (arrAdmin.length > 0) {
                        if (emergency.bonus_ktv == null || emergency.bonus_ktv == 0) {
                            if (user.balance >= bonus) {
                                int size = arrAdmin.length;
                                int moneyRewardKtv = bonus / size;
                                String title = "Nhận thưởng";
                                String description = "Bạn được khách hàng có id:" + user.id + " Tên: " + user.fullname
                                        + " thưởng cho mã nhiệm vụ cứu hộ: " + emergency.id + " - " + emergency.des;
                                for (int i = 0; i < size; i++) {
                                    Admin admin = Admin.find.byId(Integer.parseInt(arrAdmin[i]));
                                    if (admin != null) {
                                        long newBonus = admin.bonus + moneyRewardKtv;
                                        admin.bonus = newBonus;
                                        admin.save();

                                        TransactionHistoryAdmin logTransaction = new TransactionHistoryAdmin(admin.id, S.LOG_ADD_MONEY_TIP_EMERGENCY, emergency_id, S.TRUE, moneyRewardKtv,
                                                description);
                                        logTransaction.save();
                                        Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_KTV, admin.id, title, description, Utils.getTimeStamp());
                                        notify.save();
                                        PlayerManager.adminChangeBalance(admin, "bạn được thưởng: " + Utils.formatMoney(moneyRewardKtv) + ", từ nhiệm vụ: " + emergency_id);
                                        PushNotification.getInstance().SendOneDevice(admin.fcm_token, title, description);
                                        NodeJs.getInstance().emitUpdateMoney(Utils.getTypeAccountBenNodeJs(admin.level), admin.id);
                                    }
                                }

                                long newBalance = user.balance - bonus;
                                user.balance = newBalance;
                                user.update();
                                title = "Thưởng cho KTV";
                                description = "Quý khách đã thưởng cho các KTV tham gia bảo trì mã nhiệm vụ cứu hộ: " + emergency.id
                                        + " - " + emergency.des;
                                TransactionHistoryUser logTransaction = new TransactionHistoryUser(user.id, S.LOG_TIP_EMERGENCY, emergency_id, S.TRUE, -bonus,
                                        description);
                                logTransaction.save();
                                PlayerManager.userChangeBalance(user, "bạn thưởng cho KTV: " + Utils.formatMoney(bonus) + ", mã nhiệm vụ cứu hộ: " + emergency_id);
                                Map<String, Object> map = new HashMap<>();
                                map.put("newBalance", newBalance);
                                Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, title, description, Utils.getTimeStamp());
                                notify.save();
                                PushNotification.getInstance().SendOneDevice(user.fcm_token, title, description);
                                NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user.id);

                                emergency.bonus_ktv = bonus;
                                emergency.update();
                                return processSuccess(res, map);
                            } else {
                                return errorInvalid(res, "Bạn không đủ tiền để thưởng, số dư hiện tại của bạn là: " + user.balance);
                            }
                        } else {
                            return errorInvalid(res, "Bạn đã thưởng cho công việc này rồi");
                        }
                    } else {
                        return errorInvalid(res, Error.KHONG_CO_KTV_EMERGENCY_MISSION_NAY);
                    }
                } else {
                    return errorInvalid(res, Error.KHONG_CO_KTV_EMERGENCY_MISSION_NAY);
                }
            } else {
                return errorInvalid(res, Error.KHONG_CO_EMERGENCY_BAO_TRI_NAY);
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result onConfirmMetPartnerEmergency() {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            long emergency_id = req.get("emergency_id").asLong();
            Emergency emergency = Emergency.find.byId(emergency_id);
            if (emergency == null) {
                res = new ApiResponse(Error.ERROR, Error.MAINTENANCE_NOT_EXISTS, null);
            } else {
                emergency.status = StatusJob.XAC_NHAN_KTV_DEN;
                emergency.save();
                res = new ApiResponse(Error.SUCCESS, "", null);
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result onUserChat(User user) {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            String content = req.get("content").asText();
            Integer id_cskh = req.get("id_cskh").asInt();
            Integer read = req.get("read").asInt();
            ChatInfo info = new ChatInfo(S.TYPE_ACCOUNT_USER, user.id, user.id, content, id_cskh, read);
            info.fullname = user.fullname;
            info.save();
            return processSuccess(res, null);
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result onGetLogChatUser(Integer userId) {
        ApiResponse res = null;
        try {
            List<ChatInfo> list_update = ChatInfo.find.query().where().eq("type_account", 5).eq("account_id_chat", userId).eq("is_read", 0).findList();
            for (ChatInfo chat : list_update) {
                chat.is_read = 1;
                chat.update();
            }
            List<ChatInfo> lst = ChatInfo.find.query().where()
                    .eq("account_id_chat", userId).setOrderBy("id DESC").setMaxRows(50).findList();
            return processSuccess(res, lst);
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result onListProductUser() {
        ApiResponse res = null;
        String sql = RawQuery.QUERY_PRODUCT;
        sql += "WHERE a.is_show=" + S.TRUE;
        return processSuccess(res, getListResultQuery(sql));
    }

    public Result onGetProductDetail(JsonNode req) {
        ApiResponse res = null;
        try {
            int product_id = req.get("product_id").asInt();
            return processSuccess(res, getResultQuery(RawQuery.getRawQueryProductDetail(product_id)));
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result onGetListProductUser(Integer userId) {
        ApiResponse res = null;
        List<ProductUser> lst = ProductUser.find.query().where()
                .eq("user_id", userId).findList();
        return processSuccess(res, lst);
    }

    public Result onUseProductUser(User user) {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            int product_id = req.get("product_id").asInt();
            int number = Math.abs(req.get("number").asInt());

            ProductUser productUser = ProductUser.find.query().where()
                    .eq("user_id", user.id)
                    .eq("product_id", product_id).findOne();

            if (productUser != null) {
                if (productUser.number >= number) {
                    productUser.number -= number;
                    productUser.save();

                    ProductUserLogUse logUse = new ProductUserLogUse(user.id, product_id, number, Utils.getCurrentMiniseconds());
                    logUse.save();

                    Map<String, Object> map = new HashMap<>();
                    map.put("product_id", product_id);
                    map.put("new_number", productUser.number);
                    return processSuccess(res, map);
                } else {
                    return errorInvalid(res, "Quý khách chỉ có " + number + " sản phẩm này");
                }
            } else {
                return errorInvalid(res, "Bạn không có sản phẩm này để sử dụng");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result onGetListProductIdUser() {
        ApiResponse res = null;
        List<ProductType> lst = ProductType.find.all();
        return processSuccess(res, lst);
    }

    public Result onGetListProductUserByType() {
        ApiResponse res = null;
        try {
            int type = request().body().asJson().get("type").asInt();
            String sql = RawQuery.QUERY_PRODUCT;
            sql += "WHERE a.type=" + type;
            return processSuccess(res, getListResultQuery(sql));
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetDetailServicePackageInfo() {
        ApiResponse res = null;
        List<ServiceInfo> infos = ServiceInfo.find.query().where()
                .eq("service_package_id", request().body().asJson().get("service_package_id").asInt()).findList();
        return processSuccess(res, infos);
    }

    private Result onUpdateAddressUser(User user) {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            String province = req.get("province").asText();
            String district = req.get("district").asText();
            String ward = req.get("ward").asText();
            String address = req.get("address").asText();
            String address_id = req.get("address_id").asText();
            Double latitude = req.get("latitude").asDouble();
            Double longitude = req.get("longitude").asDouble();
//            String message = "";
//            if(user.province == null){
//                user.province = province;
//                user.district = district;
//                user.ward = ward;
//                user.address = address;
//                user.latitude = latitude;
//                user.longitude = longitude;
//                user.update();
//                message = "Cập nhật địa chỉ thành công!";
//            }
//            else{
//                String[] arrAddress = address_id.split("_");
//                UserTempAddress uta = UserTempAddress.find.query().where().eq("user_id", user.id).findOne();
//                if(uta == null){
//                    UserTempAddress userTempAddress = new UserTempAddress(user.id, address, province, district, ward, arrAddress[0], arrAddress[1], arrAddress[2], latitude, longitude);
//                    userTempAddress.save();
//                }
//                else{
//                    uta.province = province;
//                    uta.district = district;
//                    uta.ward = ward;
//                    uta.address = address;
//                    uta.province_id = arrAddress[0];
//                    uta.district_id = arrAddress[1];
//                    uta.ward_id = arrAddress[2];
//                    uta.latitude = latitude;
//                    uta.longitude = longitude;
//                    uta.update();
//                }
//                message = "Chúng tôi đã ghi nhận yêu cầu thay đổi địa chỉ của quý khách, chúng tôi sẽ xác nhận địa chỉ của quý khách vào thời gian sớm nhất!";
//                NodeJs.getInstance().emitUserUpdateAddress(user.id, uta.id);
//            }
//            user.password = "";
//            return processSuccess(res, message, user);


            user.province = province;
            user.district = district;
            user.ward = ward;
            user.address = address;
            String[] arrAddress = address_id.split("_");
            user.province_id = arrAddress[0];
            user.district_id = arrAddress[1];
            user.ward_id = arrAddress[2];
            user.latitude = latitude;
            user.longitude = longitude;
            user.update();
            user.password = "";
            return processSuccess(res, user);
        } catch (NullPointerException e) {
            System.out.print(e);
            return errorInvalidBodyData(res);
        }
    }

    private Result onInfoGetTypeCard() {
        ApiResponse res = null;
        List<TypeCard> lst = TypeCard.find.all();
        return processSuccess(res, lst);
    }

    private Result onRechargeCardUser(User user) {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            int type_card = req.get("type_card").asInt();
            String card_code = req.get("card_code").asText();
            String card_seri = req.get("card_seri").asText();

            TypeCard cardInfo = TypeCard.find.query().where().eq("type_card", type_card).findOne();
            if (cardInfo == null) {
                return errorInvalid(res, "Thông tin nạp card không hợp lệ");
            }

            String request = card_seri + ":" + card_code + ":" + type_card;
            String trans_id = "_" + user.username + "_"
                    + System.currentTimeMillis();
            String params = "partner=" + Config.PARTNER_CARD + "&password=" + Config.PASSWORD_PARTNER_CARD
                    + "&providerID=" + user.device + "&serial=" + card_seri + "&pin=" + card_seri + "&cardType="
                    + cardInfo.type_card + "&requestID=" + request + "&nick=" + user.username + "&encrypt=0";//1: là mã hóa card (nếu để 1 cần thêm thông tin key gì đó)

            PayCards logPayCard = new PayCards(trans_id, user.id, user.username, request, cardInfo.provider_code, card_code, card_seri, user.device);
            logPayCard.save();
            try {
                String response = Recharge.card(Config.LINK_URL_RECHARGE_CARD, params);

                String[] result = response.split("&");
                String tach = "=";
                int kp = Integer.parseInt(result[1].split(tach)[1]);
                String notify = "";
                int moneyCard = 0;
                switch (kp) {
                    case 99:// pending
                        logPayCard.response = "Thẻ không hợp lệ";
                        logPayCard.status = 0;
                        logPayCard.price = 0;
                        logPayCard.conversion_price = 0;
                        notify = "Hệ thống đang xử lý";
                        break;
                    case 0:// thanh cong
                        moneyCard = Integer.parseInt(result[3].split(tach)[1]);
                        logPayCard.response = "Nạp thẻ thành công";
                        logPayCard.status = 2;
                        logPayCard.price = moneyCard;
                        logPayCard.conversion_price = moneyCard;
                        notify = "Nạp card " + cardInfo.name_card + " thành công, tài khoản của quý khách được cộng thêm: " + Utils.formatMoney(moneyCard) + S.MONEY_NAME;
                        user.balance += moneyCard;
                        user.total_money_charging_card += moneyCard;
                        user.update();
                        PlayerManager.userChangeBalance(user, notify);
                        NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user.id);
                        NodeJs.getInstance().emitServerPush(S.TYPE_ACCOUNT_USER, user.id, "Chúc mừng quý khách đã nạp thành công " + Utils.formatMoney(moneyCard) + S.MONEY_NAME);
                        break;
                    default:// that bai
                        logPayCard.response = "Thẻ không hợp lệ";
                        logPayCard.status = 1;
                        logPayCard.price = 0;
                        logPayCard.conversion_price = 0;
                        notify = "Seri hoặc mã thẻ không hợp lệ. Xin vui lòng kiểm tra lại thông tin đã nhập hoặc có đúng thẻ của: " + cardInfo.name_card + " không?";
                        break;
                }
                logPayCard.update();

                TransactionHistoryUser logTran = new TransactionHistoryUser(user.id, S.LOG_RECHARGE_CARD, logPayCard.id, kp == 0 ? S.TRUE : S.FALSE, moneyCard, notify);
                logTran.save();

                if (kp == 99) {
                    return processSuccess(res, notify, user.balance);
                } else if (kp == 0) {
                    return processSuccess(res, notify, user.balance);
                } else {
                    return errorInvalid(res, notify);
                }
            } catch (Exception e) {
                logPayCard.update();
                e.printStackTrace();
                return errorInvalid(res, "Lỗi hệ thống 01");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetInfoRechargeBank() {
        ApiResponse res = null;
        List<BankInfo> lst = BankInfo.find.all();
        res = new ApiResponse(Error.SUCCESS, "", lst);
        return ok(Json.toJson(res));
    }

    private Result onRechargeBankUser(User user) {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            int type = req.get("type").asInt();
            long amount = req.get("card_number").asLong();
            String bank_code = req.get("bank_code").asText();
            int currency = 0;

            if (amount <= 0) {
                return errorInvalid(res, "Số tiền nạp không hợp lệ");
            }

            if (type == S.TYPE_BANK_LOCAL_ATM || type == S.TYPE_BANK_GLOBAL) {
            } else {
                return errorInvalidBodyData(res);
            }

            if (type == S.TYPE_BANK_GLOBAL) {
                currency = req.get("currency").asInt();//1: VND, 2: USD
                if (currency == S.TYPE_CARD_GLOBAL_VND || currency == S.TYPE_CARD_GLOBAL_USD) {
                } else {
                    return errorInvalidBodyData(res);
                }
            }

            BankInfo bankInfo = BankInfo.find.query().where().eq("code", bank_code).findOne();
            if (bankInfo == null) {
                return errorInvalidBodyData(res);
            }

            try {
                Reponse reponse = Recharge.depositeBankUser(type, currency, user, bankInfo, amount);
                Utils.debug("ResponseSend code bank deposite user :" + reponse.getResponsecode() + " - " + reponse.getDescriptionen());
                if (reponse.getResponsecode().equals(ResultRecharge.SUCCESS)) {
                    return processSuccess(res, reponse.getUrl());
                } else {
                    return errorInvalid(res, "Lỗi hệ thống");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return errorInvalid(res, "Có lỗi trong quá trình xử lý");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetDetailLogTransactionUser(int userId) {
        ApiResponse res = null;
        try {
            JsonNode req = request().body().asJson();
            int type = req.get("type").asInt();
            long transaction_id = req.get("transaction_id").asLong();
            Object obj = null;
            String sql = null;

            switch (type) {
                /*** Add Money ***/

                case S.LOG_RECHARGE_CARD:
                    sql = "SELECT a.id, a.card_seri, a.conversion_price AS tien_nap, a.response AS description, a.created_at" +
                            " FROM pay_cards AS a" +
                            " WHERE a.user_id =" + userId + " AND a.id=" + transaction_id;
                    break;

                case S.LOG_RECHARGE_BANK:
                    sql = "SELECT a.id, a.type, a.tien_nap, a.fee, a.bank_name, a.status, a.created_at AS created" +
                            " FROM log_recharge_ngan_luong AS a" +
                            " WHERE a.account_id =" + userId + " AND a.type_account =" + S.TYPE_ACCOUNT_USER + " AND a.id=" + transaction_id;
                    break;

                case S.LOG_RECHARGE_VIA_KTV:
                    sql = "SELECT a.id, a.description, a.description_response, a.admin_id AS ma_admin, a.money, a.status, a.created_at AS created" +
                            " FROM log_recharge_via_ktv AS a" +
                            " WHERE a.user_id=" + userId + " AND a.id=" + transaction_id;
                    break;

                case S.LOG_ADD_MONEY_SERVER:
                case S.LOG_SUB_MONEY_SERVER:
                    sql = "SELECT a.id,a.note,a.money,a.created_at" +
                            " FROM cms_add_money_logs a" +
                            " WHERE a.type=" + S.TYPE_ACCOUNT_USER + " AND a.id=" + transaction_id;
                    break;
                /*** End Add Money ***/

                case S.LOG_MUA_GOI_DICH_VU:
                    sql = "SELECT a.id, a.name, a.price, b.created" +
                            " FROM service_package AS a" +
                            " INNER JOIN log_buy_service_package_user AS b ON a.id = b.service_package_id" +
                            " WHERE b.user_id =" + userId + " AND b.id =" + transaction_id;
                    break;

                case S.LOG_MUA_SAN_PHAM:
                    sql = "SELECT a.id, a.number, a.money, a.created AS time_buy, b.id AS product_id, b.name, b.link_icon, c.name AS address_sell" +
                            " FROM product_user_log_buy AS a" +
                            " INNER JOIN product AS b ON a.product_id = b.id" +
                            " INNER JOIN shop AS c ON c.id = b.shop" +
                            " WHERE a.user_id =" + userId + " AND a.id =" + transaction_id;
                    break;

                case S.LOG_TIP_MAINTENANCE:
                    sql = "SELECT a.id AS ma_nhiem_vu, a.des AS description, a.time, a.bonus_ktv, b.address, c.name AS ten_goi_bao_tri" +
                            " FROM service_package_maintenance AS a" +
                            " INNER JOIN service_package_user AS b ON a.service_package_user_id = b.id" +
                            " INNER JOIN service_package AS c ON b.service_package_id = c.id" +
                            " WHERE b.user_id =" + userId + " AND a.id =" + transaction_id;
                    break;

                case S.LOG_TIP_EMERGENCY:
                    sql = "SELECT a.id AS ma_cuu_ho, a.des AS description, a.address, a.bonus_ktv, a.time, c.name AS ten_goi_bao_tri" +
                            " FROM emergency AS a" +
                            " INNER JOIN service_package_user AS b ON a.service_package_user_id = b.id" +
                            " INNER JOIN service_package AS c ON b.service_package_id = c.id" +
                            " WHERE a.user_id =" + userId + " AND a.id =" + transaction_id;
                    break;

                case S.LOG_PAYMENT_CART://thanh toán đơn hàng
                    sql = "SELECT a.id AS ma_don_hang, a.price, a.detail_cart, a.status, a.created" +
                            " FROM log_payment_cart_user AS a" +
                            " WHERE a.user_id =" + userId + " AND a.id =" + transaction_id;
                    break;

                default:
                    break;
            }
            if (sql != null) {
                obj = getResultQuery(sql);
            }

            return processSuccess(res, obj);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    public Result baoCapViPhamChinhSach(String transid, String responCode, String mac) {
        ApiResponse res = null;
        try {
            LogRechargeEpay log = LogRechargeEpay.find.query().where().eq("transaction_id", transid).setMaxRows(1).findOne();
            if (log != null) {
                if (log.type_account == S.TYPE_ACCOUNT_USER) {
                    User user = User.find.byId(log.account_id);
                    if (user != null) {
                        String macCheck = Recharge.checkDESMAC(transid, responCode, Config.EPAY_MERCHANT_RECEIVE_KEY);
                        if (mac.equals(macCheck))//Mac hợp lệ => Dữ liệu toàn vẹn
                        {
                            Reponse reponse = Recharge.confirmBank(transid, responCode, log.tien_nap);
                            Utils.debug("Responce code confirm bank: " + reponse.getResponsecode()
                                    + " - " + reponse.getDescriptionen());

                            log.mac_receive = mac;
                            log.response_receive = reponse.getResponsecode();
                            log.description_receive = reponse.getDescriptionvn();
                            log.status = reponse.getResponsecode().equals(ResultRecharge.SUCCESS) ? Status.SUCCESS : Status.FAILURE;
                            int status = reponse.getResponsecode().equals(ResultRecharge.SUCCESS) ? S.TRUE : S.FALSE;


                            if (log.response_receive != null) {
                                TransactionHistoryUser logTran = new TransactionHistoryUser(user.id, S.LOG_RECHARGE_BANK, log.id,
                                        status, log.tien_nap, "Nạp " + Utils.formatMoney(log.tien_nap) + " từ ngân hàng " + log.bank_name);
                                logTran.save();
                            }

                            if (reponse.getResponsecode().equals(ResultRecharge.SUCCESS)) {
                                if (log.is_add_money == S.FALSE) {
                                    user.balance += log.tien_nap;
                                    user.total_money_charging_bank += log.tien_nap;
                                    user.update();
                                    PlayerManager.userChangeBalance(user, "Nạp " + Utils.formatMoney(log.tien_nap) + S.MONEY_NAME + " từ " + log.bank_name);
                                    log.is_add_money = S.TRUE;
                                    log.update();
                                    NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user.id);
                                    NodeJs.getInstance().emitServerPush(S.TYPE_ACCOUNT_USER, user.id, "Chúc mừng quý khách đã nạp thành công " + Utils.formatMoney(log.tien_nap) + S.MONEY_NAME);
//                                        return processSuccess(res, user.balance);
                                    return ok(bank_info_recharge.render("Nạp tiền thành công, chúc mừng quý khách"));
                                } else {
                                    Utils.debug("Da cong tien cho giao dich nay");
                                    return ok(bank_info_recharge.render("Thông tin giao dịch không hợp lệ"));
                                }
                            } else {
                                log.update();
                                return ok(bank_info_recharge.render("Giao dịch lỗi"));
//                                    return errorInvalid(res, "Giao dịch lỗi");
                            }
                        } else {
                            Utils.debug("Thong tin giao dich da bi thay doi");
                            return ok(bank_info_recharge.render("Thông tin giao dịch không hợp lệ"));
                        }
                    } else {
                        return ok(bank_info_recharge.render("Không có tài khoản KH này"));
                    }
                }
                //KTV recharge
                else if (log.type_account == S.TYPE_ACCOUNT_KTV) {
                    Admin admin = Admin.find.byId(log.account_id);
                    if (admin != null) {
                        String macCheck = Recharge.checkDESMAC(transid, responCode, Config.EPAY_MERCHANT_RECEIVE_KEY);
                        if (mac.equals(macCheck))//Mac hợp lệ => Dữ liệu toàn vẹn
                        {
                            Reponse reponse = Recharge.confirmBank(transid, responCode, log.tien_nap);
                            Utils.debug("Responce code confirm bank: " + reponse.getResponsecode()
                                    + " - " + reponse.getDescriptionen());

                            log.mac_receive = mac;
                            log.response_receive = reponse.getResponsecode();
                            log.description_receive = reponse.getDescriptionvn();
                            log.status = reponse.getResponsecode().equals(ResultRecharge.SUCCESS) ? Status.SUCCESS : Status.FAILURE;
                            int status = reponse.getResponsecode().equals(ResultRecharge.SUCCESS) ? S.TRUE : S.FALSE;

                            if (log.response_receive != null) {
                                TransactionHistoryAdmin logTran = new TransactionHistoryAdmin(admin.id, S.LOG_RECHARGE_BANK, log.id,
                                        status, log.tien_nap, "Nạp " + Utils.formatMoney(log.tien_nap) + " từ ngân hàng " + log.bank_name);
                                logTran.save();
                            }

                            if (reponse.getResponsecode().equals(ResultRecharge.SUCCESS)) {
                                if (log.is_add_money == S.FALSE) {
                                    admin.balance += log.tien_nap;
                                    admin.update();
                                    PlayerManager.adminChangeBalance(admin, "Nạp " + Utils.formatMoney(log.tien_nap) + " DiV từ " + log.bank_name);
                                    log.is_add_money = S.TRUE;
                                    log.update();
                                    int typeAccountBenNodeJs = Utils.getTypeAccountBenNodeJs(admin.level);
                                    NodeJs.getInstance().emitUpdateMoney(typeAccountBenNodeJs, admin.id);
                                    NodeJs.getInstance().emitServerPush(typeAccountBenNodeJs, admin.id, "Chúc mừng bạn đã nạp thành công " + Utils.formatMoney(log.tien_nap) + S.MONEY_NAME);
//                                        return processSuccess(res, admin.balance);
                                    return ok(bank_info_recharge.render("Nạp tiền thành công, chúc mừng bạn"));
                                } else {
                                    Utils.debug("Da cong tien cho giao dich nay");
                                    return ok(bank_info_recharge.render("Thông tin giao dịch không hợp lệ"));
                                }
                            } else {
                                log.update();
                                return ok(bank_info_recharge.render("Giao dịch lỗi"));
//                                    return errorInvalid(res, "Giao dịch lỗi");
                            }
                        } else {
                            Utils.debug("Thong tin giao dich da bi thay doi");
                            return ok(bank_info_recharge.render("Thông tin giao dịch không hợp lệ"));
                        }
                    } else {
                        return ok(bank_info_recharge.render("Không có tài khoản KTV này"));
                    }
                } else
                    return ok(bank_info_recharge.render("Thông tin không hợp lệ"));
            } else {
                return ok(bank_info_recharge.render("Thông tin giao dịch không hợp lệ"));
            }
        } catch (Exception e) {
            return ok(bank_info_recharge.render("Có lỗi trong quá trình xử lý"));
        }
    }

    public Result baoCapViPhamChinhSachUser2(String error_code, String token, String order_code, String order_id) {
        ApiResponse res = null;
        try {
            LogRechargeNganLuong log = LogRechargeNganLuong.find.query().where().eq("token", token).setMaxRows(1).findOne();
            if (log != null) {
                if (log.type_account == S.TYPE_ACCOUNT_USER) {
                    User user = User.find.byId(log.account_id);
                    if (user != null) {
                        ResponseCheck response = Recharge.GetTransactionDetail(token);
                        Utils.debug("Responce code GetTransactionDetail NGAN LUONG user: " + response.getErrorCode()
                                + " - " + response.getDescription());

                        log.order_code = order_code;
                        log.order_id = order_id;
                        log.response_receive = response.getErrorCode();
                        log.description_receive = response.getDescription();
                        log.transaction_status = response.getTransactionStatus();
                        log.transaction_id_ngan_luong = response.getTransactionId();
                        log.status = response.getErrorCode().equals(ResultRecharge.SUCCESS) ? Status.SUCCESS : Status.FAILURE;

                        if (response.getTransactionStatus().equals(ResultRecharge.SUCCESS)) {
                            if (log.is_add_money == S.FALSE) {
                                user.balance += log.tien_nap;
                                user.total_money_charging_bank += log.tien_nap;
                                user.update();
                                PlayerManager.userChangeBalance(user, "Nạp " + Utils.formatMoney(log.tien_nap) + S.MONEY_NAME + " từ " + log.bank_name);
                                log.is_add_money = S.TRUE;
                                log.update();
                                int status = response.getErrorCode().equals(ResultRecharge.SUCCESS) ? S.TRUE : S.FALSE;

                                if (log.response_receive != null) {
                                    TransactionHistoryUser logTran = new TransactionHistoryUser(user.id, S.LOG_RECHARGE_BANK, log.id,
                                            status, log.tien_nap, "Nạp " + Utils.formatMoney(log.tien_nap) + " từ ngân hàng " + log.bank_name);
                                    logTran.save();
                                }
                                NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user.id);
                                NodeJs.getInstance().emitServerPush(S.TYPE_ACCOUNT_USER, user.id, "Chúc mừng quý khách đã nạp thành công " + Utils.formatMoney(log.tien_nap) + S.MONEY_NAME);
//                                        return processSuccess(res, user.balance);
                                return ok(bank_info_recharge.render("Nạp tiền thành công, chúc mừng quý khách"));
                            } else {
                                Utils.debug("Da cong tien cho giao dich nay");
                                return ok(bank_info_recharge.render("Giao dịch đã xử lý xong"));
                            }
                        } else {
                            log.update();
                            return ok(bank_info_recharge.render("Giao dịch lỗi"));
//                                    return errorInvalid(res, "Giao dịch lỗi");
                        }
                    } else {
                        return ok(bank_info_recharge.render("Không có tài khoản KH này"));
                    }
                }
                //KTV recharge
                else if (log.type_account == S.TYPE_ACCOUNT_KTV) {
                    Admin admin = Admin.find.byId(log.account_id);
                    if (admin != null) {
                        ResponseCheck response = Recharge.GetTransactionDetail(token);
                        Utils.debug("Responce code GetTransactionDetail NGAN LUONG admin: " + response.getErrorCode()
                                + " - " + response.getDescription());

                        log.order_code = order_code;
                        log.order_id = order_id;
                        log.response_receive = response.getErrorCode();
                        log.description_receive = response.getDescription();
                        log.transaction_status = response.getTransactionStatus();
                        log.transaction_id_ngan_luong = response.getTransactionId();
                        log.status = response.getErrorCode().equals(ResultRecharge.SUCCESS) ? Status.SUCCESS : Status.FAILURE;

                        if (response.getTransactionStatus().equals(ResultRecharge.SUCCESS)) {
                            if (log.is_add_money == S.FALSE) {
                                admin.balance += log.tien_nap;
                                admin.update();
                                PlayerManager.adminChangeBalance(admin, "Nạp " + Utils.formatMoney(log.tien_nap) + " DiV từ " + log.bank_name);
                                log.is_add_money = S.TRUE;
                                log.update();
                                int status = response.getErrorCode().equals(ResultRecharge.SUCCESS) ? S.TRUE : S.FALSE;
                                if (log.response_receive != null) {
                                    TransactionHistoryAdmin logTran = new TransactionHistoryAdmin(admin.id, S.LOG_RECHARGE_BANK, log.id,
                                            status, log.tien_nap, "Nạp " + Utils.formatMoney(log.tien_nap) + " từ ngân hàng " + log.bank_name);
                                    logTran.save();
                                }
                                int typeAccountBenNodeJs = Utils.getTypeAccountBenNodeJs(admin.level);
                                NodeJs.getInstance().emitUpdateMoney(typeAccountBenNodeJs, admin.id);
                                NodeJs.getInstance().emitServerPush(typeAccountBenNodeJs, admin.id, "Chúc mừng bạn đã nạp thành công " + Utils.formatMoney(log.tien_nap) + S.MONEY_NAME);
//                                        return processSuccess(res, admin.balance);
                                return ok(bank_info_recharge.render("Nạp tiền thành công, chúc mừng bạn"));
                            } else {
                                Utils.debug("Da cong tien cho giao dich nay");
                                return ok(bank_info_recharge.render("Giao dịch đã xử lý xong"));
                            }
                        } else {
                            log.update();
                            return ok(bank_info_recharge.render("Giao dịch lỗi"));
//                                    return errorInvalid(res, "Giao dịch lỗi");
                        }
                    } else {
                        return ok(bank_info_recharge.render("Không có tài khoản KTV này"));
                    }
                } else
                    return ok(bank_info_recharge.render("Thông tin không hợp lệ"));
            } else {
                return ok(bank_info_recharge.render("Thông tin giao dịch không hợp lệ"));
            }
        } catch (Exception e) {
            Utils.debug(e);
            return ok(bank_info_recharge.render("Có lỗi trong quá trình xử lý"));
        }
    }

    public Result baoCapViPhamChinhSachUser3() {
        return ok("Đã hủy giao dịch");
    }

    private Result onGetInfoCart(int userId) {
        ApiResponse res = null;
        try {
            UserCart cart = UserCart.find.query().where()
                    .eq("user_id", userId).findOne();
            return processSuccess(res, cart);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onUpdateCart(int userId) {
        ApiResponse res = null;
        try {
            String info_cart = request().body().asJson().get("info_cart").asText();
            UserCart cart = UserCart.find.query().where()
                    .eq("user_id", userId).findOne();
            if (cart != null) {
                cart.info_cart = info_cart;
                cart.time_change = Utils.getTimeStamp();
                cart.update();
            } else {
                cart = new UserCart(userId, info_cart, Utils.getTimeStamp());
                cart.save();
            }
            return processSuccess(res, null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onPaymentCart(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            String info_cart = req.get("info_cart").asText();
            String discount_code = req.get("discount_code").asText();

            JsonNode cart = Json.parse(info_cart);
            if (cart.size() > 0) {
                UserCart cartUser = UserCart.find.query().where()
                        .eq("user_id", user.id).findOne();
                if (cartUser != null) {
                    cartUser.info_cart = "[]";
                    cartUser.time_change = Utils.getTimeStamp();
                    cartUser.update();
                }

                Map<Integer, Integer> mapCard = new HashMap<>();
                List<Product> lstProduct = new ArrayList<>();
                String product_id_detail;
                StringBuilder builder = new StringBuilder();
                long price = 0L;

                for (int i = 0; i < cart.size(); i++) {
                    int productId = cart.get(i).get("id").asInt();
                    int soLuong = cart.get(i).get("so_luong").asInt();
                    mapCard.put(productId, soLuong);
                    Product product = Product.find.byId(productId);
                    if (product != null) {
                        lstProduct.add(product);
                        builder.append(productId).append(S.GHEP).append(soLuong).append(S.GHEP).append(product.shop).append(S.TACH_PHAY);
                        long priceSale = product.price - (long) ((float) product.price * product.sale);
                        price += soLuong * priceSale;
                        if (product.number < soLuong) {
                            return errorInvalid(res, "Sản phẩm " + product.name + " đã hết hàng");
                        }
                    } else {
                        return errorInvalid(res, "Giỏ hàng có sản phẩm không hợp lệ");
                    }
                }

                long coupon_id = 0;
                Coupon coupon = Coupon.find.query().where().eq("coupon", discount_code)
                        .eq("used", S.FALSE).setMaxRows(1).findOne();
                if (coupon != null) {
                    coupon_id = coupon.id;
                    if (coupon.type == 1)//giam tien
                    {
                        if (price >= coupon.han_muc) {
                            price -= Math.abs(coupon.money);
                            coupon.used = S.TRUE;
                            coupon.update();
                        }
                    } else if (coupon.type == 2)//giam %
                    {
                        if (price > -coupon.han_muc) {
                            int saleCoupon = (int) ((float) price * coupon.percent);
                            if (saleCoupon > coupon.money) {
                                price -= coupon.money;
                            } else {
                                price -= saleCoupon;
                            }
                            coupon.used = S.TRUE;
                            coupon.update();
                        }
                    }
                }

                if (price > user.balance) {
                    return errorInvalid(res, "Bạn không đủ tiền thanh toán giỏ hàng này");
                }

                int size = lstProduct.size();
                if (size > 0) {
                    product_id_detail = builder.toString();
                    product_id_detail = product_id_detail.substring(0, product_id_detail.length() - 1);

                    for (Product pr : lstProduct) {
                        Integer num = mapCard.get(pr.id);
                        if (num != null) {
                            pr.number -= num;
                            pr.update();
                            pr.number = num;
                            pr.descriptions = "";
                        }
                    }

                    LogPaymentCartUser logPaymentCart = new LogPaymentCartUser(user.id, price, product_id_detail, Json.toJson(lstProduct).toString(), 1, coupon_id, Utils.getTimeStamp());
                    logPaymentCart.save();

                    user.balance -= price;
                    user.update();

                    String description = "Thanh toán đơn hàng: " + logPaymentCart.id;
                    PlayerManager.userChangeBalance(user, description + " hết: -" + Utils.formatMoney(price));

                    TransactionHistoryUser logTran = new TransactionHistoryUser(user.id, S.LOG_PAYMENT_CART, logPaymentCart.id, S.TRUE, -price, description);
                    logTran.save();

                    NodeJs.getInstance().emitUserPaymentOrder(user.id, logPaymentCart.id);

                    return processSuccess(res, "Thanh toán thành công đơn hàng: " + logPaymentCart.id, user.balance);
                } else {
                    return errorInvalid(res, "Thông tin giỏ hàng không hợp lệ");
                }
            } else {
                return errorInvalid(res, "Thông tin giỏ hàng không hợp lệ");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onRechargeViaKtv(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            int money = req.get("money").asInt();
            int admin_id = req.get("admin_id").asInt();
            String description = req.get("description").asText();
            Admin admin = Admin.find.byId(admin_id);
            if (admin != null) {
                if (admin.level == S.LEVEL_KTV) {
                    LogRechargeViaKtv require = new LogRechargeViaKtv(user.id, money, description, admin_id, Utils.getTimeStamp());
                    require.save();

                    String notify = "Khách hàng mã số: " + user.id + " - " + user.fullname + " - địa chỉ: " + user.address + " yêu cầu nạp: " + Utils.formatMoney(money) + S.MONEY_NAME;
                    PushNotification.getInstance().SendOneDevice(admin.fcm_token, "Xác nhận nạp tiền", notify);
                    NodeJs.getInstance().emitServerPush(Utils.getTypeAccountBenNodeJs(admin.level), admin.id, "Có yêu cầu xác nhận nạp tiền từ khách hàng mã số: " + user.id + " - " + user.fullname);
                    Notify notify1 = new Notify("KH: " + user.id, S.TYPE_ACCOUNT_KTV, admin.id, "Xác nhận nạp tiền", notify, Utils.getTimeStamp());
                    notify1.save();
                    return processSuccess(res, "Gửi yêu cầu nạp tiền qua KTV mã số: " + admin.id + " - " + admin.fullname + " thành công", null);
                } else {
                    return errorInvalid(res, "KTV này không có quyền nạp tiền cho bạn");
                }
            } else {
                return errorInvalid(res, "Không có KTV này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetInfoAccountUser(User user) {
        ApiResponse res = null;
        user.password = "";
        return processSuccess(res, user);
    }

    private Result onGetListProductGift(int userId) {
        ApiResponse res = null;
        Map<String, Object> map = new HashMap<>();
        String timeCheckLog = Utils.getTimestamp(Utils.getTimestampSauNgay(30)).toString();

        String sql;
        sql = "SELECT a.product_gift_id,a.used" +
                " FROM log_product_gift_user_used a" +
                " WHERE a.user_id=" + userId + " AND DATE(a.created)>='" + timeCheckLog + "' AND a.used=" + S.FALSE;

        List<SqlRow> logUsedProductGiftUser = getListResultQuery(sql);
        map.put("gift_user_used", logUsedProductGiftUser);

        String currentTime = Utils.getCurrentTime().split(" ")[0];
        sql = "SELECT a.id,a.type,a.title,a.description,a.link_icon,a.time_event_start,a.time_event_stop FROM product_gift a" +
                " WHERE DATE(a.time_event_start)<= '" + currentTime + "' AND DATE(a.time_event_stop) >= '" + currentTime + "'";

        List<SqlRow> giftInfo = getListResultQuery(sql);
        map.put("gift_info", giftInfo);
        return processSuccess(res, map);
    }

    private Result onUseGift(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            int id_gift = req.get("id_gift").asInt();
            ProductGift productGift = ProductGift.find.byId(id_gift);
            if (productGift == null) {
                return errorInvalid(res, "Không có quà này");
            }

            LogProductGiftUserUsed logGift = LogProductGiftUserUsed.find.query().where()
                    .eq("product_gift_id", id_gift)
                    .eq("user_id", user.id).findOne();
            if (logGift != null) {
                if (logGift.used == S.TRUE) {
                    return errorInvalid(res, "Quý khách đã sử dụng quà này rồi");
                }

                long timeCurrent = Utils.getYearMonthDayGhepChuoiToLong(Utils.getTimeStamp());
                long timeEventStart = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_start);
                long timeEventStop = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_stop);
                if (timeCurrent < timeEventStart) {
                    return errorInvalid(res, "Chưa đến thời gian sử dụng quà này");
                }

                if (timeCurrent > timeEventStop) {
                    return errorInvalid(res, "Đã hết thời gian sử dụng quà này");
                }

                if (productGift.type == 1)//vật tư
                {
                    Product product = Product.find.byId(productGift.product_id);
                    if (product != null) {
                        if (product.number >= productGift.number) {
                            ProductUser productUser = ProductUser.find.query().where()
                                    .eq("user_id", user.id)
                                    .eq("product_id", productGift.product_id).findOne();
                            if (productUser != null) {
                                productUser.number += productGift.number;
                                productUser.update();
                            } else {
                                productUser = new ProductUser(user.id, productGift.product_id, productGift.number);
                                productUser.save();

                                Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, "Khuyễn mãi", "Chúc mừng quý khách đã được tặng gói bảo dưỡng (" + productGift.title + ")", Utils.getTimeStamp());
                                notify.save();
                            }

                            product.number -= productGift.number;
                            product.update();
                        } else {
                            return errorInvalid(res, "Rất tiếc. Đã hết quà tặng.");
                        }
                    } else {
                        return errorInvalid(res, "Không có quà tặng trong kho, liên hệ CSKH");
                    }
                } else if (productGift.type == 2)//ưu đãi gói dịch vụ
                {
                    logGift.used = S.TRUE;
                    logGift.update();
                    switch (productGift.id) {
                        case 2://tặng gói cước 1 tháng
                            return processSuccess(res, "Quý khách sẽ được tặng thêm 1 tháng bảo trì khi đăng ký gói cước 6 tháng bất kỳ", "[]");

                        case 3://Tặng gói cước 2 tháng
                            return processSuccess(res, "Quý khách sẽ được tặng thêm 2 tháng bảo trì khi đăng ký gói cước 12 tháng bất kỳ", "[]");

                        case 4://tặng 50% gói cước 100 khách đăng ký gói đầu tiên
                            return processSuccess(res, "Số tiền tự động được giảm khi quý khách đăng ký gói cước");

                        default:
                            break;
                    }
                } else if (productGift.type == 3) {//tặng gói dịch vụ
                    long time = req.get("appointment_time").asLong();
                    switch (productGift.id) {
                        case 1://dịch vụ 0 đồng
                            ServicePackage service = ServicePackage.find.byId(productGift.service_package_id);
                            if (service == null) {
                                return errorInvalid(res, "Không có quà tặng, liên hệ CSKH");
                            } else {
                                Long startTime = Utils.getCurrentMiniseconds() / 1000L;
                                Long endTime = startTime + Utils.getTotalSecondsPerDay() * productGift.number_day_use;
                                ServicePackageUser servicePackageUser = new ServicePackageUser(user.id, productGift.service_package_id, startTime, endTime,
                                        user.address, user.latitude, user.longitude, user.province_id, user.district_id, user.ward_id);
                                servicePackageUser.save();

                                if (time < servicePackageUser.start_time || time > servicePackageUser.end_time) {
                                    res = new ApiResponse(Error.ERROR, "Thời gian đặt lịch bảo trì không hợp lệ", null);
                                } else {
                                    ServicePackageMaintenance servicePackageMaintenance = ServicePackageMaintenance.find.query().where()
                                            .eq("service_package_user_id", servicePackageUser.id)
                                            .setMaxRows(1).findOne();
                                    if (servicePackageMaintenance == null) {
                                        String des = "Gói bảo trì theo sự kiện";
                                        ServicePackageMaintenance s = new ServicePackageMaintenance(servicePackageUser.id, des, 1, time, "", 1, "");
                                        s.time = time;
                                        s.save();
                                    }
                                }
                            }
                            break;

                        case 5://Tặng 1 lần bảo dưỡng điều hòa miễn phí khi đăng ký tài khoản
                            ServicePackage service1 = ServicePackage.find.byId(productGift.service_package_id);
                            if (service1 == null) {
                                return errorInvalid(res, "Không có quà tặng, liên hệ CSKH");
                            } else {
                                Long startTime = Utils.getCurrentMiniseconds() / 1000L;
                                Long endTime = startTime + Utils.getTotalSecondsPerDay() * 30;
                                ServicePackageUser servicePackageUser = new ServicePackageUser(user.id, productGift.service_package_id, startTime, endTime,
                                        user.address, user.latitude, user.longitude, "", "", "");
                                servicePackageUser.save();

                                if (time < servicePackageUser.start_time || time > servicePackageUser.end_time) {
                                    res = new ApiResponse(Error.ERROR, "Thời gian đặt lịch bảo trì không hợp lệ", null);
                                } else {
                                    ServicePackageMaintenance servicePackageMaintenance = ServicePackageMaintenance.find.query().where()
                                            .eq("service_package_user_id", servicePackageUser.id)
                                            .setMaxRows(1).findOne();
                                    if (servicePackageMaintenance == null) {
                                        String des = "Gói bảo trì theo sự kiện";
                                        ServicePackageMaintenance s = new ServicePackageMaintenance(servicePackageUser.id, des, 1, time, "", 1, "");
                                        s.time = time;
                                        s.save();
                                    }
                                }
                            }
                            break;

                        default:
                            break;
                    }
                } else {
                    return errorInvalid(res, "Chưa xử lý nhận quà này, liên hệ CSKH");
                }
                logGift.used = S.TRUE;
                logGift.update();
                return processSuccess(res, "Sử dụng quà thành công");
            } else {
                return errorInvalid(res, "Quý khách không có quà này để sử dụng");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetListShopRewardPoint() {
        ApiResponse res = null;
        List<ProductRewardPoints> lst = ProductRewardPoints.find.query().where().eq("is_show", S.TRUE).findList();
        return processSuccess(res, lst);
    }

    private Result onChangeRewardPoint(User user) {
        ApiResponse res = null;
        try {
            int id_gift = request().body().asJson().get("id_gift").asInt();
            ProductRewardPoints rewardPoints = ProductRewardPoints.find.byId(id_gift);
            if (rewardPoints != null) {
                if (user.reward_point > rewardPoints.price) {
                    Product product = Product.find.byId(rewardPoints.product_id);
                    if (product != null) {
                        if (product.number >= rewardPoints.number) {
                            ProductUser productUser = ProductUser.find.query().where()
                                    .eq("user_id", user.id)
                                    .eq("product_id", rewardPoints.product_id).findOne();
                            if (productUser != null) {
                                productUser.number += rewardPoints.number;
                                productUser.update();
                            } else {
                                productUser = new ProductUser(user.id, rewardPoints.product_id, rewardPoints.price);
                                productUser.save();
                            }

                            product.number -= rewardPoints.number;
                            product.update();

                            user.reward_point -= rewardPoints.price;
                            user.update();

                            LogProductRewardPointsUser log = new LogProductRewardPointsUser(rewardPoints.id, user.id, Utils.getTimeStamp());
                            log.save();
                            return processSuccess(res, "Đổi " + product.name + " số lượng " + rewardPoints.number + " thành công", user.reward_point);
                        } else {
                            return errorInvalid(res, "Sản phẩm: " + product.name + " hiện đang hết");
                        }
                    } else {
                        return errorInvalid(res, "Không có quà tặng trong kho, liên hệ CSKH");
                    }

                } else {
                    return errorInvalid(res, "Quý khách không đủ điểm thưởng để đổi quà");
                }
            } else {
                return errorInvalid(res, "Không có vật phẩm này trong kho, liên hệ CSKH");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetListServices() {
        ApiResponse res = null;
        return processSuccess(res, Service.find.all());
    }

    private Result onGetListServiceByLocation(JsonNode req) {
        ApiResponse res = null;
        try {
            double latitude = req.get("latitude").asDouble();
            double longitude = req.get("longitude").asDouble();
            double offset = S.OFFSET_POSITION;
            String sql = "SELECT a.id, a.username, a.fullname,a.phone,a.address,a.latitude,a.longitude,a.position," +
                    "a.link_avatar,a.service_id,a.rank" +
                    " FROM admin a" +
                    " WHERE a.status=1 AND a.level=" + S.LEVEL_CTV + " AND a.latitude <" + (latitude + offset) + " AND a.latitude >" + (latitude - offset) +
                    " AND a.longitude <" + (longitude + offset) + " AND a.longitude >" + (longitude - offset);

            Map<String, Object> map = new HashMap<>();
            map.put("lst_ctv", getListResultQuery(sql));
            return processSuccess(res, map);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetListCtvByServiceId(JsonNode req) {
        ApiResponse res = null;
        try {
            double latitude = req.get("latitude").asDouble();
            double longitude = req.get("longitude").asDouble();
            String service_id = req.get("service_id").asText();
            double offset = S.OFFSET_POSITION;
            String sql = "SELECT a.id, a.username, a.fullname,a.phone,a.address,a.latitude,a.longitude,a.position," +
                    "a.link_avatar,a.service_id,a.rank" +
                    " FROM admin a" +
                    " WHERE a.status=1 AND a.level=" + S.LEVEL_CTV + " AND a.service_id LIKE '%" + service_id + ",%'" +
                    " AND a.latitude <" + (latitude + offset) + " AND a.latitude >" + (latitude - offset) +
                    " AND a.longitude <" + (longitude + offset) + " AND a.longitude >" + (longitude - offset);

            return processSuccess(res, getListResultQuery(sql));
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetListAlert() {
        ApiResponse res = null;
        return processSuccess(res, Alert.find.all());
    }

    private Result onGetLinkIntroduction() {
        ApiResponse res = null;
        return processSuccess(res, Introduction.find.all());
    }

    private Result onCallCollaborator(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            double latitude = req.get("latitude").asDouble();
            double longitude = req.get("longitude").asDouble();
            int service_id = req.get("service_id").asInt();
            String address = req.get("address").asText();
            String details = req.get("details").asText();
            String images = req.get("images").asText();
            double offset = S.OFFSET_POSITION;
            String sql = "SELECT a.id,a.fcm_token" +
                    " FROM admin a" +
                    " WHERE a.status=" + S.ACCOUNT_DOING_ACTIVE + " AND a.work_status=1" +
                    " AND a.level=" + S.LEVEL_CTV + " AND a.service_id LIKE '%" + service_id + ",%'" +
                    " AND a.latitude <" + (latitude + offset) + " AND a.latitude >" + (latitude - offset) +
                    " AND a.longitude <" + (longitude + offset) + " AND a.longitude >" + (longitude - offset);

            List<SqlRow> lst = getListResultQuery(sql);
            Map<Integer, String> mapCTV = new HashMap<>();
            if (lst.size() > 0) {
                JsonNode data = Json.toJson(lst);
                if (data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        mapCTV.put(data.get(i).get("id").asInt(), data.get(i).get("fcm_token").asText());
                    }
                }
            }

            int send_ktv = 0;
            if (mapCTV.size() > 0) {
                send_ktv = mapCTV.size();
            }

            CollaboratorMission log = new CollaboratorMission(user.id, service_id, user.phone, address, details, images, latitude, longitude, send_ktv, Utils.getTimeStamp());
            log.save();

            //"Cho push thông báo đến các KTV thỏa mãn"
            if (send_ktv > 0) {
                String title = "Yêu cầu sửa chữa";
                String body = "Khách hàng tại địa chỉ: " + address + " cần gọi thợ xử lý vấn đề: " + details;
                PushNotification.getInstance().SendListDevice(mapCTV.values(), title, body);
                Map<String, Object> map = new HashMap<>();
                map.put("user_id", user.id);
                map.put("collaborator_mission", log);
                map.put("danh_sach_id_ctv", mapCTV.keySet());

                NodeJs.getInstance().emitServer_CollaboratorGetInfoMission(user.id, log.id, Json.toJson(mapCTV.keySet()).toString());

                return processSuccess(res, "Yêu cầu của bạn đã được gửi đến các thợ quanh đây", map);
            } else {
                return errorInvalid(res, "Hiện chưa có thợ rảnh quanh đây");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onEvaluateCtv(int userId, JsonNode req) {
        ApiResponse res = null;
        try {
            long collaborator_mission_id = req.get("collaborator_mission_id").asLong();
            int so_sao = req.get("so_sao").asInt();
            String content = req.get("content").asText();
            CollaboratorMission mission = CollaboratorMission.find.byId(collaborator_mission_id);
            if (mission != null) {
                if (mission.user_id == userId) {
                    if (mission.evaluate_ctv == S.FALSE) {
                        mission.evaluate_ctv = S.TRUE;
                        mission.update();

                        if (mission.admin_id != null) {
                            Admin admin = Admin.find.byId(mission.admin_id);
                            if (admin != null) {
                                EvaluateCtv evaluateCtv = new EvaluateCtv(mission.user_id, mission.admin_id, mission.service_id, mission.id, so_sao, content, Utils.getTimeStamp());
                                evaluateCtv.save();
                                String rank = admin.rank;
                                if (rank != null) {
                                    String[] arr = admin.rank.split(S.TACH_SHIFT_TRU);
                                    if (arr.length == 2) {
                                        int soLanDanhGia = Integer.parseInt(arr[0]) + 1;
                                        int soSao = Integer.parseInt(arr[1]) + so_sao;
                                        rank = soLanDanhGia + S.TACH_SHIFT_TRU + soSao;
                                    } else {
                                        rank = 1 + "_" + so_sao;
                                    }
                                } else {
                                    rank = 1 + "_" + so_sao;
                                }
                                admin.rank = rank;
                                admin.update();
                            }
                            return processSuccess(res, "Đánh giá cộng tác viên thành công");
                        } else {
                            return errorInvalid(res, "Chưa có cộng tác viên làm nhiệm vụ này");
                        }
                    } else {
                        return errorInvalid(res, "Quý khách đã đánh giá nhiệm vụ này rồi");
                    }
                } else {
                    return errorInvalid(res, "Bạn không có quyền đánh giá nhiệm vụ này");
                }
            } else {
                return errorInvalid(res, "Không có nhiệm vụ này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetDetailEvaluateCtv(JsonNode req) {
        ApiResponse res = null;
        try {
            int admin_id = req.get("admin_id").asInt();
            int service_id = req.get("service_id").asInt();
            String sql = "SELECT a.so_sao,a.content,a.created,b.fullname AS nick_evaluate,b.link_avatar AS avatar_evaluate" +
                    " FROM evaluate_ctv a" +
                    " INNER JOIN user b ON b.id = a.user_id" +
                    " WHERE a.admin_id=" + admin_id + " AND a.service_id=" + service_id;

            return processSuccess(res, getListResultQuery(sql));
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetInfoMissionCollaborator(JsonNode req) {
        ApiResponse res = null;
        try {
            long collaborator_mission = req.get("collaborator_mission_id").asLong();
            CollaboratorMission mission = CollaboratorMission.find.byId(collaborator_mission);
            if (mission != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("service_id", mission.service_id);
                map.put("details", mission.details);
                map.put("images", mission.images);
                map.put("created", mission.created);
                return processSuccess(res, map);
            } else {
                return errorInvalid(res, "Không có thông tin cho nhiệm vụ này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onUpdateCartWaitBuy(int userId, JsonNode req) {
        ApiResponse res = null;
        try {
            String info_cart_wait_buy = req.get("info_cart_wait_buy").asText();
            UserCart cart = UserCart.find.query().where()
                    .eq("user_id", userId).findOne();
            if (cart != null) {
                cart.info_cart_wait_buy = info_cart_wait_buy;
                cart.time_change = Utils.getTimeStamp();
                cart.update();
            } else {
                cart = new UserCart(userId, "[]", Utils.getTimeStamp());
                cart.info_cart_wait_buy = info_cart_wait_buy;
                cart.save();
            }
            return processSuccess(res, null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onRegisterReceiveGiftProduct(int userId, JsonNode req) {
        ApiResponse res = null;
        try {
            int product_gift_id = req.get("product_gift_id").asInt();
            ProductGift productGift = ProductGift.find.byId(product_gift_id);
            if (productGift == null) {
                return errorInvalid(res, "Không có quà này");
            }

            LogProductGiftUserUsed logGift = LogProductGiftUserUsed.find.query().where()
                    .eq("product_gift_id", product_gift_id)
                    .eq("user_id", userId).findOne();
            if (logGift != null) {
                return errorInvalid(res, "Quý khách đã đăng ký nhận gói quà này rồi");
            } else {
                long timeCurrent = Utils.getYearMonthDayGhepChuoiToLong(Utils.getTimeStamp());
                long timeEventStart = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_start);
                long timeEventStop = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_stop);
                if (timeCurrent < timeEventStart) {
                    return errorInvalid(res, "Chưa đến thời gian đăng ký nhận quà");
                }

                if (timeCurrent > timeEventStop) {
                    return errorInvalid(res, "Đã hết thời gian đăng ký nhận quà");
                }

                String sql;
                switch (productGift.id) {
                    case 1://dịch vụ 0 đồng
                        sql = "SELECT COUNT(*) AS number FROM user";
                        JsonNode dt1 = Json.toJson(getResultQuery(sql));
                        long number = dt1.get("number").asLong();
                        if (number >= 1000L) {
                            return errorInvalid(res, "Đã đủ số người nhận quà");
                        }
                        break;

                    case 2://Tặng thêm 1 tháng với thuê bao 6 tháng
                        sql = "SELECT COUNT(*) AS number FROM service_package_user WHERE user_id =" + userId + " AND service_package_id !=44 AND service_package_id !=45";//44 id gói khuyễn mãi 0 đồng
                        JsonNode dt0 = Json.toJson(getResultQuery(sql));
                        long number0 = dt0.get("number").asLong();
                        if (number0 > 0) {
                            return errorInvalid(res, "Quý khách sẽ được tặng thêm 1 tháng bảo trì khi đăng ký gói cước 6 tháng bất kỳ");
                        } else {
                            return errorInvalid(res, "Khuyễn mãi chỉ giành cho các khách hàng đã từng đăng ký sử dụng gói dịch vụ");
                        }

                    case 3://Tặng thêm 2 tháng với thuê bao 12 tháng
                        sql = "SELECT COUNT(*) AS number FROM service_package_user WHERE user_id =" + userId + " AND service_package_id !=44 AND service_package_id !=45";//44 id gói khuyễn mãi 0 đồng
                        JsonNode dt3 = Json.toJson(getResultQuery(sql));
                        long number3 = dt3.get("number").asLong();
                        if (number3 > 0) {
                            return errorInvalid(res, "Quý khách sẽ được tặng thêm 2 tháng bảo trì khi đăng ký gói cước 12 tháng bất kỳ");
                        } else {
                            return errorInvalid(res, "Khuyễn mãi chỉ giành cho các khách hàng đã từng đăng ký sử dụng gói dịch vụ");
                        }

                    case 4://tặng 50% gói cước 100 khách đăng ký gói đầu tiên
                        sql = "SELECT COUNT(*) AS number FROM service_package_user WHERE service_package_id !=44";
                        JsonNode dt2 = Json.toJson(getResultQuery(sql));
                        long number2 = dt2.get("number").asLong();
                        if (number2 < 100) {
                            return errorInvalid(res, "Số tiền tự động được giảm khi quý khách đăng ký gói cước");
                        } else {
                            return errorInvalid(res, "Đã đủ số người nhận quà");
                        }
                    default:
                        break;
                }
                LogProductGiftUserUsed log = new LogProductGiftUserUsed(product_gift_id, userId, Utils.getTimeStamp(), S.FALSE);
                log.save();
                Map<String, Object> map = new HashMap<>();
                map.put("product_gift_id", log.product_gift_id);
                map.put("used", log.used);
                return processSuccess(res, "Chúc mừng quý khách đã nhận được quà tặng", map);
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetListBanner() {
        ApiResponse res = null;
        List<Banner> list = Banner.find.all();
        return processSuccess(res, list);
    }

    private Result onBecomeCooperator(int userId, JsonNode req) {
        ApiResponse res = null;
        try {
            String fullname = req.get("fullname").asText();
            String birthday = req.get("birthday").asText();
            String phone = req.get("phone").asText();
            String address = req.get("address").asText();
            String works = req.get("works").asText();
            CollaboratorRegister check = CollaboratorRegister.find.query().where().eq("user_id", userId).setMaxRows(1).findOne();
            if (check == null) {
                CollaboratorRegister register = new CollaboratorRegister(userId, fullname, birthday, phone, address, works, 1, Utils.getTimeStamp());
                register.save();

                return processSuccess(res, "Bạn đã đăng ký thành công, chờ hệ thống xét duyệt");
            } else {
                return processSuccess(res, "Bạn đã đăng ký trở thành cộng tác viên rồi");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result checkRegisterCooperator(int userId) {
        ApiResponse res = null;
        CollaboratorRegister check = CollaboratorRegister.find.query().where().eq("user_id", userId).setMaxRows(1).findOne();
        boolean result = check != null ? true : false;
        return processSuccess(res, result);
    }

    private Result onBenefitCollaborator() {
        ApiResponse res = null;
        Information benefitCollaborator = Information.find.query().where().eq("type", 7).setMaxRows(1).findOne();
        return processSuccess(res, benefitCollaborator);
    }

    private Result onCancelCallCollaborator(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            long collaborator_mission_id = req.get("collaborator_mission_id").asLong();
            String reason = req.get("reason").asText();
            CollaboratorMission mission = CollaboratorMission.find.byId(collaborator_mission_id);
            if (mission != null) {
                if (user.id == mission.user_id) {
                    if (mission.status == 0 || mission.status == 1) {//0 chưa ai nhận, 1: có người nhận
                        mission.status = 4;
                        String note = Utils.getCurrentTime() + " " + (mission.admin_id == null ? "Chưa có CTV nhận nhiệm vụ" : "Đã có CTV nhận nhiệm vụ");
                        mission.note = note;
                        mission.reason_cancel = reason;
                        mission.update();
                        if (user.cancel_call_ctv != null) {
                            user.cancel_call_ctv += 1;
                        } else {
                            user.cancel_call_ctv = 1;
                        }
                        user.update();
                        if (mission.admin_id != null) {
                            Admin admin = Admin.find.byId(mission.admin_id);
                            if (admin != null) {
                                String body = "Khách hàng mã số: " + user.id + " - " + user.fullname + " đã hủy nhiệm vụ: " + mission.details;
                                PushNotification.getInstance().SendOneDevice(admin.fcm_token, "Hủy nhiệm vụ", body);
                                NodeJs.getInstance().emitServerPush(Utils.getTypeAccountBenNodeJs(admin.level), admin.id, body);
                                NodeJs.getInstance().emitCancelCallCollaborator(collaborator_mission_id);
                            }
                        }
                        return processSuccess(res, "Đã hủy yêu cầu gọi cộng tác viên thành công");
                    } else {
                        if (mission.status == 4) {
                            return errorInvalid(res, "Quý khách đã hủy yêu cầu này rồi");
                        } else {
                            return errorInvalid(res, "Yêu cầu của quý khách không được thực hiện");
                        }
                    }
                } else {
                    return errorInvalid(res, "Quý khách không đặt lịch gọi cộng tác viên này");
                }
            } else {
                return errorInvalid(res, "Không có lịch gọi cộng tác viên này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onCancelCallEmergency(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            long emergency_id = req.get("emergency_id").asLong();
            String reason = req.get("reason").asText();
            Emergency emergency = Emergency.find.byId(emergency_id);
            if (emergency != null) {
                if (emergency.user_id == user.id) {
                    if (emergency.status != 7) {
                        if (user.cancel_call_emergency != null) {
                            user.cancel_call_emergency += 1;
                        } else {
                            user.cancel_call_emergency = 1;
                        }
                        user.update();
                        if (emergency.status <= 4) {
                            emergency.status = 7;//7: hủy yêu cầu
                            String note = "";
                            if (emergency.partner_id == null) {
                                note = Utils.getCurrentTime() + " - Chưa có người được giao nhiệm vụ";
                            } else {
                                if (emergency.partner_id.trim().length() > 0) {
                                    note = Utils.getCurrentTime() + " - Đã có người làm nhiệm vụ";
                                    String[] arrAdmin = emergency.partner_id.split(S.TACH_PHAY);
                                    for (String adminId : arrAdmin) {
                                        Admin admin = Admin.find.byId(Integer.parseInt(adminId));
                                        if (admin != null) {
                                            String body = "Khách hàng mã số: " + user.id + " - " + user.fullname + " đã hủy nhiệm vụ: " + emergency.des;
                                            PushNotification.getInstance().SendOneDevice(admin.fcm_token, "Hủy nhiệm vụ", body);
                                            NodeJs.getInstance().emitServerPush(Utils.getTypeAccountBenNodeJs(admin.level), admin.id, body);
                                        }
                                    }
                                }
                            }
                            emergency.note = note;
                            emergency.des_cancel = reason;
                            emergency.update();
                            NodeJs.getInstance().emitCancelCallEmergency(emergency_id);
                            return processSuccess(res, "Đã hủy yêu cầu cứu hộ thành công");
                        } else {
                            return errorInvalid(res, "Yêu cầu của quý khách không được thực hiện");
                        }
                    } else {
                        if (emergency.status == 7) {
                            return errorInvalid(res, "Quý khách đã hủy nhiệm vụ cứu hộ này rồi");
                        } else {
                            return errorInvalid(res, "Yêu cầu của quý khách không được thực hiện");
                        }
                    }
                } else {
                    return errorInvalid(res, "Quý khách không gọi nhiệm vụ cứu hộ này");
                }
            } else {
                return errorInvalid(res, "Không có nhiệm vụ cứu hộ này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onEnterTheReferralCode(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            String introduction_code = req.get("introduction_code").asText();
            LogEnterIntroductionCode log = LogEnterIntroductionCode.find.query().where()
                    .eq("user_id", user.id).setMaxRows(1).findOne();
            if (log != null) {
                return errorInvalid(res, "Quý khách đã nhập mã giới thiệu rồi");
            }
            Map recipient = onCheckInviteCode(introduction_code);
            if(recipient != null){
                user.balance += Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU;
                user.update();
                Integer recipient_id = Integer.parseInt(recipient.get("id").toString());
                if(recipient.get("type").equals("user")){
                    log = new LogEnterIntroductionCode(user.id, recipient_id, 1, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, Utils.getTimeStamp());
                    log.save();
                    User user_recived = (User)recipient.get("user");
                    String descriptions = "Quý khách đã nhận được " + Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU + "DIV khi giới thiệu được khách hàng mã số: " + user.id;
                    TransactionHistoryUser tran = new TransactionHistoryUser(user_recived.id, S.LOG_ADD_MONEY_INTRODUCTION_USER, log.id, S.TRUE, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, descriptions);
                    tran.save();
                    user_recived.balance += Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU;
                    user_recived.update();
                    PushNotification.getInstance().SendOneDevice(user_recived.fcm_token, "Giới thiệu khách hàng", descriptions);
                    NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user_recived.id);
                }
                else{
                    Admin admin = (Admin)recipient.get("partner");
                    log = new LogEnterIntroductionCode(user.id, recipient_id, 2, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, Utils.getTimeStamp());
                    log.save();
                    String descriptions = "Bạn đã nhận được " + Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU  + " giới thiệu được khách hàng mã số: " + user.id;
                    TransactionHistoryAdmin tran = new TransactionHistoryAdmin(admin.id, S.LOG_ADD_MONEY_INTRODUCTION_USER, log.id, S.TRUE, Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU, descriptions);
                    tran.save();
                    admin.bonus_introduce_customer += Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU;
                    admin.update();
                    PushNotification.getInstance().SendOneDevice(admin.fcm_token, "Giới thiệu khách hàng", descriptions);
                    NodeJs.getInstance().emitUpdateMoney(Utils.getTypeAccountBenNodeJs(admin.level), admin.id);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("money", Config.MONEY_THUONG_NHAP_MA_GIOI_THIEU);
                map.put("balance", user.balance);
                return processSuccess(res, map);
            }
            else {
                return errorInvalid(res, "Không có mã giới thiệu này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onCheckCoupon(JsonNode req) {
        ApiResponse res = null;
        try {
            String discount_code = req.get("discount_code").asText();
            Coupon coupon = Coupon.find.query().where().eq("coupon", discount_code).setMaxRows(1).findOne();
            if (coupon != null) {
                if (coupon.used == S.TRUE) {
                    return errorInvalid(res, "Mã giảm giá đã sử dụng");
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("money_sale", coupon.money);
                    map.put("description", coupon.description);
                    return processSuccess(res, map);
                }
            } else {
                return errorInvalid(res, "Không có mã giảm giá này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetLinkVerifyBaoKim(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            int type = req.get("type").asInt();//1: the ATM online (napas), 2: bank quoc te (VISA,...), 3: internet banking
            long amount = req.get("amount").asLong();
            String bank_code = req.get("bank_code").asText();
            String fullname = req.get("fullname").asText().trim();
            String email = req.get("email").asText().trim();
            String mobile = req.get("mobile").asText().trim();
            int currency = 0;
            if (amount <= 0) {
                return errorInvalid(res, "Số tiền nạp không hợp lệ");
            }
            String current_code;
            String payment_method;
            if (type == S.TYPE_BANK_LOCAL_ATM) {
                payment_method = "ATM_ONLINE";
                current_code = "vnd";
            } else if (type == S.TYPE_BANK_GLOBAL) {
                payment_method = "VISA";
                currency = req.get("currency").asInt();//1: VND, 2: USD
                if (currency == S.TYPE_CARD_GLOBAL_VND) {
                    current_code = "vnd";
                } else if (currency == S.TYPE_CARD_GLOBAL_USD) {
                    current_code = "usd";
                } else {
                    return errorInvalidBodyData(res);
                }
            } else if (type == S.TYPE_BANK_LOCAL_INTERNET_BANKING) {
                payment_method = "IB_ONLINE";
                current_code = "vnd";
            } else {
                return errorInvalidBodyData(res);
            }

            BankInfo bankInfo = BankInfo.find.query().where().eq("code", bank_code).findOne();
            if (bankInfo == null) {
                return errorInvalidBodyData(res);
            }

            if (fullname.length() == 0) {
                fullname = user.fullname != null ? user.fullname : "";
            }
            if (email.length() == 0) {
                email = user.email != null ? user.email : "";
            }
            if (mobile.length() == 0) {
                mobile = user.phone != null ? user.phone : "";
            }

            try {
                ResponseSend reponse = Recharge.GetUrlCheckoutNganLuong(type, current_code, amount + "", payment_method, bankInfo,
                        user, fullname, email, mobile);
                Utils.debug("ResponseSend code NGAN LUONG user :" + reponse.getError_code() + " - " + reponse.getDescription());
                if (reponse.getError_code().equals(ResultRecharge.SUCCESS)) {
                    return processSuccess(res, reponse.getCheckout_url());
                } else {
                    return errorInvalid(res, "Lỗi hệ thống");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return errorInvalid(res, "Có lỗi trong quá trình xử lý");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onUpdateNotifySystemUser(int userId, JsonNode req) {
        ApiResponse res = null;
        try {
            String notify = req.get("notify").asText();
            ReadNotifySystem noti = ReadNotifySystem.find.query().where()
                    .eq("type_account", S.TYPE_ACCOUNT_USER)
                    .eq("account_id", userId).setMaxRows(1).findOne();
            if (noti != null) {
                noti.notify_system = notify;
                noti.update();
            } else {
                noti = new ReadNotifySystem(S.TYPE_ACCOUNT_USER, userId, notify);
                noti.save();
            }
            return processSuccess(res, "Cập nhật trạng thái thành công");
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onUpdatePositionWhenRegisterAccount(User user, JsonNode req) {
        ApiResponse res = null;
        try {
            double latitude = req.get("latitude").asDouble();
            double longitude = req.get("longitude").asDouble();
            String position = req.get("position").asText();
            return processSuccess(res, "Cập nhật thông tin thành công");
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onActiveGiftcode(User user, JsonNode req){
        ApiResponse res = null;
        try {
            String code = req.get("code").asText();
            String province_id = user.province_id;
            String district_id = user.district_id;
            String ward_id = user.ward_id;
            if(province_id == null || district_id == null || ward_id == null){
                return errorInvalid(res, "Quý khách vui lòng cập nhật địa chỉ trước khi sử dụng mã quà tặng");
            }
            else{
                GiftCode giftCode = GiftCode.find.query().where().eq("code", code).findOne();
                if(giftCode == null){
                    return errorInvalid(res, "Mã quà tặng không hợp lệ");
                }
                else{
                    String address_id = province_id + "_" + district_id + "_" + ward_id;
                    if(giftCode.area_id != null && address_id.indexOf(giftCode.area_id) != 0){
                        return errorInvalid(res, "Mã quà tặng không được áp dụng trong khu vực của Quý khách");
                    }
                    else if(giftCode.use_by != null){
                        return errorInvalid(res, "Mã quà tặng đã được sử dụng");
                    }
                    else if(Utils.getTimeStamp().after(giftCode.expire_date)){
                        return errorInvalid(res, "Mã quà tặng đã hết hạn sử dụng");
                    }
                    else if(giftCode.type == 1){
                        giftCode.use_by = user.id;
                        giftCode.use_date = Utils.getTimeStamp();
                        user.balance = user.balance + giftCode.div;
                        giftCode.update();
                        user.update();
                        String message = "Chúc mừng Quý khách đã nhận được " + giftCode.div + " DIV từ mã Voucher";
                        Map<String, Object> map = new HashMap<>();
                        map.put("balance", user.balance);
                        return processSuccess(res, message, map);
                    }
                    else if(giftCode.type == 2){
                        ServicePackage service = ServicePackage.find.byId(giftCode.service_package_id);
                        List<ServicePackageUser> servicePackageUsers = ServicePackageUser.find.query().where().eq("service_package_id", service.id).eq("user_id", user.id).findList();
                        Map<String, Object> map = new HashMap<>();
                        map.put("type", 2);
                        map.put("giftcode_id", giftCode.id);
                        map.put("servicePackageUsers", servicePackageUsers);
                        map.put("name", service.name);
                        return processSuccess(res, "", map);
                    }
                    else{
                        return errorInvalid(res, "Lỗi không xác định");
                    }
                }
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onInfoEvaluateMaintenance(User user, JsonNode req){
        ApiResponse res = null;
        try {
            Integer maintenance_id = req.get("maintenance_id").asInt();
            AdminMission adminMission = AdminMission.find.query().where().eq("service_package_maintenance_id", maintenance_id).findOne();
            if(adminMission != null){
                adminMission.position_start = "";
                adminMission.time_start_go = 0L;
                adminMission.time_start_job = 0L;
            }
            return processSuccess(res, adminMission);
        }
        catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onInfoEvaluateEmergency(User user, JsonNode req){
        ApiResponse res = null;
        try {
            Integer emergency_id = req.get("emergency_id").asInt();
            AdminEmergency adminEmergency = AdminEmergency.find.query().where().eq("emergency_id", emergency_id).findOne();
            if(adminEmergency != null){
                adminEmergency.position_start = "";
                adminEmergency.time_start_go = 0L;
                adminEmergency.time_start_job = 0L;
            }
            return processSuccess(res, adminEmergency);
        }
        catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetCodeResetPassword(JsonNode req){
        ApiResponse res = null;
        try {
            String username = req.get("username").asText();
            String email = req.get("email").asText();
            User user = User.find.query().where().eq("username", username).findOne();
            if(user == null){
                return errorInvalid(res, "Tài khoản không tồn tại");
            }
            else if(!user.email.equals(email)){
                return errorInvalid(res, "Email không đúng với thông tin cá nhân");
            }
            else{
                Date date = new Date();
                String otp = String.valueOf(date.getTime()) + String.valueOf(user.id);
                otp = otp.substring(otp.length()- 5);
                Timestamp expired_time = new Timestamp(System.currentTimeMillis() + 1800000);
                UserResetPassword u = new UserResetPassword(username, email, otp, expired_time);
                u.save();
                GmailSend.sendGmail("Quên mật khẩu", email, "Mã xác thực của Quý khách là: " + otp + ".\nMã xác thực chỉ có hiệu lực trong vòng 30 phút.");
                return processSuccess(res, null);
            }
        }
        catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }

    }

    private Result onCheckCodeResetPassword(JsonNode req){
        ApiResponse res = null;
        try {
            String token = req.get("token").asText();
            JsonNode decodeToken = JWTUntils.decodeJWT(token);
            if(decodeToken == null){
                return errorInvalid(res, "Mã xác thực không hợp lệ");
            }
            else{
                UserResetPassword userResetPassword = UserResetPassword.find.query().where().eq("username", decodeToken.get("username").asText()).lt("expired_time", new Timestamp(System.currentTimeMillis())).findOne();
                if(userResetPassword == null){
                    return errorInvalid(res, "Thông tin xác thực không đúng hoặc đã hết hạn. Vui lòng thử lại");
                }
                else{
                    return processSuccess(res, decodeToken.get("id"));
                }
            }
        }
        catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onResetPassword(JsonNode req){
        ApiResponse res = null;
        try {
            String otp = req.get("otp").asText();
            String username = req.get("username").asText();
            UserResetPassword userResetPassword = UserResetPassword.find.query().where()
                    .eq("otp", otp)
                    .eq("username", username)
                    .gt("expired_time", new Timestamp(System.currentTimeMillis()))
                    .findOne();
            if(userResetPassword == null){
                return errorInvalid(res, "Mã xác thực không đúng hoặc đã hết hạn");
            }
            else{
                User user = User.find.query().where().eq("username", username).findOne();
                if(user == null){
                    return errorInvalid(res, "Tài khoản không tồn tại");
                }
                else{
                    user.password = req.get("password").asText();
                    user.update();
                    userResetPassword.expired_time = new Timestamp(System.currentTimeMillis());
                    return processSuccess(res, null);
                }
            }

        }
        catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onCheckInviteCode(JsonNode req){
        ApiResponse res = null;
        try {
            String invite_code = req.get("invite_code").asText();
            Map map = onCheckInviteCode(invite_code);
            if(map == null){
                return processSuccess(res, false);
            }
            else{
                return processSuccess(res, true);
            }
        }
        catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Map onCheckInviteCode(String invite_code){
        Map<String, Object> map = new HashMap<>();
        if(invite_code.indexOf("P") == 0){
            Integer partner_id = Integer.parseInt(invite_code.substring(1));
            Admin partner = Admin.find.byId(partner_id);
            if(partner == null){
                return null;
            }
            else{
                map.put("type", "user");
                map.put("id", partner_id);
                map.put("partner", partner);
            }
        }
        else if(invite_code.indexOf("C") == 0){
            Integer user_id = Integer.parseInt(invite_code.substring(1));
            User user = User.find.byId(user_id);
            if(user == null){
                return null;
            }
            else{
                map.put("type", "user");
                map.put("id", user_id);
                map.put("user", user);
            }
        }
        else{
            return null;
        }
        return map;
    }

    private Result onGetGiftcodeServicePackage(User user, JsonNode req){
        ApiResponse res = null;
        try {
            Boolean isNewServicePackage = req.get("is_new").asBoolean();
            int giftcode_id = req.get("giftcode_id").asInt();
            GiftCode giftCode = GiftCode.find.byId(giftcode_id);
            ServicePackage servicePackage = ServicePackage.find.byId(giftCode.service_package_id);
            Long time = ServicePackageTime.find.byId(servicePackage.time_id).time;
            Date date = new Date();
            Long currentTime = date.getTime() / 1000;
            String message = "";
            int service_package_user_id_update = 0;
            if(isNewServicePackage){
                Long endTime = currentTime + time;
                String address = req.get("address").asText();
                Double latitude = req.get("latitude").asDouble();
                Double longitude = req.get("longitude").asDouble();
                String province_id = req.get("province_id").asText();
                String district_id = req.get("district_id").asText();
                String ward_id = req.get("ward_id").asText();
                ServicePackageUser servicePackageUser = new ServicePackageUser(user.id, servicePackage.id, currentTime, endTime, address, latitude, longitude, province_id, district_id, ward_id);
                servicePackageUser.save();
                service_package_user_id_update = servicePackageUser.id;
                message = "Chúc mừng Quý khách đã nhận được gói dịch vụ " + servicePackage.name;
            }
            else{
                int service_package_user_id = req.get("service_package_user_id").asInt();
                ServicePackageUser servicePackageUser = ServicePackageUser.find.byId(service_package_user_id);
                if(servicePackageUser == null){
                    return errorInvalid(res, "Gói dịch vụ khách hàng không tồn tại");
                }
                if(servicePackageUser.end_time > currentTime){
                    servicePackageUser.end_time += time;
                }
                else{
                    servicePackageUser.end_time = currentTime + time;
                }
                servicePackageUser.update();
                service_package_user_id_update = servicePackageUser.id;
                SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");
                Long etn = servicePackageUser.end_time*1000;
                Date end_time_new = new Date(etn);
                String str_end_time = mdyFormat.format(end_time_new);
                message = "Gói dịch vụ " + servicePackage.name + " của Quý khách đã được gia hạn đến " + str_end_time;
            }
            giftCode.use_by = user.id;
            giftCode.use_date = Utils.getTimeStamp();
            giftCode.service_package_user_id = service_package_user_id_update;
            giftCode.update();
            return processSuccess(res, message,null);
        }
        catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onGetRuleEmergency(){
        String condition = "Đang cập nhật";
        String fee = "Đang cập nhật";
        try {
            Information info_1 = Information.find.query().where().eq("type", 9).findOne();
            Information info_2 = Information.find.query().where().eq("type", 10).findOne();
            if(info_1 != null){
                condition = info_1.content;
            }
            if(info_2 != null){
                fee = info_2.content;
            }
        }
        catch (NullPointerException e){

        }
        Map<String, Object> map = new HashMap<>();
        map.put("condition", condition);
        map.put("fee", fee);
        ApiResponse res = null;
        return processSuccess(res, "",map);
    }

    private Result onCheckIsReadChat(User user){
        ApiResponse res = null;
        ChatInfo chatInfo = ChatInfo.find.query().where().eq("type_account", 5).eq("account_id_chat", user.id).eq("is_read", 0).findOne();
        if(chatInfo != null){
            return processSuccess(res, "", true);
        }
        else{
            return processSuccess(res, "", false);
        }
    }
}