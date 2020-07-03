package com.zpz.common.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String NAME = "videorecord";

    public static String getAppPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(SD_PATH);
        sb.append(File.separator);
        sb.append(NAME);
        sb.append(File.separator);
        return sb.toString();
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                String[] filePaths = file.list();
                for (String path : filePaths) {
                    Log.e("dfdf", "deleteFile: " );
                    deleteFile(filePath + File.separator + path);
                }
                file.delete();
            }
        }
    }



    //缓存目录
    public static File getCacheDir(Context context, String cacheName) {
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir != null) {
            File result = new File(cacheDir, cacheName);
            if (!result.mkdirs() && (!result.exists() || !result.isDirectory())) {
                // File wasn't able to create a directory, or the result exists but not a directory
             return null;
            }
            return result;
        }
        return null;
    }
}
