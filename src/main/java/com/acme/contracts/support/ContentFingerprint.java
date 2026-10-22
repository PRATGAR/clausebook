package com.acme.contracts.support;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.stereotype.Component;

@Component
public class ContentFingerprint {

    public String of(String content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(DigestNames.ETAG);
        return HexFormat.of().formatHex(digest.digest(content.getBytes(StandardCharsets.UTF_8)));
    }
}
