package com.example.weatherapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ImageLoader {
    private static final String TAG = ImageLoader.class.getSimpleName();
    @NonNull
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private ImageLoader() {
    }

    @Contract("null -> null")
    @Nullable
    public static Bitmap loadImage(@Nullable final String imageLink) {
        if (imageLink == null)
            return null;
        final Future<Bitmap> call = executorService.submit(new Callable<Bitmap>() {
            @Override
            public Bitmap call() {
                final URL imageUrl;
                try {
                    imageUrl = new URL(imageLink);
                    return BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                } catch (Exception e) {
                    Log.e(TAG, "Error while loading imageUrl", e);
                }
                return null;
            }
        });

        try {
            return call.get();
        } catch (Exception e) {
            Log.e(TAG, "Error while executing call", e);
        }
        return null;
    }
}
