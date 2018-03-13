package sample.qrcode;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static com.google.zxing.BarcodeFormat.QR_CODE;

/**
 * Created by Cookie on 16/1/30.
 */
public class QRCodeUtils {
    private static final String IMAGE_FORMAT = "png";

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;

    private static final String FRONT_IMAGE_NAME = "qrcode-logo.png";
    private static final BufferedImage FRONT_IMG;
    private static final int FRONT_IMG_START_X;
    private static final int FRONT_IMG_START_Y;

    private static final QRCodeWriter WRITER = new QRCodeWriter();

    static {
        InputStream inputStream = null;
        BufferedInputStream headBIS = null;
        BufferedImage image = null;
        int start_x = 0;
        int start_y = 0;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream(FRONT_IMAGE_NAME);
            headBIS = new BufferedInputStream(inputStream);
            image = ImageIO.read(headBIS);

            start_x = (WIDTH - image.getWidth()) /2;
            start_y = (HEIGHT - image.getHeight()) /2;
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
        FRONT_IMG = image;
        FRONT_IMG_START_X = start_x;
        FRONT_IMG_START_Y = start_y;
    }

    public static ByteArrayOutputStream encode(String content) {
        ByteArrayOutputStream result = null;
        try {
            BitMatrix matrix = WRITER.encode(content, QR_CODE, WIDTH, HEIGHT);
            result = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, IMAGE_FORMAT, result);
        } catch (Exception e) {
            e.printStackTrace();
            close(result);
        }
        return result;
    }

    public static ByteArrayOutputStream encodeWithLogo(String content) {
        if (FRONT_IMG == null) {
            return encode(content);
        }
        ByteArrayOutputStream result = null;
        try {
            BitMatrix matrix = WRITER.encode(content, QR_CODE, WIDTH, HEIGHT);
            BufferedImage backImage = QRCodeUtils.toBufferedImage(matrix);

            Graphics2D g = backImage.createGraphics();
            g.drawImage(FRONT_IMG, FRONT_IMG_START_X, FRONT_IMG_START_Y, null);

            result = new ByteArrayOutputStream();
            if(!ImageIO.write(backImage, IMAGE_FORMAT, result)) {
                throw new IOException("Could not write an image of format png.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            close(result);
        }
        return result;
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

    private static void close(Closeable obj) {
        if (obj == null) return;
        try {
            obj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
