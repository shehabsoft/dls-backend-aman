package ae.rta.dls.backend.security.util;

import ae.rta.dls.backend.common.errors.SystemException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;

@Component
public class CryptoUtil {

    public static final String UNICODE_FORMAT = "UTF8";

    private Cipher decryptionCipher;

    private Cipher encryptionCipher;

    public CryptoUtil(Cipher encryptionCipher, Cipher decryptionCipher) {
        this.encryptionCipher = encryptionCipher;
        this.decryptionCipher = decryptionCipher;
    }

    public String encrypt(String valueToEncrypt) {
        try {
            byte[] encValue = encryptionCipher.doFinal(valueToEncrypt.getBytes(UNICODE_FORMAT));
            return Encoders.BASE64.encode(encValue);
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException ex) {
            throw new SystemException(ex);
        }
    }

    public String decrypt(String valueToDecrypt) {
        try {
            byte[] decodedValue = Decoders.BASE64.decode(valueToDecrypt);
            return new String(decryptionCipher.doFinal(decodedValue));
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            throw new SystemException(ex);
        }
    }
}
