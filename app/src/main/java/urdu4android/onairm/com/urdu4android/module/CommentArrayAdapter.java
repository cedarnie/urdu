package urdu4android.onairm.com.urdu4android.module;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.volley.images.ImageCacheManager;


public class CommentArrayAdapter extends ArrayAdapter<Comment> {

	private final String TAG = getClass().getSimpleName();
	private  List<Comment> commentList;
	private CommentData mCommentData;

    static class ViewHolder{
        private TextView tvUsername;
        private TextView tvDetails;
        private NetworkImageView mImageView;
    }

	public CommentArrayAdapter(Context context, CommentData newData) {
		super(context, R.layout.activity_video_comment_list_item);
        commentList = newData.getVideos();
        mCommentData = newData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder viewHolder;

		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.activity_video_comment_list_item, null);

			viewHolder = new ViewHolder();
            viewHolder.tvUsername = (TextView) v.findViewById(R.id.textView_username);
            viewHolder.tvDetails = (TextView) v.findViewById(R.id.textView_detail);
            viewHolder.mImageView = (NetworkImageView) v.findViewById(R.id.imageView_avatar);

			v.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		Comment comment = commentList.get(position);
		if(comment != null){
            viewHolder.tvUsername.setText(comment.getUsername());
            viewHolder.tvDetails.setText(comment.getDetails());
            viewHolder.mImageView.setImageUrl(comment.getIconUrl(), ImageCacheManager.getInstance().getImageLoader());
		}

		return v;
	}

	@Override
	public int getCount() {
        return commentList.size();
	}

    @Override
    public Comment getItem(int position) {
        return commentList.get(position);
    }

    public CommentData getCommentData() {
        return mCommentData;
    }

    public void clear(){
        commentList.clear();
    }

	public void add(List<Comment> newData)
	{
		if(!newData.isEmpty()){
            commentList.addAll(newData);
            Log.e("videoList size:", String.valueOf(commentList.size()));
			notifyDataSetChanged();
		}
	}



}
