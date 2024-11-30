package com.encyption.demo;


import com.sbi.epay.encryptdecrypt.exception.EncryptionDecryptionException;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class DecryptionEnService {
    @Autowired
    DecryptionService decryptionService;
    public String decrypt(String encryptedValue, String key, EncryptionDecryptionAlgo algorithm,
                          GCMIvLength gcmIvLength, GCMTagLength gcmTagLength) throws EncryptionDecryptionException {
        return DecryptionService.decryptKey(key, encryptedValue, algorithm, gcmIvLength, gcmTagLength);
    }    }

