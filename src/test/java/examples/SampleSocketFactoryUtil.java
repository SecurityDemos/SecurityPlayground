package examples;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.security.*;
import java.security.cert.CertificateException;

public class SampleSocketFactoryUtil {

    private final SSLContext sslContext;
    private final SSLServerSocketFactory serverSocketFactory;
    private final SSLSocketFactory socketFactory;

    public SampleSocketFactoryUtil(String keyStorePath, char[] password) throws Exception {
        sslContext = createSSLContext(keyStorePath, password);
        serverSocketFactory = createServerSocketFactory();
        socketFactory = sslContext.getSocketFactory();
    }

    private SSLContext createSSLContext(String keyStorePath, char[] password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(keyStorePath);
        keyStore.load(fis, password);
        fis.close();

        SSLContext context = SSLContext.getDefault();
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, password);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), SecureRandom.getInstanceStrong());
        return context;
    }

    private SSLServerSocketFactory createServerSocketFactory() throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
        return factory;

    }

    public SSLServerSocket createServerSocket(int port) throws IOException {
        return (SSLServerSocket) serverSocketFactory.createServerSocket(port);
    }

    public SSLSocket createSocket(InetAddress address, int port) throws IOException {
        return (SSLSocket) socketFactory.createSocket(address, port);
    }
}
