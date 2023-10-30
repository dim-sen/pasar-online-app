package com.online.pasaronlineapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

@Component
public class KeyUtils {

    @Value("access-token-private")
    private String accessTokenPrivateKeyPath;

    @Value("access-token-public")
    private String accessTokenPublicKeyPath;

    @Value("refresh-token-private")
    private String refreshTokenPrivateKeyPath;

    @Value("refresh-token-public")
    private String refreshTokenPublicKeyPath;

    private KeyPair _accessTokenKeyPair;

    private KeyPair _refreshTokenKeyPair;

    private KeyPair getAccessTokenKeyPair() {
        if (Objects.isNull(_accessTokenKeyPair)) {
            _accessTokenKeyPair = getKeyPair(accessTokenPublicKeyPath, accessTokenPrivateKeyPath);
        }
        return _accessTokenKeyPair;
    }

    private KeyPair getRefreshTokenKeyPair() {
        if (Objects.isNull(_refreshTokenKeyPair)) {
            _refreshTokenKeyPair = getKeyPair(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath);
        }
        return _refreshTokenKeyPair;
    }

    private KeyPair getKeyPair(String tokenPublicKeyPath, String tokenPrivateKeyPath) {
        KeyPair keyPair;

        File publicKeyFile = new File(tokenPublicKeyPath);
        File privateKeyFile = new File(tokenPrivateKeyPath);

        if (publicKeyFile.exists() && privateKeyFile.exists()) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                byte[] publicKeyByte = Files.readAllBytes(publicKeyFile.toPath());
                EncodedKeySpec publicEncodedKeySpec = new X509EncodedKeySpec(publicKeyByte);
                PublicKey publicKey = keyFactory.generatePublic(publicEncodedKeySpec);

                byte[] privateKeyByte = Files.readAllBytes(privateKeyFile.toPath());
                PKCS8EncodedKeySpec privateEncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
                PrivateKey privateKey = keyFactory.generatePrivate(privateEncodedKeySpec);

                keyPair = new KeyPair(publicKey, privateKey);

                return keyPair;
            } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Public and Private Key Do Not Exist");
        }
    }

    public RSAPublicKey getAccessTokenPublicKey() {
        return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
    }

    public RSAPrivateKey getAccessTokenPrivateKey() {
        return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
    }

    public RSAPublicKey getRefreshTokenPublicKey() {
        return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
    }

    public RSAPrivateKey getRefreshTokenPrivateKey() {
        return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
    }
}
