package urdu4android.onairm.com.urdu4android.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.TitleController;

public class VideoDescActivity extends Activity implements View.OnClickListener {


    private static final String EXTRA_PENDING_INTENT = "pending_intent";
    private TitleController titleController;
    private ImageButton btnplay;
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private int position;



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
        titleController = new TitleController(this, findViewById(R.id.subTitleBar));
        btnplay=(ImageButton)this.findViewById(R.id.btnplay);

        btnplay.setOnClickListener(this);

        mediaPlayer=new MediaPlayer();
        surfaceView=(SurfaceView) this.findViewById(R.id.surfaceView_video);

        //设置SurfaceView自己不管理的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (position>0) {
                    try {
                        //开始播放
                        play();
                        //并直接从指定位置开始播放
                        mediaPlayer.seekTo(position);
                        position=0;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnplay:
                play();
                break;
            default:
                break;
        }

    }
    @Override
    protected void onPause() {
        //先判断是否正在播放
        if (mediaPlayer.isPlaying()) {
            //如果正在播放我们就先保存这个播放位置
            position=mediaPlayer.getCurrentPosition()
            ;
            mediaPlayer.stop();
        }
        super.onPause();
    }

    private void play() {
        try {
            mediaPlayer.reset();
            mediaPlayer
                    .setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置需要播放的视频
            mediaPlayer.setDataSource("/storage/emulated/0/Download/test-dance.3gp");
            //把视频画面输出到SurfaceView
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepare();
            //播放
            mediaPlayer.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}


