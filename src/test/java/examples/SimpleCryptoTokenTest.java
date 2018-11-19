package examples;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SimpleCryptoTokenTest {
    @Test
    public void storeAndLoad() throws Exception {
        SimpleCryptoToken simpleCryptoToken = new SimpleCryptoToken();

        // Create it
        simpleCryptoToken.init();
        String key1 = simpleCryptoToken.dumpKey();

        // Create one crypto token
        Map<String, Object> myData = new HashMap<>();
        myData.put("username", "admin");

        String token = simpleCryptoToken.createToken(myData);

        System.err.println("ENCODED : " + token);

        // Save it
        simpleCryptoToken.save("/tmp/store.p12", "1235".toCharArray());


        // Now load it again
        simpleCryptoToken = new SimpleCryptoToken();
        simpleCryptoToken.load("/tmp/store.p12", "1235".toCharArray());
        String key2 = simpleCryptoToken.dumpKey();


        Assert.assertThat(key2, IsEqual.equalTo(key1));

        // Decode the token
        Map<String, Object> decoded = simpleCryptoToken.verifyToken(token);

        Assert.assertThat(decoded.get("username"), IsEqual.equalTo("admin"));

    }
}
