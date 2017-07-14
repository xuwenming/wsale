package jb.util.wx;

import com.alibaba.fastjson.JSONObject;
import jb.absx.F;
import jb.util.Constants;
import jb.util.EnumConstants;
import jb.util.oss.OSSUtil;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by wenming on 2016/8/17.
 */
public class DownloadMediaUtil {

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExtName(String contentType) {
        String fileEndWitsh = "";
        if ("image/jpeg".equals(contentType))
            fileEndWitsh = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileEndWitsh = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileEndWitsh = ".amr";
        else if ("video/mp4".equals(contentType))
            fileEndWitsh = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileEndWitsh = ".mp4";
        return fileEndWitsh;
    }

    public static String downloadMedia(String realPath, String mediaId, String dirName) {
        return downloadMedia(realPath, mediaId, dirName, EnumConstants.MSG_TYPE.IMAGE.getCode());
    }

    /**
     * 获取媒体文件
     * @param mediaId 媒体文件id
     * @param
     * */
    public static String downloadMedia(String realPath, String mediaId, String dirName, String type) {
        if(F.empty(mediaId)) return null;

//        String filePath = null;
        // 拼接请求地址
        String requestUrl = WeixinUtil.getDownloadMediaUrl(mediaId);
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.connect();

            Calendar calendar = Calendar.getInstance();
            if (!dirName.endsWith("/")) dirName += "/";

            dirName = dirName + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);

            // 根据内容类型获取扩展名
            String fileExt = DownloadMediaUtil.getFileExtName(conn.getHeaderField("Content-Type"));
            // 将mediaId作为文件名
            String fileName = mediaId + fileExt;

            String result = null;
            if(EnumConstants.MSG_TYPE.IMAGE.getCode().equals(type)) {
                result = OSSUtil.putInputStream(OSSUtil.bucketName, conn.getInputStream(), Constants.UPLOADFILE+"/"+dirName+"/"+fileName);
            } else {
                realPath += Constants.UPLOADFILE+"/"+dirName;
                FileUtils.copyInputStreamToFile(conn.getInputStream(), new File(realPath, fileName));
                result = Constants.UPLOADFILE+"/"+dirName+"/"+fileName;
            }
            conn.disconnect();

            return result;
        } catch (Exception e) {
            String error = String.format("下载媒体文件失败：%s", e);
            System.out.println(error);
        }
        return null;
    }

    public static String downloadHeadImage(String headimgurl, String openid) {
        if(F.empty(headimgurl)) return null;

        try {
            URL url = new URL(headimgurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.connect();

            if(!F.empty(openid) && F.empty(DownloadMediaUtil.getFileExtName(conn.getHeaderField("Content-Type")))) {
                conn.disconnect();
                JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getUserInfoUrl(openid, null), "GET", null));
                return downloadHeadImage(jsonObject.getString("headimgurl"));
            }

            String result = OSSUtil.putInputStream(OSSUtil.bucketName, conn.getInputStream(), headimgurl.substring(headimgurl.indexOf("mmopen")));
            conn.disconnect();

            return result;
        } catch (Exception e) {
            String error = String.format("上传头像失败：%s", e);
            System.out.println(error);
        }
        return headimgurl;
    }

    public static String downloadHeadImage(String headimgurl) {
        return downloadHeadImage(headimgurl, null);
    }
}
