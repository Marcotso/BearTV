package com.fongmi.android.tv.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.fongmi.android.tv.App;
import com.fongmi.android.tv.R;

public class ImgUtil {

    public static void load(String url, ImageView view) {
        Glide.with(App.get()).load(url).centerCrop().error(R.drawable.ic_img_error).placeholder(R.drawable.ic_img_loading).into(view);
    }

    public static void load(String vodName, String vodPic, ImageView view) {
        if (TextUtils.isEmpty(vodPic)) onLoadFailed(vodName, view);
        else Glide.with(App.get()).asBitmap().load(vodPic).skipMemoryCache(true).centerCrop().sizeMultiplier(Prefers.getThumbnail()).signature(new ObjectKey(vodPic + "_" + Prefers.getQuality())).placeholder(R.drawable.ic_img_loading).listener(getListener(vodName, view)).into(view);
    }

    private static RequestListener<Bitmap> getListener(String vodName, ImageView view) {
        return new RequestListener<>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                ImgUtil.onLoadFailed(vodName, view);
                return true;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };
    }

    private static void onLoadFailed(String vodName, ImageView view) {
        String text = vodName.isEmpty() ? "" : vodName.substring(0, 1);
        if (text.isEmpty()) view.setImageResource(R.drawable.ic_img_error);
        else view.setImageDrawable(TextDrawable.builder().beginConfig().width(ResUtil.dp2px(150)).height(ResUtil.dp2px(200)).endConfig().buildRect(text, ColorGenerator.MATERIAL.getColor(text)));
    }
}
