package jb.util;

import  java.awt.Color;
import  java.awt.Font;
import  java.awt.Graphics;
import  java.awt.Image;
import  java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import  javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import  com.sun.image.codec.jpeg.JPEGCodec;
import  com.sun.image.codec.jpeg.JPEGImageEncoder;
import jb.absx.F;
import jb.util.oss.OSSUtil;
import jb.util.wx.DownloadMediaUtil;
import org.springframework.http.HttpRequest;

public final class ImageUtils {
    public ImageUtils() {}

    /** */ /**
     * 把图片印刷到图片上
     *
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     * @param x
     *            --x坐标
     * @param y
     *            --y坐标
     */
    public final static String pressImage(String pressImg, String targetImg, int x, int y, String realPath) {
        try {
            if(F.empty(targetImg))  return null;
            // 目标文件
            URL url = new URL(targetImg);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //File _file = new File(realPath + targetImg);
            Image src = ImageIO.read(url);

            Metadata metadata = ImageMetadataReader.readMetadata(conn.getInputStream());
            for(Directory directory : metadata.getDirectories()) {
                if(directory.containsTag(ExifDirectoryBase.TAG_ORIENTATION)) {
                    int orientation = directory.getInt(ExifDirectoryBase.TAG_ORIENTATION);
                    int angel = 0;
                    // 原图片的方向信息
                    if(6 == orientation ){
                        //6旋转90
                        angel = 90;
                    }else if( 3 == orientation){
                        //3旋转180
                        angel = 180;
                    }else if( 8 == orientation){
                        //8旋转90
                        angel = 270;
                    }
                    if(angel != 0)
                        src = RotateImage.Rotate(src, angel);

                    break;
                }
            }

            int w = src.getWidth(null);
            int h = src.getHeight(null);
            int width, height;
            if (w / h > 0) {
                width = w > 1280 ? 1280 : w;
                height = (int) (h * width / w);
            } else {
                height = h > 1280 ? 1280 : h;
                width = (int) (w * height / h);
            }

            BufferedImage image = new  BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);

            //水印文件
            File _fileMark = new File(realPath + pressImg);
            Image src_mark = ImageIO.read(_fileMark);
            int width_mark = src_mark.getWidth(null)/3 > image.getWidth()/3 ? image.getWidth()/3 : src_mark.getWidth(null)/3;
            int height_mark = src_mark.getHeight(null)/3 > image.getHeight()/3 ? image.getHeight()/3 : src_mark.getHeight(null)/3;
            g.drawImage(src_mark, (width - width_mark) - x ,
                    (height - height_mark) - y , width_mark, height_mark, null);
            //水印文件结束
            g.dispose();
            int index = targetImg.indexOf(Constants.UPLOADFILE);
            if(index == -1) index = targetImg.indexOf("attached");
            String filePath = targetImg.substring(index, targetImg.lastIndexOf("/"));
            String fileName = "handle_" + targetImg.substring(targetImg.lastIndexOf("/") + 1);
//            FileOutputStream out = new FileOutputStream(new File(realPath + filePath, fileName));
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(image);
//            out.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, fileName.substring(fileName.lastIndexOf(".") + 1), baos);

            String result = OSSUtil.putInputStream(OSSUtil.bucketName, new ByteArrayInputStream(baos.toByteArray()), filePath + "/" + fileName);

//            return filePath + "/" + fileName;
            return result;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final static String pressImage(String targetImg, int x, int y, String realPath) {
        return pressImage("wsale/images/watermark.png", targetImg, x, y, realPath);
    }
    public final static String pressImage(String targetImg, String realPath) {
        return pressImage(targetImg, 5, 5, realPath);
    }


    /** */ /**
     * 打印文字水印图片
     *
     * @param pressText
     *            --文字
     * @param targetImg --
     *            目标图片
     * @param fontName --
     *            字体名
     * @param fontStyle --
     *            字体样式
     * @param color --
     *            字体颜色
     * @param fontSize --
     *            字体大小
     * @param x --
     *            偏移量
     * @param y
     */
    public   static   void  pressText(String pressText, String targetImg,
                                      String fontName, int  fontStyle,  int  color,  int  fontSize,  int  x,
                                      int  y) {
        try  {
            File _file = new  File(targetImg);
            Image src = ImageIO.read(_file);
            int  wideth = src.getWidth( null );
            int  height = src.getHeight( null );
            BufferedImage image = new  BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0 ,  0 , wideth, height,  null );
            // String s="www.qhd.com.cn";
            g.setColor(Color.RED);
            g.setFont(new  Font(fontName, fontStyle, fontSize));
            g.drawString(pressText, wideth - fontSize - x, height - fontSize
                    / 2  - y);
            g.dispose();
            FileOutputStream out = new  FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch  (Exception e) {
            System.out.println(e);
        }
    }

    public static String replaceHtmlTag(String str, String tag, String tagAttrib, String startTag, String endTag, String realPath) {

        try {
            String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*" ;
            String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"" ;
            Pattern patternForTag = Pattern.compile (regxpForTag,Pattern. CASE_INSENSITIVE );
            Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib,Pattern. CASE_INSENSITIVE );
            Matcher matcherForTag = patternForTag.matcher(str);
            StringBuffer sb = new StringBuffer();
            boolean result = matcherForTag.find();
            while (result) {
                StringBuffer sbreplace = new StringBuffer( "<"+tag+" ");
                Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
                if (matcherForAttrib.find()) {
                    String attributeStr = matcherForAttrib.group(1);
                    if(attributeStr.indexOf(OSSUtil.cdnUrl) == -1) {
                        //String fileExt = attributeStr.substring(attributeStr.lastIndexOf(".") + 1).toLowerCase();
                        URL url = new URL(attributeStr);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setRequestMethod("GET");
                        conn.connect();
                        String fileExt = DownloadMediaUtil.getFileExtName(conn.getHeaderField("Content-Type"));
                        String path = OSSUtil.putInputStream(OSSUtil.bucketName, conn.getInputStream(),  getFilePath(fileExt));
                        path = pressImage(path, realPath);
                        conn.disconnect();
                        matcherForAttrib.appendReplacement(sbreplace, startTag + path + endTag);
                    }
                }
                matcherForAttrib.appendTail(sbreplace);
                matcherForTag.appendReplacement(sb, sbreplace.toString());
                result = matcherForTag.find();
            }
            matcherForTag.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            String error = String.format("replaceHtmlTag失败：%s", e);
            System.out.println(error);
        }
        return str;
    }

    public static String getFilePath(String fileExt) {
        String savePath = "attached/image/";
        SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthDf = new SimpleDateFormat("MM");
        SimpleDateFormat dateDf = new SimpleDateFormat("dd");
        Date date = new Date();
        String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
        savePath += ymd;
        String newFileName = jb.absx.UUID.uuid() + "." + fileExt;
        return savePath + newFileName;
    }

    public static void main(String[] args) {
        pressImage("E:/temp/watermark.png", "http://wsale.oss-cn-shanghai.aliyuncs.com/uploadfiles/123.jpg", 5,5, "");
    }
}
