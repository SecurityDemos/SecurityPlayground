package examples;


import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

public class SimpleCryptoToken {
    public static final String KEY_ALGO = "AES";
    public static final String CRYPTO_ALGO = "AES_128/CBC/NOPADDING";
    public static final String STORE_TYPE = "PKCS12";
    public static final int PADDING_SIZE = 16;
    private SecretKey secretKey;

    /**
     * Generates a new DES3 key
     */
    public void init() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance(KEY_ALGO);
        SecureRandom rnd = SecureRandom.getInstanceStrong();
        gen.init(128, rnd);
        secretKey = gen.generateKey();

    }

    // ONLY FOR DEBUG PURPOUSES
    public String dumpKey() {
        if (secretKey != null) {
            return new String(Hex.encode(secretKey.getEncoded()));
        }

        return null;
    }

    public void save(String path, char[] password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(STORE_TYPE);
        keyStore.load(null, password);
        KeyStore.ProtectionParameter protParam =
                new KeyStore.PasswordProtection(password);

        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(secretKey);
        keyStore.setEntry("myKey", skEntry, protParam);

        FileOutputStream fos = new FileOutputStream(path);
        keyStore.store(fos, password);
        fos.close();
    }

    public void load(String path, char[] password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance(STORE_TYPE);
        FileInputStream fis = new FileInputStream(path);
        keyStore.load(fis, password);
        fis.close();
        secretKey = (SecretKey) keyStore.getKey("mykey", password);
        //keyStore.setKeyEntry("mykey",secretKey.getEncoded(),null);
    }

    public String createToken(Map<String, Object> data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException {
        String jsonData = toJsonString(data);
        int l = 0;


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Now encrypt
        Cipher cipher = Cipher.getInstance(CRYPTO_ALGO);
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(bos, cipher);
        byte[] bb = jsonData.getBytes(StandardCharsets.UTF_8);
        l += bb.length;
        cipherOutputStream.write(bb);
        while (l % PADDING_SIZE != 0) {
            cipherOutputStream.write(" ".getBytes(StandardCharsets.UTF_8));
            l++;
        }
        cipherOutputStream.close();

        byte[] enc = bos.toByteArray();

        return Base64.getEncoder().encodeToString(enc);
    }

    public Map<String, Object> verifyToken(String token) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, JSONException, IOException, InvalidAlgorithmParameterException {
        byte[] enc = Base64.getDecoder().decode(token);
        ByteArrayInputStream bis = new ByteArrayInputStream(enc);
        Cipher cipher = Cipher.getInstance(CRYPTO_ALGO);
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

        CipherInputStream cipherInputStream = new CipherInputStream(bis, cipher);
        InputStreamReader reader = new InputStreamReader(cipherInputStream, StandardCharsets.UTF_8);
        StringBuffer buf = new StringBuffer();
        char[] chars = new char[1024];
        int l = 0;
        while ((l = reader.read(chars, 0, chars.length)) > 0) {
            buf.append(chars, 0, l);
        }
        reader.close();
        cipherInputStream.close();
        bis.close();


        // convert json object to map
        return toMap(buf.toString());
    }

    private Map<String, Object> toMap(String jsonString) throws JSONException {
        org.json.JSONTokener tokener = new JSONTokener(jsonString);
        JSONObject obj = (JSONObject) tokener.nextValue();
        Map<String, Object> map = new TreeMap<>();
        Iterator<String> i = obj.keys();
        while (i.hasNext()) {
            String k = i.next();
            map.put(k, obj.get(k));
        }
        return map;
    }

    private String toJsonString(Map<String, Object> data) {
        JSONObject json = new JSONObject();
        data.forEach((k, v) -> {
            try {
                json.put(k, v);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        return json.toString();
    }
}
