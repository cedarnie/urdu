package urdu4android.onairm.com.urdu4android.tool.ScrollView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.android.volley.toolbox.NetworkImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.tool.Utils;
import urdu4android.onairm.com.urdu4android.volley.images.ImageCacheManager;

/**
 *  Filename:    AutoScrollViewBanner.java    Description:   
 *  @author:     shangqu    @version:    1.0  
 *  Create at:   2013-8-9 下午4:13:14   
 */
public class AutoScrollViewBanner extends RelativeLayout  {

    private final static int VIEW_BANNER_CLICK_MAX_LENGTH = 40;
    // 图片总数
    private int mTotal = 0;
    private Context mCtx;
    private int mCurIndex;
    boolean mIsFirst;
    private Timer mTimer;
    private AutoScrollTimerTask myTimerTask;
    private Handler myHandler;
    private boolean mNeedAuto = true;
    private boolean mCanScroll = true;
    public boolean isLoop = false;

    private LinearLayout mIndicatorLayout;
    private BannerViewPager mViewFlipper;
    /* 指示器的单个item */
    private List<ImageView> mIndicatorList;

    private int mIndicatorBg;
    private int mIndicatorLyBg;

    private List<IBanner> loopListImg;

    // TimerTask间隔定为500ms
    private static long TIMER_PERIOD = 500L;
    // flipper自动切换间隔为 8 * 500ms
    private static int TIMER_COUNTER = 12;

    /* 指示器位置改变事件回掉接口 */
    public interface OnChangeListener {
        /**
         * 指示器位置回调函数
         *
         * @param last    原指示器位置
         * @param current 当前指示器位置
         */
        void onChange(int last, int current);
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(int index, String imgAction);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public BannerViewPager getViewFlipper() {
        return mViewFlipper;
    }

    public AutoScrollViewBanner(Context ctx, int w, int h) {
        super(ctx);
        this.mCtx = ctx;
        this.mCurIndex = 0;
        this.mIsFirst = true;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_autoscroll_banner, this, true);
        mViewFlipper = (BannerViewPager) findViewById(R.id.auto_scroll_banner);
        mIndicatorLayout = (LinearLayout) findViewById(R.id.auto_scroll_view_indicator);
        mIndicatorList = new ArrayList<ImageView>();
        mIndicatorBg = R.drawable.selector_indicator;

        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Utils.getScreenWith(ctx) * h / w);
        mViewFlipper.setLayoutParams(layoutParams);
    }


    public void setAuto(boolean f) {
        mNeedAuto = f;
    }

    public void setCanScroll(boolean canScroll) {
        mCanScroll = canScroll;
    }

    @Override
    protected void onAttachedToWindow() {
        if (mNeedAuto) {
            startFlipperTimer();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopFlipperTimer();
    }

    public void setIndicatorImg(int imgRes) {
        mIndicatorBg = imgRes;
    }

    public void setIndicatorLyBg(int bgRes) {
        mIndicatorLyBg = bgRes;
    }

    class TGPageAdapter extends PagerAdapter {

        private List<IBanner> listImage;

        public TGPageAdapter(List<IBanner> listImage) {
            this.listImage = listImage;
        }

        @Override
        public int getCount() {
            if (listImage == null) {
                return 0;
            }
            if (listImage.size() == 1) {
                return 1;
            }
            return listImage.size() * 1000;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= listImage.size();
            // change WebImageView to NetworkImageView by nxs
//            WebImageView downloadImageView = new WebImageView(getContext());
//            downloadImageView.setDefaultResId(R.drawable.default_bigicon);
////            downloadImageView.setCover(R.drawable.bg_banner_shadow);
            NetworkImageView downloadImageView = new NetworkImageView(getContext());
            downloadImageView.setDefaultImageResId(R.drawable.default_bigicon);
            final int index = position;
            downloadImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onItemClick(index, listImage.get(index).getJumpUri());
                    }
                }
            });
            if (TextUtils.isEmpty(listImage.get(position).getImageUrl())) {
                downloadImageView.setScaleType(ImageView.ScaleType.CENTER);
//                downloadImageView.setDefaultResId(R.drawable.default_bigicon);
                downloadImageView.setDefaultImageResId(R.drawable.default_bigicon);
            } else {
                downloadImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                downloadImageView.setImageUrl(listImage.get(position).getImageUrl());
                downloadImageView.setImageUrl(listImage.get(position).getImageUrl(), ImageCacheManager.getInstance().getImageLoader());

            }
            container.addView(downloadImageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return downloadImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (null != object) {
                container.removeView((View) object);
            }
        }

    }

    TGPageAdapter pageAdapter;

    public void initData(ArrayList<IBanner> listImg) {
        if (listImg == null) {
            return;
        }
        loopListImg = listImg;
        mViewFlipper.removeAllViews();
        mIndicatorLayout.removeAllViews();
        mIndicatorList.clear();
        mTotal = 0;
        pageAdapter = new TGPageAdapter(loopListImg);
        mViewFlipper.setAdapter(pageAdapter);
        mViewFlipper.setOnPageChangeListener(onPageChangeListener);
        mTotal = loopListImg.size();
        for (int i = 0; i < mTotal; i++) {
            final int index = i;
//			ScreenTools screenTools = ScreenTools.instance(getContext().getApplicationContext());
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = Utils.dip2px(getContext(), 8.0f);
            imageView.setImageResource(mIndicatorBg);
            mIndicatorLayout.addView(imageView, params);
            mIndicatorList.add(imageView);
        }

        mIndicatorLayout.setBackgroundResource(mIndicatorLyBg);
        if (listImg.size() != 1) {
            mViewFlipper.setCurrentItem(listImg.size() * 500, false);
        }
        updateIndicator(0, 0);
    }

    static class MyHandler extends Handler {

        WeakReference<AutoScrollViewBanner> mBanner;

        MyHandler(AutoScrollViewBanner banner) {
            mBanner = new WeakReference<AutoScrollViewBanner>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null == mBanner.get()) {
                return;
            }
            if (mBanner.get().mIsFirst != false) {
                mBanner.get().flipperShowNext();
            } else {
                mBanner.get().mIsFirst = true;
            }
        }
    }

    /**
     * 启动MyViewFlipper中的定时器
     */
    public void startFlipperTimer() {
        stopFlipperTimer();
        myHandler = new MyHandler(this);
        this.mTimer = new Timer();
        this.myTimerTask = new AutoScrollTimerTask();
        this.mTimer.scheduleAtFixedRate(myTimerTask, 0, TIMER_PERIOD);
    }

    /**
     * 暂停flipper定时器
     */
    public void stopFlipperTimer() {
        if (this.myTimerTask != null) {
            myTimerTask.mCount = 0;
            this.myTimerTask.cancel();
            this.myTimerTask = null;
        }
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
    }

    /**
     * 重置计数~
     */
    public void resetTimer() {
        if (null != myTimerTask) {
            myTimerTask.mCount = 0;
        }
    }

    OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            updateIndicator(mCurIndex, arg0);
            mCurIndex = arg0;
            resetTimer();
        }
    };

    /**
     * 往后翻页
     */
    public void flipperShowNext() {
        if (mTotal == 0) {
            return;
        }
        if (!mCanScroll) {
            return;
        }

        int old = mCurIndex;
        mCurIndex = (mCurIndex + 1) % pageAdapter.getCount();
        mViewFlipper.setCurrentItem(mCurIndex);
        updateIndicator(old, mCurIndex);
    }

    private void updateIndicator(int old, int now) {
        if (loopListImg.size() == 0) {
            return;
        }
        old %= loopListImg.size();
        now %= loopListImg.size();
        ImageView oldView = mIndicatorList.get(old);
        ImageView curView = mIndicatorList.get(now);
        oldView.setSelected(false);
        curView.setSelected(true);
    }

    public int getTotal() {
        return this.mTotal;
    }

    class AutoScrollTimerTask extends TimerTask {

        @Override
        public void run() {
            if (this.mCount >= TIMER_COUNTER) {
                myHandler.sendMessage(new Message());
                this.mCount = 0;
            } else {
                this.mCount++;
            }
        }

        public int mCount = 0;
    }

}
