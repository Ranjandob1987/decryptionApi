package com.encyption.demo.request;

import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;

public class DecryptRequest {
    private String encryptedValue; // Base64 encoded
    private String key;            // Base64 encoded
}
