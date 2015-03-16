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


public class NovelArrayAdapter extends ArrayAdapter<Novel> {

	private final String TAG = getClass().getSimpleName();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm a", Locale.US);
	/**
	 *  The data that drives the adapter
	 */
	private  List<Novel> novelList;

	/**
	 * The last network response containing twitter metadata
	 */
	private NovelData mNovelData;


    static class ViewHolder{
        private TextView tvTitle;
        private TextView tvAuther;
        private NetworkImageView mImageView;
    }

	public NovelArrayAdapter(Context context, NovelData newData) {
		super(context, R.layout.activity_novel_list_item);
        novelList = newData.getNovels();
        mNovelData = newData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder viewHolder;

		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.activity_novel_list_item, null);

			viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) v.findViewById(R.id.textView_title);
            viewHolder.tvAuther = (TextView) v.findViewById(R.id.textView_auther);
            viewHolder.mImageView = (NetworkImageView) v.findViewById(R.id.imageView_novel);

			v.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		Novel novel = novelList.get(position);
		if(novel != null){
            viewHolder.tvTitle.setText(novel.getTitle());
            viewHolder.tvAuther.setText(novel.getAuthor());
            viewHolder.mImageView.setDefaultImageResId(R.drawable.bingbing);
           // viewHolder.mImageView.setImageUrl(novel.getImgUrl(), ImageCacheManager.getInstance().getImageLoader());
		}

		return v;
	}

	@Override
	public int getCount() {
        return novelList.size();
	}

    @Override
    public Novel getItem(int position) {
        return novelList.get(position);
    }

    private String formatDisplayDate(Date date){
		if(date != null){
			return sdf.format(date);
		}
		return "";
	}

    public NovelData getmNovelData() {
        return mNovelData;
    }

    public void clear(){
        novelList.clear();
    }

	public void add(List<Novel> newData)
	{
		if(!newData.isEmpty()){
            novelList.addAll(newData);
            Log.e(TAG+"-novelList size:", String.valueOf(novelList.size()));
			notifyDataSetChanged();
		}
	}


}
