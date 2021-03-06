package com.zpz.common.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.zpz.common.R;

import java.util.ArrayList;

//选择图片
public class ChoosePictureUtils {
    //选择图片
    public static void choosePictureCommon(Context context, int maxSelectCount, final Action<ArrayList<String>> tAction){
        Album.image(context)
                .multipleChoice()
                .widget(Widget.newDarkBuilder(context)
                        .title("选择图片") // Title.
                        .mediaItemCheckSelector(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.appColor))//按钮颜色
                        .statusBarColor(ContextCompat.getColor(context, R.color.appColor))
                        .toolBarColor(ContextCompat.getColor(context, R.color.appColor)).build())// Toolbar color..build())// StatusBar color.)
                .camera(true)
                .columnCount(3)
                .selectCount(maxSelectCount)
                .onResult(new com.yanzhenjie.album.Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {
                            list.add(result.get(i).getPath());
                        }
                        tAction.onAction(list);
                    }
                }).start();
    }


    //记录选择过的图片
    public static void choosePictureChecked(Context context, int maxSelectCount, ArrayList<String> arrayList, final Action<ArrayList<String>> tAction){
        //构建albumFiles
        ArrayList<AlbumFile> albumFiles = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            AlbumFile albumFile = new AlbumFile();
            albumFile.setChecked(true);
            albumFile.setPath(arrayList.get(i));
            albumFiles.add(albumFile);
        }
        Album.image(context)
                .multipleChoice()
                .checkedList(albumFiles)
                .widget(Widget.newDarkBuilder(context)
                        .title("选择图片") // Title.
                        .mediaItemCheckSelector(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.appColor))//按钮颜色
                        .statusBarColor(ContextCompat.getColor(context, R.color.appColor))
                        .toolBarColor(ContextCompat.getColor(context, R.color.appColor)).build())// Toolbar color..build())// StatusBar color.)
                .camera(true)
                .columnCount(3)
                .selectCount(maxSelectCount)
                .onResult(new com.yanzhenjie.album.Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {
                            list.add(result.get(i).getPath());
                        }
                        tAction.onAction(list);
                    }
                }).start();
    }


    public interface Action<T> {

        /**
         * When the action responds.
         *
         * @param result the result of the action.
         */
        void onAction(@NonNull T result);

    }
}
