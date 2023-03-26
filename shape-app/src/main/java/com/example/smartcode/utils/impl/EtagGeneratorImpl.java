package com.example.smartcode.utils.impl;

import com.example.smartcode.common.Taggable;
import com.example.smartcode.exception.InvalidEtagException;
import com.example.smartcode.utils.EtagGenerator;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class EtagGeneratorImpl implements EtagGenerator {

    private final HttpServletRequest request;
    private Mac mac;
    @Value("${SECRET_ETAG_KEY}")
    private String key;

    @PostConstruct
    private void init() throws NoSuchAlgorithmException, InvalidKeyException {
        mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKey);
    }

    public String generateETag(Taggable entity) {
        String message = entity.generateETagMessage();
        return Base64.getEncoder().encodeToString(mac.doFinal(message.getBytes()));
    }

    @Override
    public void verifyETag(Taggable entity) throws InvalidEtagException {
        String eTag = request.getHeader("If-Match").replace("\"", "");
        if (!generateETag(entity).equals(eTag)) {
            throw new InvalidEtagException();
        }
    }
}
