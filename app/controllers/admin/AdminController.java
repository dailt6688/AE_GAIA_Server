package controllers.admin;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.user.ServicePackageController;
import helper.*;
import helper.Error;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.*;
import nodeJs.NodeJs;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import server.PlayerManager;
import server.ServerControl;
import tmp.NotifyBean;

import javax.xml.soap.Node;
import java.util.*;

public class AdminController extends Controller {

    public AdminController() {
        ServerControl.getInstance();
    }

    private ApiResponse res;

    public Result adminController() {
        try {
            JsonNode req = request().body().asJson();
            Integer user_id = req.get(Key.USER_ID).asInt();
            String token = req.get(Key.TOKEN).asText();
            Integer cmd = req.get(Key.CMD).asInt();
            Admin admin = Admin.find.byId(user_id);
            Boolean checkToken = JWTUntils.validateJWTAdmin(token, admin);
            if (admin == null) {
                res = new ApiResponse(Error.ERROR, Error.USER_NOT_EXIST, null);
            } else if (!checkToken) {
                res = new ApiResponse(Error.INVALID_TOKEN, "", null);
            } else {
                switch (cmd) {
                    case Cmd.ASSIGN_EMERGENCY:
                        return AssignEmergency(admin);
                    case Cmd.GET_LIST_EMERGENCY:
                        return ListEmergency();
                    case Cmd.LIST_PARTNER_FREE:
                        return ListPartnerFree(admin);
                    case Cmd.LIST_MISSION:
                        return ListMission(admin);
                    case Cmd.PARTNER_ARRIVED:
                        return PartnerArrived(admin);
                    case Cmd.PARTNER_GET_LIST_APPLIANCE:
                        return PartnerGetListAppliance();
                    case Cmd.PARTNER_ADD_APPLIANCE:
                        return PartnerAddAppliance();
                    case Cmd.KTV_MISSION_REPORT:
                        return onKtvMissionReport(admin);
                    case Cmd.UPDATE_POSITION_ADMIN:
                        return onUpdatePositionAdmin(admin);
                    case Cmd.GET_MISSION_EMERGENCY:
                        return onGetMissionEmergency(user_id);
                    case Cmd.GET_LIST_MISSION_EMERGENCY_WAIT:
                        return onGetListMissionEmergencyWait();
                    case Cmd.KTV_START_MISSION:
                        return onKtvStartMission(admin, req);
                    case Cmd.ADMIN_LOGOUT:
                        return onAdminLogout(admin);
                    case Cmd.UPDATE_FCM_TOKEN_ADMIN:
                        return onUpdateFcmTokenAdmin(admin);
                    case Cmd.ADMIN_CHANGE_PASSWORD:
                        return onAdminChangePassword(admin);
                    case Cmd.GET_LIST_NOTIFY_ADMIN:
                        return onGetListNotifyAdmin(user_id);
                    case Cmd.DELETE_NOTIFY_ADMIN:
                        return onDeleteNotifyAdmin();
                    case Cmd.READ_NOTIFY_ADMIN:
                        return onReadNotifyAdmin();
                    case Cmd.GET_LOG_TRANSACTION_ADMIN:
                        return onGetLogTransactionAdmin(user_id);
                    case Cmd.GET_LOG_MAINTENANCE_AND_EMERGENCY_ADMIN:
                        return onGetLogMaintenanceAndEmergency(user_id);
                    case Cmd.KTV_START_EMERGENCY:
                        return onKtvStartEmergency(admin, req);
                    case Cmd.PARTNER_ARRIVED_EMERGENCY:
                        return onPartnerArrivedEmergency(admin);
                    case Cmd.KTV_EMERGENCY_REPORT:
                        return onKtvEmergencyReport(admin);
                    case Cmd.KTV_CHAT:
                        return onKtvChat(user_id);
                    case Cmd.GET_LOG_CHAT_KTV:
                        return onGetLogChatKtv(user_id);
                    case Cmd.CSKH_CHAT:
                        return onCskhChat(admin);
                    case Cmd.CSKH_GET_CHAT_WAIT_REPLY:
                        return onCskhGetChatWaitReply(user_id);
                    case Cmd.GET_MAINTENANCE_INFO:
                        return onGetMaintenanceInfo();
                    case Cmd.GET_EMERGENCY_INFO:
                        return onGetEmergencyInfo();
                    case Cmd.LIST_PRODUCT_KTV:
                        return onListProductKtv();
                    case Cmd.GET_PRODUCT_DETAIL_KTV:
                        return onProductDetailKtv(req);
                    case Cmd.GET_LIST_PRODUCT_KTV:
                        return onGetListProductKtv(user_id);
                    case Cmd.USE_PRODUCT_KTV:
                        return onUseProductKtv(admin);
                    case Cmd.GET_LIST_PRODUCT_ID_KTV:
                        return onGetListProductIdKtv();
                    case Cmd.GET_LIST_PRODUCT_KTV_BY_TYPE:
                        return onGetListProductKtvByType();
                    case Cmd.GET_INFO_RECHARGE_BANK_KTV:
                        return onGetInfoRechargeBankKtv();
                    case Cmd.GET_DETAIL_LOG_TRANSACTION_KTV:
                        return onGetDetailLogTransactionKtv(user_id);
                    case Cmd.GET_MISSION_DAY_TO_DAY:
                        return onGetMissionDayToDay(user_id);
                    case Cmd.GET_REQUIRE_RECHARGE_BY_ME:
                        return onGetRequireRechargeByMe(user_id);
                    case Cmd.CONFIRM_RECHARGE_VIA_KTV:
                        return onConfirmRechargeViaKtv(admin);
                    case Cmd.REGISTER_ACCOUNT_BANK_RECEIVE_MONEY:
                        return onRegisterAccountBankReceiveMoney(admin);
                    case Cmd.UPDATE_INFO_BANK_RECEIVE_MONEY:
                        return onUpdateInfoBankReceiveMoney(req);
                    case Cmd.GET_LIST_ACCOUNT_BANK_RECEIVE_MONEY:
                        return onGetListAccountBankReceiveMoney(user_id);
                    case Cmd.REQUIRE_PAYMENT:
                        return onRequirePayment(admin);
                    case Cmd.GET_INFO_PAYMENT_MINIMUM:
                        return onGetInfoPaymentMinimum(admin);
                    case Cmd.DELETE_INFO_BANK_RECEIVE_MONEY:
                        return onDeleteInfoBankReceiveMoney(user_id);
                    case Cmd.GET_INFO_CART_KTV:
                        return onGetInfoCardKtv(user_id);
                    case Cmd.UPDATE_CART_KTV:
                        return onUpdateCardKtv(user_id);
                    case Cmd.PAYMENT_CART_KTV:
                        return onPaymentCartKtv(admin, req);
                    case Cmd.GET_INFO_ACCOUNT_ADMIN:
                        return onGetInfoAccountAdmin(admin);
                    case Cmd.COLLABORATOR_RECEIVE_MISSION:
                        return onCollaboratorReceiveMission(admin, req);
                    case Cmd.COLLABORATOR_GET_INFO_MISSION:
                        return onCollaboratorGetInfoMission(req);
                    case Cmd.COLLABORATOR_GET_LIST_MISSION:
                        return onCollaboratorGetListMission(admin);
                    case Cmd.UPDATE_ACCOUNT_INFO_ADMIN:
                        return onUpdateAccountInfo(admin, req);
                    case Cmd.COLLABORATOR_MISSION_REPORT:
                        return onCollaboratorMissionReport(user_id, req);
                    case Cmd.GET_LOG_COLLABORATOR_WORK:
                        return onGetLogCollaboratorWork(user_id);
                    case Cmd.POLICY_KTV:
                        return onPolicyKtv();
                    case Cmd.UPDATE_ADDRESS_ADMIN:
                        return onUpdateAddressAdmin(admin, req);
                    case Cmd.UPDATE_CART_WAIT_BUY_ADMIN:
                        return onUpdateCartWaitBuyAdmin(user_id, req);
                    case Cmd.INFO_APP_KTV:
                        return onInfoAppKtv(admin.device, req);
                    case Cmd.UPDATE_NOTIFY_SYSTEM_KTV:
                        return onUpdateNotifySystemKtv(user_id, req);
                    case Cmd.SWITCHBOARD_CARE_KTV:
                        return onSwitchBoardCareKtv();
                    case Cmd.GET_LIST_USER_CHANGE_SERVICE_PACKAGE_ADDRESS:
                        return new ServicePackageController().adminGetListUserChangeSercivePackageAddress(req);
                    case Cmd.PARTNER_SAVE_SERVICE_PACKAGE_USER_ADDRESS:
                        return new ServicePackageController().partnerSaveServicePackageUserAddress(admin, req);
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


    public Result login() {
        try {
            JsonNode req = request().body().asJson();
            String username = req.get("username").asText();
            String password = req.get("password").asText();
            int device = req.get("device").asInt();
            //username, balance, status, phone, address, rank, score, email
            List<Admin> admins = Admin.find.query().where().eq("username", username).eq("password", password).setMaxRows(1).findList();
            if (admins.size() == 0) {
                res = new ApiResponse(Error.ERROR, Error.INVALID_ACCOUNT, null);
                return ok(Json.toJson(res));
            }
            Admin admin = admins.get(0);
            if (admin.status == S.ACCOUNT_LOCK) {
                NodeJs.getInstance().emitLockAccount(Utils.getTypeAccountBenNodeJs(admin.level), admin.id);
                return errorInvalid("Tài khoản của bạn đã bị khóa");
            }

            admin.device = device;
            admin.update();

            admin.password = "";
            System.out.println(admin.username);
            String token = JWTUntils.genJWT(admin);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", admin);

            PlayerManager.putMapAdmin(admin, device);
            res = new ApiResponse(Error.SUCCESS, "", map);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result register() {
        ApiResponse res;
        try {
            JsonNode req = request().body().asJson();
            String username = req.get("username").asText();
            String password = req.get("password").asText();
            int device = req.get("device").asInt();
            String fullname = req.get("fullname").asText();
            String phone = req.get("phone").asText();
            String address = req.get("address").asText();
            String email = req.get("email").asText();
            String link_avatar = req.get("link_avatar").asText();
            String province_id = req.get("province_id").asText();
            String district_id = req.get("district_id").asText();
            String ward_id = req.get("ward_id").asText();
            String birthday = req.get("birthday").asText();
            List<Admin> users = Admin.find.query().where().eq("username", username).setMaxRows(1).findList();
            if (users.size() > 0) {
                res = new ApiResponse(Error.ERROR, Error.USER_EXIST, null);
                return ok(Json.toJson(res));
            }
            Admin admin = new Admin(username, password, S.LEVEL_CTV, fullname, phone, address, email, S.ACCOUNT_UNLOCK, Utils.getTimeStamp(),
                    S.ACCOUNT_DOING_INACTIVE, link_avatar, device, province_id, district_id, ward_id, birthday);
            admin.device = device;
            admin.save();
            String token = JWTUntils.genJWT(admin);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", admin);
            res = new ApiResponse(Error.SUCCESS, "", map);
            PlayerManager.putMapAdmin(admin, device);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    private Result ListPartnerFree(Admin admin) {
        if (admin.level > 2) {
            res = new ApiResponse(Error.ERROR, Error.NOT_PERMISSION, null);
        } else {
            String sql = "SELECT admin.fullname, admin.phone, admin.id " +
                    "FROM admin_role " +
                    "INNER JOIN admin ON admin_role.admin_id = admin.id " +
                    "WHERE admin.work_status = 1 AND admin_role.manager_id = " + admin.id;
            SqlQuery query = Ebean.createSqlQuery(sql);
            List<SqlRow> partners = query.findList();

            Map<String, Object> map = new HashMap<>();
            map.put("partners", partners);
            res = new ApiResponse(Error.SUCCESS, "", map);
        }
        return ok(Json.toJson(res));
    }

    private Result ListEmergency() {
        String sql = "SELECT emergency.*, user.fullname, user.username, emergency.id as id  " +
                "FROM emergency " +
                "INNER JOIN user ON emergency.user_id = user.id " +
                "WHERE emergency.status = 1";
        SqlQuery query = Ebean.createSqlQuery(sql);
        List<SqlRow> emergencies = query.findList();
        List<Object> list_emergency = new ArrayList<>();
        int s = emergencies.size();
        for (int i = 0; i < s; i++) {
            Map<String, Object> map_emergency = new HashMap<>();
            map_emergency.put("emergency", emergencies.get(i));
            Integer service_package_user_id = (Integer) emergencies.get(i).get("service_package_user_id");
            if (service_package_user_id > 0) {
                ServicePackageUser servicePackageUser = ServicePackageUser.find.byId(service_package_user_id);
                map_emergency.put("servicePackageUser", servicePackageUser);
            }
            list_emergency.add(map_emergency);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("emergencies", list_emergency);
        res = new ApiResponse(Error.SUCCESS, "", map);
        return ok(Json.toJson(res));
    }

    //giao nhiem vu cuu ho khan cap
    private Result AssignEmergency(Admin admin) {
        if (admin.level > 2) {
            res = new ApiResponse(Error.ERROR, Error.NOT_PERMISSION, null);
            return ok(Json.toJson(res));
        } else {
            try {
                JsonNode req = request().body().asJson();
                long emergency_id = req.get("emergency_id").asLong();
                Integer admin_id = req.get("admin_id").asInt();
                Emergency emergency = Emergency.find.byId(emergency_id);
                if (emergency == null) {
                    res = new ApiResponse(Error.ERROR, Error.EMERGENCY_NOT_EXIST, null);
                    return ok(Json.toJson(res));
                }
                AdminEmergency adminEmergency = new AdminEmergency(emergency_id, admin_id, "", 0L, 0L, 0L, "", "");
                adminEmergency.save();
                String partner_id = emergency.partner_id;
                if (partner_id == "") partner_id = admin_id.toString();
                else partner_id = partner_id + "," + admin_id.toString();
                emergency.partner_id = partner_id;
                emergency.save();
                //push noti
                PushNotification.getInstance().SendOneDevice(admin.fcm_token, "Nhiệm vụ cứu hộ khẩn cấp", emergency.des);

                Map<String, Object> map = new HashMap<>();
                map.put("emergency", emergency);
                res = new ApiResponse(Error.SUCCESS, "", map);
                return ok(Json.toJson(res));
            } catch (NullPointerException e) {
                return errorInvalidBodyData();
            }
        }
//            AdminEmergency adminEmergency = new AdminEmergency();
//            res = new ApiResponse(Error.SUCCESS, "", null);
//        return ok(Json.toJson(res));
    }

    //danh sach nhiem vu hang ngay
    private Result ListMission(Admin admin) {
        try {
            JsonNode req = request().body().asJson();
            Long time = req.get("time").asLong();
            Long time_end = time + 86400;
            String sql = "SELECT admin_mission.*, service_package_maintenance.time, service_package_maintenance.status, " +
                    "service_package_user.address, service_package_user.id as service_package_user_id, service_package_user.latitude, " +
                    "service_package_user.longitude, service_package_user.user_id, user.phone, user.fullname " +
                    "FROM admin_mission " +
                    "INNER JOIN service_package_maintenance ON admin_mission.service_package_maintenance_id = service_package_maintenance.id " +
                    "INNER JOIN service_package_user ON service_package_maintenance.service_package_user_id = service_package_user.id " +
                    "INNER JOIN user ON service_package_user.user_id = user.id " +
                    "WHERE admin_mission.admin_id = " + admin.id + " AND (service_package_maintenance.time BETWEEN " + time + " AND " + time_end + ")" +
                    "ORDER BY service_package_maintenance.time ASC";
            SqlQuery query = Ebean.createSqlQuery(sql);
            List<SqlRow> missions = query.findList();
            Map<String, Object> map = new HashMap<>();
            map.put("missions", missions);
            Map<String, Object> startPoint = new HashMap<>();
            startPoint.put("latitude", 21.020294);
            startPoint.put("longitude", 105.821175);
            map.put("startPoint", startPoint);
            res = new ApiResponse(Error.SUCCESS, "", map);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    //giao nhiem vu hang ngay
//    private Result AddMission(AdminBean admin){
//        if(admin.level > 2){
//            res = new ApiResponse(Error.ERROR, Error.NOT_PERMISSION, null);
//        }
//        else{
//            res = new ApiResponse(Error.SUCCESS, "", null);
//        }
//        return ok(Json.toJson(res));
//    }

    //KTV đến nơi
    private Result PartnerArrived(Admin admin) {
        try {
            JsonNode req = request().body().asJson();
            Integer mission_id = req.get("mission_id").asInt();
            AdminMission adminMission = AdminMission.find.byId(mission_id);
            if (adminMission != null) {
                if (adminMission.admin_id != admin.id) {
                    res = new ApiResponse(Error.ERROR, Error.NOT_PERMISSION, null);
                } else {
                    ServicePackageMaintenance servicePackageMaintenance = ServicePackageMaintenance.find.byId(adminMission.service_package_maintenance_id);
                    if (servicePackageMaintenance.status != StatusJob.BAT_DAU_DI) {
                        res = new ApiResponse(Error.ERROR, Error.CMD_NOT_EXECUTE, null);
                    } else {
                        adminMission.time_start_job = Utils.getCurrentMiniseconds();
                        adminMission.save();
                        servicePackageMaintenance.status = StatusJob.KTV_DA_DEN;
                        servicePackageMaintenance.save();

                        //push firebase
                        ServicePackageUser servicePackageUser = ServicePackageUser.find.byId(servicePackageMaintenance.service_package_user_id);
                        if (servicePackageUser != null) {
                            User user = User.find.byId(servicePackageUser.user_id);
                            if (user != null) {
                                PushNotification.getInstance().SendOneDevice(user.fcm_token, "KTV " + admin.fullname + " đã đến chỗ quý khách", "KTV " + admin.fullname + " đã đến chỗ quý khách");
                            }
                        }
                        res = new ApiResponse(Error.SUCCESS, "", null);
                    }
                }
            } else {
                return errorInvalid("Không có nhiệm vụ này");
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result PartnerGetListAppliance() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_user_id = req.get("service_package_user_id").asInt();
            List<Appliance> appliances = Appliance.find.query().where()
                    .eq("service_package_user_id", service_package_user_id)
                    .findList();
            Map<String, Object> map = new HashMap<>();
            map.put("list_appliance", appliances);
            res = new ApiResponse(Error.SUCCESS, "", map);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result PartnerAddAppliance() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_user_id = req.get("service_package_user_id").asInt();
            Object listAppliance = req.get("listAppliance");
            Date date = new Date();
            for (int i = 0; i < ((JsonNode) listAppliance).size(); i++) {
                String name = ((JsonNode) listAppliance).get(i).get("name").toString().replace("\"", "");
                String manufacturer = ((JsonNode) listAppliance).get(i).get("manufacturer").toString().replace("\"", "");
                String model = ((JsonNode) listAppliance).get(i).get("model").toString().replace("\"", "");
                String quantity = ((JsonNode) listAppliance).get(i).get("quantity").toString().replace("\"", "");
                Long time = date.getTime() / 1000;
                Appliance appliance = new Appliance(name, manufacturer, model, Integer.parseInt(quantity), time, 1, service_package_user_id, 2);
                appliance.save();
            }
            res = new ApiResponse(Error.SUCCESS, "", null);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onKtvMissionReport(Admin admin) {
        try {
            JsonNode reg = request().body().asJson();
            int service_package_maintenance_id = reg.get("service_package_maintenance_id").asInt();
            ServicePackageMaintenance maintenance = ServicePackageMaintenance.find.byId(service_package_maintenance_id);
            if (maintenance != null) {
                int admin_id = admin.id;
                int admin_mission_id = reg.get("admin_mission_id").asInt();
                AdminMission adminMission = AdminMission.find.byId(admin_mission_id);
                if (adminMission != null) {
                    if (admin_id == adminMission.admin_id) {
                        adminMission.time_end = Utils.getCurrentMiniseconds();
                        adminMission.save();
                        String content = reg.get("content").asText();
                        int service_package_user_id = reg.get("service_package_user_id").asInt();
                        AdminMissionReport check = AdminMissionReport.find.query().where()
                                .eq("admin_id", admin.id)
                                .eq("admin_mission_id", admin_mission_id).setMaxRows(1).findOne();
                        if (check == null) {
                            AdminMissionReport mission = new AdminMissionReport(admin_id, admin_mission_id, content, service_package_user_id, Utils.getTimeStamp());
                            mission.save();
                            maintenance.status = StatusJob.DA_XONG;
                            maintenance.update();
                        } else {
                            return errorInvalid("Bạn đã báo cáo cho nhiệm vụ này rồi");
                        }
                    } else {
                        return errorInvalid("Bạn không có nhiệm vụ này");
                    }
                } else {
                    return errorInvalid("Bạn không có nhiệm vụ này");
                }
            } else {
                return errorInvalid("Bạn không bảo trì gói dịch vụ này");
            }

            res = new ApiResponse(Error.SUCCESS, "", null);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result errorInvalidBodyData() {
        res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
        return ok(Json.toJson(res));
    }

    private Result errorInvalid(String errorMessage) {
        res = new ApiResponse(Error.ERROR, errorMessage, null);
        return ok(Json.toJson(res));
    }

    private Result processSuccess(Object dataResponse) {
        res = new ApiResponse(Error.SUCCESS, "", dataResponse);
        return ok(Json.toJson(res));
    }

    private Result processSuccess(String message, Object dataResponse) {
        res = new ApiResponse(Error.SUCCESS, message, dataResponse);
        return ok(Json.toJson(res));
    }

    private Result onUpdatePositionAdmin(Admin admin) {
        try {
            JsonNode reg = request().body().asJson();
            double latitude = reg.get("latitude").asDouble();
            double longitude = reg.get("longitude").asDouble();
            String position = reg.get("position").asText();
            admin.latitude = latitude;
            admin.longitude = longitude;
            admin.position = position;
            admin.time_update = Utils.getTimeStamp();
            admin.update();
            return processSuccess(null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetMissionEmergency(int adminId) {
        String sql = "SELECT admin_emergency.*, emergency.*, admin_emergency.id as id, user.phone, user.fullname " +
                "FROM admin_emergency " +
                "INNER JOIN emergency ON admin_emergency.emergency_id = emergency.id " +
                "INNER JOIN user ON emergency.user_id = user.id " +
                "WHERE admin_emergency.admin_id = " + adminId + " AND emergency.status < 5 " +
                "ORDER BY emergency.time ASC";
        SqlQuery query = Ebean.createSqlQuery(sql);
        List<SqlRow> missions = query.findList();
        Map<String, Object> map = new HashMap<>();
        map.put("emergencies", missions);
        return processSuccess(map);
    }

    public Result onGetListMissionEmergencyWait() {
        List<AdminEmergency> lst = AdminEmergency.find.query().where().eq("status", 1/*nhiệm vụ chưa giao (đang chờ giao)*/).findList();
        Map<String, Object> map = new HashMap<>();
        map.put("ListMissionEmergencyWait", lst);
        return processSuccess(map);
    }

    public Result onKtvStartMission(Admin admin, JsonNode req) {
        try {
            int service_package_maintenance_id = req.get("service_package_maintenance_id").asInt();
            ServicePackageMaintenance maintenance = ServicePackageMaintenance.find.byId(service_package_maintenance_id);
            if (maintenance != null) {
                maintenance.status = StatusJob.BAT_DAU_DI;
                maintenance.save();
                int admin_mission_id = req.get("admin_mission_id").asInt();
                double latitude = req.get("latitude").asDouble();
                double longitude = req.get("longitude").asDouble();
                String position = req.get("position").asText();

                AdminMission adminMission = AdminMission.find.byId(admin_mission_id);
                if (adminMission != null) {
                    if (adminMission.admin_id == admin.id) {
                        adminMission.time_start_go = Utils.getCurrentMiniseconds();
                        adminMission.position_start = Utils.getInfoPositionMap(latitude, longitude, position);
                        adminMission.save();
                        //push firebase
                        ServicePackageUser servicePackageUser = ServicePackageUser.find.byId(maintenance.service_package_user_id);
                        if (servicePackageUser != null) {
                            User user = User.find.byId(servicePackageUser.user_id);
                            if (user != null) {
                                PushNotification.getInstance().SendOneDevice(user.fcm_token, "KTV " + admin.fullname + " bắt đầu đi đến chỗ quý khách", "KTV " + admin.fullname + " bắt đầu đi đến chỗ quý khách");
                            }
                        }

                        return processSuccess(null);
                    } else {
                        return errorInvalid("Bạn không có nhiệm vụ này");
                    }
                } else {
                    return errorInvalid("Bạn không có nhiệm vụ này");
                }
            } else {
                return errorInvalid("Bạn không bảo trì gói dịch vụ này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onAdminLogout(Admin admin) {
        PlayerManager.adminLogout(admin);
        return processSuccess(null);
    }

    public Result onUpdateFcmTokenAdmin(Admin admin) {
        try {
            String fcm_token = request().body().asJson().get("fcm_token").asText();
            admin.fcm_token = fcm_token;
            admin.update();
            return processSuccess(null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onAdminChangePassword(Admin admin) {
        try {
            JsonNode req = request().body().asJson();
            String oldPassword = req.get("oldPassword").asText();
            String password = req.get("password").asText();
            if (!admin.password.equals(oldPassword)) {
                res = new ApiResponse(Error.ERROR, Error.INVALID_PASSWORD, null);
            } else {
                admin.password = password;
                admin.update();
                res = new ApiResponse(Error.SUCCESS, "", null);
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onGetListNotifyAdmin(Integer adminId) {
        try {
            List<Notify> lst = Notify.find.query().where()
                    .eq("type_account", S.TYPE_ACCOUNT_KTV)
                    .eq("account_id", adminId)
                    .eq("is_delete", S.FALSE).findList();

            List<NotifyBean> lstResponse = new ArrayList<>();
            if (lst != null) {
                for (Notify n : lst) {
                    lstResponse.add(new NotifyBean(n.id, n.title, n.content, n.is_read, n.created));
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("notify_admin", lstResponse);

            List<Information> lst1 = Information.find.query().where().eq("type", 8).findList();
            map.put("notify_system", lst1);

            ReadNotifySystem noti = ReadNotifySystem.find.query().where()
                    .eq("type_account", S.TYPE_ACCOUNT_KTV)
                    .eq("account_id", adminId).setMaxRows(1).findOne();
            map.put("status_read_notify_system", noti != null ? noti.notify_system : "");

            return processSuccess(map);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onDeleteNotifyAdmin() {
        try {
            JsonNode req = request().body().asJson();
            long userId = req.get(Key.USER_ID).asLong();
            long id_notify = req.get("id_notify").asLong();

            Notify notify = Notify.find.byId(id_notify);
            if (notify != null) {
                if (notify.account_id == userId && notify.type_account == S.TYPE_ACCOUNT_KTV) {
                    notify.is_delete = S.TRUE;
                    notify.save();
                    return processSuccess(id_notify);
                } else {
                    return errorInvalid("Bạn không có thông báo này");
                }
            } else {
                return errorInvalid("Bạn không có thông báo này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onReadNotifyAdmin() {
        try {
            JsonNode req = request().body().asJson();
            long userId = req.get(Key.USER_ID).asLong();
            long id_notify = req.get("id_notify").asLong();
            int status = req.get("status").asInt();

            Notify notify = Notify.find.byId(id_notify);
            if (notify != null) {
                if (notify.account_id == userId && notify.type_account == S.TYPE_ACCOUNT_KTV) {
                    notify.is_read = status;
                    notify.save();
                    return processSuccess(id_notify);
                } else {
                    return errorInvalid("Bạn không có thông báo này");
                }
            } else {
                return errorInvalid("Bạn không có thông báo này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onGetLogTransactionAdmin(Integer userId) {
        try {
            List<TransactionHistoryAdmin> lst = TransactionHistoryAdmin.find.query().where().eq("admin_id", userId).findList();
            return processSuccess(lst);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onGetLogMaintenanceAndEmergency(int adminId) {
        String sql = "SELECT a.id AS ma_bao_tri, a.name AS mo_ta, b.status, b.time AS time_bao_tri, d.name AS ten_goi" +
                " FROM admin_mission AS a" +
                " INNER JOIN service_package_maintenance AS b ON a.service_package_maintenance_id = b.id" +
                " INNER JOIN service_package_user AS c ON c.id = b.service_package_user_id" +
                " INNER JOIN service_package AS d ON d.id = c.service_package_id" +
                " WHERE a.admin_id = " + adminId;

        List<SqlRow> lst = getListResultQuery(sql);
        Map<String, Object> map = new HashMap<>();
        map.put("maintenance", lst);

        sql = "SELECT a.id AS ma_cuu_ho, b.status, b.des AS mo_ta, b.address, b.time AS time_bao_tri" +
                " FROM admin_emergency a" +
                " INNER JOIN emergency AS b ON a.emergency_id = b.id" +
                " WHERE a.admin_id = " + adminId;

        List<SqlRow> lst1 = getListResultQuery(sql);
        map.put("emergency", lst1);
        return processSuccess(map);
    }


    private static List<SqlRow> getListResultQuery(String sql) {
        SqlQuery query = Ebean.createSqlQuery(sql);
        List<SqlRow> results = query.findList();
        return results;
    }

    private static SqlRow getResultQuery(String sql) {
        SqlQuery query = Ebean.createSqlQuery(sql);
        SqlRow result = query.findOne();
        return result;
    }

    public Result onKtvStartEmergency(Admin admin, JsonNode req) {
        try {
            long emergency_id = req.get("emergency_id").asLong();
            Emergency emergency = Emergency.find.byId(emergency_id);
            if (emergency != null) {
                emergency.status = StatusJob.BAT_DAU_DI;
                emergency.save();
                int admin_emergency_id = req.get("admin_emergency_id").asInt();
                double latitude = req.get("latitude").asDouble();
                double longitude = req.get("longitude").asDouble();
                String position = req.get("position").asText();
                AdminEmergency adminEmergency = AdminEmergency.find.byId(admin_emergency_id);
                if (adminEmergency != null) {
                    if (adminEmergency.admin_id == admin.id) {
                        adminEmergency.time_start_go = Utils.getCurrentMiniseconds();
                        adminEmergency.position_start = Utils.getInfoPositionMap(latitude, longitude, position);
                        adminEmergency.save();
                        //push firebase
                        User user = User.find.byId(emergency.user_id);
                        if (user != null) {
                            PushNotification.getInstance().SendOneDevice(user.fcm_token, "KTV " + admin.fullname + " bắt đầu đi đến chỗ quý khách", "KTV " + admin.fullname + " bắt đầu đi đến chỗ quý khách");
                        }

                        return processSuccess(null);
                    } else {
                        return errorInvalid("Bạn không có nhiệm vụ này");
                    }
                } else {
                    return errorInvalid("Bạn không có nhiệm vụ này");
                }
            } else {
                return errorInvalid("Bạn không xử lý gói cứu hộ khẩn cấp này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onPartnerArrivedEmergency(Admin admin) {
        try {
            JsonNode req = request().body().asJson();
            Integer admin_emergency_id = req.get("admin_emergency_id").asInt();
            AdminEmergency adminEmergency = AdminEmergency.find.byId(admin_emergency_id);
            if (adminEmergency != null) {
                if (adminEmergency.admin_id != admin.id) {
                    res = new ApiResponse(Error.ERROR, Error.NOT_PERMISSION, null);
                } else {
                    Emergency emergency = Emergency.find.byId(adminEmergency.emergency_id);
                    if (emergency.status != StatusJob.BAT_DAU_DI) {
                        res = new ApiResponse(Error.ERROR, Error.CMD_NOT_EXECUTE, null);
                    } else {
                        adminEmergency.time_start_job = Utils.getCurrentMiniseconds();
                        adminEmergency.save();
                        emergency.status = StatusJob.KTV_DA_DEN;
                        emergency.save();

                        //push firebase
                        User user = User.find.byId(emergency.user_id);
                        if (user != null) {
                            PushNotification.getInstance().SendOneDevice(user.fcm_token, "KTV " + admin.fullname + " đã đến chỗ quý khách", "KTV " + admin.fullname + " đã đến chỗ quý khách");
                        }
                        res = new ApiResponse(Error.SUCCESS, "", null);
                    }
                }
            } else {
                return errorInvalid("Không có nhiệm vụ này");
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onKtvEmergencyReport(Admin admin) {
        try {
            JsonNode reg = request().body().asJson();
            long emergency_id = reg.get("emergency_id").asLong();
            int status = reg.get("status").asInt();
            Emergency emergency = Emergency.find.byId(emergency_id);
            if (emergency != null) {
                int admin_id = admin.id;
                int admin_emergency_id = reg.get("admin_emergency_id").asInt();
                AdminEmergency adminEmergency = AdminEmergency.find.byId(admin_emergency_id);
                if (adminEmergency != null) {
                    if (admin_id == adminEmergency.admin_id) {
                        adminEmergency.time_end = Utils.getCurrentMiniseconds();
                        adminEmergency.save();
                        String content = reg.get("content").asText();
                        AdminEmergencyReport check = AdminEmergencyReport.find.query().where()
                                .eq("admin_id", admin.id).eq("emergency_id", emergency_id).setMaxRows(1).findOne();
                        if (check == null) {
                            AdminEmergencyReport mission = new AdminEmergencyReport(admin_id, admin_emergency_id, content, emergency.service_package_user_id, Utils.getTimeStamp());
                            mission.save();
                            emergency.status = status;
                            emergency.update();
                        } else {
                            return errorInvalid("Bạn đã báo cáo cho nhiệm vụ này rồi");
                        }
                    } else {
                        return errorInvalid("Bạn không có nhiệm vụ này");
                    }
                } else {
                    return errorInvalid("Bạn không có nhiệm vụ này");
                }
            } else {
                return errorInvalid("Bạn không xử lý nhiệm vụ cứu hộ này");
            }

            res = new ApiResponse(Error.SUCCESS, "", null);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    public Result onKtvChat(Integer adminId) {
        try {
            JsonNode req = request().body().asJson();
            String content = req.get("content").asText();
            Integer id_cskh = req.get("id_cskh").asInt();
            Integer read = req.get("read").asInt();
            ChatInfo info = new ChatInfo(S.TYPE_ACCOUNT_KTV, adminId, adminId, content, id_cskh, read);
            info.save();
            return processSuccess(null);
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result onGetLogChatKtv(Integer userId) {
        try {
            List<ChatInfo> lst = ChatInfo.find.query().where()
                    .eq("type_account", S.TYPE_ACCOUNT_KTV)
                    .eq("account_id", userId)/*.setOrderBy("id ASC")*/.setMaxRows(50).findList();
            return processSuccess(lst);
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result onCskhChat(Admin admin) {
        try {
            if (admin.level == S.LEVEL_CSKH) {
                JsonNode req = request().body().asJson();
                String content = req.get("content").asText();
                int type_account_chat = req.get("type_account_chat").asInt();
                int account_id_chat = req.get("account_id_chat").asInt();
                int read = req.get("read").asInt();
                ChatInfo info = new ChatInfo(S.LEVEL_CSKH, admin.id, account_id_chat, content, admin.id, read);
                info.type_account_chat = type_account_chat;
                info.save();
                return processSuccess(null);
            } else {
                return errorInvalid("bạn không phải CSKH");
            }
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result onCskhGetChatWaitReply(Integer adminId) {
        List<ChatInfo> lst = ChatInfo.find.query().where()
                .eq("is_read", S.FALSE).findList();

        Map<Integer, Map<Integer, List<ChatInfo>>> map = new HashMap<>();
        Map<Integer, List<ChatInfo>> m;
        for (ChatInfo c : lst) {
            m = map.get(c.type_account);
            if (m != null) {
                List<ChatInfo> lstChat = m.get(c.account_id);
                if (lstChat != null) {
                    lstChat.add(c);
                } else {
                    lstChat = new ArrayList<>();
                    lstChat.add(c);
                }
            } else {
                Map<Integer, List<ChatInfo>> tmp = new HashMap<>();
                List<ChatInfo> lstChat = new ArrayList<>();
                lstChat.add(c);
                tmp.put(c.account_id, lstChat);
                map.put(c.type_account, tmp);
            }
            c.is_read = S.TRUE;
            c.id_cskh = adminId;
            c.update();
        }

        return processSuccess(map);
    }

    Result onGetMaintenanceInfo() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_maintenance_id = req.get("service_package_maintenance_id").asInt();
            ServicePackageMaintenance servicePackageMaintenance = ServicePackageMaintenance.find.byId(service_package_maintenance_id);
            if (servicePackageMaintenance != null) {
                return processSuccess(servicePackageMaintenance);
            } else {
                return errorInvalid(Error.SERVICE_PACKAGE_NOT_EXISTS);
            }
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    Result onGetEmergencyInfo() {
        try {
            JsonNode req = request().body().asJson();
            long emergency_id = req.get("emergency_id").asLong();
            Emergency emergency = Emergency.find.byId(emergency_id);
            if (emergency != null) {
                return processSuccess(emergency);
            } else {
                return errorInvalid(Error.SERVICE_PACKAGE_NOT_EXISTS);
            }
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    private Result onListProductKtv() {
        String sql = RawQuery.QUERY_PRODUCT;
        sql += "WHERE a.is_show=" + S.TRUE;
        return processSuccess(getListResultQuery(sql));
    }

    private Result onProductDetailKtv(JsonNode req) {
        try {
            int product_id = req.get("product_id").asInt();
            return processSuccess(getResultQuery(RawQuery.getRawQueryProductDetail(product_id)));
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetListProductKtv(Integer adminId) {
        List<ProductKtv> lst = ProductKtv.find.query().where()
                .eq("admin_id", adminId).findList();
        return processSuccess(lst);
    }

    private Result onUseProductKtv(Admin admin) {
        try {
            JsonNode req = request().body().asJson();
            int product_id = req.get("product_id").asInt();
            int number = Math.abs(req.get("number").asInt());

            ProductKtv productKtv = ProductKtv.find.query().where()
                    .eq("admin_id", admin.id)
                    .eq("product_id", product_id).findOne();

            if (productKtv != null) {
                if (productKtv.number >= number) {
                    productKtv.number -= number;
                    productKtv.save();

                    ProductKtvLogUse logUse = new ProductKtvLogUse(admin.id, product_id, number, Utils.getCurrentMiniseconds());
                    logUse.save();

                    Map<String, Object> map = new HashMap<>();
                    map.put("product_id", product_id);
                    map.put("new_number", productKtv.number);
                    return processSuccess(map);
                } else {
                    return errorInvalid("Quý khách chỉ có " + number + " sản phẩm này");
                }
            } else {
                return errorInvalid("Bạn không có sản phẩm này để sử dụng");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetListProductIdKtv() {
        List<ProductType> lst = ProductType.find.all();
        return processSuccess(lst);
    }

    private Result onGetListProductKtvByType() {
        try {
            int type = request().body().asJson().get("type").asInt();
            List<Product> lst = Product.find.query().where()
                    .eq("type", type).findList();
            return processSuccess(lst);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetInfoRechargeBankKtv() {
        List<BankInfo> lst = BankInfo.find.all();
        return processSuccess(lst);
    }

    private Result onGetDetailLogTransactionKtv(int adminId) {
        try {
            JsonNode req = request().body().asJson();
            int type = req.get("type").asInt();
            long transaction_id = req.get("transaction_id").asLong();
            Object obj = null;
            String sql = null;

            switch (type) {
                /*** Add Money ***/
//                case S.LOG_ADD_MONEY_TIP_MAINTENANCE://log giao dịch đã đủ thông tin
//                    break;
//
//                case S.LOG_ADD_MONEY_TIP_EMERGENCY:
//                    break;

//                case S.LOG_RECHARGE_CARD:
//                    break;

                case S.LOG_RECHARGE_BANK:
                    sql = "SELECT a.id, a.status, a.type, a.tien_nap, a.fee, a.bank_name, a.status, a.created_at AS created" +
                            " FROM log_recharge_ngan_luong a" +
                            " INNER JOIN transaction_history_user b ON a.id = b.transaction_id" +
                            " WHERE a.account_id =" + adminId + " AND a.type_account =" + S.TYPE_ACCOUNT_KTV + " AND a.id=" + transaction_id;
                    break;
                /*** End Add Money ***/

                case S.LOG_MUA_SAN_PHAM:
                    sql = "SELECT a.number, a.money, a.created AS time_buy, b.id AS product_id, b.name, b.link_icon, c.name AS address_sell" +
                            " FROM product_ktv_log_buy a" +
                            " INNER JOIN product b ON a.product_id = b.id" +
                            " INNER JOIN shop c ON c.id = b.shop" +
                            " WHERE a.admin_id =" + adminId + " AND a.id =" + transaction_id;
                    break;

                case S.LOG_PAYMENT_CART://thanh toán đơn hàng
                    sql = "SELECT a.id AS ma_don_hang, a.price, a.detail_cart, a.status, a.created" +
                            " FROM log_payment_cart_admin a" +
                            " WHERE a.admin_id =" + adminId + " AND a.id =" + transaction_id;
                    break;

                case S.LOG_REQUIRE_PAYMENT:
                    sql = "SELECT a.id, a.type, a.status, a.balance_wait_payment, a.bonus_wait_payment, a.bonus_introduce_customer_wait_payment, a.total_money_payment,a.vnd,a.conversion_rate, a.description, a.bank_info, a.created" +
                            " FROM admin_require_payment a" +
                            " WHERE a.admin_id=" + adminId + " AND a.id =" + transaction_id;
                    break;

                case S.CONFIRM_RECHARGE_KTV:
                    sql = "SELECT a.id, a.balance_tru, a.so_tien_da_tru_am, a.description, a.created_at" +
                            " FROM log_ktv_confirm_recharge_from_user a" +
                            " WHERE a.admin_id=" + adminId + " AND a.id =" + transaction_id;
                    break;
                default:
                    break;
            }
            if (sql != null) {
                obj = getResultQuery(sql);
            }

            return processSuccess(obj);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetMissionDayToDay(int admin_id) {
        try {
            JsonNode req = request().body().asJson();
            long start_time = req.get("start_time").asLong();
            long end_time = req.get("end_time").asLong();
            if (start_time == end_time) {
                end_time += 86399L;
            }
            String sql = "SELECT a.*, b.time, b.status, c.address" +
                    " FROM admin_mission AS a" +
                    " INNER JOIN service_package_maintenance AS b ON a.service_package_maintenance_id = b.id" +
                    " INNER JOIN service_package_user AS c ON b.service_package_user_id = c.id" +
                    " WHERE a.admin_id = " + admin_id + " AND (b.time BETWEEN " + start_time + " AND " + end_time + ")";
            SqlQuery query = Ebean.createSqlQuery(sql);
            List<SqlRow> missions = query.findList();
            return processSuccess(missions);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetRequireRechargeByMe(int adminId) {
        String sql = "SELECT a.id AS transaction_id, a.user_id, a.money, a.description,a.created_at AS created, b.fullname AS user_fullname" +
                " FROM log_recharge_via_ktv a" +
                " INNER JOIN user AS b ON a.user_id = b.id" +
                " WHERE a.admin_id=" + adminId + " AND a.status= 1";
        return processSuccess(getListResultQuery(sql));
    }

    private Result onConfirmRechargeViaKtv(Admin admin) {
        try {
            if (admin.status == S.ACCOUNT_LOCK) {
                return errorInvalid(Error.LOCK_ACCOUNT);
            }
            JsonNode req = request().body().asJson();
            long transaction_id = req.get("transaction_id").asLong();
            int status = req.get("status").asInt();//1:đồng ý, 0: hủy
            LogRechargeViaKtv require = LogRechargeViaKtv.find.byId(transaction_id);
            if (require != null) {
                if (require.admin_id == admin.id) {
                    require.status = (status == S.TRUE ? 2 : 3);

                    User user = User.find.byId(require.user_id);
                    String notify;
                    String descriptionLogTransaction = "Nạp " + Utils.formatMoney(require.money) + S.MONEY_NAME + " qua KTV";
                    if (require.is_add_money == S.FALSE) {
                        if (status == S.TRUE) {
                            require.is_add_money = S.TRUE;
                            require.description_response = descriptionLogTransaction;

                            if (admin.balance >= require.money) {
                                admin.balance -= require.money;
                                admin.update();

                                String description = "Trừ: -" + Utils.formatMoney(require.money) + S.MONEY_NAME + " do xác nhận nạp tiền cho khách hàng mã số: " + user.id + " - " + user.fullname;
                                LogKtvConfirmRechargeFromUser log = new LogKtvConfirmRechargeFromUser(admin.id, require.money, 0, description, Utils.getTimeStamp());
                                log.save();
                                TransactionHistoryAdmin logTran = new TransactionHistoryAdmin(admin.id, S.CONFIRM_RECHARGE_KTV, log.id, S.TRUE, -require.money, "Xác nhận nạp tiền cho khách hàng");
                                logTran.save();

                                PlayerManager.adminChangeBalance(admin, description);
                            } else {
                                long tmp = admin.balance;
                                if ((long) require.money > (tmp + admin.han_muc - admin.so_tien_da_tru_am)) {
                                    require.status = 4;
                                    require.update();

                                    LogKtvConfirmRechargeFromUser log = new LogKtvConfirmRechargeFromUser(admin.id, 0, 0, "Bạn không đủ số dư để xác nhận nạp " + Utils.formatMoney(require.money) + S.MONEY_NAME + " cho khách hàng mã số: " + user.id + " - " + user.fullname, Utils.getTimeStamp());
                                    log.save();
                                    TransactionHistoryAdmin logTran = new TransactionHistoryAdmin(admin.id, S.CONFIRM_RECHARGE_KTV, log.id, S.FALSE, 0, "Xác nhận nạp tiền cho khách hàng");
                                    logTran.save();
                                    return errorInvalid("Bạn không đủ số dư cho phép để xử lý giao dịch này");
                                } else {
                                    int tienTruAm = require.money - (int) tmp;
                                    admin.balance -= tmp;
                                    admin.so_tien_da_tru_am += tienTruAm;
                                    admin.update();

                                    String description = "Trừ: -" + Utils.formatMoney(tmp) + S.MONEY_NAME + " do xác nhận nạp tiền cho khách hàng mã số: " + user.id + " - " + user.fullname;
                                    LogKtvConfirmRechargeFromUser log = new LogKtvConfirmRechargeFromUser(admin.id, (int) tmp, tienTruAm, description, Utils.getTimeStamp());
                                    log.save();
                                    TransactionHistoryAdmin logTran = new TransactionHistoryAdmin(admin.id, S.CONFIRM_RECHARGE_KTV, log.id, S.TRUE, -require.money, "Xác nhận nạp tiền cho khách hàng");
                                    logTran.save();

                                    PlayerManager.adminChangeBalance(admin, description);
                                }
                            }

                            require.update();

                            notify = "Nạp " + Utils.formatMoney(require.money) + S.MONEY_NAME + " qua KTV mã số: " + admin.id + " - " + admin.fullname + " thành công";
                            user.balance += require.money;
                            user.update();
                            PlayerManager.userChangeBalance(user, notify);

                            TransactionHistoryUser logTran = new TransactionHistoryUser(user.id, S.LOG_RECHARGE_VIA_KTV, require.id, S.TRUE, require.money, descriptionLogTransaction);
                            logTran.save();

                            Notify notifyLog = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, "Nạp tiền qua KTV", notify, Utils.getTimeStamp());
                            notifyLog.save();
                            PushNotification.getInstance().SendOneDevice(user.fcm_token, "Nạp tiền qua KTV", notify);
                            NodeJs.getInstance().emitUpdateMoney(S.TYPE_ACCOUNT_USER, user.id);
                            NodeJs.getInstance().emitServerPush(S.TYPE_ACCOUNT_USER, user.id, "Chúc mừng quý khách đã nạp thành công " + Utils.formatMoney(require.money) + S.MONEY_NAME);
                            return processSuccess("Xác nhận nap tiền cho khách hàng mã số: " + user.id + " - " + user.fullname + " thành công", null);
                        } else {
                            require.is_add_money = S.TRUE;
                            require.description_response = descriptionLogTransaction;
                            require.update();
                            notify = "KTV mã số: " + admin.id + " - " + admin.fullname + " không xác nhận khách hàng mã số: " + user.id + " - " + user.fullname + " nạp tiền";
                            Notify notifyLog = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, "Từ chối xác nhận nạp tiền qua KTV", notify, Utils.getTimeStamp());
                            notifyLog.save();

                            TransactionHistoryUser logTran = new TransactionHistoryUser(user.id, S.LOG_RECHARGE_VIA_KTV, require.id, S.FALSE, require.money, descriptionLogTransaction);
                            logTran.save();

                            PushNotification.getInstance().SendOneDevice(user.fcm_token, "KTV từ chối xác nhận nạp tiền", notify);
                            return processSuccess("Từ chối xác nhận nạp tiền thành công", null);
                        }
                    } else {
                        return errorInvalid("Bạn đã xác nhận cho yêu cầu nạp tiền này rồi");
                    }
                } else {
                    return errorInvalid("Bạn không có quyền thực hiện điều này");
                }
            } else {
                return errorInvalid("Không có yêu cầu nạp tiền này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onRegisterAccountBankReceiveMoney(Admin admin) {
        try {
            JsonNode req = request().body().asJson();
            String chu_tai_khoan = req.get("chu_tai_khoan").asText();
            String so_tai_khoan = req.get("so_tai_khoan").asText();
            String tinh_thanh = req.get("tinh_thanh").asText();
            String bank_name = req.get("bank_name").asText();
            String chi_nhanh = req.get("chi_nhanh").asText();
            AccountBankAdmin bankCheck = AccountBankAdmin.find.query().where()
                    .eq("bank_name", bank_name)
                    .eq("admin_id", admin.id).findOne();

            if (bankCheck == null) {
                AccountBankAdmin accountBank = new AccountBankAdmin(admin.level, admin.id,
                        chu_tai_khoan, so_tai_khoan, tinh_thanh, bank_name, chi_nhanh);
                accountBank.save();
                return processSuccess("Đăng ký ngân hàng nhận tiền thành công");
            } else {
                if (!so_tai_khoan.equals(bankCheck.so_tai_khoan)) {
                    AccountBankAdmin accountBank = new AccountBankAdmin(admin.level, admin.id,
                            chu_tai_khoan, so_tai_khoan, tinh_thanh, bank_name, chi_nhanh);
                    accountBank.save();
                    return processSuccess("Đăng ký ngân hàng nhận tiền thành công");
                }
                return errorInvalid("Bạn đã đăng ký số tài khoản này rồi");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onUpdateInfoBankReceiveMoney(JsonNode req) {
        try {
            long account_bank_id = req.get("account_bank_id").asLong();
            String chu_tai_khoan = req.get("chu_tai_khoan").asText();
            String so_tai_khoan = req.get("so_tai_khoan").asText();
            String tinh_thanh = req.get("tinh_thanh").asText();
            String bank_name = req.get("bank_name").asText();
            String chi_nhanh = req.get("chi_nhanh").asText();
            AccountBankAdmin bankCheck = AccountBankAdmin.find.byId(account_bank_id);

            if (bankCheck != null) {
                bankCheck.chu_tai_khoan = chu_tai_khoan;
                bankCheck.so_tai_khoan = so_tai_khoan;
                bankCheck.tinh_thanh = tinh_thanh;
                bankCheck.bank_name = bank_name;
                bankCheck.chi_nhanh = chi_nhanh;
                bankCheck.update();
                return processSuccess("Cập nhật thông tin ngân hàng thành công", null);
            } else {
                return errorInvalid("Không có thông tin ngân hàng này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetListAccountBankReceiveMoney(int adminId) {
        List<AccountBankAdmin> lst = AccountBankAdmin.find.query().where()
                .eq("admin_id", adminId).findList();
        return processSuccess(lst);
    }

    private Result onRequirePayment(Admin admin) {
        try {
            if (admin.status == S.ACCOUNT_LOCK) {
                return errorInvalid(Error.LOCK_ACCOUNT);
            }
            JsonNode req = request().body().asJson();
            int balance = Math.abs(req.get("balance").asInt());
            int bonus = Math.abs(req.get("bonus").asInt());
            int bonus_introduce_customer = Math.abs(req.get("bonus_introduce_customer").asInt());
            long account_bank_admin_id = req.get("account_bank_admin_id").asLong();
            int type = req.get("type").asInt();//1: chuyển khoản, 2: nhận tiền mặt

            if (type == 1 || type == 2) {
            } else {
                return errorInvalidBodyData();
            }

            String bank_info = null;
            if (type == 1) {
                AccountBankAdmin accountBank = AccountBankAdmin.find.byId(account_bank_admin_id);
                if (accountBank == null) {
                    return errorInvalid("Bạn chưa đăng ký ngân hàng này để thanh toán");
                } else {
                    bank_info = accountBank.chu_tai_khoan + "|" + accountBank.bank_name + "|" + accountBank.chi_nhanh;
                }
            }

            if (admin.province_id == null) {
                return errorInvalid("Bạn cần cập nhật lại địa chỉ Thành Phố");
            }

            VnCity area = VnCity.find.byId(admin.province_id);
            if (area == null) {
                return errorInvalid("Bạn cần cập nhật lại địa chỉ Thành Phố");
            }

            List<CmsConfigPayment> lstConfig = CmsConfigPayment.find.query().where().eq("area_id", area.area).findList();
            if (lstConfig.size() == 0) {
                return errorInvalid("Khu vực này chưa hỗ trợ thanh toán");
            }

            Map<Integer, CmsConfigPayment> mapConfig = new HashMap<>();
            for (CmsConfigPayment c : lstConfig) {
                mapConfig.put(c.id_money, c);
            }
            CmsConfigPayment config1 = mapConfig.get(1);//balance_wait_payment
            CmsConfigPayment config2 = mapConfig.get(2);//bonus_wait_payment
            CmsConfigPayment config3 = mapConfig.get(3);//bonus_introduce_customer_wait_payment

            if (config1 == null || config2 == null || config3 == null) {
                return errorInvalid("Lỗi hệ thống");
            }

            if (balance > admin.balance_wait_payment || bonus > admin.bonus_wait_payment || bonus_introduce_customer > admin.bonus_introduce_customer_wait_payment) {
                return errorInvalid("Bạn không đủ số dư để rút");
            }

            long balance_tmp = balance + config1.limit;
            long bonus_tmp = bonus + config2.limit;
            long bonus_introduce_customer_tmp = bonus_introduce_customer + config3.limit;

            if (balance_tmp > admin.balance_wait_payment || bonus_tmp > admin.bonus_wait_payment || bonus_introduce_customer_tmp > admin.bonus_introduce_customer_wait_payment) {
                return errorInvalid("Không thỏa mãn số dư tối thiểu");
            }

            long vnd = (long) ((double) balance * config1.tyle) + (long) ((double) bonus * config2.tyle) + (long) ((double) bonus_introduce_customer * config3.tyle);

            int totalMoneyPayment = balance + bonus + bonus_introduce_customer;

            admin.balance -= balance;
            admin.balance_wait_payment -= balance;
            admin.bonus -= bonus;
            admin.bonus_wait_payment -= bonus;
            admin.bonus_introduce_customer -= bonus_introduce_customer;
            admin.bonus_introduce_customer_wait_payment -= bonus_introduce_customer;
            admin.update();
            String description = "Yêu cầu thanh toán tiền lương: -" + Utils.formatMoney(balance) + ", thưởng: -" + Utils.formatMoney(bonus) +
                    ", thưởng giới thiệu khách hàng: -" + Utils.formatMoney(bonus_introduce_customer) +
                    ", Tổng: -" + Utils.formatMoney(totalMoneyPayment) + S.MONEY_NAME + ", Tổng tiền VND: +" + Utils.formatMoney(vnd);
            PlayerManager.adminChangeBalance(admin, description);

            String conversion_rate = "1_" + config1.tyle + S.TACH_PHAY + "2_" + config2.tyle + S.TACH_PHAY + "3_" + config3.tyle;

            AdminRequirePayment requirePayment = new AdminRequirePayment(admin.id, admin.level, type, balance, bonus, bonus_introduce_customer,
                    totalMoneyPayment, vnd, conversion_rate, account_bank_admin_id, description, Utils.getTimeStamp());
            if (type == 1) {
                requirePayment.bank_info = bank_info;
            }
            requirePayment.save();

            TransactionHistoryAdmin logTran = new TransactionHistoryAdmin(admin.id, S.LOG_REQUIRE_PAYMENT, requirePayment.id, S.TRUE, -totalMoneyPayment, "Gửi yêu cầu thanh toán");
            logTran.save();

            admin.password = "";
            return processSuccess("Gửi yêu cầu thanh toán thành công", admin);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetInfoPaymentMinimum(Admin admin) {
        if (admin.province_id == null) {
            return errorInvalid("Bạn cần cập nhật lại địa chỉ Thành Phố");
        }
        VnCity area = VnCity.find.byId(admin.province_id);

        int area_id = area.area;
        String sql = "SELECT a.id_money,b.name,a.time,a.limit,a.tyle" +
                " FROM cms_config_payment a" +
                " INNER JOIN cms_config_payment_money b ON a.id_money=b.id" +
                " WHERE a.area_id=" + area_id;
        return processSuccess(getListResultQuery(sql));
    }

    private Result onDeleteInfoBankReceiveMoney(int adminId) {
        try {
            long account_bank_id = request().body().asJson().get("account_bank_id").asLong();
            AccountBankAdmin accountBank = AccountBankAdmin.find.byId(account_bank_id);
            if (accountBank != null) {
                if (accountBank.admin_id == adminId) {
                    accountBank.delete();
                    return processSuccess("Xóa thành công", account_bank_id);
                } else {
                    return errorInvalid("Bạn không có thông tin tài khoản này");
                }
            } else {
                return errorInvalid("Không có thông tin tài khoản này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetInfoCardKtv(int adminId) {
        try {
            AdminCart cart = AdminCart.find.query().where()
                    .eq("admin_id", adminId).findOne();
            return processSuccess(cart);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onUpdateCardKtv(int adminId) {
        try {
            String info_cart = request().body().asJson().get("info_cart").asText();
            AdminCart cart = AdminCart.find.query().where()
                    .eq("admin_id", adminId).findOne();
            if (cart != null) {
                cart.info_cart = info_cart;
                cart.time_change = Utils.getTimeStamp();
                cart.update();
            } else {
                cart = new AdminCart(adminId, info_cart, Utils.getTimeStamp());
                cart.save();
            }
            return processSuccess(null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onPaymentCartKtv(Admin admin, JsonNode req) {
        try {
            if (admin.status == S.ACCOUNT_LOCK) {
                return errorInvalid(Error.LOCK_ACCOUNT);
            }
            String info_cart = req.get("info_cart").asText();
            String discount_code = req.get("discount_code").asText();

            JsonNode cart = Json.parse(info_cart);
            if (cart.size() > 0) {
                AdminCart adminCart = AdminCart.find.query().where()
                        .eq("user_id", admin.id).findOne();
                if (adminCart != null) {
                    adminCart.info_cart = "[]";
                    adminCart.time_change = Utils.getTimeStamp();
                    adminCart.update();
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
                            return errorInvalid("Sản phẩm " + product.name + " đã hết hàng");
                        }
                    } else {
                        return errorInvalid("Giỏ hàng có sản phẩm không hợp lệ");
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

                if (price > admin.balance) {
                    return errorInvalid("Bạn không đủ tiền thanh toán giỏ hàng này");
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

                    LogPaymentCartAdmin logPaymentCart = new LogPaymentCartAdmin(admin.id, price, product_id_detail, Json.toJson(lstProduct).toString(), 1, coupon_id, Utils.getTimeStamp());
                    logPaymentCart.save();

                    admin.balance -= price;
                    admin.update();

                    String description = "Thanh toán đơn hàng: " + logPaymentCart.id;
                    PlayerManager.adminChangeBalance(admin, description + " hết: -" + Utils.formatMoney(price));

                    TransactionHistoryAdmin logTran = new TransactionHistoryAdmin(admin.id, S.LOG_PAYMENT_CART, logPaymentCart.id, S.TRUE, -price, description);
                    logTran.save();

                    NodeJs.getInstance().emitPartnerPaymentOrder(admin.id, logPaymentCart.id);

                    return processSuccess("Thanh toán thành công đơn hàng: " + logPaymentCart.id, admin.balance);
                } else {
                    return errorInvalid("Thông tin giỏ hàng không hợp lệ");
                }
            } else {
                return errorInvalid("Thông tin giỏ hàng không hợp lệ");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetInfoAccountAdmin(Admin admin) {
        System.out.println("get info user");
        admin.password = "";
        return processSuccess(admin);
    }

    private Result onCollaboratorReceiveMission(Admin admin, JsonNode req) {
        try {
            if (admin.status == S.ACCOUNT_LOCK) {
                return errorInvalid(Error.LOCK_ACCOUNT);
            }
            if (admin.level == S.LEVEL_CTV) {
                long collaborator_mission_id = req.get("collaborator_mission_id").asLong();
                CollaboratorMission mission = CollaboratorMission.find.byId(collaborator_mission_id);
                if (mission.status == S.FALSE) {
                    if (admin.service_id.contains(mission.service_id + ",")) {
                        mission.admin_id = admin.id;
                        mission.status = S.TRUE;
                        mission.update();
                        Map<String, Object> map = new HashMap<>();
                        map.put("phone_kh", mission.phone);
                        map.put("service_id", mission.service_id);
                        map.put("address", mission.address);
                        map.put("details", mission.details);
                        User user = User.find.byId(mission.user_id);
                        if (user != null) {
                            PushNotification.getInstance().SendOneDevice(user.fcm_token, "Có cộng tác viên nhận việc", "Cộng tác viên mã số: " + admin.id + " - " + admin.fullname + " đã nhận làm nhiệm vụ của bạn");
                        }
                        return processSuccess(map);
                    } else {
                        return errorInvalid("Kỹ năng của bạn không phù hợp với nhiệm vụ này");
                    }
                } else {
                    return errorInvalid("Đã có người nhận nhiệm vụ này rồi");
                }
            } else {
                return errorInvalid(Error.NOT_PERMISSION);
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onCollaboratorGetInfoMission(JsonNode req) {
        try {
            long collaborator_mission = req.get("collaborator_mission_id").asLong();
            CollaboratorMission mission = CollaboratorMission.find.byId(collaborator_mission);
            if (mission != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("service_id", mission.service_id);
                map.put("details", mission.details);
                map.put("images", mission.images);
                map.put("created", mission.created);
                return processSuccess(map);
            } else {
                return errorInvalid("Không có thông tin cho nhiệm vụ này");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onCollaboratorGetListMission(Admin admin) {
        if (admin.level == S.LEVEL_CTV) {
            String condition = admin.service_id;
            if (condition.length() > 0) {
                condition = condition.substring(0, condition.length() - 1);
            } else {
                condition = "''";
            }
            double offset = S.OFFSET_POSITION;
            String sql = "SELECT a.id, a.service_id,a.details,a.images,a.created, a.phone, a.address, a.latitude, a.longitude, a.status, b.fullname" +
                    " FROM collaborator_mission a" +
                    " INNER JOIN user AS b ON a.user_id = b.id" +
                    " WHERE (a.status = 0 OR (a.status = 1 AND a.admin_id = " + admin.id + "))" +
                    " AND a.latitude <" + (admin.latitude + offset) + " AND a.latitude >" + (admin.latitude - offset) +
                    " AND a.longitude <" + (admin.longitude + offset) + " AND a.longitude >" + (admin.longitude - offset) +
                    " AND a.service_id IN (" + condition + ")";
            return processSuccess(getListResultQuery(sql));
        } else {
            return errorInvalid(Error.NOT_PERMISSION);
        }
    }

    private Result onUpdateAccountInfo(Admin admin, JsonNode req) {
        try {
            String fullname = req.get("fullname").asText();
            String mobile = req.get("mobile").asText();
            String email = req.get("email").asText();
            String birthday = req.get("birthday").asText();
            admin.fullname = fullname;
            admin.phone = mobile;
            admin.email = email;
            admin.birthday = birthday;
            admin.update();
            admin.password = "";
            return processSuccess("Cập nhật thông tin tài khoản thành công", admin);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onCollaboratorMissionReport(int adminId, JsonNode req) {
        try {
            long collaborator_mission_id = req.get("collaborator_mission_id").asLong();
            String content = req.get("content").asText();
            int status = req.get("status").asInt();
            CollaboratorMission mission = CollaboratorMission.find.byId(collaborator_mission_id);
            if (mission != null) {
                if (mission.admin_id != null && mission.admin_id == adminId) {
                    if (mission.status == 1) {
                        CollaboratorMissionReport report = new CollaboratorMissionReport(adminId, collaborator_mission_id, content, Utils.getTimeStamp());
                        report.save();
                        mission.status = status;
                        mission.update();
                    } else {
                        return errorInvalid("Bạn đã báo cáo nhiệm vụ này rồi");
                    }
                } else {
                    return errorInvalid("Bạn không làm nhiệm vụ này");
                }
            } else {
                return errorInvalid("Không có nhiệm vụ này");
            }
            return processSuccess(null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onGetLogCollaboratorWork(int adminId) {
        String sql = "SELECT a.id, a.service_id,a.address,a.details,a.images,a.status,a.created" +
                " FROM collaborator_mission a" +
                " WHERE a.admin_id=" + adminId;
        return processSuccess(getListResultQuery(sql));
    }

    private Result onPolicyKtv() {
        Information info = Information.find.query().where().eq("type", 6).setMaxRows(1).findOne();
        Map<String, Object> map = new HashMap<>();
        String policy = "Text điều khoản sử dụng";
        if (info != null) {
            policy = info.content;
        }
        map.put("policy", policy);
        return processSuccess(map);
    }

    private Result onUpdateAddressAdmin(Admin admin, JsonNode req) {
        try {
            String province = req.get("province").asText();
            String district = req.get("district").asText();
            String ward = req.get("ward").asText();
            String address = req.get("address").asText();
            String address_id = req.get("address_id").asText();
            admin.province = province;
            admin.district = district;
            admin.ward = ward;
            admin.address = address;
            String[] arrAddress = address_id.split("_");
            admin.province_id = arrAddress[0];
            admin.district_id = arrAddress[1];
            admin.ward_id = arrAddress[2];
            admin.update();

            admin.password = "";
            return processSuccess(admin);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onUpdateCartWaitBuyAdmin(int adminId, JsonNode req) {
        try {
            String info_cart_wait_buy = req.get("info_cart_wait_buy").asText();
            AdminCart cart = AdminCart.find.query().where()
                    .eq("admin_id", adminId).findOne();
            if (cart != null) {
                cart.info_cart_wait_buy = info_cart_wait_buy;
                cart.time_change = Utils.getTimeStamp();
                cart.update();
            } else {
                cart = new AdminCart(adminId, "[]", Utils.getTimeStamp());
                cart.info_cart_wait_buy = info_cart_wait_buy;
                cart.save();
            }
            return processSuccess(null);
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onInfoAppKtv(int device, JsonNode req) {
        try {
            String version = req.get("version").asText();
            AppInfo info = AppInfo.find.query().where().eq("version", version).eq("provider_id", device).setMaxRows(1).findOne();
            if (info != null) {
                return processSuccess(info);
            } else {
                return errorInvalid("Không có thông tin AppInfo");
            }
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onUpdateNotifySystemKtv(int adminId, JsonNode req) {
        try {
            String notify = req.get("notify").asText();
            ReadNotifySystem noti = ReadNotifySystem.find.query().where()
                    .eq("type_account", S.TYPE_ACCOUNT_KTV)
                    .eq("account_id", adminId).setMaxRows(1).findOne();
            if (noti != null) {
                noti.notify_system = notify;
                noti.update();
            } else {
                noti = new ReadNotifySystem(S.TYPE_ACCOUNT_KTV, adminId, notify);
                noti.save();
            }
            return processSuccess("Cập nhật trạng thái thành công");
        } catch (NullPointerException e) {
            return errorInvalidBodyData();
        }
    }

    private Result onSwitchBoardCareKtv() {
        Information info = Information.find.query().where().eq("type", 5).setMaxRows(1).findOne();
        if (info != null) {
            return processSuccess(info);
        } else {
            return errorInvalid("Không có thông tin chăm sóc khách hàng");
        }
    }

}
