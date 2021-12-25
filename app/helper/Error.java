package helper;

public interface Error {
    int SUCCESS = 1;
    int ERROR = 2;
    int INVALID_TOKEN = 3;
    String INVALID_BODY_DATA = "Dữ liệu không hợp lệ, vui lòng cập nhật phiên bản mới hoặc liên hệ số điện thoại chăm sóc khách hàng " + S.HOTLINE;
    String USER_EXIST = "Tài khoản đã tồn tại!";
    String USER_NOT_EXIST = "Tài khoản không tồn tại!";
    String INVALID_ACCOUNT = "Tên đăng nhập hoặc mật khấu không đúng!";
    String INVALID_PASSWORD = "Mật khẩu không đúng!";
    String SERVICE_PACKAGE_NOT_EXISTS = "Gói dịch vụ không tồn tại!";
    String CANNOT_SEND_MULTIPLE_EMERGENCY = "Quý khách không thể gửi đồng thời nhiều yêu cầu cứu hộ!";
    String INVALID_FORM_DATA = "Dữ liệu không đúng định dạng!";
    String NOT_PERMISSION_DELETE_APPLIANCE = "Không thể xoá thiết bị này!";
    String APPLIANCE_NOT_EXISTS = "Thiết bị không tồn tại!";
    String NOT_PERMISSION = "Bạn không có quyền hạn này!";
    String CMD_NOT_EXISTS = "Mã lệnh không tồn tại!";
    String CMD_NOT_EXECUTE = "Yêu cầu không được thực hiện!";
    String MAINTENANCE_NOT_EXISTS = "Lịch bảo dưỡng không tồn tại!";
    String NOT_CHANGE_SCHEDULE = "Không thể thay đổi được lịch!";
    String KHONG_CO_MISSION_BAO_TRI_NAY = "Không có nhiệm vụ bảo trì này";
    String KHONG_CO_KTV_BAO_TRI_MISSION_NAY = "Không có KTV bảo trì nhiệm vụ này";
    String KHONG_CO_KTV_NAY = "Không có KTV này";
    String KHONG_DU_TIEN = "Quý khách không đủ tiền để mua gói dịch vụ này";
    String EMERGENCY_NOT_EXIST = "Yêu cầu cứu hộ không tồn tại";
    String KHONG_CO_EMERGENCY_BAO_TRI_NAY = "Không có nhiệm vụ cứu hộ này";
    String KHONG_CO_KTV_EMERGENCY_MISSION_NAY = "Không có KTV cứu hộ nhiệm vụ này";
    String LOCK_ACCOUNT = "Tài khoản của bạn đã bị khóa";

}
