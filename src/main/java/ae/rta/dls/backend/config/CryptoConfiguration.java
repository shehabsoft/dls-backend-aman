package ae.rta.dls.backend.config;

import ae.rta.dls.backend.common.util.StringUtil;
import io.jsonwebtoken.io.Decoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Configuration
public class CryptoConfiguration {

    private final Logger log = LoggerFactory.getLogger(CryptoConfiguration.class);

    private static final String ALGORITHM_AES = "AES";

    @Value("#{environment['CRYPTO_SECRET_KEY']}")
    private String secretKey;

    @Bean
    public Cipher decryptionCipher() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Key key = generateSecretKey();

        String decodedKey = new String(Decoders.BASE64.decode(secretKey), StandardCharsets.UTF_8);
        IvParameterSpec iv = new IvParameterSpec(decodedKey.getBytes(StandardCharsets.UTF_8));

        Cipher decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, iv);

        return decryptionCipher;
    }

    @Bean
    public Cipher encryptionCipher() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Key key = generateSecretKey();

        String decodedKey = new String(Decoders.BASE64.decode(secretKey), StandardCharsets.UTF_8);
        IvParameterSpec iv = new IvParameterSpec(decodedKey.getBytes(StandardCharsets.UTF_8));

        Cipher encryptionCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key, iv);

        return encryptionCipher;
    }

    @Bean
    public Key generateSecretKey() throws InvalidKeyException {
        if (StringUtil.isBlank(secretKey)) {
            log.error("Crypto Secret Key Is Empty !!!");
            throw new InvalidKeyException("Crypto Secret Key Is Empty !!!");
        }

        String key = new String(Decoders.BASE64.decode(secretKey), StandardCharsets.UTF_8);
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);
    }
}
