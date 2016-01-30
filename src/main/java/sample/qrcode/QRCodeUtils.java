package sample.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Cookie on 16/1/30.
 */
public class QRCodeUtils {
    private static final String IMAGE_FORMAT = "png";

    public static final int BLACK = 0xFF000000;
    public static final int WHITE = 0xFFFFFFFF;

    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;

    private static final String FRONT_IMAGE_NAME = "qrcode-logo.png";
    private static BufferedImage FRONT_IMAGE;

    private static int FRONT_IMAGE_WIDTH_START;
    private static int FRONT_IMAGE_HEIGHT_START;

    private static final QRCodeWriter WRITER = new QRCodeWriter();

    static {
        InputStream inputStream = null;
        BufferedInputStream headBIS = null;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream(FRONT_IMAGE_NAME);
            headBIS = new BufferedInputStream(inputStream);
            FRONT_IMAGE = ImageIO.read(headBIS);

            FRONT_IMAGE_WIDTH_START = (WIDTH - FRONT_IMAGE.getWidth()) / 2;
            FRONT_IMAGE_HEIGHT_START = (HEIGHT - FRONT_IMAGE.getHeight()) / 2;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (headBIS != null) {
                try {
                    headBIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ByteArrayOutputStream encode(String content) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BitMatrix matrix = WRITER.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        MatrixToImageWriter.writeToStream(matrix, IMAGE_FORMAT, os);
        return os;
    }

    public static ByteArrayOutputStream encodeWithLogo(String content) throws Exception {
        if (FRONT_IMAGE == null) {
            return encode(content);
        }
        ByteArrayOutputStream os = null;

        BitMatrix matrix = WRITER.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        BufferedImage backImage = QRCodeUtils.toBufferedImage(matrix);

        Graphics2D g = backImage.createGraphics();
        g.drawImage(FRONT_IMAGE, FRONT_IMAGE_WIDTH_START, FRONT_IMAGE_HEIGHT_START, null);

        os = new ByteArrayOutputStream();
        if(!ImageIO.write(backImage, IMAGE_FORMAT, os)) {
            throw new IOException("Could not write an image of format png");
        }
        return os;
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int w = matrix.getWidth();
        int h = matrix.getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}
