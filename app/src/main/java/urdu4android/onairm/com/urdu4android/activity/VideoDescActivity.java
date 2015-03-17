package urdu4android.onairm.com.urdu4android.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.support.v4.view.ViewPager.LayoutParams;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.TitleController;
import urdu4android.onairm.com.urdu4android.module.CommentArrayAdapter;
import urdu4android.onairm.com.urdu4android.module.CommentData;
import urdu4android.onairm.com.urdu4android.module.CommentManager;

import urdu4android.onairm.com.urdu4android.module.VideoArrayAdapter;
import urdu4android.onairm.com.urdu4android.module.VideoManager;
import urdu4android.onairm.com.urdu4android.tool.Player;

public class VideoDescActivity extends Activity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> , Response.Listener<CommentData>, Response.ErrorListener {

    private final String TAG = VideoDescActivity.class.getSimpleName();
    private static final String EXTRA_PENDING_INTENT = "pending_intent";
    private TitleController titleController;
//    private ImageButton btnplay;
//    private MediaPlayer mediaPlayer;
//    private SurfaceView surfaceView;
//    private int position;

    private CommentArrayAdapter adapter=null;
    private PullToRefreshListView pullListView;


    private SurfaceView surfaceView;
    private ImageButton btnPause, btnPlayUrl, btnStop;
    private SeekBar skbProgress;
    private Player player;

    private LinearLayout layoutOperation;
    private ImageButton btnFullscreen;

    private int fullWidth;
    private int fullHeight;



    public static void actionStart(Activity activity, Intent pendingIntent) {
        Intent intent = new Intent(activity, VideoDescActivity.class);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_desc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);   //全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 应用运行时，保持屏幕高亮，不锁屏
        titleController = new TitleController(this, findViewById(R.id.subTitleBar));


        fullWidth=getWindowManager().getDefaultDisplay().getWidth();
        fullHeight=getWindowManager().getDefaultDisplay().getHeight();

        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView_video);
        layoutOperation = (LinearLayout)findViewById(R.id.layout_operation);
        btnPlayUrl = (ImageButton) this.findViewById(R.id.button_play);
        btnFullscreen = (ImageButton) this.findViewById(R.id.button_fullscreen);

        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        String url = "/storage/emulated/0/Download/test-dance.3gp";
        player = new Player(surfaceView, skbProgress, layoutOperation, btnPlayUrl, btnFullscreen,url,fullWidth,fullHeight);

        pullListView = (PullToRefreshListView) findViewById(R.id.refreshListView_video_comment);
        pullListView.setOnRefreshListener(this);
        pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullListView.getRefreshableView().setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter == null) {
            adapter = new CommentArrayAdapter(this,new CommentData());
            pullListView.setAdapter(adapter);
            // Get the first page
            CommentManager.getInstance().requestGetComments(this, this, 1);
        }else{
            pullListView.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {   //activity销毁后，释放资源
        super.onDestroy();
        if (player != null) {
            player.stop();
        }
        System.gc();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        CommentManager.getInstance().requestGetComments(this, this, 1);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        CommentManager.getInstance().requestGetComments(this, this, adapter.getCommentData().getPage() + 1);
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e(TAG, "Error retrieving additional videos: " + volleyError.getMessage());
    }

    @Override
    public void onResponse(CommentData response) {
        if(response.getStatusCode()==0){
            adapter.getCommentData().setPage(response.getPage());
            if(response.getPage()==1){
                adapter.clear();
            }
            adapter.add(response.getVideos());
            adapter.notifyDataSetChanged();
            pullListView.onRefreshComplete();
        }

    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.button_fullscreen:
//                ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
//                lp.height = getWindowManager().getDefaultDisplay().getWidth();
//                lp.width = getWindowManager().getDefaultDisplay().getHeight();
//                surfaceView.setLayoutParams(lp);
//                break;
//
//            default:
//                break;
//        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }
}


