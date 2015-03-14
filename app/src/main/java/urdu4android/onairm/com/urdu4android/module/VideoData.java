package urdu4android.onairm.com.urdu4android.module;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class VideoData {

    @SerializedName("statusCode")
    private int statusCode;

	@SerializedName("page")
	private int page;

    @SerializedName("count")
    private int count;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Video> videos = new ArrayList<Video>();



    public VideoData(){
    }
    public VideoData( int statusCode, int page, int pageSize, int count , String message,
			List<Video> videos) {
		super();
        this.statusCode = statusCode;
		this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.message = message;
		this.videos = videos;
	}

	public int getPage() {
		return page;
	}

    public int getCount() {
        return count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
