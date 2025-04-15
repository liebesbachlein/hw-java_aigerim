package space.service.auth;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class EncryptionService {

    public static String encode(String original) {
        return new StringBuilder(original).reverse().toString();
    }

    public static String decode(String secret) {
        return new StringBuilder(secret).reverse().toString();
    }
}
