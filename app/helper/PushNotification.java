package helper;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
import play.libs.Json;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushNotification {

    private static PushNotification instance = null;

    public static PushNotification getInstance() {
        if (instance == null) {
            instance = new PushNotification();
        }
        return instance;
    }

    public PushNotification() {
//        initSDK();
    }

//    private void initSDK() {
//        try {
//            FileInputStream serviceAccount = new FileInputStream("conf/serviceAccountKeyFirebase.json");
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://divu-app.firebaseio.com")
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//            Utils.debug("Init Firebase success");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void SendOneDevice(String tokenId, String title, String body) {
        if (tokenId != null && tokenId.length() > 15) {
            pushFirebaseToOneDeviceByHttp(getJsonSendHttp(tokenId, title, body));
        } else {
            Utils.debug("Token push khong phu hop");
        }
    }

    public void SendListDevice(List<String> listToken, String title, String body) {

        for (String tokenId : listToken) {
            SendOneDevice(tokenId, title, body);
        }
    }

    public void SendListDevice(Collection<String> listToken, String title, String body) {

        for (String tokenId : listToken) {
            SendOneDevice(tokenId, title, body);
        }
    }

    private String getJsonSendHttp(String tokenId, String title, String body) {
        Map<String, String> m = new HashMap<>();
        m.put("title", title);
        m.put("body", body);
        Map<String, Object> map = new HashMap<>();
        map.put("to", tokenId);
        map.put("data", m);
        map.put("notification", m);
        return Json.toJson(map).toString();
    }

    private void pushFirebaseToOneDeviceByHttp(String json) {
        pushFirebaseNotification(json);
    }

    private void pushFirebaseToTopicByHttp(String topic, String title, String body) {
        pushFirebaseNotification(getJsonSendHttp(topic, title, body));
    }

    private void pushFirebaseNotification(String json) {
        try {
            URL obj = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", Config.KEY_FIREBASE);

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(json.getBytes("UTF-8"));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Utils.debug("ResponseSend result push notification: " + responseCode);

//            /***Doc noi dung tra ve***/
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            Utils.debug(response);
//            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /***OLD***/
//    private String getJsonSend(String title, String body) {
//        Map<String, String> m = new HashMap<>();
//        m.put("title", title);
//        m.put("body", body);
//        return Json.toJson(m).toString();
//    }
//
//    private void pushFirebaseNotification(String tokenId, String json) {
//        if (tokenId != null && tokenId.trim().length() > 15) {
//            Message message = Message.builder()
//                    .putData("data", json)
//                    .putData("notification", json)
//                    .setToken(tokenId)
//                    .build();
//            try {
//                String response = FirebaseMessaging.getInstance().send(message);
//                Utils.debug(response);
//            } catch (FirebaseMessagingException e) {
//                Utils.debug("Push Message Firebase failure");
//                e.printStackTrace();
//            }
//        } else {
//            Utils.debug("Token Id Push null");
//        }
//    }
//    /***OLD***/
}
