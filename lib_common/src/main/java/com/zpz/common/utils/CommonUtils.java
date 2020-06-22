package com.zpz.common.utils;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.zpz.common.base.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//工具
public class CommonUtils {
    /**
     * md5加密
     * @param sourceStr 要加密的字符串
     * @return
     */
    public static String EncoderByMd5(String sourceStr) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

    /**
     * 获取颜色
     * @param id 获取颜色
     * @return
     */
    @ColorInt
    public static int getColor( @ColorRes int id) {
      return   ContextCompat.getColor(BaseApplication.getInstance(),id);
    }


}
