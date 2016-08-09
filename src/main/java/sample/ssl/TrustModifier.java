package sample.ssl;

import javax.net.ssl.*;
import java.security.cert.*;

public class TrustModifier {
    private static final TrustingHostNameVerifier VERIFIER = new TrustingHostNameVerifier();
    private static final TrustManager[] MANAGER = new TrustManager[]{new AlwaysTrustManager()};

    private final SSLSocketFactory factory;

    public TrustModifier(String type) throws Exception {
        SSLContext ctx = SSLContext.getInstance(type);
        ctx.init(null, MANAGER, null);
        factory = ctx.getSocketFactory();
    }

    /** Call this and It will modify the trust settings. */
    public void modify(HttpsURLConnection conn) throws Exception {
        conn.setSSLSocketFactory(factory);
        conn.setHostnameVerifier(VERIFIER);
    }

    private static final class TrustingHostNameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class AlwaysTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
        public X509Certificate[] getAcceptedIssuers() { return null; }
    }
}
