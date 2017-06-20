package jb.util;

import it.sauronsoftware.jave.*;
import jb.absx.F;
import jb.util.oss.OSSUtil;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by wenming on 2016/9/19.
 */
public class MP3Util {
    public static String convert(String src, HttpServletRequest request) {
        String filePath = "", fileName = "";
        File source = null, target = null;
        try {
            if(F.empty(src))  return null;
            String realPath = request.getSession().getServletContext().getRealPath("/");
            filePath = src.substring(0, src.lastIndexOf("/"));
            fileName = src.substring(src.lastIndexOf("/") + 1, src.lastIndexOf(".")) + ".mp3";
            // 来源文件
            source = new File(realPath + src);
            target = new File(realPath + filePath + "/" + fileName);
            AudioAttributes audio = new AudioAttributes();
            Encoder encoder = new Encoder();

//            for (String ss : encoder.getSupportedEncodingFormats()) {
//                System.out.println(ss);
//            }
            // pcm_s16le libmp3lame libvorbis libfaac
            audio.setCodec("libmp3lame");
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp3");
            attrs.setAudioAttributes(audio);

            encoder.encode(source, target, attrs);

        } catch(Exception e) {
            //e.printStackTrace();
        }
        String result = OSSUtil.putFile(OSSUtil.bucketName, target, filePath + "/" + fileName);
        if(source != null) source.delete();
        return result;
    }

    public static int getDuration(String filePath, HttpServletRequest request) {
        try {
            File file = new File(request.getSession().getServletContext().getRealPath("/") + filePath);
            MP3File f = (MP3File) AudioFileIO.read(file);
            MP3AudioHeader audioHeader = f.getMP3AudioHeader();
            String trackLen = audioHeader.getTrackLengthAsString();
            System.out.println("trackLen:" + trackLen);
            String[] times = trackLen.split(":");
            String slen = "0", mlen = "0", hlen = "0";
            if(times.length == 1) {
                slen = times[0];
            } else if(times.length == 2) {
                mlen = times[0];
                slen = times[1];
            } else if(times.length == 3) {
                hlen = times[0];
                mlen = times[1];
                slen = times[2];
            }

           return Integer.parseInt(hlen)*60*60 + Integer.parseInt(mlen)*60 + Integer.parseInt(slen);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
