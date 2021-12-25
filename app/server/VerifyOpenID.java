package server;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VerifyOpenID {

    private static final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public static JsonNode verifyFacebook(String access_token) {
        try {
            // access_token =
            // "CAAEY8kad9zABAKmU6ZALGre1FWdNuZBpK6nHT0YYZBYyqjEHyvxGYmbTddVghizl5JCBslaMGIULFl24RMvASlpHj48yz3xbn9PVQy7KqAZAPpGxNjQoQAZBRMBbt7qeAYAE16PUKBM6U4mXZCUlPe8fK6ofKT7JbAPMMAuvxauM9HHcpZAj4KvoetFPZAJkI9HJHcHAVJQ4ZCPVRETCU7vZAP";

            String url = "https://graph.facebook.com/me?access_token=" + access_token;
            // System.out.println("\nSending 'GET' request to URL : " + url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            // add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("ResponseSend Code Facebook: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return Json.parse(response.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static JsonNode verifyGooglePlus(String accessToken) {
        try {

            String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + accessToken;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            // add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("ResponseSend Code Google+ : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return Json.parse(response.toString());
        } catch (Exception e) {
            return null;
        }
    }

}
