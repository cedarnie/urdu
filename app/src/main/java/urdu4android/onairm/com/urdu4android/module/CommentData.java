package urdu4android.onairm.com.urdu4android.module;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommentData {

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
    private List<Comment> comments = new ArrayList<Comment>();



    public CommentData(){
    }
    public CommentData(int statusCode, int page, int pageSize, int count, String message,
                       List<Comment> comments) {
		super();
        this.statusCode = statusCode;
		this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.message = message;
		this.comments = comments;
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

    public List<Comment> getVideos() {
        return comments;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
