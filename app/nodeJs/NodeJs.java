package nodeJs;

import helper.S;
import helper.Utils;
import io.socket.emitter.Emitter;
import models.Admin;
import models.CcuLog;
import models.User;
import org.json.JSONObject;
import server.PlayerManager;

public class NodeJs {

    private static NodeJs instance = null;

    public static NodeJs getInstance() {
        if (instance == null) {
            instance = new NodeJs();
        }
        return instance;
    }

    protected SocketIO socketIO;

    public NodeJs() {
        socketIO = new SocketIO();

        socketIO.getSocket().on("GetInfoCCU", onGetInfoCcu);
        socketIO.getSocket().on("LogOut", onLogOut);
    }

    /*** Listen event ***/
    private Emitter.Listener onGetInfoCcu = new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
            try {
                JSONObject data = (JSONObject) objects[0];
                int ccu = data.getInt("ccu");
                int ccu_users = data.getInt("ccu_users");
                int ccu_ktv = data.getInt("ccu_ktv");
                int ccu_cskh = data.getInt("ccu_cskh");
                int ccu_ctv = data.getInt("ccu_ctv");
                String ccu_info = data.getString("ccu_info");
                String action_user = data.getString("action_user");

                String[] currentTime = Utils.getCurrentTime().split(" ");
                CcuLog log = new CcuLog();
                log.ccu = ccu;
                log.ccu_users = ccu_users;
                log.ccu_ktv = ccu_ktv;
                log.ccu_cskh = ccu_cskh;
                log.ccu_ctv = ccu_ctv;
                log.ccu_info = ccu_info;
                log.action_user = action_user;
                log.date = currentTime[0];
                log.time = currentTime[1];
                log.save();
                System.out.println("--> " + currentTime[1] + "    -  Khach hang: " + ccu_users + " - Admin: " + ccu_ktv + " - CSKH: " + ccu_cskh + " - Cong tac vien: " + ccu_ctv);
            } catch (Exception e) {
            }
        }
    };

    private Emitter.Listener onLogOut = new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
            try {
                JSONObject data = (JSONObject) objects[0];
                int type_account = data.getInt("type_account");
                int account_id = data.getInt("account_id");
                if (type_account == S.TYPE_ACCOUNT_USER) {
                    if (PlayerManager.mapUser.containsKey(account_id)) {
                        User user = User.find.byId(account_id);
                        if (user != null) {
                            PlayerManager.userLogout(user);
                        }
                    }
                } else {
                    if (type_account == S.TYPE_ACCOUNT_KTV || type_account == S.LEVEL_CTV) {
                        if (PlayerManager.mapAdmin.containsKey(account_id)) {
                            Admin admin = Admin.find.byId(account_id);
                            if (admin != null) {
                                PlayerManager.adminLogout(admin);
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    };
    /*** End Listen event ***/

    /*** Send event ***/
    private void emit(String command, Object data) {
        socketIO.getSocket().emit(command, data);
    }

    public void emitUpdateMoney(int typeAccountBenNodeJs, int accountId) {
        String a = "type_account:" + typeAccountBenNodeJs + ",user_id:" + accountId;
        emit("UpdateMoney", a);
    }

    public void emitLockAccount(int typeAccountBenNodeJs, int accountId) {
        String a = "type_account:" + typeAccountBenNodeJs + ",user_id:" + accountId;
        emit("LockAccount", a);
    }

    public void emitServer_CollaboratorGetInfoMission(int user_id, long collaborator_mission_id, String danh_sach_id_ctv) {
        String a = "user_id:" + user_id + "|collaborator_mission_id:" + collaborator_mission_id + "|danh_sach_id_ctv:" + danh_sach_id_ctv;
        emit("Server_CollaboratorGetInfoMission", a);
    }

    public void emitDoiTruongNhanYeuCauCuuHo(int typeAccountBenNodeJs, int accountId) {
        String a = "type_account:" + typeAccountBenNodeJs + ",user_id:" + accountId;
        emit("DoiTruongNhanYeuCauCuuHo", a);
    }

    public void emitServerPush(int typeAccountBenNodeJs, int accountId, String message) {
        String a = "type_account@" + typeAccountBenNodeJs + "|user_id@" + accountId + "|message@" + message;
        emit("ServerPush", a);
    }

    public void emitGetInfoCCU() {
        emit("GetInfoCCU", "");
    }

    public void emitReloadConfig() {
        emit("ReloadConfig", "");
    }

    public void emitShutdownServer() {
        emit("ShutdownServer", "");
    }

    public void emitCancelCallEmergency(long emergency_id) {
        emit("CancelCallEmergency", emergency_id);
    }

    public void emitCancelCallCollaborator(long collaborator_id) {
        emit("CancelCallCollaborator", collaborator_id);
    }

    public void emitNewEmergency(long emergency_id){
        emit("NewEmergency", emergency_id);
    }

    public void emitNewMaintenance(long maintenance_id){
        emit("NewMaintenance", maintenance_id);
    }

    public void emitChangeMaintenance(long maintenance_id){
        emit("ChangeMaintenance", maintenance_id);
    }

    public void emitUserUpdateAddress(long user_id, long userTempAddress_id){
        String str = user_id + "," + userTempAddress_id;
        emit("UserUpdateAddress", str);
    }

    public void emitUserPaymentOrder(long user_id, long logPaymentCartUser_id){
        String str = user_id + "," + logPaymentCartUser_id;
        emit("UserPaymentOrder", str);
    }

    public void emitPartnerPaymentOrder(long admin_id, long logPaymentCartAdmin_id){
        String str = admin_id + "," + logPaymentCartAdmin_id;
        emit("PartnerPaymentOrder", str);
    }

    /*** Send event ***/

}
