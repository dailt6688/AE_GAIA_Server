package helper;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.*;
import models.Admin;
import models.User;
import play.libs.Json;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;

public class JWTUntils {

    public static String genJWT(final Object obj) {
        String result = "";
        try {
            result = Jwts.builder()
                    .setClaims(new HashMap<String, Object>() {{
                        put("json", Json.toJson(obj));
                    }})
                    .signWith(SignatureAlgorithm.HS256, "jwt_secret_app_divu")
                    .compact();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Boolean validateJWT(String jwt, User user) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("jwt_secret_app_divu"))
                    .parseClaimsJws(jwt).getBody();
            JsonNode user_decode = Json.toJson(claims.get("json"));
            Integer id = user_decode.get("id").intValue();
            String username = user_decode.get("username").asText();
            return user.id == id && user.username.equals(username);
        } catch (Exception e) {
            System.out.println("Error token user: " + user.username);
        }
//        catch (SignatureException e) {
//            System.out.println("Error token 1 user: " + user.username);
//        } catch (MalformedJwtException e) {
//            System.out.println("Error token 2 user: " + user.username);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Error token 3 user: " + user.username);
//        }
        return false;
    }

    public static Boolean validateJWTAdmin(String jwt, Admin admin) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("jwt_secret_app_divu"))
                    .parseClaimsJws(jwt).getBody();
            JsonNode user_decode = Json.toJson(claims.get("json"));
            Integer id = user_decode.get("id").intValue();
            String username = user_decode.get("username").asText();
            return admin.id == id && admin.username.equals(username);
        } catch (Exception e) {
            System.out.println("Error token admin: " + admin.username);
        }
//        catch (SignatureException e) {
//            System.out.println("Error token 1 admin: " + admin.username);
//        } catch (MalformedJwtException e) {
//            System.out.println("Error token 2 admin: " + admin.username);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Error token 3 admin: " + admin.username);
//        }
        return false;
    }

    public static JsonNode decodeJWT(String jwt){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("jwt_secret_app_divu"))
                    .parseClaimsJws(jwt).getBody();
            JsonNode user_decode = Json.toJson(claims.get("json"));
            return user_decode;
        } catch (Exception e) {
            System.out.println("Error token");
        }
        return null;
    }
}
