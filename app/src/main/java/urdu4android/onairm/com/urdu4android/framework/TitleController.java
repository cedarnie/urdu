package urdu4android.onairm.com.urdu4android.framework;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import urdu4android.onairm.com.urdu4android.R;


/**
 * 标题栏控制器
 * Created by apple on 15/3/9.
 */
public class TitleController {

    private final Activity activity;
    private TextView titleText;
    private Button titleBtnRight;
    private Button titleBtnLeft;
//    private ImageView titleBtnRightNewMessage;
    private ProgressBar progressBar;
//    private StyleSearchView searchView;
    private LinearLayout titleContainer;

    public TitleController(final Activity activity, View titleBar) {
        this.activity = activity;
        if (titleBar != null) {
            this.titleText = (TextView) titleBar.findViewById(R.id.titleText);
            this.titleBtnRight = (Button) titleBar.findViewById(R.id.titleBtnRight);
            this.titleBtnLeft = (Button) titleBar.findViewById(R.id.titleBtnLeft);

            this.progressBar = (ProgressBar) titleBar.findViewById(R.id.progressBar);
            this.titleContainer = (LinearLayout) titleBar.findViewById(R.id.titleTextContainer);
        }
        if (activity != null) {
            setDefaultBack(activity);
            setTitleText(activity.getTitle());
            titleBtnLeft.setCompoundDrawablesWithIntrinsicBounds(R.drawable.back, 0, 0, 0);
        } else {
            setTitleLeft(0, null);
        }
    }

    public TitleController(View titleBar) {
        this(null, titleBar);
    }

    public Activity getActivity() {
        return activity;
    }


    public void setTitleRight(int res, View.OnClickListener listener) {
        if (titleBtnRight == null) return;
        if (res == 0)
            titleBtnRight.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        else
            titleBtnRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
        titleBtnRight.setOnClickListener(listener);
    }


    public void setTitleLeft(int res, View.OnClickListener listener) {
        if (titleBtnLeft == null) return;
        if (res == 0)
            titleBtnLeft.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        else
            titleBtnLeft.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
        titleBtnLeft.setOnClickListener(listener);
    }


    public void setTitleRight(String text, View.OnClickListener listener) {
        if (titleBtnRight == null) return;
        titleBtnRight.setText(text);
        titleBtnRight.setOnClickListener(listener);
    }

    public void setDefaultBack(final Activity activity) {
        titleBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }




//    public void setTitleLeftWithRightDrawable(Location location, View.OnClickListener listener) {
//        if (leftRegionComponent == null) return;
//        leftRegionComponent.setTitleLeftWithRightDrawable(location, listener);
//    }

    public void setTitleText(CharSequence title) {
        if (titleText == null || titleContainer == null) return;
        titleContainer.setVisibility(View.VISIBLE);
        titleText.setText(title);
    }

    public void setProgress(int progress) {
        if (progressBar != null) {
            if (progress < 100) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

}
