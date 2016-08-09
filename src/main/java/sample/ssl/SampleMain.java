package sample.ssl;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Cookie on 16/6/14.
 */
public class SampleMain {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        {
            URL url = new URL("https://10.0.0.180/apiv1/sys/heartbeat");

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            TrustModifier.modify(urlConnection, "TLS");
            urlConnection.connect();

            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((inputLine = in.readLine()) != null) System.out.println(inputLine);
            in.close();
        }

        {
            System.out.println();
            System.out.println("----------- 分隔線 -----------");
            System.out.println();

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket("10.0.0.180", 443);

            String[] suites = socket.getSupportedCipherSuites();
            socket.setEnabledCipherSuites(suites);
            socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
                public void handshakeCompleted(HandshakeCompletedEvent event) {
                    System.out.println("Handshake successful!");
                    System.out.println("Using cipher suite: " + event.getCipherSuite());
                    System.out.println();
                }
            });
            System.out.println("Start Handshake");
            socket.startHandshake();

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            out.println("GET /apiv1/sys/heartbeat HTTP/1.0");
            out.println("Host: 10.0.0.180");
            out.println("User-Agent: curl/7.43.0");
            out.println("Accept: */*");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;

            if (out.checkError()) System.out.println("SSLSocketClient:  java.io.PrintWriter error");
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

            in.close();
            out.close();
            socket.close();
        }
    }
}
