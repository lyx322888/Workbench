package com.zpz.common.utils;

import com.zpz.common.base.AppConfig;
import com.zpz.common.utils.base64.Base64Object;

public class QiniuUtils {
    //给图片加水印
    public static String watermark (String url,String content){

        content = Base64Object.stringToBase64(content);
        if (url.startsWith(AppConfig.IMAGES)) {
//            url =  String.format(url+"?imageView2/0/q/75|watermark/2/text/%s/font/5a6L5L2T/fontsize/940/fill/IzAwMDAwMA==/dissolve/100/gravity/SouthEast/dx/10/dy/10",content);
            url =  url+"?watermark/2/text/"+content+"/font/5a6L5L2T/fontsize/940/fill/I0Y1RUZFRg==/dissolve/90/gravity/South/dx/10/dy/100";
            return url;
        }else {
            return "";
        }

    }


}
