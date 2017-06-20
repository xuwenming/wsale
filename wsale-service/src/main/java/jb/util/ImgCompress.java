package jb.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 图片压缩处理
 * @author 崔素强
 */
@SuppressWarnings("restriction")
public class ImgCompress {
    private Image img;
    private static int width;
    private static int height;
    public static void main(String[] args) throws Exception {
//        System.out.println("开始：" + new Date().toLocaleString());
//        ImgCompress imgCom = new ImgCompress("D:\\temp\\123.png");
//        imgCom.resizeFix(width, height, "D:\\temp\\456.png");
        imgCompress("D:\\temp\\123.jpg", "D:\\temp\\456.jpg");
//        System.out.println("结束：" + new Date().toLocaleString());
    }
    
    public static void imgCompress(String srcFilePath, String descFilePath) throws Exception{
    	ImgCompress imgCom = new ImgCompress(srcFilePath);
    	imgCom.resizeFix(width, height, descFilePath);
    }
    
    public static void imgCompress(InputStream is, String descFilePath) throws Exception{
    	ImgCompress imgCom = new ImgCompress(is);
    	imgCom.resizeFix(width, height, descFilePath);
    }
    
    /**
     * 构造函数
     */
    public ImgCompress(String fileName) throws IOException {
        File file = new File(fileName);// 读入文件
        img = ImageIO.read(file);      // 构造Image对象
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }
    /**
     * 构造函数
     */
    public ImgCompress(InputStream is) throws IOException {
    	img = ImageIO.read(is);      // 构造Image对象
    	width = img.getWidth(null);    // 得到源图宽
    	height = img.getHeight(null);  // 得到源图长
    }
    /**
     * 按照宽度还是高度进行压缩
     * @param w int 最大宽度
     * @param h int 最大高度
     */
    public void resizeFix(int w, int h, String filePath) throws IOException {
        if (width / height > 0) {
            w = width > 960 ? 960 : width;
            resizeByWidth(w, filePath);
        } else {
            h = height > 1280 ? 1280 : height;
            resizeByHeight(h, filePath);
        }
    }
    /**
     * 以宽度为基准，等比例放缩图片
     * @param w int 新宽度
     */
    public void resizeByWidth(int w, String filePath) throws IOException {
        int h = (int) (height * w / width);
        resize(w, h, filePath);
    }
    /**
     * 以高度为基准，等比例缩放图片
     * @param h int 新高度
     */
    public void resizeByHeight(int h, String filePath) throws IOException {
        int w = (int) (width * h / height);
        resize(w, h, filePath);
    }
    /**
     * 强制压缩/放大图片到固定的大小
     * @param w int 新宽度
     * @param h int 新高度
     */
	public void resize(int w, int h, String filePath) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        File destFile = new File(filePath);
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码
        out.close();
    }
}