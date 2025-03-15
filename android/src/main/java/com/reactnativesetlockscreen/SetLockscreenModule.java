package com.reactnativesetlockscreen;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SetLockscreenModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "SetLockscreen";
    private final ReactApplicationContext reactContext;

    public SetLockscreenModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void setLockScreen(String imageUrl, Promise promise) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            promise.reject("ERROR", "Lock screen wallpaper setting is not supported on this device.");
            return;
        }

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(reactContext);

            wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
            promise.resolve("Lock screen wallpaper set successfully.");
        } catch (IOException e) {
            Log.e("SetLockscreen", "Error setting lock screen wallpaper", e);
            promise.reject("ERROR", "Failed to set lock screen wallpaper: " + e.getMessage());
        }
    }
}
