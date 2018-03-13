package sample.ssl;

import javax.net.SocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cookie on 16/8/9.
 */
public class SampleMain3 {

    public static void main(String[] args) throws IOException, InterruptedException {

        SocketFactory factory = SocketFactory.getDefault();
        Socket socket = factory.createSocket("127.0.0.1", 8080);

        final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    out.println("GET /apiv1/sys/heartbeat HTTP/1.0");
                    out.println("Host: 10.0.0.180");
                    out.println("User-Agent: curl/7.43.0");
                    out.println("Accept: */*");
                    out.println();
                    out.flush();

                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;

        if (out.checkError())
            System.out.println("SocketClient:  java.io.PrintWriter error");

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);

        TimeUnit.SECONDS.sleep(15);

        out.close();
        in.close();
        socket.close();
    }
}
