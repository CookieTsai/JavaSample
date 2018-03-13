package sample.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by Cookie on 16/1/30.
 */
public class SampleMain {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream stream = QRCodeUtils.encodeWithLogo("2c741c0a-d5db-4684-839a-cde0b01439db");
        if (stream == null) throw new RuntimeException("Create QRCode Failed.");
        FileOutputStream out = new FileOutputStream("output.png");
        out.write(stream.toByteArray());
        stream.close();
        out.close();
    }
}
