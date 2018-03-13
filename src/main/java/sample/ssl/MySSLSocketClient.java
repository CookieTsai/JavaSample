package sample.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Cookie on 16/6/14.
 */
public class MySSLSocketClient {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, InterruptedException {
        SocketFactory factory = SSLSocketFactory.getDefault();
        final SSLSocket socket = (SSLSocket) factory.createSocket("10.0.0.180", 443);
        socket.startHandshake();

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

        out.println("POST /apiv1/login HTTP/1.1");
        out.println("Host: 10.0.0.180");
        out.println("Connection: close");
        out.println("li: zh_TW");
        out.println("User-Agent: Paw/2.3.3 (Macintosh; OS X/10.10.5) GCDHTTPRequest");
        out.println("Content-Type: application/json");
        out.println("Content-Length: 86");
        out.println();
        out.println("{\"id\":\"+886919236465\",\"pw\":\"IscRnj8qKwTo4JDRjo0zgAZrJnA=\",\"tp\":\"7\",\"dn\":\"Samsung PF2\"}");
        out.println();
        out.flush();

        if (out.checkError()) System.out.println("SSLSocketClient:  java.io.PrintWriter error");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) System.out.println(inputLine);

        out.close();
        in.close();
        socket.close();
    }
}
