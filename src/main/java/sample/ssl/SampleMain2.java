package sample.ssl;

import javax.net.ssl.*;
import java.io.*;

/**
 * Created by Cookie on 16/8/9.
 */
public class SampleMain2 {

    public static void main(String[] args) throws IOException {
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
