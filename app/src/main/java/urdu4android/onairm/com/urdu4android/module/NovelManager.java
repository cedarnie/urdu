package urdu4android.onairm.com.urdu4android.module;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import urdu4android.onairm.com.urdu4android.Config;
import urdu4android.onairm.com.urdu4android.volley.GsonRequest;
import urdu4android.onairm.com.urdu4android.volley.RequestManager;

public class NovelManager {

	private final String TAG = getClass().getSimpleName();
	private static NovelManager mInstance;

	private static String request_url = Config.SERVER_URL+"/getNovels";

	private static String COMMENT_PAGE_NUM = "page";
	private static String COMMENT_PAGE_SIZE = "pageSize";
	public static int COMMENT_DEFAULT_PAGE_SIZE = 10;

	public static NovelManager getInstance(){
		if(mInstance == null) {
			mInstance = new NovelManager();
		}
		return mInstance;
	}

	public <T> void requestGetNovels(Listener<NovelData> listener, ErrorListener errorListener,  int pageNum){

		Uri.Builder uriBuilder = Uri.parse(request_url).buildUpon()
				.appendQueryParameter(COMMENT_PAGE_SIZE, "" + COMMENT_DEFAULT_PAGE_SIZE)
				.appendQueryParameter(COMMENT_PAGE_NUM, "" + pageNum);

		String uri = uriBuilder.build().toString();
		Log.i(TAG, "getVideoNovel: uri = " + uri);

		GsonRequest<NovelData> request = new GsonRequest<NovelData>(Method.GET
				, uri
				, NovelData.class
				, listener
				, errorListener);

		Log.i(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}

}
