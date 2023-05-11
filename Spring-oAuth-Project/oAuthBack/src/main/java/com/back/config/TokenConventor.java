package com.back.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.back.entity.User;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import org.json.JSONException;
//import org.json.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;


@Component
public class TokenConventor {

    @Value("${token.secret}")
    private String secret;

    private Base64.Decoder decoder = Base64.getUrlDecoder();

    public String createJWT(User user){
        return JWT.create().withSubject(user.getEmail()).sign(Algorithm.HMAC256(secret));
    }

    public String getUserEmail(String token) throws Exception {
        if(isDecoding(token)) {
            String[] chunks = token.split("\\.");
            String body = new String(decoder.decode(chunks[1]));
            return (String) new JSONObject(body).get("sub");
        }
        else {
            throw new Exception();
        }
    }

    public boolean isDecoding(String token){
        String[] chunks = token.split("\\.");

        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), sa.getJcaName());

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];

        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);

        if (!validator.isValid(tokenWithoutSignature, signature)) {
            System.out.println("Could not verify JWT token integrity!");
            return false;
        }
        return true;
    }


    public static void main(String args[]) {
//        String t = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
//        String[] chunks = t.split("\\.");
//        System.out.println(new String(new TokenConventor().decoder.decode(chunks[1])));
    }
}