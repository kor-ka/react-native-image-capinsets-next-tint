package dk.madslee.imageCapInsets;

import java.util.HashMap;
import android.graphics.drawable.Drawable;

public class RCTImageCache {
    private static RCTImageCache instance = null;
    private HashMap<String, Drawable> mCache;

    public static RCTImageCache getInstance() {
        if (instance == null) {
            instance = new RCTImageCache();
        }

        return instance;
    }

    protected RCTImageCache() {
        mCache = new HashMap<>();
    }

    public void put(String key, Drawable drawable) {
        mCache.put(key, drawable);
    }

    public Drawable get(String key) {
        return mCache.get(key);
    }

    public boolean has(String key) {
        return mCache.containsKey(key);
    }
}
