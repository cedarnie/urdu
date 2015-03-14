package urdu4android.onairm.com.urdu4android.module;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.module.Video;
import urdu4android.onairm.com.urdu4android.module.VideoData;
import urdu4android.onairm.com.urdu4android.module.VideoManager;
import urdu4android.onairm.com.urdu4android.volley.images.ImageCacheManager;


//public class VideoArrayAdapter extends ArrayAdapter<String> implements Listener<VideoData>, ErrorListener {
public class VideoArrayAdapter extends ArrayAdapter<Video> {

	private final String TAG = getClass().getSimpleName();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm a", Locale.US);
	/**
	 *  The data that drives the adapter
	 */
	private  List<Video> videoList;

	/**
	 * The last network response containing twitter metadata
	 */
	private VideoData mVideoData;


    static class ViewHolder{
        private TextView tvTitle;
        private TextView tvIntro;
        private NetworkImageView mImageView;
    }

	public VideoArrayAdapter(Context context, VideoData newData) {
		super(context, R.layout.activity_video_list_item);
        videoList = newData.getVideos();
        mVideoData = newData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder viewHolder;

		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.activity_video_list_item, null);

			viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) v.findViewById(R.id.textView_title);
            viewHolder.tvIntro = (TextView) v.findViewById(R.id.textView_desc);
            viewHolder.mImageView = (NetworkImageView) v.findViewById(R.id.imageView_video);

			v.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		Video video = videoList.get(position);
		if(video != null){
            viewHolder.tvTitle.setText(video.getTitle());
            viewHolder.tvIntro.setText(video.getIntro());
            viewHolder.mImageView.setImageUrl(video.getImgUrl(), ImageCacheManager.getInstance().getImageLoader());
		}

		return v;
	}

	@Override
	public int getCount() {
        return videoList.size();
	}

    @Override
    public Video getItem(int position) {
        return videoList.get(position);
    }

    private String formatDisplayDate(Date date){
		if(date != null){
			return sdf.format(date);
		}
		return "";
	}

    public VideoData getmVideoData() {
        return mVideoData;
    }

    public void clear(){
        videoList.clear();
    }

	public void add(List<Video> newData)
	{
		if(!newData.isEmpty()){
            videoList.addAll(newData);
            Log.e("videoList size:", String.valueOf(videoList.size()));
			notifyDataSetChanged();
		}
	}


}
