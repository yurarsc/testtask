package com.example.testtask.auth;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

public class PEMHelper  {
    private static byte[] loadPEM(URL url) throws IOException {
        try(InputStream in = url.openStream()) {
            String pem = new String(in.readAllBytes(), StandardCharsets.ISO_8859_1);
            Pattern parse = Pattern.compile("(?m)(?s)^---*BEGIN.*---*$(.*)^---*END.*---*$.*");
            String encoded = parse.matcher(pem).replaceFirst("$1");
            return Base64.getMimeDecoder().decode(encoded);
        }
    }

    public static PrivateKey loadPrivateKey(URL url) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] rsaPK = loadPEM(url);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(rsaPK);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
