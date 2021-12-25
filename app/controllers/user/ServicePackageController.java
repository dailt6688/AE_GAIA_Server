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
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicePackageController extends Controller {
    private ApiResponse res;
    private User user;
    private Admin admin;

    @Inject
    public ServicePackageController(User user) {
        this.user = user;
    }

    public ServicePackageController() {

    }

    public Result listPackage() {
        Date date = new Date();
        Long time = date.getTime() / 1000;
        List<ServicePackage> services = ServicePackage.find.query().where().eq("parent_id", "0").findList();
//        String sql =
//                "SELECT *, service_package_user.id as id  " +
//                        "FROM service_package_user " +
//                        "INNER JOIN service_package ON service_package_user.service_package_id = service_package.id " +
//                        "WHERE user_id = " + this.user.id + " AND start_time < " + time + " AND end_time > " + time;
//        SqlQuery query = Ebean.createSqlQuery(sql);
//        List<SqlRow> servicePackageUsers = query.findList();
        Map<String, Object> map = new HashMap<>();
        map.put("services", services);
//        map.put("servicePackageUsers", servicePackageUsers);
        res = new ApiResponse(Error.SUCCESS, "", map);
        return ok(Json.toJson(res));
    }


    public Result listPackageUser() {
        Date date = new Date();
        Long time = date.getTime() / 1000;
        String sql =
                "SELECT *, service_package_user.id as id  " +
                        "FROM service_package_user " +
                        "INNER JOIN service_package ON service_package_user.service_package_id = service_package.id " +
                        "WHERE user_id = " + this.user.id + " AND start_time < " + time + " AND end_time > " + time;
        SqlQuery query = Ebean.createSqlQuery(sql);
        List<SqlRow> servicePackageUsers = query.findList();
        Map<String, Object> map = new HashMap<>();
        map.put("servicePackageUsers", servicePackageUsers);
        res = new ApiResponse(Error.SUCCESS, "", map);
        return ok(Json.toJson(res));
    }

    public Result info() {
        try {
            JsonNode req = request().body().asJson();
            Integer parent_id = req.get("package_id").asInt();
            List<ServicePackage> services = ServicePackage.find.query().where().eq("parent_id", parent_id).findList();
            List<ServicePackageSquare> squares = ServicePackageSquare.find.all();
            List<ServicePackageTime> times = ServicePackageTime.find.all();
            Map<String, Object> map = new HashMap<>();
            map.put("services", services);
            map.put("squares", squares);
            map.put("times", times);
            res = new ApiResponse(Error.SUCCESS, "", map);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result userGetPackage(User user) {
        try {
            JsonNode req = request().body().asJson();
            Integer servicePackageId = req.get("service_package_id").asInt();
            String address = req.get("address").asText();
            Double latitude = req.get("latitude").asDouble();
            Double longitude = req.get("longitude").asDouble();
//            Long appointment_time = req.get("appointment_time").asLong();
            ServicePackage service = ServicePackage.find.byId(servicePackageId);
            if (service == null) {
                res = new ApiResponse(Error.ERROR, Error.SERVICE_PACKAGE_NOT_EXISTS, null);
            } else {
                long priceSaleOrigin = service.price - (long) ((float) service.price * service.sale);
                /*** Xử lý sự kiện ***/
                long timePlus = 0L;
                if (service.time_id == 2 || service.time_id == 3) {
                    if (service.time_id == 2) {
                        //goi 6 tháng
                        ProductGift productGift = ProductGift.find.byId(2);
                        if (productGift != null) {
                            long timeCurrent = Utils.getYearMonthDayGhepChuoiToLong(Utils.getTimeStamp());
                            long timeEventStart = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_start);
                            long timeEventStop = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_stop);
                            if (timeCurrent < timeEventStart) {
                            } else if (timeCurrent > timeEventStop) {
                            } else {
                                timePlus = Utils.getTotalSecondsPerDay() * 30L;
                                LogProductGiftUserUsed logG = new LogProductGiftUserUsed(2, user.id, Utils.getTimeStamp(), S.TRUE);
                                logG.save();

                                Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, "Khuyễn mãi", "Chúc mừng quý khách đã được tặng thêm 1 tháng sử dụng gói dịch vụ (" + service.name + ")", Utils.getTimeStamp());
                                notify.save();
                            }
                        }
                    } else if (service.time_id == 3) {
                        //goi 12 thang
                        ProductGift productGift = ProductGift.find.byId(3);
                        if (productGift != null) {
                            long timeCurrent = Utils.getYearMonthDayGhepChuoiToLong(Utils.getTimeStamp());
                            long timeEventStart = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_start);
                            long timeEventStop = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_stop);
                            if (timeCurrent < timeEventStart) {
                            } else if (timeCurrent > timeEventStop) {
                            } else {
                                timePlus = Utils.getTotalSecondsPerDay() * 60L;
                                LogProductGiftUserUsed logG = new LogProductGiftUserUsed(3, user.id, Utils.getTimeStamp(), S.TRUE);
                                logG.save();

                                Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, "Khuyễn mãi", "Chúc mừng quý khách đã được tặng thêm 2 tháng sử dụng gói dịch vụ (" + service.name + ")", Utils.getTimeStamp());
                                notify.save();
                            }
                        }
                    }
                }

                ProductGift productGift = ProductGift.find.byId(4);//Khuyến mãi giảm 50% gói cước cho 100 khách hàng đầu tiên
                if (productGift != null) {
                    long timeCurrent = Utils.getYearMonthDayGhepChuoiToLong(Utils.getTimeStamp());
                    long timeEventStart = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_start);
                    long timeEventStop = Utils.getYearMonthDayGhepChuoiToLong(productGift.time_event_stop);
                    if (timeCurrent < timeEventStart) {
                    } else if (timeCurrent > timeEventStop) {
                    } else {
                        String sql = "SELECT COUNT(*) AS number FROM service_package_user WHERE service_package_id !=44 AND service_package_id !=45";
                        JsonNode dt2 = Json.toJson(RawQuery.getResultQuery(sql));
                        long number2 = dt2.get("number").asLong();
                        if (number2 < 100) {//Khuyen mai
                            LogProductGiftUserUsed logGift = LogProductGiftUserUsed.find.query().where()
                                    .eq("product_gift_id", 4)
                                    .eq("user_id", user.id).findOne();
                            if (logGift == null) {
                                long priceSale = (int) (double) (priceSaleOrigin * 0.5);
                                if (user.balance >= priceSale) {
                                    ServicePackageTime s_time = ServicePackageTime.find.byId(service.time_id);
                                    Date date = new Date();
                                    Long startTime = date.getTime() / 1000;
                                    Long endTime = startTime + s_time.time + timePlus;
                                    ServicePackageUser servicePackageUser = new ServicePackageUser(this.user.id, servicePackageId, startTime, endTime,
                                            address, latitude, longitude, user.province_id, user.district_id, user.ward_id);
                                    servicePackageUser.save();
                                    user.balance -= priceSale;
                                    user.save();

                                    LogBuyServicePackageUser logBuy = new LogBuyServicePackageUser(user.id, service.id, Utils.getTimeStamp());
                                    logBuy.save();

                                    TransactionHistoryUser logTransaction = new TransactionHistoryUser(user.id, S.LOG_MUA_GOI_DICH_VU, logBuy.id, S.TRUE, -priceSale, service.name);
                                    logTransaction.save();

                                    logGift = new LogProductGiftUserUsed(4, user.id, Utils.getTimeStamp(), S.TRUE);
                                    logGift.save();

                                    Notify notify = new Notify(S.SENDER, S.TYPE_ACCOUNT_USER, user.id, "Khuyễn mãi", "Chúc mừng quý khách đã được giảm 50% gói dịch vụ, tương đương với: " + Utils.formatMoney(priceSale) + S.MONEY_NAME + ". Số" + S.MONEY_NAME + " đã trừ trực tiếp vào đơn hàng", Utils.getTimeStamp());
                                    notify.save();

                                    res = new ApiResponse(Error.SUCCESS, "Chúc mừng quý khách được giảm 50% gói dịch vụ", user.balance);
                                } else {
                                    res = new ApiResponse(Error.ERROR, Error.KHONG_DU_TIEN, null);
                                }

                                return ok(Json.toJson(res));
                            }
                        }

                        LogProductGiftUserUsed logG = new LogProductGiftUserUsed(4, user.id, Utils.getTimeStamp(), S.TRUE);
                        logG.save();
                    }
                }

                /*** End Xử lý sự kiện ***/

                if (user.balance >= priceSaleOrigin) {
                    ServicePackageTime s_time = ServicePackageTime.find.byId(service.time_id);
                    Date date = new Date();
                    Long startTime = date.getTime() / 1000;
                    Long endTime = startTime + s_time.time + timePlus;
                    ServicePackageUser servicePackageUser = new ServicePackageUser(this.user.id, servicePackageId, startTime, endTime,
                            address, latitude, longitude, user.province_id, user.district_id, user.ward_id);
                    servicePackageUser.save();
                    user.balance -= priceSaleOrigin;
                    user.save();

                    LogBuyServicePackageUser logBuy = new LogBuyServicePackageUser(user.id, service.id, Utils.getTimeStamp());
                    logBuy.save();

                    TransactionHistoryUser logTransaction = new TransactionHistoryUser(user.id, S.LOG_MUA_GOI_DICH_VU, logBuy.id, S.TRUE, -priceSaleOrigin, service.name);
                    logTransaction.save();

                    res = new ApiResponse(Error.SUCCESS, "", user.balance);
                } else {
                    res = new ApiResponse(Error.ERROR, Error.KHONG_DU_TIEN, null);
                }
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result getListAppliance() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_user_id = req.get("service_package_user_id").asInt();
            List<Appliance> appliances = Appliance.find.query().where()
                    .eq("service_package_user_id", service_package_user_id)
                    .findList();
            ServicePackageUser servicePackageUser = ServicePackageUser.find.byId(service_package_user_id);
            Map<String, Object> map = new HashMap<>();
            map.put("list_appliance", appliances);
            map.put("service_package_user", servicePackageUser);
            res = new ApiResponse(Error.SUCCESS, "", map);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

//    public Result adminAddAppliance(){
//        ApiResponse res;
//        try {
//            JsonNode req = request().body().asJson();
//            Integer user_id = req.get("user_id").asInt();
//            String name = req.get("name").asText();
//            String manufacturer = req.get("manufacturer").asText();
//            String model = req.get("model").asText();
//            Integer quantity = req.get("quantity").asInt();
//            Integer service_package_user_id = req.get("service_package_user_id").asInt();
//            Integer type = req.get("type").asInt();
//            String token = req.get("token").asText();
//            UserBean user = UserBean.find.byId(user_id);
//            Boolean checkToken = JWTUntils.validateJWT(token, user);
//            if(user == null){
//                res = new ApiResponse(Error.ERROR, Error.USER_NOT_EXIST, null);
//            }
//            else if(!checkToken){
//                res = new ApiResponse(Error.INVALID_TOKEN, "", null);
//            }
//            else{
//                Date date = new Date();
//                Long time = date.getTime()/1000;
//                Appliance appliance = new Appliance(name, manufacturer, model, quantity, time, type, service_package_user_id, 2);
//                appliance.save();
//                List<Appliance> appliances = Appliance.find.query().where().eq("service_package_user_id", service_package_user_id).findList();
//                Map<String,Object> map = new HashMap<>();
//                map.put("data", appliances);
//                res = new ApiResponse(Error.SUCCESS, "", map);
//            }
//            return ok(Json.toJson(res));
//        } catch (NullPointerException e) {
//            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
//            return ok(Json.toJson(res));
//        }
//    }

    public Result userAddAppliance() {
        try {
            JsonNode req = request().body().asJson();
            String name = req.get("name").asText();
            String manufacturer = req.get("manufacturer").asText();
            Integer quantity = req.get("quantity").asInt();
            Integer service_package_user_id = req.get("service_package_user_id").asInt();
            Date date = new Date();
            Long time = date.getTime() / 1000;
            Appliance appliance = new Appliance(name, manufacturer, "", quantity, time, 0, service_package_user_id, 1);
            appliance.save();
            List<Appliance> appliances = Appliance.find.query().where().eq("service_package_user_id", service_package_user_id).findList();
            Map<String, Object> map = new HashMap<>();
            map.put("data", appliances);
            res = new ApiResponse(Error.SUCCESS, "", map);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result userDeleteAppliance() {
        try {
            JsonNode req = request().body().asJson();
            Integer appliance_id = req.get("appliance_id").asInt();
            Appliance appliance = Appliance.find.byId(appliance_id);
            if (appliance == null) {
                res = new ApiResponse(Error.ERROR, Error.APPLIANCE_NOT_EXISTS, null);
            } else {
                ServicePackageUser servicePackageUser = ServicePackageUser.find.byId(appliance.service_package_user_id);
                if (servicePackageUser.user_id != this.user.id || appliance.status != 1) {
                    res = new ApiResponse(Error.ERROR, Error.NOT_PERMISSION_DELETE_APPLIANCE, null);
                } else {
                    appliance.delete();
                    res = new ApiResponse(Error.SUCCESS, "", null);
                }
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result appointmentTime() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_user_id = req.get("service_package_user_id").asInt();
            Long time = req.get("appointment_time").asLong();
            Integer number = req.get("number").asInt();
            ServicePackageUser packageUser = ServicePackageUser.find.byId(service_package_user_id);
            if (packageUser != null) {
                if (time < packageUser.start_time || time > packageUser.end_time) {
                    res = new ApiResponse(Error.ERROR, "Thời gian đặt lịch bảo trì không hợp lệ", null);
                } else {
                    ServicePackageMaintenance servicePackageMaintenance = ServicePackageMaintenance.find.query().where()
                            .eq("service_package_user_id", service_package_user_id)
                            .eq("number", number).findOne();
                    if (servicePackageMaintenance == null) {
                        String des = "Bảo dưỡng thiết bị lần " + number;
                        ServicePackageMaintenance s = new ServicePackageMaintenance(service_package_user_id, des, 1, time, "", number, "");
                        s.save();
                        NodeJs.getInstance().emitNewMaintenance(s.id);
                        res = new ApiResponse(Error.SUCCESS, "", null);
                    } else if (servicePackageMaintenance.admin_id.length() > 0) {
                        res = new ApiResponse(Error.ERROR, Error.NOT_CHANGE_SCHEDULE, null);
                    } else {
                        servicePackageMaintenance.time = time;
                        servicePackageMaintenance.save();
                        NodeJs.getInstance().emitChangeMaintenance(servicePackageMaintenance.id);
                        res = new ApiResponse(Error.SUCCESS, "", null);
                    }
                }
            } else {
                res = new ApiResponse(Error.ERROR, "Quý khách không có gói bảo trì này", null);
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    Result MaintenanceSchedule() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_user_id = req.get("service_package_user_id").asInt();

            List<ServicePackageMaintenance> maintenance_shedule = ServicePackageMaintenance
                    .find.query().where().eq("service_package_user_id", service_package_user_id).findList();
            Map<String, Object> map = new HashMap<>();
            map.put("maintenance_shedule", maintenance_shedule);
            res = new ApiResponse(Error.SUCCESS, "", map);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    Result ConfirmMetPartner() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_maintenance_id = req.get("service_package_maintenance_id").asInt();
            ServicePackageMaintenance maintenance = ServicePackageMaintenance.find.byId(service_package_maintenance_id);
            if (maintenance == null) {
                res = new ApiResponse(Error.ERROR, Error.MAINTENANCE_NOT_EXISTS, null);
            } else {
                maintenance.status = StatusJob.XAC_NHAN_KTV_DEN;
                maintenance.save();
                res = new ApiResponse(Error.SUCCESS, "", null);
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    Result CancelMaintenance() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_maintenance_id = req.get("service_package_maintenance_id").asInt();
            ServicePackageMaintenance maintenance = ServicePackageMaintenance.find.byId(service_package_maintenance_id);
            if (maintenance == null) {
                res = new ApiResponse(Error.ERROR, Error.MAINTENANCE_NOT_EXISTS, null);
            } else {
                maintenance.status = StatusJob.BO_QUA;
                maintenance.save();
                res = new ApiResponse(Error.SUCCESS, "", null);
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    Result GetListMaintenanceCurrent() {
//        Long startOfDay = Utils.getTimeMillisStartOfDay();
//        Long endOfDay = startOfDay + 86400;
        String sql = "SELECT service_package_maintenance.*, service_package_user.*, service_package_maintenance.id as id " +
                "FROM service_package_maintenance " +
                "INNER JOIN service_package_user ON service_package_maintenance.service_package_user_id = service_package_user.id " +
                "WHERE service_package_user.user_id = " + user.id + " AND (service_package_maintenance.status = 2 OR service_package_maintenance.status = 3)";
        SqlQuery query = Ebean.createSqlQuery(sql);
        List<SqlRow> list_maintenance_today = query.findList();
        res = new ApiResponse(Error.SUCCESS, "", list_maintenance_today);
        return ok(Json.toJson(res));
    }

    Result onGetInfoServicePackageUserByMaintenanceId() {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_maintenance_id = req.get("service_package_maintenance_id").asInt();
            String sql = "SELECT service_package_user.*, service_package_maintenance.status, service_package_maintenance.admin_id " +
                    "FROM service_package_maintenance " +
                    "INNER JOIN service_package_user ON service_package_maintenance.service_package_user_id = service_package_user.id " +
                    "WHERE service_package_maintenance.id = " + service_package_maintenance_id + " AND service_package_user.user_id = " + user.id;
            SqlQuery query = Ebean.createSqlQuery(sql);
            List<SqlRow> info_service_package_user = query.findList();
            res = new ApiResponse(Error.SUCCESS, "", info_service_package_user);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }

    }

    public Result changeServicePackageAddress(JsonNode req){
        try {
            int service_package_user_id = req.get("service_package_user_id").asInt();
            String province_id = req.get("province_id").asText();
            String district_id = req.get("district_id").asText();
            String ward_id = req.get("ward_id").asText();
            Double latitude = req.get("latitude").asDouble();
            Double longitude = req.get("longitude").asDouble();
            String address = req.get("address").asText();
            Integer confirm_by = 0;
            ServicePackageTempAddress s = ServicePackageTempAddress.find.query().where().eq("status", 0).eq("service_package_user_id", service_package_user_id).findOne();
            if(s == null){
                ServicePackageTempAddress servicePackageTempAddress =
                        new ServicePackageTempAddress(address, province_id, district_id, ward_id, latitude, longitude, service_package_user_id, 0, 1, confirm_by);
                servicePackageTempAddress.save();
            }
            else{
                s.province_id = province_id;
                s.district_id = district_id;
                s.ward_id = ward_id;
                s.latitude = latitude;
                s.longitude = longitude;
                s.address = address;
                s.update();
            }
            res = new ApiResponse(Error.SUCCESS, "", null);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result checkWaitingConfirmServicePackage(JsonNode req){
        try {
            int service_package_user_id = req.get("service_package_user_id").asInt();
            ServicePackageTempAddress spta = ServicePackageTempAddress.find.query().where().eq("status", 0).eq("service_package_user_id", service_package_user_id).findOne();
            res = new ApiResponse(Error.SUCCESS, "", spta);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    public Result adminGetListUserChangeSercivePackageAddress(JsonNode req){
        res = new ApiResponse(Error.SUCCESS, "", null);
        return ok(Json.toJson(res));
    }

    public Result partnerSaveServicePackageUserAddress(Admin admin, JsonNode req){
        try {
            int service_package_user_id = req.get("service_package_user_id").asInt();
            double latitude = req.get("latitude").asDouble();
            double longitude = req.get("longitude").asDouble();
            ServicePackageTempAddress spta = ServicePackageTempAddress.find.query().where().eq("service_package_user_id", service_package_user_id).lt("status", 2).findOne();
            if(spta == null){
                spta = new ServicePackageTempAddress("", "", "", "", latitude, longitude, service_package_user_id, 1, 2, admin.id);
                spta.save();
            }
            else{
                spta.latitude = latitude;
                spta.longitude = longitude;
                spta.confirm_by = admin.id;
                spta.update();
            }
            res = new ApiResponse(Error.SUCCESS, "", null);
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }
}
