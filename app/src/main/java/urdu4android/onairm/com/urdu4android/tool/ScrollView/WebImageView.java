package urdu4android.onairm.com.urdu4android.tool.ScrollView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.RequestCreator;
//import com.squareup.picasso.Transformation;

/**
 * Created by 6a209 on 14/11/10.
 * <p/>
 * a simple Picasso ImageView wrap
 */
public class WebImageView extends ImageView {


    private boolean mIsAttachedToWindow;

    private String mUrl;

    private Drawable mDefaultDrawable;

    private Transformation mTransoformation;

    private int mWidth = 0;
    private int mHeight = 0;
    private boolean isWidthFixMode = false;

    public WebImageView(Context context) {
        this(context, null);
    }

    public WebImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setImageUrl(String url, Transformation transformation) {
        mTransoformation = transformation;
        mUrl = url;
        if (TextUtils.isEmpty((url))) {
            return;
        }
        if (mIsAttachedToWindow) {
            RequestCreator requestCreator = Picasso.with(getContext()).load(mUrl);
            if (null != mDefaultDrawable) {
                requestCreator = requestCreator.placeholder(mDefaultDrawable);
            }
            if (null != transformation) {
                requestCreator = requestCreator.transform(transformation);
            }
            requestCreator.into(this);
        }
    }

    public void setImageUrl(String url) {
        setImageUrl(url, null);
    }

    public void setCycleImageUrl(String url) {
        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int size = Math.min(source.getWidth(), source.getHeight());

                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;

                Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                if (squaredBitmap != source) {
                    source.recycle();
                }

                Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                paint.setShader(shader);
                paint.setAntiAlias(true);

                float r = size / 2f;
                canvas.drawCircle(r, r, r, paint);

                squaredBitmap.recycle();
                return bitmap;
            }


            @Override
            public String key() {
                return "cycle";
            }
        };
        setImageUrl(url, transformation);
    }

    public void setRoundCornerImageUrl(String url, final int corner) {
        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                if (source == null) {
                    return null;
                }

                int w = source.getWidth(), h = source.getHeight();
                if (w <= 0 || h <= 0) {
                    return null;
                }

                // 生成圆角
                Bitmap round = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(round);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                canvas.drawRoundRect(new RectF(0, 0, w, h), corner, corner, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

                // 生成结果图片
                Bitmap out = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas outCanvas = new Canvas(out);
                outCanvas.drawBitmap(source, 0, 0, null);
                outCanvas.drawBitmap(round, 0, 0, paint);
                source.recycle();
                round.recycle();

                return out;
            }

            @Override
            public String key() {
                return "Round";
            }
        };

    }

    public void setDefaultDrawable(Drawable drawable) {
        mDefaultDrawable = drawable;
    }

    public void setDefaultResId(int id) {
        mDefaultDrawable = getResources().getDrawable(id);
    }


    @Override
    public void onAttachedToWindow() {
        mIsAttachedToWindow = true;
        setImageUrl(mUrl, mTransoformation);
        super.onAttachedToWindow();
    }


    @Override
    public void onDetachedFromWindow() {
        Picasso.with(getContext()).cancelRequest(this);
        mIsAttachedToWindow = false;
        setImageBitmap(null);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isWidthFixMode) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = 0;
            if (mWidth > 0) {
                height = width * mHeight / mWidth;
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setAspectRatio(int width, int height) {
        if (width > 0 && height > 0) {
            this.isWidthFixMode = true;
            this.mWidth = width;
            this.mHeight = height;
        }
    }

}
