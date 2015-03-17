package urdu4android.onairm.com.urdu4android.tool;

/**
 * Created by apple on 15/3/16.
 */


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import urdu4android.onairm.com.urdu4android.R;


public class Player  implements View.OnClickListener,OnBufferingUpdateListener,
        OnCompletionListener, MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback {
    private int videoWidth;
    private int videoHeight;
    public MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private SeekBar skbProgress;
    private Timer mTimer=new Timer();

    private LinearLayout layoutOperation;
    private boolean display;   //用于是否显示操作栏
    private int postSize;    //保存已播视频大小
    private boolean flagPlay = true;   //用于判断视频是否在播放中
    //    private upDateSeekBar update;   //更新进度条用
    private ImageButton btnPlay;  //播放或停止按钮
    private ImageButton btnFullscreen;
    private SeekBar seekbar;   //进度条控件

    private String  url;
    private SurfaceView surfaceView;

    private int fullWidth;
    private int fullHeight;

    public Player(SurfaceView surfaceView, SeekBar skbProgress, LinearLayout layoutOperation,
                  ImageButton btnPlayUrl, ImageButton btnFullscreen, String url, int fullWidth, int fullHeight)
    {
        this.surfaceView=surfaceView;
        this.btnPlay=btnPlayUrl;
        this.btnFullscreen=btnFullscreen;
        this.layoutOperation = layoutOperation;
        this.skbProgress=skbProgress;
        this.url=url;
        this.surfaceView.setOnClickListener(this);
        this.btnPlay.setOnClickListener(this);
        this.btnFullscreen.setOnClickListener(this);
        this.fullWidth=fullWidth;
        this.fullHeight=fullHeight;

        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /*******************************************************
     * 通过定时器和Handler来更新进度条
     ******************************************************/
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if(mediaPlayer==null)
                return;
            if(flagPlay==false)
                return;
            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false ) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {

            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            if (duration > 0) {
                long pos = skbProgress.getMax() * position / duration;
                skbProgress.setProgress((int) pos);
            }
        };
    };


    public void playUrl(String videoUrl)
    {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepare();//prepare之后自动播放
            //mediaPlayer.start();
            btnPlay.setBackgroundResource(R.drawable.btn_pause);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void play()
    {
        mediaPlayer.start();
    }

    public void pause()
    {
        mediaPlayer.pause();
    }

    public void stop()
    {
        if (mediaPlayer != null) {
//            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        Log.e("mediaPlayer", "surface changed");
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);

            this.playUrl(url);
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }
        Log.e("mediaPlayer", "surface created");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            postSize = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
            mediaPlayer.release();
            flagPlay=false;
        }
        Log.e("mediaPlayer", "surface destroyed");
    }


    @Override
    /**
     * 通过onPrepared播放
     */
    public void onPrepared(MediaPlayer arg0) {
        Log.e("mediaPlayer", "onPrepared");
//        videoWidth = mediaPlayer.getVideoWidth();
//        videoHeight = mediaPlayer.getVideoHeight();
//        if (videoHeight != 0 && videoWidth != 0) {
//            arg0.start();
//        }

        layoutOperation.setVisibility(View.GONE);  //准备完成后，隐藏控件

        display = false;
        if (mediaPlayer != null) {
            mediaPlayer.start();  //开始播放视频
            flagPlay=true;
        } else {
            return;
        }
        if (postSize > 0) {  //说明中途停止过（activity调用过pase方法，不是用户点击停止按钮），跳到停止时候位置开始播放
            Log.i("hck", "seekTo ");
            mediaPlayer.seekTo(postSize);   //跳到postSize大小位置处进行播放
        }
//        new Thread(update).start();   //启动线程，更新进度条

    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        skbProgress.setSecondaryProgress(bufferingProgress);
        int currentProgress=skbProgress.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
        Log.e(currentProgress+"% play", bufferingProgress + "% buffer");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_play:
                /**
                 * 如果视频在播放，则调用mediaPlayer.pause();停止播放视频，反之，mediaPlayer.start()
                 */
                if (mediaPlayer.isPlaying()) {
                    btnPlay.setBackgroundResource(R.drawable.btn_play);
                    mediaPlayer.pause();
//                    postSize = mediaPlayer.getCurrentPosition();
                } else {
//                    if (flag == false) {
//                        flag = true;
//                        new Thread(update).start();
//                    }
                    mediaPlayer.start();
                    btnPlay.setBackgroundResource(R.drawable.btn_pause);

                }
                break;
            case R.id.surfaceView_video:
                if (display) {
                    layoutOperation.setVisibility(View.GONE);
                    display = false;
                } else {
                    layoutOperation.setVisibility(View.VISIBLE);
                    display = true;
                }
                break;
            case R.id.button_fullscreen:
                surfaceHolder.setFixedSize(fullWidth, fullHeight);
                break;

        }
    }

}
