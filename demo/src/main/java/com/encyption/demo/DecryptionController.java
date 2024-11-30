package com.encyption.demo;


import com.encyption.demo.request.DecryptRequest;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decryption")
public class DecryptionController {

    @Autowired
    DecryptionEnService decryptionEnService;

    @PostMapping
    public ResponseEntity<String> decryptValue(@RequestBody DecryptRequest request) {

        try {
            String decryptedValue = decryptionEnService.decrypt(
                    request.getEncryptedValue(),
                    request.getKey(),
                    request.getAlgorithm(),
                    request.getGcmIvLength(),
                    request.getGcmTagLength()
            );
            return ResponseEntity.ok(decryptedValue);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during decryption: " + e.getMessage());
        }
    }
}
