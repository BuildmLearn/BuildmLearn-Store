package org.buildmlearn.appstore.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class of the Volley
 */
public class VolleySingleton {

    private static VolleySingleton instance;
    private static ImageLoader imageLoader;

    /**
     * Private Constructor of the class
     * @param context Context object of the current activity
     */
    private VolleySingleton(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);


            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    /**
     * Gets the instance of the Volley
     * @param context Context object of the current Activity
     * @return The object of VolleySingleton class
     */
    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    /**
     * Gets the ImageLoader instance of the Volley
     * @return ImageLoader Object
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}