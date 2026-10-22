package com.acme.contracts.support;

import com.acme.contracts.config.AppProperties;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.stereotype.Component;

@Component
public class PinHasher {

    private final AppProperties props;

    public PinHasher(AppProperties props) {
        this.props = props;
    }

    public String hash(String pin) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(props.signature().pinDigest());
        return HexFormat.of().formatHex(digest.digest(pin.getBytes(StandardCharsets.UTF_8)));
    }
}
