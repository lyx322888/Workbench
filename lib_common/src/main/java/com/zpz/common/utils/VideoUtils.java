package com.zpz.common.utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;

import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.util.VideoProgressListener;

import java.io.File;

public class VideoUtils {

    //压缩文件
    public static void videoCompressor(Context context, String filePath, final VieoProgressListener videoProgressListener) throws Exception {
        File moviesDir = new File(context.getCacheDir(), "movie");
        moviesDir.mkdirs();
        String filePrefix = "speed_video";
        String fileExtn = ".mp4";
        File dest = new File(moviesDir, filePrefix + fileExtn);
        int fileNo = 0;
        while (dest.exists()) {
            fileNo++;
            dest = new File(moviesDir, filePrefix + fileNo + fileExtn);
        }
        final String outputfilePath = dest.getAbsolutePath();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);
        final int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        VideoProcessor.processor(context)
                .input(filePath)
                .output(outputfilePath)
                .outWidth(originWidth)
                .outHeight(originHeight)
                .progressListener(new VideoProgressListener() {
                    @Override
                    public void onProgress(float progress) {
                        videoProgressListener.onProgress(progress,outputfilePath);
                    }
                })
                .process();
    }
    //
    public interface VieoProgressListener{
        void onProgress(float progress,String outputfilePath);
    }
}
