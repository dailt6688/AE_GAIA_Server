package controllers.cms;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.ConfigException;
import helper.*;
import helper.Error;
import nodeJs.NodeJs;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class CmsController extends Controller {
    public Result cmsController() {
        ApiResponse res = null;
        try {
            if (Config.IP_SERVER.contains(request().remoteAddress())) {
                JsonNode req = request().body().asJson();
                int cmd = req.get(Key.CMD).asInt();

                switch (cmd) {
                    case Cmd.CMS_PUSH_FIREBASE:
                        return onPushFirebase(req, res);
                    case Cmd.CMS_EMIT_UPDATE_MONEY:
                        return onEmitUpdateMoneyUser(req, res);
                    case Cmd.CMS_EMIT_LOCK_ACCOUNT:
                        return onEmitLockAccount(req, res);
                    case Cmd.CMS_EMIT_SERVER_NOTIFY:
                        return onEmitServerNotify(req, res);

                    default:
                        return errorInvalid(res, Error.CMD_NOT_EXISTS);
                }
            } else {
                return errorInvalid(res, "Thông tin truy cập của bạn đã được lưu lại phục vụ quá trình điều tra");
            }
        } catch (ConfigException.Null e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result errorInvalidBodyData(ApiResponse res) {
        res = new ApiResponse(Error.ERROR, Error.INVALID_BODY_DATA, null);
        return ok(Json.toJson(res));
    }

    private Result errorInvalid(ApiResponse res, String errorMessage) {
        res = new ApiResponse(Error.ERROR, errorMessage, null);
        return ok(Json.toJson(res));
    }

    private Result processSuccess(ApiResponse res, Object dataResponse) {
        res = new ApiResponse(Error.SUCCESS, "", dataResponse);
        return ok(Json.toJson(res));
    }

    private Result onPushFirebase(JsonNode req, ApiResponse res) {
        try {
            String tokenId = req.get("tokenId").asText();
            String title = req.get("title").asText();
            String body = req.get("body").asText();
            PushNotification.getInstance().SendOneDevice(tokenId, title, body);
            return processSuccess(res, "Send require Push Firebase success");
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onEmitUpdateMoneyUser(JsonNode req, ApiResponse res) {
        try {
            int account_id = req.get("account_id").asInt();
            int type_account_ben_nodeJs = req.get("type_account_ben_nodeJs").asInt();
            NodeJs.getInstance().emitUpdateMoney(type_account_ben_nodeJs, account_id);
            return processSuccess(res, "Send require update money success");
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onEmitLockAccount(JsonNode req, ApiResponse res) {
        try {
            int account_id = req.get("account_id").asInt();
            int type_account_ben_nodeJs = req.get("type_account_ben_nodeJs").asInt();
            NodeJs.getInstance().emitLockAccount(type_account_ben_nodeJs, account_id);
            return processSuccess(res, "Send require lock account success");
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }

    private Result onEmitServerNotify(JsonNode req, ApiResponse res) {
        try {
            int account_id = req.get("account_id").asInt();
            int type_account_ben_nodeJs = req.get("type_account_ben_nodeJs").asInt();
            String message = req.get("message").asText();
            NodeJs.getInstance().emitServerPush(type_account_ben_nodeJs, account_id, message);
            return processSuccess(res, "Send require server notify success");
        } catch (NullPointerException e) {
            return errorInvalidBodyData(res);
        }
    }
}
