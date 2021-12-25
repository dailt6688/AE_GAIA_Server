package controllers.user;

import com.fasterxml.jackson.databind.JsonNode;
import helper.*;
import helper.Error;
import models.*;
import nodeJs.NodeJs;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData.FilePart;
import server.GmailSend;

import javax.inject.Inject;
import java.io.File;
import java.util.*;

public class EmergencyController extends Controller {

    private User user;
    private ApiResponse res;

    @Inject
    public EmergencyController(User user) {
        this.user = user;
    }

    public Result uploadImages() {
        ApiResponse res;
        String user_id = request().getHeader(Key.USER_ID);
        String token = request().getHeader(Key.TOKEN);
        User user = User.find.byId(Integer.parseInt(user_id));
        int type_image = Integer.parseInt(request().getHeader(Key.TYPE_IMAGE));
        Boolean checkToken = JWTUntils.validateJWT(token, user);
        if (user == null) {
            res = new ApiResponse(Error.ERROR, Error.USER_NOT_EXIST, null);
        } else if (!checkToken) {
            res = new ApiResponse(Error.INVALID_TOKEN, "", null);
        } else {
            Http.MultipartFormData body = request().body().asMultipartFormData();
            FilePart imgPart = body.getFile("img");
            String fileName = imgPart.getFilename();
            String contentType = imgPart.getContentType();
            File file = (File) imgPart.getFile();
            File newFile;
            String img_url = "";
            if (type_image == S.EMERGENCY) {
                newFile = new File("public/images/emergency/" + fileName + ".jpg");
                img_url = S.EMERGENCY_FOLDER + fileName + ".jpg";
            } else {
                newFile = new File("public/images/cooperator/" + fileName + ".jpg");
                img_url = S.COOPERATOR_FOLDER + fileName + ".jpg";
            }
            file.renameTo(newFile);
            res = new ApiResponse(Error.SUCCESS, "", img_url);
        }
        return ok(Json.toJson(res));
    }

    public Result UserSendEmergency(User user) {
        try {
            JsonNode req = request().body().asJson();
            Integer service_package_user_id = req.get("service_package_user_id").asInt();
            String address = req.get("address").asText();
            Double latitude = req.get("latitude").asDouble();
            Double longitude = req.get("longitude").asDouble();
            String images = req.get("images").asText();
            String phone = req.get("phone").asText();
            String des = req.get("des").asText();

            //k cho goi cuu ho neu huy 5 lan/ngay
            long startOfDay = Utils.getTimeStartOfDay();
            Integer num = Emergency.find.query().where().eq("user_id", user.id).eq("status", 7).gt("time", startOfDay).findCount();
            if(num > 4){
                String end = Utils.timeStampToString(startOfDay + 86399999);
                res = new ApiResponse(Error.ERROR, "Tài khoản của Quý khách tạm thời bị khoá tính năng cứu hộ đến " + end + ". Lí do: Huỷ cứu hộ khẩn cấp quá số lần quy định trong ngày", null);
                return ok(Json.toJson(res));
            }
            ServicePackageUser service = ServicePackageUser.find.byId(service_package_user_id);
            if (service_package_user_id > 0 && service == null) {
                res = new ApiResponse(Error.ERROR, Error.SERVICE_PACKAGE_NOT_EXISTS, null);
            } else {
                Emergency emergency_check = Emergency.find.query().where().eq("user_id", user.id).lt("status", 4).findOne();
                if (emergency_check != null) {
                    res = new ApiResponse(Error.ERROR, Error.CANNOT_SEND_MULTIPLE_EMERGENCY, null);
                } else {
                    if(service_package_user_id == 0){
                        if(user.balance < 10000){
                            res = new ApiResponse(Error.ERROR, "Số dư của Quý khách phải lớn hơn 10.000 DIV", null);
                            return ok(Json.toJson(res));
                        }
                        else{
                            user.balance -= 10000;
                            user.update();
                        }
                    }
                    Emergency emergency = new Emergency(this.user.id, service_package_user_id, phone, des, address, latitude, longitude, images, 1, "");
                    emergency.save();
                    Map<String, Object> map = new HashMap<>();
                    map.put("emergency", emergency);
                    map.put("balance", user.balance);
                    res = new ApiResponse(Error.SUCCESS, "", map);

                    NodeJs.getInstance().emitNewEmergency(emergency.id);

                    if (user.province_id != null) {
                        List<Admin> lstAdmin = Admin.find.query().where().eq("level", S.LEVEL_MANAGER).eq("district_work_id", user.district_id).findList();
                        if (lstAdmin.size() > 0) {
                            List<String> lstToken = new ArrayList<>();
                            String body = "Có yêu cầu cứu hộ mới";
                            for (Admin a : lstAdmin) {
                                lstToken.add(a.fcm_token);
                                NodeJs.getInstance().emitDoiTruongNhanYeuCauCuuHo(S.TYPE_ACCOUNT_KTV, a.id);
                                GmailSend.sendGmail("Khách gọi cứu hộ", a.email, "Khách hàng mã số: " + user.id + " - " + user.fullname + " - gọi cứu hộ: " + emergency.des);
                            }
                            PushNotification.getInstance().SendListDevice(lstToken, body, body);
                        } else {
                            Utils.debug("Khong co quan ly o khu vuc ma tinh: " + user.province_id);
                        }
                    } else {
                        Utils.debug("Khach hang id: " + user.id + " chua cap nhat ma tinh");
                    }
//                    PushNotification.getInstance().
                }
            }
            return ok(Json.toJson(res));
        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }

    Result UserGetListEmergency() {
        Emergency emergency = Emergency.find.query().where().eq("user_id", user.id).lt("status", 5).findOne();
        Map<String, Object> map = new HashMap<>();
        map.put("emergency", emergency);
        res = new ApiResponse(Error.SUCCESS, "", map);
        return ok(Json.toJson(res));
    }

    Result onGetInfoEmergency() {
        try {
            JsonNode req = request().body().asJson();
            long emergency_id = req.get("emergency_id").asLong();
            Emergency emergency = Emergency.find.byId(emergency_id);
            res = new ApiResponse(Error.SUCCESS, "", emergency);
            return ok(Json.toJson(res));

        } catch (NullPointerException e) {
            res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
            return ok(Json.toJson(res));
        }
    }
}
