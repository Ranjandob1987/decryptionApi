package com.encyption.demo.request;

import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;

public class DecryptRequest {
    private String encryptedValue; // Base64 encoded
    private String key;            // Base64 encoded
    private EncryptionDecryptionAlgo algorithm;
    private GCMIvLength gcmIvLength;
    private GCMTagLength gcmTagLength;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public EncryptionDecryptionAlgo getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(EncryptionDecryptionAlgo algorithm) {
        this.algorithm = algorithm;
    }

    public GCMIvLength getGcmIvLength() {
        return gcmIvLength;
    }

    public void setGcmIvLength(GCMIvLength gcmIvLength) {
        this.gcmIvLength = gcmIvLength;
    }

    public GCMTagLength getGcmTagLength() {
        return gcmTagLength;
    }

    public void setGcmTagLength(GCMTagLength gcmTagLength) {
        this.gcmTagLength = gcmTagLength;
    }

    public String getEncryptedValue() {
        return encryptedValue;
    }

    public void setEncryptedValue(String encryptedValue) {
        this.encryptedValue = encryptedValue;
    }
}
