package dk.madslee.imageCapInsets;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.graphics.drawable.DrawableCompat;

import dk.madslee.imageCapInsets.utils.NinePatchBitmapFactory;
import dk.madslee.imageCapInsets.utils.RCTImageLoaderListener;
import dk.madslee.imageCapInsets.utils.RCTImageLoaderTask;


public class RCTImageCapInsetView extends ImageView {
    private Rect mCapInsets;
    private String mUri;
    private int tintColor = -1;

    public RCTImageCapInsetView(Context context) {
        super(context);

        mCapInsets = new Rect();
    }

    public void setCapInsets(Rect insets) {
        mCapInsets = insets;
        if (mUri != null) {
            reload();
        }
    }

    public void setSource(String uri) {
        mUri = uri;
        reload();
    }

    public void setTintColor(int tintColor){
        this.tintColor = tintColor;
        reload();
    }

    public void reload() {
        final String key = mUri + "-" + mCapInsets.toShortString() + "-" + this.tintColor;
        final RCTImageCache cache = RCTImageCache.getInstance();

        if (cache.has(key)) {
            setBackground(cache.get(key).getConstantState().newDrawable());
            return;
        }

        RCTImageLoaderTask task = new RCTImageLoaderTask(mUri, getContext(), new RCTImageLoaderListener() {
            @Override
            public void onImageLoaded(Bitmap bitmap) {
                if (bitmap == null) {
                  return;
                }

                int top = dp2px(mCapInsets.top);
                int right = bitmap.getWidth() - dp2px(mCapInsets.right);
                int bottom = bitmap.getHeight() - dp2px(mCapInsets.bottom);
                int left = dp2px(mCapInsets.left);

                Drawable drawable = NinePatchBitmapFactory
                        .createNinePathWithCapInsets(getResources(), bitmap, top, left, bottom, right, null);
                setBackground(drawable);

                if(RCTImageCapInsetView.this.tintColor != -1){
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, RCTImageCapInsetView.this.tintColor);
                }

                cache.put(key, drawable);
            }
        });

        task.execute();
    }

    private int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
