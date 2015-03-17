package urdu4android.onairm.com.urdu4android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.ActionBarAware;
import urdu4android.onairm.com.urdu4android.framework.SelectDrawable;
import urdu4android.onairm.com.urdu4android.framework.TitleController;

/**
 * Created by apple on 15/3/9.
 */

public class TabActivity extends FragmentActivity implements ActionBarAware, View.OnClickListener {
    public static final int REQ_CHOOSE_TITLE = 6;
    public static final int DIALOG_UPDATE = 2;
    public static final int DIALOG_BACK = 3;
    private final String TAG = getClass().getSimpleName();
    public static Integer[] needLoginTabId = new Integer[]{3}; // 点击"我"Tab页的进入登录页
    //    private static VersionResponse newestVersion;
    private static String cancelVersion;
    private static String currentVersion;
    public FragmentTabHost mTabHost;
    public TabWidget mTabWidget;
    public static TabActivity instance;


    public static TabActivity get() {
        return instance;
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TabActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_out_left, R.anim.stay);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        instance = this;
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());

        setContentView(R.layout.activity_tab);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabWidget = (TabWidget) findViewById(android.R.id.tabs);


        mTabHost.addTab(mTabHost.newTabSpec("live").setIndicator(makeIndicator(mTabWidget, R.string.live, new SelectDrawable(getResources(), R.drawable.ic_tab_work, R.drawable.ic_tab_work_off))),
                LiveFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("video").setIndicator(makeIndicator(mTabWidget, R.string.video, new SelectDrawable(getResources(), R.drawable.ic_tab_work, R.drawable.ic_tab_work_off))),
                VideoFragment.class, null);

//            Bundle arg = new Bundle();
//            arg.putBoolean(SimplePullListFragment.ARG_SHOW_TITLE_BAR, true);

        mTabHost.addTab(mTabHost.newTabSpec("novel").setIndicator(makeIndicator(mTabWidget, R.string.novel, new SelectDrawable(getResources(), R.drawable.ic_tab_work, R.drawable.ic_tab_work_off))),
                NovelFragment.class, null);

//        mTabHost.addTab(mTabHost.newTabSpec("poem").setIndicator(makeIndicator(mTabWidget, R.string.poem, new SelectDrawable(getResources(), R.drawable.ic_tab_work, R.drawable.ic_tab_work_off))),
//                NovelFragment.class, null);

    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }


    private View makeIndicator(TabWidget tabWidget, int resid, Drawable iconDrawable) {
        View view = getLayoutInflater().inflate(R.layout.tab_indicator, tabWidget, false);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getString(resid));
        textView.setCompoundDrawablesWithIntrinsicBounds(null, iconDrawable, null, null);
        return view;
    }

//    @Override
//    public void onBackPressed() {
//        showDialogSafely(DIALOG_BACK);
//
//    }

    @Override
    public TitleController getTitleController() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

//    private void fetchVersion() {
//        //read UpdateStatus From UpdateController
//        UpdateController.initVersion(this.getApplicationContext());
//        newestVersion = UpdateController.newestVersion;
////        cancelTime = UpdateController.cancelTime;
//        cancelVersion = UpdateController.cancelVersion;
//        currentVersion = UpdateController.currentVersion;
//
//        if (!App.RELEASE) return;
//
//        Map<String, String> paramPost = new HashMap<String, String>();
//        paramPost.put("platform", "android");
//        paramPost.put("version", currentVersion);
//        paramPost.put("cancel_version", cancelVersion);
//        paramPost.put("platform", "android");
//        getSpiceManager().execute(new SimpleRequest<VersionResponse>("/sys/updates", paramPost, VersionResponse.class), new BaseListener<VersionResponse>(this) {
////        getSpiceManager().execute(new VersionRequest(), new BaseListener<VersionResponse>(this) {
//            @Override
//            protected void onSuccess(VersionResponse versionResponse) {
//                newestVersion = versionResponse;
//                tryShowVersionDialog();
//            }
//        });
//    }


    private void showDialogSafely(int dialogUpdate) {
        try {
            showDialog(dialogUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
        if (currentFragment != null) {
            if (currentFragment instanceof View.OnClickListener) {
                View.OnClickListener fragment = (View.OnClickListener) currentFragment;
                fragment.onClick(v);
            } else {
                Log.i(TAG, "Current Fragment is not implementing onClickListener");
            }
        } else {
            Log.w(TAG, "Cannot find current Fragment");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}