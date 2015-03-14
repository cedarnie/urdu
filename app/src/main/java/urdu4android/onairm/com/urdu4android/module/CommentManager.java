package urdu4android.onairm.com.urdu4android.module;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import urdu4android.onairm.com.urdu4android.Config;
import urdu4android.onairm.com.urdu4android.volley.GsonRequest;
import urdu4android.onairm.com.urdu4android.volley.RequestManager;

public class CommentManager {

	private final String TAG = getClass().getSimpleName();
	private static CommentManager mInstance;

	private static String request_url = Config.SERVER_URL+"/getNews";

	private static String COMMENT_PAGE_NUM = "page";
	private static String COMMENT_PAGE_SIZE = "pageSize";
	public static int COMMENT_DEFAULT_PAGE_SIZE = 10;

	public static CommentManager getInstance(){
		if(mInstance == null) {
			mInstance = new CommentManager();
		}
		return mInstance;
	}

	public <T> void requestGetComments(Listener<CommentData> listener, ErrorListener errorListener,  int pageNum){

		Uri.Builder uriBuilder = Uri.parse(request_url).buildUpon()
				.appendQueryParameter(COMMENT_PAGE_SIZE, "" + COMMENT_DEFAULT_PAGE_SIZE)
				.appendQueryParameter(COMMENT_PAGE_NUM, "" + pageNum);

		String uri = uriBuilder.build().toString();
		Log.i(TAG, "getVideoComment: uri = " + uri);

		GsonRequest<CommentData> request = new GsonRequest<CommentData>(Method.GET
				, uri
				, CommentData.class
				, listener
				, errorListener);

		Log.i(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}

}
