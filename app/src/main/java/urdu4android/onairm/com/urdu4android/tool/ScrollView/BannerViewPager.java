package urdu4android.onairm.com.urdu4android.tool.ScrollView;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class BannerViewPager extends ViewPager {

    private int mTouchSlop;
    private float mDownX;
    private float mDownY;
    private boolean request;

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerViewPager(Context context) {
        super(context);
        init(context);
    }
    private void init(Context ctx){
        final ViewConfiguration configuration = ViewConfiguration.get(ctx);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                request=false;
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = event.getX();
                float curY = event.getY();
                float xDiff = Math.abs(mDownX - curX);
                float yDiff = Math.abs(mDownY - curY);
                if (xDiff > mTouchSlop && xDiff > yDiff) {
//                    getParent().requestDisallowInterceptTouchEvent(true);
                    request=true;
                } else {

                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
////                if (mAutoTask != null) {
////                    mAutoTask.pause();
////                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
////                if (mAutoTask != null) {
////                    mAutoTask.resume();
////                }
//                break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

    public boolean request() {
        return request;
    }
}
