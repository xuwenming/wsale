package jb.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import jb.absx.F;
import jb.pageModel.User;
import jb.pageModel.ZcForumBbs;
import jb.pageModel.ZcProduct;
import jb.util.oss.OSSUtil;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: (二维码)
 * @author：luoguohui
 * @date：2015-10-29 下午05:27:13     
 */
public class QrcodeUtil {
    private static final int QRCOLOR = 0xFF000000;   //默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色


    public static void main(String[] args) throws WriterException {
        try {
            //System.out.println("data:image/png;base64," + getLogoQRCode("https://www.baidu.com/", "长按查看该店铺"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成带logo的二维码图片
     *
     */
    public static String getLogoQRCode(String qrUrl, Object o, HttpServletRequest request) {
//      String filePath = (javax.servlet.http.HttpServletRequest)request.getSession().getServletContext().getRealPath("/") + "resources/images/logoImages/llhlogo.png";
        //filePath是二维码logo的路径，但是实际中我们是放在项目的某个路径下面的，所以路径用上面的，把下面的注释就好
        String content = qrUrl;
        try {
            QrcodeUtil zp = new QrcodeUtil();
            BufferedImage qr = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 180, 180, zp.getDecodeHintType());
            return zp.addLogo_QRCode(qr, o, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给二维码图片添加Logo
     *
     */
    public String addLogo_QRCode(BufferedImage qr, Object o, HttpServletRequest request) {
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/");
            String objectId = null, objectName = null, objectDesc = null, area = null;
            BufferedImage icon = null;
            if(o instanceof User) {
                User u = (User) o;
                objectId = u.getId();
                //objectIcon = u.getHeadImage();
                objectName = u.getNickname();
                objectDesc = "长按查看该店铺";
//                area = u.getArea();
                icon = ImageIO.read(new URL(u.getHeadImage()));
            } else if(o instanceof ZcProduct) {
                ZcProduct p = (ZcProduct) o;
                objectId = p.getId();
                objectName = p.getContent();
                objectDesc = "长按查看详情";
//                icon = ImageIO.read(new File(realPath + p.getIcon()));
                icon = ImageIO.read(new URL(p.getIcon()));
            } else if(o instanceof ZcForumBbs) {
                ZcForumBbs bbs = (ZcForumBbs) o;
                objectId = bbs.getId();
                objectName = bbs.getBbsTitle();
                objectDesc = "长按查看详情";
                icon = ImageIO.read(new URL(bbs.getIcon()));
            }
            objectName = objectName.length() > 7 ? objectName.substring(0, 6) + "..." : objectName;
           // File iconPic = new File(objectIcon);

            /**
             * 读取二维码图片，并构建绘图对象
             */
            BufferedImage image = qr;
            Graphics2D g = image.createGraphics();

            /**
             * 读取Logo图片
             */
            BufferedImage logo = ImageIO.read(new File(realPath + "wsale/images/logo.png"));
            /**
             * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
             */
            int widthLogo = logo.getWidth(null)>image.getWidth()*2/10?(image.getWidth()*2/10):logo.getWidth(null),
                heightLogo = logo.getHeight(null)>image.getHeight()*2/10?(image.getHeight()*2/10):logo.getWidth(null);

            /**
             * logo放在中心
             */
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;

            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.dispose();

            int width = icon.getWidth(null);
            int height = icon.getHeight(null);
            x = 0;
            y = 0;
            if(width > height) {
                x = (width - height)/2;
                y = 0;

                width = width - x;
            } else {
                y = (height - width)/2;
                x = 0;

                height = height - y;
            }

            //新的图片，把带logo的二维码下面加上文字
            BufferedImage outImage = new BufferedImage(640, 960, BufferedImage.TYPE_INT_RGB);
            Graphics2D outg = outImage.createGraphics();

            outg.setColor(new Color(86, 86, 86));
            outg.fillRect(0, 0, 640, 960);//填充整个屏幕
            outg.drawImage(icon, 0, 0, 640, 640, x,y, width,height, null);
            //画二维码到新的面板
//            outg.drawImage(image, 300, height-100-180, image.getWidth(), image.getHeight(), null);
            outg.setColor(new Color(108, 57, 40));
            outg.fillRect(295,640+30,190,190);
            outg.drawImage(image, 300, 640+35, image.getWidth(), image.getHeight(), null);
//            outg.drawImage(icon, 120, 110, 120, 120, null);
            //画文字到新的面板
            outg.setColor(new Color(108, 57, 40));
            outg.fillRect(0, 640, 295, 100);

            outg.fillRect(485,640+160,155,100);
            //画文字到新的面板
            outg.setColor(Color.white);
            outg.setFont(new Font("宋体",Font.BOLD, 30)); //字体、字型、字号
            outg.drawString(objectName, 40 , 640 + 60); //画文字
//
//            if(!F.empty(area))
//                outg.drawString(area, 145 + 120 , 210); // 画文字

            outg.setFont(new Font("宋体",0,20)); //字体、字型、字号
            int strWidth = outg.getFontMetrics().stringWidth(objectDesc);
            outg.drawString(objectDesc, (180-strWidth)/2 + 300 , 640 + 250); //画文字
            outg.dispose();
            outImage.flush();
            image = outImage;

            logo.flush();
            image.flush();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, "png", baos);

            //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
            //可以看到这个方法最终返回的是这个二维码的imageBase64字符串
            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>  其中${imageBase64QRCode}对应二维码的imageBase64字符串
            String filepath = null;
            if(objectId.length() > 4) {
                filepath = Constants.UPLOADFILE + "/QR/" + objectId.substring(0, 2) + "/" + objectId.substring(2, 4);
            } else {
                filepath = Constants.UPLOADFILE + "/QR";
            }
            String result = OSSUtil.putInputStream(OSSUtil.bucketName, new ByteArrayInputStream(baos.toByteArray()), filepath + "/" + objectId + ".png");
            //FileUtils.copyInputStreamToFile(new ByteArrayInputStream(baos.toByteArray()), new File(realPath, filepath + "/" + objectId + ".png"));

            baos.close();
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 构建初始化二维码
     *
     * @param bm
     * @return
     */
    public BufferedImage fileToBufferedImage(BitMatrix bm)
    {
        BufferedImage image = null;
        try
        {
            int w = bm.getWidth(), h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 生成二维码bufferedImage图片
     *
     * @param content
     *            编码内容
     * @param barcodeFormat
     *            编码类型
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param hints
     *            设置参数
     * @return
     */
    public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints) {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    public Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }
}

