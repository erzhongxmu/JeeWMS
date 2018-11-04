package org.jeecgframework.core.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
 
import javax.imageio.ImageIO;
 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
 

public class QRcodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    /** 生成二维码 */
    public static BufferedImage createImage(String content)throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        // 插入图片
//        QRcodeUtil.insertImage(image, logo, true);
        return image;
    }
     
 
    /** 在二维码中间插入logo图片 */
    private static void insertImage(BufferedImage source, InputStream logo, boolean needCompress)throws Exception {
        if(logo==null)return;
        Image src = ImageIO.read(logo);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
     
     
    /**
     * 生成二维码
     * @param content 文本内容
     * @param logo 内嵌图标
     * @param output 输出流
     * @throws Exception
     */
    public static void encode(String content, InputStream logo, OutputStream output) throws Exception {
        BufferedImage image = QRcodeUtil.createImage(content);
        ImageIO.write(image, FORMAT_NAME, output);
    }
    public static void encode(String content, InputStream logo, File out)throws Exception {
        if(!out.getParentFile().exists()){
            out.getParentFile().mkdirs();
        }
        out.createNewFile();
        QRcodeUtil.encode(content, logo, new FileOutputStream(out));
    }
    public static void encode(String content, File logo, OutputStream out)throws Exception {
        if(logo.exists()){
            QRcodeUtil.encode(content, new FileInputStream(logo), out);
        }else{
            QRcodeUtil.encode(content,(InputStream)null, out);
        }
    }
    public static void encode(String content, File logo, File out)throws Exception {
        if(!out.getParentFile().exists()){
            out.getParentFile().mkdirs();
        }
        out.createNewFile();
        if(logo.exists()){
            QRcodeUtil.encode(content, new FileInputStream(logo), out);
        }else{
            QRcodeUtil.encode(content,(InputStream)null, out);
        }
    }
    public static void encode(String content, InputStream logo, String out)throws Exception {
        QRcodeUtil.encode(content, logo, new File(out));
    }
    public static void encode(String content, String logo, String out) throws Exception {
        QRcodeUtil.encode(content, new File(logo), new File(out));
    }
    public static void encode(String content, OutputStream out) throws Exception {
        QRcodeUtil.encode(content, (InputStream)null, out);
    }
    public static void encode(String content, String out)throws Exception {
        QRcodeUtil.encode(content,(InputStream)null, new File(out));
    }
 
     
     
 
    /**
     * 解析二维码,input是二维码图片流
     */
    public static String decode(InputStream input) throws Exception {
        BufferedImage image = ImageIO.read(input);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }
    /**
     * 解析二维码,file是二维码图片
     */
    public static String decode(File file) throws Exception{
        return decode(new FileInputStream(file));
    }
    /**
     * 解析二维码,path是二维码图片路径
     */
    public static String decode(String path) throws Exception {
        return QRcodeUtil.decode(new File(path));
    }
 
    public static void main(String[] args) throws Exception {
        QRcodeUtil.encode("http://baike.xsoftlab.net/view/1114.html","", "D:/abc/qr/1.jpg");
        String data = QRcodeUtil.decode("D:/abc/qr/1.jpg");
        System.out.println(data);
    }
 
    private static class BufferedImageLuminanceSource extends LuminanceSource {
        private final BufferedImage image;
        private final int left;
        private final int top;
 
        public BufferedImageLuminanceSource(BufferedImage image) {
            this(image, 0, 0, image.getWidth(), image.getHeight());
        }
 
        public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width,
                int height) {
            super(width, height);
 
            int sourceWidth = image.getWidth();
            int sourceHeight = image.getHeight();
            if (left + width > sourceWidth || top + height > sourceHeight) {
                throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
            }
 
            for (int y = top; y < top + height; y++) {
                for (int x = left; x < left + width; x++) {
                    if ((image.getRGB(x, y) & 0xFF000000) == 0) {
                        image.setRGB(x, y, 0xFFFFFFFF); // = white
                    }
                }
            }
 
            this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
            this.image.getGraphics().drawImage(image, 0, 0, null);
            this.left = left;
            this.top = top;
        }
 
        public byte[] getRow(int y, byte[] row) {
            if (y < 0 || y >= getHeight()) {
                throw new IllegalArgumentException("Requested row is outside the image: " + y);
            }
            int width = getWidth();
            if (row == null || row.length < width) {
                row = new byte[width];
            }
            image.getRaster().getDataElements(left, top + y, width, 1, row);
            return row;
        }
 
        public byte[] getMatrix() {
            int width = getWidth();
            int height = getHeight();
            int area = width * height;
            byte[] matrix = new byte[area];
            image.getRaster().getDataElements(left, top, width, height, matrix);
            return matrix;
        }
 
        public boolean isCropSupported() {
            return true;
        }
 
        public LuminanceSource crop(int left, int top, int width, int height) {
            return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width,
                    height);
        }
 
        public boolean isRotateSupported() {
            return true;
        }
 
        public LuminanceSource rotateCounterClockwise() {
            int sourceWidth = image.getWidth();
            int sourceHeight = image.getHeight();
            AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
            BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth,
                    BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g = rotatedImage.createGraphics();
            g.drawImage(image, transform, null);
            g.dispose();
            int width = getWidth();
            return new BufferedImageLuminanceSource(rotatedImage, top,
                    sourceWidth - (left + width), getHeight(), width);
        }
    }
 
}
