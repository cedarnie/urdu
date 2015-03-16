package urdu4android.onairm.com.urdu4android.module;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import urdu4android.onairm.com.urdu4android.Config;
import urdu4android.onairm.com.urdu4android.volley.GsonRequest;
import urdu4android.onairm.com.urdu4android.volley.RequestManager;

public class VideoManager {

	private final String TAG = getClass().getSimpleName();
	private static VideoManager mInstance;

	private static String request_url = Config.SERVER_URL + Config.REQUEST_NEWS;
	private static String VIDEO_DEFAULT_SEARCH_TERM = "CapTech";
	private static String VIDEO_QUERY = "q";
	private static String VIDEO_INCLUDE_ENTITIES = "include_entities";
	private static String VIDEO_MAX_ID = "max_id";
	private static String VIDEO_PAGE_NUM = "page";
	private static String VIDEO_PAGE_SIZE = "pageSize";
	public static int VIDEO_DEFAULT_PAGE_SIZE = 10;

	public static VideoManager getInstance(){
		if(mInstance == null) {
			mInstance = new VideoManager();
		}

		return mInstance;
	}

	public <T> void requestGetVideos(Listener<VideoData> listener, ErrorListener errorListener,  int pageNum){

		Uri.Builder uriBuilder = Uri.parse(request_url).buildUpon()
//				.appendQueryParameter(VIDEO_QUERY, hashtag)
//				.appendQueryParameter(VIDEO_INCLUDE_ENTITIES, "true")
				.appendQueryParameter(VIDEO_PAGE_SIZE, "" + VIDEO_DEFAULT_PAGE_SIZE)
				.appendQueryParameter(VIDEO_PAGE_NUM, "" + pageNum);

		String uri = uriBuilder.build().toString();
		Log.i(TAG +"getVideoForHashtag: uri  " ,  uri);

		GsonRequest<VideoData> request = new GsonRequest<VideoData>(Method.GET
				, uri
				, VideoData.class
				, listener
				, errorListener);

		Log.v(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}

}
