package urdu4android.onairm.com.urdu4android;


import android.app.Application;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import urdu4android.onairm.com.urdu4android.volley.RequestManager;
import urdu4android.onairm.com.urdu4android.volley.images.ImageCacheManager;

/**
 * Example application for adding an L1 image cache to Volley. 
 * 
 * @author Trey Robinson
 *
 */
public class MainApplication extends Application {
	
	private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided
	
	@Override
	public void onCreate() {
		super.onCreate();

		init();
	}

	/**
	 * Intialize the request manager and the image cache 
	 */
	private void init() {
        Log.d("MainApplication", "init-nxs");
        RequestManager.init(this);
		createImageCache();
	}
	
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.  
	 */
	private void createImageCache(){
		ImageCacheManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, ImageCacheManager.CacheType.MEMORY);
	}
}