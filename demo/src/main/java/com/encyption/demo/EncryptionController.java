package com.encyption.demo;




import com.encyption.demo.request.DecryptRequest;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.service.KeyGeneratorService;
import com.sbi.epay.encryptdecrypt.service.KeyProviderService;
import com.sbi.epay.encryptdecrypt.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private DecryptionService decryptionService;

    @Autowired
    private KeyGeneratorService keyGeneratorService;

    @Autowired
    private KeyProviderService keyProviderService;

    private final String aek = "r4YJLpWvRkDujmWMgl5FlQ=="; // Static AEK for simplicity



    // Step 1: Initialize KEK, MEK, and SecretKey during the first API call.

    public SecretKey generateSecretKey() throws Exception {
        // Step 1: Generate KEK and MEK
        String kek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
        String mek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);

        // Step 2: Encrypt KEK using AEK
        String encryptedKek = EncryptionService.encryptSecretKey(kek, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);

        // Step 3: Encrypt MEK using KEK
        String encryptedMek = EncryptionService.encryptSecretKey(mek, kek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);

        // Step 4: Decrypt MEK to retrieve usable SecretKey
        return KeyProviderService.getDecryptedMEK(encryptedMek, encryptedKek, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
    }

    /**
     * Encodes a SecretKey into a Base64 String.
     */
    public String encodeSecretKey(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Decodes a Base64 String into a SecretKey.
     */
    public SecretKey decodeSecretKey(String encodedKey) {
        byte[] decodedKeyBytes = Base64.getDecoder().decode(encodedKey);
        return new javax.crypto.spec.SecretKeySpec(decodedKeyBytes, 0, decodedKeyBytes.length, "AES");
    }


    // API 1: Encrypt JSON
    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptJson(@RequestBody DecryptRequest json) throws Exception {

SecretKey secretKey=generateSecretKey();
        byte[] encryptedBytes = encryptionService.encryptValue(secretKey, json.toString(), EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

        return ResponseEntity.ok(encryptedString);
    }

    // API 2: Decrypt Encrypted String to JSON
    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptToJson(@RequestBody String encryptedString) throws Exception {
        SecretKey secretKey=generateSecretKey();

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
        String decryptedJson = decryptionService.decryptValue(encryptedBytes, secretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);

        return ResponseEntity.ok(decryptedJson);
    }
}
