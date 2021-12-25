package recharge.epay;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;


public class Test {

    public static void main(String[] args) {

//        String url_Hue = "http://113.164.227.19:8015/service.asmx?wsdl";
//        try {
//            Hue_testDeposite(url_Hue);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }


    public static void Hue_testDeposite(String Strurl) throws Exception {
        URL url = new URL(Strurl);
        ServiceSoap12Stub srv = new ServiceSoap12Stub(url, null);
        String merchantid = "TA123";//merchantCode gui trong tai lieu
        String stan = "324";
        String termtxndatetime = "20131212232323";
        String txnAmount = "10000";
        String fee = "0";
        String userName = "NGUYEN HONG NHUNG";
        String issuerID = "EPAY";
        String tranID = "fdsfdsfsdf";
        String bankID = "999999";
        String respUrl = "fsdfdsfsdfsdf";
        String key = "reesatersuusrtiy12312kty";//day la merchant_send_key gui trong tai lieu
        String temp = merchantid + stan + termtxndatetime + txnAmount + fee + userName + issuerID + tranID + bankID + respUrl;
        String mac = DESMAC(temp, key);

        Reponse kq = srv.deposit(merchantid, stan, termtxndatetime, txnAmount, fee, userName, issuerID, tranID, bankID, mac, respUrl);
        System.out.println("KQ tra ve:" + kq.getResponsecode());
        System.out.println("Mo ta:" + kq.getDescriptionvn());
    }


    public static void Hue_testConfirm(String Strurl) throws Exception {
        URL url = new URL(Strurl);
        ServiceSoap12Stub srv = new ServiceSoap12Stub(url, null);
        String merchantcode = "TA123";//merchantCode gui trong tai lieu
        String tranid = "fdsfdsfsdf";
        String txnAmount = "10000";
        String confirmCode = "00";
        String key = "reesatersuusrtiy12312kty";//day la merchant_send_key gui trong tai lieu
        String temp = merchantcode + tranid + txnAmount + confirmCode;
        String mackey = DESMAC(temp, key);
        Reponse kq = srv.comfirm(merchantcode, tranid, txnAmount, confirmCode, mackey);
        System.out.println("KQ tra ve:" + kq.getResponsecode());
        System.out.println("Mo ta:" + kq.getDescriptionvn());
    }


    public static String DESMAC(String msg, String key) throws Exception {
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


    public static String sha1(String data) throws Exception {
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


}
