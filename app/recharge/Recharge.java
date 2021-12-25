package recharge;

import helper.Config;
import helper.S;
import helper.Utils;
import models.BankInfo;
import models.LogRechargeEpay;
import models.LogRechargeNganLuong;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import recharge.epay.Reponse;
import recharge.epay.ServiceSoap12Stub;
import recharge.nganluong.ResponseCheck;
import recharge.nganluong.ResponseSend;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Recharge {
    // HTTP POST request
    public static String card(String url, String params) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-type", "text/html");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        // System.out.println("\nSending 'POST' request to URL : " + url);
        // System.out.println("Post parameters : " + params);
        System.out.println("\nResponseSend Code Charge Card: " + responseCode + "\n");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static Reponse depositeBankUser(int typeCard, int currencyCode, User user, BankInfo bankInfo, long tienNap) throws Exception {
        URL url = new URL(Config.EPAY_LINK_URL_RECHARGE);
        ServiceSoap12Stub srv = new ServiceSoap12Stub(url, null);
        String merchantId = Config.EPAY_MERCHANT_CODE;
        long timeStamp = Utils.getCurrentMiniseconds();
        String stan = "" + timeStamp;
        String termTxnDatetime = getTimetransaction();
        String txnAmount = "" + tienNap;
        String fee = "0";
        String userName = "";
        if (typeCard == S.TYPE_BANK_LOCAL_ATM) {
            userName = user.username;
        } else if (typeCard == S.TYPE_BANK_GLOBAL) {
            String ho = "";
            String ten = "";
            if (user.fullname != null && user.fullname.trim().length() > 0) {
                String[] arr = user.fullname.split(" ");
                if (arr.length == 3) {
                    ho = arr[0];
                    ten = arr[2];
                } else if (arr.length == 2) {
                    ho = arr[0];
                    ten = arr[1];
                } else {
                    ho = arr[0];
                    ten = arr[0];
                }
            }
            String email = user.email;
            String phone = user.phone;
            String currency = "";
            String locate = "";
            if (currencyCode == S.TYPE_CARD_GLOBAL_VND) {
                currency = "VND";
                locate = "vi-VN";
            } else if (currencyCode == S.TYPE_CARD_GLOBAL_USD) {
                currency = "USD";
                locate = "en-us";
            }

            userName = "[" + ho + "]|[" + ten + "]|[" + email + "]|[" + phone + "]|[" + user.username + "]|[" + currency + "]|[" + locate + "]";
        }
        String issuerID = Config.EPAY_ISSUER_ID;
        String tranID = Config.EPAY_MERCHANT_CODE + "_" + user.username + "_" + timeStamp;
        String bankID = bankInfo.code;
        String respUrl = Config.EPAY_RESPONSE_LINK;
        String key = Config.EPAY_MERCHANT_SEND_KEY;//day la merchant_send_key gui trong tai lieu
        String temp = merchantId + stan + termTxnDatetime + txnAmount + fee + userName + issuerID + tranID + bankID + respUrl;
        StringBuilder tmp_send = new StringBuilder();
        tmp_send.append(merchantId).append("_").append(stan).append("_").append(termTxnDatetime).append("_")
                .append(txnAmount).append("_").append(fee).append("_").append(userName).append("_").append(issuerID)
                .append("_").append(tranID).append("_").append(bankID);
        String mac = DESMAC(temp, key);

        Reponse reponse = srv.deposit(merchantId, stan, termTxnDatetime, txnAmount, fee, userName, issuerID, tranID, bankID, mac, respUrl);
//        System.out.println("ResponseSend code bank :" + reponse.getResponsecode());
//        System.out.println("Mo ta:" + reponse.getDescriptionvn());
        LogRechargeEpay logCharge = new LogRechargeEpay(typeCard, S.TYPE_ACCOUNT_USER, user.id, tienNap, fee, userName
                , Config.EPAY_ISSUER_ID, tranID, bankInfo.code, bankInfo.bank_name, tmp_send.toString(), mac, reponse.getResponsecode(), reponse.getDescriptionvn(), reponse.getUrl(), Utils.getTimeStamp());
        logCharge.save();

        return reponse;
    }

    public static Reponse confirmBank(String transactionId, String responseCode, long tienNap) throws Exception {
        URL url = new URL(Config.EPAY_LINK_URL_RECHARGE);
        ServiceSoap12Stub srv = new ServiceSoap12Stub(url, null);
        String merchantCode = Config.EPAY_MERCHANT_CODE;
        String tranId = transactionId;
        String txnAmount = "" + tienNap;
        String confirmCode = responseCode;
        String key = Config.EPAY_MERCHANT_SEND_KEY;//day la merchant_send_key gui trong tai lieu
        String temp = merchantCode + tranId + txnAmount + confirmCode;
        String macKey = DESMAC(temp, key);
        Reponse response = srv.comfirm(merchantCode, tranId, txnAmount, confirmCode, macKey);
//        System.out.println("ResponseSend confirm bank :" + response.getResponsecode());
//        System.out.println("Mo ta:" + response.getDescriptionvn());
        return response;
    }

    public static String getTimetransaction() {
        String result = "";
        String[] timeStamp = Utils.getCurrentTime().split(" ");
        String date = timeStamp[0].replace("-", "");
        String time = timeStamp[1].split("\\.")[0].replace(":", "");
        result = date + time;
        return result;
    }

    public static String checkDESMAC(String tranid, String reponseCode, String merchantKeyReceive) {
        try {
            return DESMAC(tranid + reponseCode, merchantKeyReceive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILURE";
    }

    private static String DESMAC(String msg, String key) throws Exception {
        String salt = "hywebpg5";
        String mode = "ECB";// "CBC"
        String padding = "NoPadding";// "PKCS5Padding";
        String type = "DESede";
        SecretKeySpec skey = getKey(key, type);

        msg = sha1(msg);
        byte[] mgsByte = macPadding(msg);

        String encMode = type + "/" + mode + "/" + padding;
        Cipher cipher = Cipher.getInstance(encMode);
        IvParameterSpec ivspec = new IvParameterSpec(salt.getBytes());
        if ("CBC".equals(mode)) {
            cipher.init(Cipher.ENCRYPT_MODE, skey, ivspec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, skey);
        }
        byte[] cipherText = cipher.doFinal(mgsByte);

        return byte2hex(cipherText);
    }


    private static SecretKeySpec getKey(String keys, String mode) {
        SecretKeySpec pass = new SecretKeySpec(keys.getBytes(), mode);
        return pass;
    }


    private static byte[] macPadding(String str) {
        // byte[] oldByteArray = ISOUtil.hex2byte(str);
        // byte[] oldByteArray = hex2packbyte(str);
        byte[] oldByteArray = hexStringToByteArray(str);
        int numberToPad = 8 - oldByteArray.length % 8;
        byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
        System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
        for (int i = oldByteArray.length; i < newByteArray.length; ++i) {
            newByteArray[i] = 0;
        }
        return newByteArray;
    }


    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(
                    s.charAt(i + 1), 16));
        }
        return data;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < (b.length - 1)) {
                hs = hs + "";
            }
        }
        return hs.toUpperCase();
    }


    private static String sha1(String data) throws Exception {
        // if(internetloyalty.util.Utility.isStringNonEmpty(data))
        {
            // byte[] dataByteArray = ISOUtil.hex2byte(data);
            try {
                return DigestUtils.shaHex(data).toUpperCase();
                //
                // MessageDigest algorithm = MessageDigest.getInstance("SHA-1");
                // algorithm.update(dataByteArray); //set input
                // byte[] digest = algorithm.digest(); //generate digest
                //
                // return //ISOUtil.hexString(digest);
            } catch (Exception ex) {
                throw new Exception("SHA-1: " + ex.toString());
            }
        }
    }

    /*** BAO KIM ***/
    public static ResponseSend GetUrlCheckoutNganLuong(int typeBank, String cur_code, String totalAmount, String payment_method
            , BankInfo bankInfo, User user, String fullname, String email, String mobile) throws Exception {
        String request = "";

        //create params
        request += "function=SetExpressCheckout";
        request += "&cur_code=" + cur_code;
        request += "&version=3.1";
        request += "&merchant_id=" + Config.NL_MERCHANT_ID_SEND;
        request += "&receiver_email=" + Config.NL_RECEIVER_EMAIL;
        request += "&merchant_password=" + CreateMD5Hash(Config.NL_MERCHANT_PASSWORD_SEND);
        String transaction_id = Config.NL_MERCHANT_ID_SEND + "_" + user.username + "_" + Utils.getTotalSecondsPerDay();
        request += "&order_code=" + transaction_id;
        request += "&total_amount=" + totalAmount;
        request += "&payment_method=" + payment_method;
        request += "&bank_code=" + bankInfo.code;
        request += "&payment_type=1";
        request += "&order_description=" + "nap_" + totalAmount + "vao_app_DiVu";
        request += "&tax_amount=0";
        request += "&fee_shipping=0";
        request += "&discount_amount=0";
        request += "&return_url=" + Config.NL_RETURN_URL;
        request += "&cancel_url=" + Config.NL_CANCEL_URL;
        request += "&buyer_fullname=" + fullname;
        request += "&buyer_email=" + email;
        request += "&buyer_mobile=" + mobile;

        //send
        URL obj = null;
        try {
            obj = new URL(Config.NL_URL_RECHARGE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(request);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            // System.out.println("\nSending 'POST' request to URL : " + url);
            // System.out.println("Post parameters : " + params);
//            System.out.println("\nResponse Code Charge Ngan Luong: " + responseCode + "\n");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            ResponseSend result = new ResponseSend(response.toString());
            LogRechargeNganLuong log = new LogRechargeNganLuong(typeBank, S.TYPE_ACCOUNT_USER, user.id, Integer.parseInt(totalAmount), "0",
                    user.username, Config.NL_MERCHANT_ID_SEND, transaction_id, bankInfo.code, bankInfo.bank_name, request, result.getError_code(),
                    result.getDescription(), result.getCheckout_url(), result.getToken(), Utils.getTimeStamp());
            log.save();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResponseCheck GetTransactionDetail(String token) throws Exception {
        String request = "";
        request += "function=GetTransactionDetail";
        request += "&version=3.1";
        request += "&merchant_id=" + Config.NL_MERCHANT_ID_CHECK;
        request += "&merchant_password=" + CreateMD5Hash(Config.NL_MERCHANT_PASSWORD_CHECK);
        request += "&token=" + token;

        //send
        URL obj = null;
        try {
            obj = new URL(Config.NL_URL_RECHARGE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(request);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            // System.out.println("\nSending 'POST' request to URL : " + url);
            // System.out.println("Post parameters : " + params);
//            System.out.println("\nResponse Code Charge Ngan Luong: " + responseCode + "\n");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            ResponseCheck result = new ResponseCheck(response.toString());
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String CreateMD5Hash(String password) {
        return DigestUtils.md5Hex(password);
    }

    /*** END BAO KIM ***/
}
